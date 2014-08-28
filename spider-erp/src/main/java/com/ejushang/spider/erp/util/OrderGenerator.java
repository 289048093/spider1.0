package com.ejushang.spider.erp.util;

import com.ejushang.spider.domain.Conf;
import com.ejushang.spider.service.sysconfig.IConfService;
import com.ejushang.spider.util.DateUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * User:Amos.zhou
 * Date: 13-12-25
 * Time: 上午11:09
 */
public class OrderGenerator {

    /**
     * 生成订单号
     * @return
     */
    public static String getOrderNo() {
        //得到当前的时间，格式是yymmdd
        String currDate = DateUtils.formatDate(DateUtils.getCurrentDate(), DateUtils.DateFormatType.SIMPLE_DATE_FORMAT);
        //得到数据库中的自增的值
        Integer value = getIncrementNo();
         //得到3位随机数
        String randomStr = RandomStringUtils.random(3,SystemConfConstant.ALL_NUMBER);
        return currDate + value + randomStr;
    }

    /**
     * 得到数据库中的自增的值
     * @return
     */
    private static Integer getIncrementNo() {
        IConfService configService = (IConfService) ApplicationContextUtils.getBean("confService");
        Conf conf = configService.findConfByKey(SystemConfConstant.NEXT_ORDER_NO);
        //如果忘记初始化配置表中订单号的配置
        if(conf == null){
            conf = new Conf();
            conf.setConfigKey(SystemConfConstant.NEXT_ORDER_NO);
            conf.setConfigValue(String.valueOf(SystemConfConstant.MIN_ORDER_NO));
            conf.setConfigComment("下一个订单号");
            configService.saveConf(conf);
        }
        String confValue = conf.getConfigValue();
        if(confValue.length() != 5){
            throw new IllegalStateException("配置表中没有订单号必须是5位起始数！");
        }
        Integer value = Integer.parseInt(confValue);
        value++;
        //每轮从11111--99999,达到99999以后又从11111开始 ，构成一个环
        if(value>SystemConfConstant.MAX_ORDER_NO){
            value = SystemConfConstant.MIN_ORDER_NO;
        }

        //更新配置表中订单的值，先更新，再使用，可以避免先使用，然后更新失败，导致重复值的情况
        conf.setConfigValue(String.valueOf(value));
        configService.updateValueByKey(conf) ;
        return value;
    }


}
