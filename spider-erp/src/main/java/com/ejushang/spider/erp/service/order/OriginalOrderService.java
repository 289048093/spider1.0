package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.domain.OriginalOrderItem;
import com.ejushang.spider.erp.common.mapper.OriginalOrderItemMapper;
import com.ejushang.spider.erp.common.mapper.OriginalOrderMapper;
import com.ejushang.spider.service.order.IOriginalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 13-12-25
 * Time: 上午11:43
 */
@Service
public class OriginalOrderService implements IOriginalOrderService {

    private static final Logger log = LoggerFactory.getLogger(OriginalOrderService.class);

    @Resource
    private OriginalOrderMapper originalOrderMapper;

    @Resource
    private OriginalOrderItemMapper originalOrderItemMapper;


    @Override
    public OriginalOrder get(Integer id) {
        return originalOrderMapper.get(id);
    }

    /**
     * 原始订单查询
     * @param originalOrder 查询条件
     * @return
     */
    public List<OriginalOrder> findOriginalOrders(Boolean processed) {
        return originalOrderMapper.findOriginalOrders(processed);
    }


    /**
     * 原始订单新增
     * @param originalOrder 订单记录
     * @return
     */
    public int saveOriginalOrder(OriginalOrder originalOrder) {
        if(log.isInfoEnabled()){
            log.info("原始订单新增："+originalOrder);
        }
        return originalOrderMapper.saveOriginalOrder(originalOrder);
    }

    /**
     * 原始订单更新
     * @param originalOrder 订单记录
     * @return
     */
    public int updateOriginalOrder(OriginalOrder originalOrder) {
        if(log.isInfoEnabled()){
            log.info("原始订单更新："+originalOrder);
        }
        return originalOrderMapper.updateOriginalOrder(originalOrder);
    }

    /**
     * 原始订单删除
     * @param originalOrder 订单记录
     * @return
     */
    public int deleteOriginalOrder(OriginalOrder originalOrder) {
        if(log.isInfoEnabled()){
            log.info("原始订单删除："+originalOrder);
        }
        return originalOrderMapper.deleteOriginalOrder(originalOrder);
    }

    /**
     * 保存原始订单及其订单项
     * @param originalOrder 原始订单
     * @param originalOrderItems 订单项集合
     */
    @Transactional
    public void saveOriginalOrderAndItem(OriginalOrder originalOrder, List<OriginalOrderItem> originalOrderItems){
        if(log.isInfoEnabled()){
            log.info("保存原始订单及其订单项："+originalOrder+""+originalOrderItems);
        }
        // 保存原始订单
        originalOrderMapper.saveOriginalOrder(originalOrder);
        // 保存原始订单项
        if(originalOrderItems != null){
            for(OriginalOrderItem originalOrderItem : originalOrderItems){
                originalOrderItem.setOrderId(originalOrder.getId());
                originalOrderItemMapper.saveOriginalOrderItem(originalOrderItem);
            }
        }
    }

    @Transactional
    @Override
    public int updateBatchProcessed(Integer[] ids) {
        //每次调用传入的数组长度最大不超过100
        int maxLength = 100;
        if(ids.length <= maxLength) {
            return originalOrderMapper.updateBatchProcessed(ids);
        }
        int count = 0;
        for(int i=0; i<=(ids.length / maxLength); i++) {
            int start = i * maxLength;
            int end = Math.min(start + maxLength, ids.length);
            count += originalOrderMapper.updateBatchProcessed(Arrays.copyOfRange(ids, start, end));
        }
        if(count != ids.length) {
            throw new RuntimeException(String.format("批量更新原始订单为已处理的时候出错,传进来的id数量[%d],修改成功的数量[%d]", ids.length, count));
        }
        return count;
    }
}
