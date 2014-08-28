package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Delivery;
import com.ejushang.spider.query.DeliveryQuery;

import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午11:17
 */
public interface DeliveryMapper {


    /**
     * 保存一个物流设计的信息
     *
     * @param delivery 物流信息实体
     */
    public int saveDelivery(Delivery delivery);


    /**
     * 通过所有的条件查询物流信息
     *
     * @return 物流信息集合
     */
    public List<Delivery> findDeliveryAll(DeliveryQuery deliveryQuery);

    /**
     * 修改物流
     *
     * @param delivery 物流实体
     */
    public int updateDelivery(Delivery delivery);

    /**
     * 删除物流信息
     *
     * @param id 要删除物流信息的ID
     */
    public int deleteDelivery(Integer id);

    /**
     * 通过ID获得物流对象
     *
     * @param id
     * @return
     */
    public Delivery findDeliveryById(int id);

    /**
     * 通过name获得物流对象集合
     *
     * @param name
     * @return
     */
    public List<Delivery> findDeliveryByNames(String name);

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
     * @return Delivery数组
     */
    public List<Delivery> findByName(String name);
}
