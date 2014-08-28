package com.ejushang.spider.erp.service.sysconfig;

import com.ejushang.spider.domain.Conf;
import com.ejushang.spider.erp.common.mapper.ConfMapper;
import com.ejushang.spider.service.sysconfig.IConfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-6
 * Time: 上午10:37
 */
@Service
public class ConfService implements IConfService {

    private static final Logger log = LoggerFactory.getLogger(ConfService.class);

    @Resource
    private ConfMapper confMapper;

    /**
     * 查询所有记录
     *
     * @param conf
     * @return conf记录的集合
     */
    @Transactional(readOnly = true)
    @Override
    public List<Conf> findConfAll(Conf conf) {
        if (log.isInfoEnabled()) {
            log.info("ConfService中的findConfAll操作，参数conf="+conf.toString());
        }
        return confMapper.findConfAll(conf);
    }

    /**
     * 根据id来查询记录
     *
     * @param id
     * @return 单条conf记录
     */
    @Transactional(readOnly = true)
    @Override
    public Conf findConfById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("ConfService中的findConfById操作，参数id="+id.toString());
        }
        return confMapper.findConfById(id);
    }

    /**
     * 根据key来查询记录
     *
     * @param configKey
     * @return 单条conf记录
     */
    @Transactional(readOnly = true)
    @Override
    public Conf findConfByKey(String configKey) {
        if (log.isInfoEnabled()) {
            log.info("ConfService中的findConfByKey操作，参数configKey="+configKey);
        }
        return confMapper.findConfByKey(configKey);
    }

    /**
     * 根据key来查询记录
     *
     * @param configKey
     * @return 单条conf记录
     */
    @Override
    public String findConfValue(String configKey) {
        Conf conf = this.findConfByKey(configKey);
        if(conf == null) return null;
        return conf.getConfigValue();
    }

    @Override
    public Integer findConfIntegerValue(String configKey) {
        Integer result = null;
        Conf conf = this.findConfByKey(configKey);
        if(conf == null) return result;
        try {
            result = Integer.parseInt(conf.getConfigValue());
        } catch (NumberFormatException e) {
            log.error("配置项的value在转成Integer的时候报错,key[{}],value[{}]", configKey, conf.getConfigValue());
        }
        return result;
    }

    /**
     * 插入一条记录
     *
     * @param conf
     * @return 新增记录的条数
     */
    @Transactional
    @Override
    public Integer saveConf(Conf conf) {
        if (log.isInfoEnabled()) {
            log.info("ConfService中的saveConf操作，参数conf="+conf.toString());
        }
        Conf conf1 = confMapper.findConfByKey(conf.getConfigKey());
        if (conf1 == null) {
            return confMapper.saveConf(conf);
        } else {
            return 0;
        }
    }

    /**
     * 删除一条记录
     *
     * @param id
     * @return 删除记录的条数
     */
    @Transactional
    @Override
    public Integer deleteConfById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("ConfService中的deleteConfById操作，参数id="+id.toString());
        }
        return confMapper.deleteConfById(id);
    }

    /**
     * 根据id修改记录
     *
     * @param conf
     * @return 更新conf记录的条数
     */
    @Transactional
    @Override
    public Integer updateConfById(Conf conf) {
        if (log.isInfoEnabled()) {
            log.info("ConfService中的updateConfById操作，参数conf="+conf.toString());
        }
        return confMapper.updateConfById(conf);
    }

    /**
     * 根据id修改记录
     *
     * @param conf
     * @return 更新conf记录的条数
     */
    @Transactional
    @Override
    public Integer updateValueByKey(Conf conf) {
        if (log.isInfoEnabled()) {
            log.info("ConfService中的updateConfById操作，参数conf="+conf.toString());
        }
        return confMapper.updateValueByKey(conf);
    }

}
