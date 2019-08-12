package com.catchu.util;

import com.catchu.enums.OrderTypeEnum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 订单号生成工具列，生成20位数字编码
 * 生成规则：单号类型1位 + 15位时间戳 + 用户id后4位 + 随机数4位
 * @author junzhongliu
 * @date 2018/9/30 11:52
 */
public class OrderNoUtil {
    /**
     * 取商品id长度的后三位，不足三位的前面补0至三位
     */
    private static final int GOODS_ID_LENGTH = 3;

    /**
     * 取用户id长度的后2位，不足2位的前面补0至2位
     */
    private static final int USER_ID_LENGTH = 2;

    /**
     * 取时间戳后8位
     */
    private static final int TIMESTAMP_LENGTH = 8;

    /**
     * 生成订单单号编码
     * @param userId
     */
    public static String getOrderCode(Long userId,Integer orderType){
        return OrderTypeEnum.findChar(orderType) + getCode(userId);
    }

    /**
     * 取用户id后四位
     * @param userId
     * @return
     */
    public static String getUserIdCode(Long userId){
        if(Objects.isNull(userId)){
            userId = 10L;
        }
        String userIdStr = String.valueOf(userId);
        if(userIdStr.length() <= USER_ID_LENGTH){
            userIdStr =String.format("%04d", userId);
        }else{
            userIdStr = userIdStr.substring(userIdStr.length()-USER_ID_LENGTH,userIdStr.length());
        }
        return userIdStr;
    }

    public static String getCode(Long userId){
        return getTimeStamp()+getUserIdCode(userId)+getRandomCode();
    }

    /**
     * 生成时间戳，yyyyMMddHHmmssss格式，如：201810092011026
     * @return
     */
    private static String getTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
        return sdf.format(new Date());
    }

    /**
     * 生成四位随机数
     * @return
     */
    public static String getRandomCode(){
        return String.valueOf(String.format("%04d", (int) (Math.random() * 9999)));
    }

    /******************下面的方法暂时没用到***********************/
    /**
     * 取商品id的后四位
     * @param goodsId
     * @return
     */
    public static String getGoodsIdCode(Long goodsId){
        if(Objects.isNull(goodsId)){
            goodsId = 100L;
        }
        String goodIdStr = String.valueOf(goodsId);
        if(goodIdStr.length() <= GOODS_ID_LENGTH){
            goodIdStr =String.format("%03d", goodsId);
        }else{
            goodIdStr = goodIdStr.substring(goodIdStr.length()-GOODS_ID_LENGTH,goodIdStr.length());
        }
        return goodIdStr;
    }

    /**
     * 取时间戳后8位
     * @return
     */
    private static String getDateTime(){
        String dateStr = String.valueOf(System.currentTimeMillis());
        return dateStr.substring(dateStr.length() - TIMESTAMP_LENGTH,dateStr.length());
    }

    public static void main(String[] args) {
        for(int i=0;i<10000;i++){
            System.out.println(getOrderCode(23L,1));
        }
        System.out.println("E20190703123804800231083".length());
    }
}
