package com.tiger.sell.utils;

import java.util.Random;

public class KeyGenerateUtil {
    /**
     * 主订单ID生成策略：a+系统日期+6位随机数
     * @return
     */
    public static synchronized String generateOrderMasterID(){
        Random random = new Random();
        Integer sixNum = random.nextInt(900000)+100000;
        return "a"+System.currentTimeMillis()+sixNum.toString();
    }

    /**
     * 订单详情ID生成策略：b+系统日期+6位随机数
     * @return
     */
    public static synchronized String generateOrderDetailID(){
        Random random = new Random();
        Integer sixNum = random.nextInt(900000)+100000;
        return "b"+System.currentTimeMillis()+sixNum.toString();
    }
}
