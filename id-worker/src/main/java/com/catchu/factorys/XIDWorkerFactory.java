package com.catchu.factorys;

import com.catchu.XSnowFlakeIDWorker;
import lombok.Data;

/**
 * ID生成器工厂
 */
@Data
public class XIDWorkerFactory {

    private XSnowFlakeIDWorker xSnowFlakeIDWorker;

    private XIDWorkerFactory() {
        xSnowFlakeIDWorker = new XSnowFlakeIDWorker();
    }

    private static class Assistant {
        final static XIDWorkerFactory factory = new XIDWorkerFactory();
    }

    public static XIDWorkerFactory getInstance() {
        return Assistant.factory;
    }
}
