package com.ejushang.spider.taobao.helper;

import com.ejushang.spider.util.Money;

import java.util.Date;

/**
 * User: Baron.Zhang
 * Date: 13-12-13
 * Time: 下午5:50
 */
public class ConvertUtils {
    /**
     * 将Long对象转换为String对象
     * @param l
     * @return
     */
    public static String convertLong2String(Long l){
        return l == null ? null : String.valueOf(l);
    }

    /**
     * 将以元为单位的货币String转换为以分为单位的货币Long
     * @param y
     * @return
     */
    public static Long convertYuanToCent(String y){
        return y == null || y == "" ? null : Money.YuanToCent(y);
    }


}
