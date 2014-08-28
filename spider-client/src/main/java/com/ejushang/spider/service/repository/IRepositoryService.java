package com.ejushang.spider.service.repository;

import com.ejushang.spider.domain.Repository;
import com.ejushang.spider.query.RepositoryQuery;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.RepositoryVo;

import java.util.HashMap;
import java.util.List;

/**
 * User: tin
 * Date: 13-12-23
 * Time: 上午11:31
 */
public interface IRepositoryService {
//   查询区

    /**
     * 查询所有仓库数据
     *
     * @return 返回所有仓库所有数据
     */
    public List<Repository> findRepository();
    /**
     * 查询所有仓库数据
     *
     * @return 返回所有仓库所有数据
     */
    public List<RepositoryVo> findRepositoryAll();


    /**
     * 通过id查询仓库单条数据
     *
     * @param id 仓库实体信息
     */
    public Repository findRepositoryById(Integer id);
    /**
     * 通过id查询仓库单条数据
     *
     * @param name 仓库名
     */
    public Repository findRepositoryByName(String name);
    /**
     * 通过Repository查询仓库数据
     *
     * @param repository 条件仓库实体信息
     */
    public Page findRepositoryByRepository(RepositoryQuery repository);
    /**
     * 通过prodId查询仓库数据
     *
     * @param  prodId 条件仓库实体信息
     */
    public Repository findRepositoryByProdId(Integer prodId);
//=============================================================
//    更新区

    /**
     * 更新仓库
     *
     * @param repository 实体仓库信息
     */
    public Integer updateRepository(Repository repository);
//==============================================================
//    删除区

    /**
     * 删除仓库
     *
     * @param ids 仓库id数组
     */
    public Integer deleteRepositoryById(Integer[] ids);
//===============================================================
//    插入区

    /**
     * 插入仓库数据
     *
     * @param repository 仓库实体信息
     */
    public Integer saveRepository(Repository repository);
//  =========================================================

}
