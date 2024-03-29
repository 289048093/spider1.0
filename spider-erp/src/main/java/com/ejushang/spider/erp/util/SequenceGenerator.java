package com.ejushang.spider.erp.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改人：Amos.zhou
 * 修改时间:2013-12-26
 * 从生成订单做成通用的
 *
 * User: Json.zhu
 * Date: 13-12-25
 * Time: 下午4:29
 *
 */
public class SequenceGenerator {
    private static  volatile SequenceGenerator generator = new SequenceGenerator();
    private static Map<String,KeyInfo> keyList = new HashMap<String,KeyInfo>();

    static {
        keyList.put(SystemConfConstant.NEXT_ORDER_NO,new OrderKeyInfo(SystemConfConstant.NEXT_ORDER_NO,200));
        keyList.put(SystemConfConstant.NEXT_MEAL_SET_NO,new MealSetKeyInfo(SystemConfConstant.NEXT_MEAL_SET_NO,5));
    }

    private SequenceGenerator() {
    };

    /**工厂方法*/
    public static SequenceGenerator getInstance() {
        return generator;
    }

    /** 获取下一个value*/
    private   String getNextKey(String keyName) {
        KeyInfo key = keyList.get(keyName);
        if(key==null){
           throw new IllegalArgumentException("不存在配置项相关的序列键生成信息");
        }
        return key.getNextNo();
    }

    /**
     * 获取下一个订单编号
     * @return
     */
    public String getNextOrderNo(){
        return getNextKey(SystemConfConstant.NEXT_ORDER_NO);
    }

    /**
     * 获取下一个套餐编号
     * @return
     */
    public String getNextMealSetNo(){
        return getNextKey(SystemConfConstant.NEXT_MEAL_SET_NO);
    }
}
