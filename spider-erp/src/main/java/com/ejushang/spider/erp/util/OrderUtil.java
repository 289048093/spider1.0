package com.ejushang.spider.erp.util;

import com.ejushang.spider.constant.OrderSplitStatus;
import com.ejushang.spider.domain.Order;
import com.ejushang.spider.exception.ErpBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: liubin
 * Date: 14-4-12
 */
public class OrderUtil {

    private static final Logger log = LoggerFactory.getLogger(OrderUtil.class);

    public static void checkSplitStatus(Order order, String operationName) {
        if(order == null) {
            log.error("检查拆分状态的时候发现订单为null");
            throw new ErpBusinessException("没有找到对应的订单信息");
        }
        if(order.getSplitStatus() == null || OrderSplitStatus.WAIT_SPLIT.toString().equals(order.getSplitStatus())
                || order.getRepoId() == null || order.getRepoId() == 0) {
            throw new ErpBusinessException(String.format("订单[%s]不能进行%s操作,请先对该订单进行手动拆单", order.getOrderNo(), operationName == null ? "" : operationName));
        }

    }


}
