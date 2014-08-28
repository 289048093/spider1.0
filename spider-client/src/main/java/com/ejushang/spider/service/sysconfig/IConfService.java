package com.ejushang.spider.service.sysconfig;

import com.ejushang.spider.domain.Conf;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-23
 * Time: 上午11:43
 */
public interface IConfService {

    /**
     *  插入一条记录
     * @param conf
     * @return 新增记录的条数
     */
    public Integer saveConf(Conf conf);
    /**
     * 删除一条记录
     * @param id
     * @return 删除记录的条数
     */
    public Integer deleteConfById(Integer id);
    /**
     * 查询所有记录
     * @param conf
     * @return conf记录的集合
     */
    public List<Conf> findConfAll(Conf conf);
    /**
     * 根据id来查询记录
     * @param id
     * @return 单条conf记录
     */
    public Conf findConfById(Integer id);
    /**
     * 根据key来查询记录
     * @param configKey
     * @return 单条conf记录
     */
    public Conf findConfByKey(String configKey);
    /**
     * 根据key来查询记录
     * @param configKey
     * @return conf的值
     */
    public String findConfValue(String configKey);
    /**
     * 根据key来查询记录
     * @param configKey
     * @return conf的值,可能返回null
     */
    public Integer findConfIntegerValue(String configKey);
    /**
     * 根据id修改记录
     * @param conf
     * @return 更新conf记录的条数
     */
    public Integer updateConfById(Conf conf);

    /**
     * 根据key来修改value值
     * @param conf
     * @return 更新记录的条数
     */
    public Integer updateValueByKey(Conf conf);


}
