package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.domain.OriginalOrderItem;
import com.ejushang.spider.erp.common.mapper.OriginalOrderItemMapper;
import com.ejushang.spider.service.order.IOriginalOrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * User: Baron.Zhang
 * Date: 13-12-25
 * Time: 上午11:43
 */
@Service
public class OriginalOrderItemService implements IOriginalOrderItemService {

    private static final Logger log = LoggerFactory.getLogger(OriginalOrderItemService.class);

    @Resource
    private OriginalOrderItemMapper originalOrderItemMapper;

    /**
     * 原始订单项新增
     * @param originalOrderItem 订单项记录
     * @return
     */
    public int saveOriginalOrderItem(OriginalOrderItem originalOrderItem) {
        if(log.isInfoEnabled()){
            log.info("原始订单项新增："+originalOrderItem);
        }
        return originalOrderItemMapper.saveOriginalOrderItem(originalOrderItem);
    }

    /**
     * 原始订单项更新
     * @param originalOrderItem 订单项记录
     * @return
     */
    public int updateOriginalOrderItem(OriginalOrderItem originalOrderItem) {
        if(log.isInfoEnabled()){
            log.info("原始订单项更新："+originalOrderItem);
        }
        return originalOrderItemMapper.updateOriginalOrderItem(originalOrderItem);
    }

    /**
     * 原始订单项删除
     * @param originalOrderItem 订单项记录
     * @return
     */
    public int deleteOriginalOrderItem(OriginalOrderItem originalOrderItem) {
        if(log.isInfoEnabled()){
            log.info("原始订单项删除："+originalOrderItem);
        }
        return originalOrderItemMapper.deleteOriginalOrderItem(originalOrderItem);
    }
}
