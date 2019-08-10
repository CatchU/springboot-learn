package com.catchu;


import com.catchu.builders.XIDWorkerConfigurationBuilder;

/**
 * XSNOWFLAKE ID生成器
 */
public class XSnowFlakeIDWorker {

    /**
     * 开始时间截 (2018-12-10)
     */
    private final long twepoch = 1544371200L;

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final long dbIdBits = 9L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是511
     */
    private final long maxDBId = -1L ^ (-1L << dbIdBits);

    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 8L;

    /**
     * 机器ID向左移17位
     */
    private final long workerIdShift = dbIdBits + sequenceBits;

    /**
     * 数据标识id向左移8位
     */
    private final long dbIdShift = sequenceBits;

    /**
     * 时间截向左移22位(8+5+9)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dbIdBits;

    /**
     * 生成序列的掩码，这里为255
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~31)
     */
    private final long workerId = XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getServerId();

    /**
     * 支持的最大DB个数
     */
    private final int maxDBCount = 512;

    /**
     * 生成最大DB个数的掩码，这里为511
     */
    private final long maxDBCountMask = -1L ^ (-1L << dbIdBits);

    /**
     * 毫秒内序列(0~255)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    public XSnowFlakeIDWorker() {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
    }

    public synchronized long nextId(long routingKey) {
        long dbId = routingKey % maxDBCount;
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (workerId << workerIdShift) //
                | (dbId << dbIdShift) //
                | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 从ID中解析DB索引
     *
     * @param id
     * @return
     */
    public long getDBIndexFromID(long id, int currentDBCount) {
        return ((id >> 8) & maxDBCountMask) % currentDBCount;
    }
}
