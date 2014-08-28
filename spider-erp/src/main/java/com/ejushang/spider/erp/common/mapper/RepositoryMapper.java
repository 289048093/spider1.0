package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Repository;
import com.ejushang.spider.query.RepositoryQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: tin
 * Date: 13-12-23
 * Time: 上午11:36
 */
public interface RepositoryMapper {
//   查询区

    /**
     * 查询所有仓库数据
     * @return 返回Repository类型的List
     */
    public List<Repository> findRepository();
    /**
     * 通过id查询仓库单条数据
     * @param id 仓库实体信息
     */

    public  Repository findRepositoryById(Integer id);

    /**
     * 通过仓库名称查询仓库
     * @param name
     * @return Repository对象
     */
    public  Repository findRepositoryByName(String name);

    /**
     * 通过repository查询仓库数据
     * @param repository 条件实体信息
     */
    public List<Repository> findRepositoryByRepositoryQuery(@Param("repository")RepositoryQuery repository,@Param("page")Page page);

    /**
     * 查询分页总条数
     * @param repository
     * @return
     */
    public int findRepositoryCountByRepositoryQuery(RepositoryQuery repository);
    /**
     * 通过name查询Repository是否有数据
     * @param
     * @return 返回查询的行数
     */
    public Integer findRepositoryCountByNameAndCode(Repository repository);


//========================================================
//   更新区
    /**
     *更新仓库
     * @param repository  实体仓库信息
     */
    public Integer updateRepository( Repository repository);
//=====================================================
//    删除区
    /**
     * 删除仓库
     * @param id 仓库id
     */
    public Integer deleteRepositoryById(Integer id);
//=======================================================
//    插入区

    /**
     * 插入仓库数据
     * @param repository 仓库实体信息
     */
    public Integer saveRepository(Repository repository);

//=========================================================

}
