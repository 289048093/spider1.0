package com.ejushang.spider.service.delivery;

/**
 * User: 龙清华
 * Date: 13-12-24
 * Time: 上午10:10
 */

import com.ejushang.spider.domain.Delivery;
import com.ejushang.spider.query.DeliveryQuery;
import com.ejushang.spider.vo.DeliveryVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:35
 * <p/>
 * 物流设计service类
 * <p/>
 * insertDelivery()
 * deleteDelivery()
 * queryDelivery()
 */

public interface IDeliveryService {
    /**
     * 保存物流信息
     *
     * @param delivery 物流信息实体
     * @return 插入的条数
     */
    public int saveDelivery(Delivery delivery);


    /**
     * 通过已有的条件获得所有物流技术信息
     */
    public List<DeliveryVo> findDeliveryAll(DeliveryQuery delivery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 修改物流信息
     *
     * @param delivery 物流信息实体
     * @return 修改的条数
     */
    public int updateDelivery(Delivery delivery);

    /**
     * 删除物流信息 通过ID
     *
     * @param idArray 物流id数组
     * @return 删除的条数
     */
    public int deleteDelivery(int[] idArray);
    /**
     * 通过ID获得物流对象
     *
     * @param id
     * @return
     */
    public Delivery findDeliveryById(int id);

    /**
     * 通过name获得物流对象
     *
     * @param name
     * @return
     */
    public Delivery findDeliveryByName(String name);

    /**
     * 通过name获得物流对象
     *
     * @param name
     * @return  Delivery
     */
    public Delivery findByName(String name);
}

