package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Storage;
import com.ejushang.spider.query.StorageQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: tin
 * Date: 13-12-23
 * Time: 下午2:03
 */
public interface StorageMapper {
//   查询区

    /**
     * 查询出所有Storage的数据
     *
     * @return 返回一个Storage类型的List
     */
    public List<Storage> findStorage();

    /**
     * 查询出以Storage作为条件的数据
     *
     * @param storage storageQuery类
     * @return 返回一个Storage类型的List
     */
    public List<Storage> findStorageByStorage(StorageQuery storage);


    /**
     * 分页查询库存
     * @param storage
     * @return Storage类型的List
     */
    public List<Storage> findJoinStorageByPage(@Param("storage")StorageQuery storage, @Param("page")Page page);
    /**
     * 通过id查询库存信息
     *
     * @param id 库存id
     * @return 返回Storage类型
     */
    public Storage findStorageById(Integer id);

    /**
     * 根据仓库ID查询库存
     * @param repoId
     * @return
     */
    public List<Storage> findStorageByRepoId(Integer repoId);
    /**
     * 通过prodId查询库存
     *
     * @param prodId 商品I
     * @return  返回影响行数
     */
    public Integer findStorageCountByProdId(Integer prodId);
    /**
     * 通过prodId查询库存
     *
     * @param prodId 商品I
     * @return  返回影响Storage对象
     */
     public Storage findStorageByProdId(Integer prodId);

//===============================================================
//    更新区

    /**
     * 更新库存
     *
     * @param storage 实体库存信息
     */
    public Integer updateStorage(Storage storage);
//    ============================================================
//    删除区

    /**
     * 删除库存
     *
     * @param id 库存id
     */
    public Integer deleteStorageById(Integer id);
//    ============================================================
    /**
     * 删除库存通过商品ID
     *
     * @param prodId 库存id
     */
    public Integer deleteStorageByProdId(Integer prodId);
    /**
     * 删除库存通过仓库ID
     *
     * @param repoId 仓库id
     */
    public Integer deleteStorageByRepoId(Integer repoId);
//    ==============================================================

//    插入区

    /**
     * 插入库存数据
     *
     * @param storage 实体库存信息
     */
    public Integer saveStorage(Storage storage);
//=================================================

}
