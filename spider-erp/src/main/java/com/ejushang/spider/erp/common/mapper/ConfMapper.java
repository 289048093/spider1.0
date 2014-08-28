package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Conf;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-23
 * Time: 上午10:34
 */

public interface ConfMapper {

    /**
     * 插入一条记录
     *
     * @param conf
     * @return 新增Conf记录条数
     */
    public Integer saveConf(Conf conf);

    /**
     * 删除一条记录
     *
     * @param id
     * @return 删除Conf记录条数
     */
    public Integer deleteConfById(Integer id);

    /**
     * 查询所有记录
     *
     * @param conf
     * @return Conf记录条数
     */
    public List<Conf> findConfAll(Conf conf);

    /**
     * 根据id来查询记录
     *
     * @param id
     * @return Conf记录条数
     */
    public Conf findConfById(Integer id);

    /**
     * 根据key来查询记录
     *
     * @param configKey
     * @return Conf记录条数
     */
    public Conf findConfByKey(String configKey);

    /**
     * 根据id修改记录
     *
     * @param conf
     * @return 更新Conf记录条数
     */
    public Integer updateConfById(Conf conf);

    /**
     * 根据key来修改value值
     *
     * @param conf
     * @return 更新Conf记录条数
     */
    public Integer updateValueByKey(Conf conf);
}
