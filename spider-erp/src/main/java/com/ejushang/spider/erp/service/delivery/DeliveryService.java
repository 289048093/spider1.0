package com.ejushang.spider.erp.service.delivery;

import com.ejushang.spider.constant.DeliveryType;
import com.ejushang.spider.domain.Delivery;
import com.ejushang.spider.erp.common.mapper.DeliveryMapper;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.query.DeliveryQuery;
import com.ejushang.spider.service.delivery.IDeliveryService;
import com.ejushang.spider.vo.DeliveryVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:35
 * <p/>
 * 物流设计service类
 * <p/>
 */
@Transactional
@Service
public class DeliveryService implements IDeliveryService {
    private static final Logger log = LoggerFactory.getLogger(DeliveryService.class);

    @Resource
    DeliveryMapper deliveryMapper;

    /**
     * 保存物流信息
     *
     * @param delivery 物流信息实体
     */
    public int saveDelivery(Delivery delivery) {
        if (log.isInfoEnabled()) {
            log.info("保存物流信息：delivery" + delivery);
        }
        List<Delivery> tempList = deliveryMapper.findDeliveryByNames(delivery.getName());
        if (this.isExist(tempList, delivery)) {
            return -1;
        }
        return deliveryMapper.saveDelivery(delivery);
    }

    /**
     * 获得所有物流技术信息
     */
    @Transactional(readOnly = true)
    public List<DeliveryVo> findDeliveryAll(DeliveryQuery deliveryQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("用于查询的：deliveryQuery" + deliveryQuery);
        }
        if (deliveryQuery != null) {
            if (log.isInfoEnabled()) {
                log.info("用于查询的传来的名字是否有空格：deliveryQuery.getName()" + deliveryQuery.getName());
            }
            if (deliveryQuery.getName() != null) {
                deliveryQuery.setName(deliveryQuery.getName().trim());
            }
        }
        List<Delivery> deliveryList = deliveryMapper.findDeliveryAll(deliveryQuery);
        if (log.isInfoEnabled()) {
            log.info("用于查询的传来的名字是否有空格：deliveryQuery.getName()" + deliveryQuery.getName());
            log.info("查询的原始结果" + deliveryList);
        }
        List<DeliveryVo> deliveryVoList = new ArrayList<DeliveryVo>();
        DeliveryVo deliveryVo = null;
        for (int i = 0; i < deliveryList.size(); i++) {
            deliveryVo = new DeliveryVo();
            PropertyUtils.copyProperties(deliveryVo, deliveryList.get(i));
            deliveryVoList.add(deliveryVo);
        }
        if (log.isInfoEnabled()) {
            log.info("查询结果 deliveryVo" + deliveryVo);
        }
        return deliveryVoList;
    }

    public boolean isExist(List<Delivery> tempList, Delivery delivery) {
        for (Delivery temp : tempList) {
            //判断物流信息是否存在，不包括自己
            if (temp != null && !temp.getId().equals(delivery.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 修改物流信息
     *
     * @param delivery 物流信息实体
     */
    public int updateDelivery(Delivery delivery) {
        if (log.isInfoEnabled()) {
            log.info("修改的信息：delivery" + delivery);
        }
        List<Delivery> tempList = deliveryMapper.findDeliveryByNames(delivery.getName());
        if (this.isExist(tempList, delivery)) {
            return -1;
        }
        return deliveryMapper.updateDelivery(delivery);
    }

    /**
     * 删除物流信息 通过ID
     *
     * @param idArray 物流id
     */
    public int deleteDelivery(int[] idArray) {
        for (int id : idArray) {
            if (log.isInfoEnabled()) {
                log.info("删除的ID：ID" + id);
            }
            deliveryMapper.deleteDelivery(id);
        }
        return idArray.length;
    }

    @Override
    public Delivery findDeliveryById(int id) {
        if (log.isInfoEnabled()) {
            log.info("通过id进行查询：id" + id);
        }
        return deliveryMapper.findDeliveryById(id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Delivery findDeliveryByName(String name) {
        if (log.isInfoEnabled()) {
            log.info("通过name进行查询：name" + name);
        }
        return deliveryMapper.findDeliveryByName(name);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Delivery findByName(String name) {
        if (log.isInfoEnabled()) {
            log.info("通过name进行查询：name" + name);
        }
        List<Delivery> deliveryList = deliveryMapper.findByName(name);
        if (deliveryList.isEmpty()) {
            String msg = String.format("名字为%s的物流公司在数据库中没有不存在，请联系管理员", DeliveryType.valueOf(name));
            log.info(msg);
            throw new ErpBusinessException(msg);
        }
        return deliveryList.get(0);
    }
}

