package com.ejushang.spider.service.repository;

import com.ejushang.spider.constant.StorageQuantityType;
import com.ejushang.spider.domain.Storage;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.query.StorageQuery;
import com.ejushang.spider.util.Page;

import java.util.List;

/**
 * User: tin
 * Date: 13-12-23
 * Time: 下午1:59
 */
public interface IStorageService {
//   插入区

    /**
     * 查询出所有Storage的数据
     *
     *
     * @return 返回一个Storage类型的List
     */
    public List<Storage> findStorage();

    /**
     * 分页查询
     *
     * @param storageQuery
     * @return 返回一个StorageQuery实体
     */
    public Page findJoinStorage(StorageQuery storageQuery);


    /**
     * 通过prodId查询库存
     *
     * @param prodId 商品I
     * @return 返回影响行数
     */
    public Integer findStorageCountByProdId(Integer prodId);

    /**
     * 通过prodId查询库存
     *
     * @param prodId 商品I
     * @return 返回Storage对象
     */
    public Storage findStorageByProdId(Integer prodId);


//    =========================================================

    /**
     * 更新库存
     *
     * @param storage 需要更改的库存对象, 需要有 id 属性
     */
    public Integer updateStorage(Storage storage);

    /**
     * 删除库存
     *
     * @param id 库存id
     */
    public Integer deleteStorageById(Integer id);

    /**
     * 插入库存数据
     *
     * @param storage 实体库存信息
     */
    public Integer saveStorage(Storage storage);

    /**
     * 通过id查询库存信息
     *
     * @param id 库存id
     * @return 返回Storage类型
     */
    public Storage findStorageById(Integer id);

    /**
     * 操作库存数量
     *
     * @param productId
     * @param quantity
     * @return
     */
    boolean manipulateStorage(Integer productId, Integer quantity);
}
