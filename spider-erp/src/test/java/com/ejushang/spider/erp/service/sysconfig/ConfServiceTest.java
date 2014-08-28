package com.ejushang.spider.erp.service.sysconfig;

import com.ejushang.spider.domain.Conf;
import com.ejushang.spider.service.sysconfig.IConfService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * User: Blomer
 * Date: 13-12-23
 * Time: 下午1:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-erp.xml")
public class ConfServiceTest {
    @Autowired
    private IConfService confService;

    /**
     * 查找配置记录
     */
    @Test
    public void testFindConfAll(){
        Conf conf = new Conf();
        List<Conf> confs = confService.findConfAll(conf);

        assertThat(confs,notNullValue());
    }

    /**
     * 根据id查找配置记录
     */
    @Test
    public void testFindConfById(){
        Conf conf = confService.findConfById(15);

        assertThat(conf,notNullValue());
    }

    /**
     * 根据key查找配置记录
     */
    @Test
    public void testFindByKey(){
        Conf conf = confService.findConfByKey("benson");

        assertThat(conf,notNullValue());
    }
    /**
     * 配置新增
     */
    @Test
    public void testSaveConf() {
        Conf conf = new Conf();
        conf.setConfigKey("ben1");
        conf.setConfigValue("yjs");
        conf.setConfigComment("hello");
        int count = confService.saveConf(conf);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 配置记录删除
     */
    @Test
    public void testDeleteConfById(){
        int count = confService.deleteConfById(2);

        assertThat(count,is(equalTo(1)));
    }

    /**
     * 配置更新
     */
    @Test
    public void testUpdateById(){
        Conf conf = new Conf();
        conf.setId(34);
        conf.setConfigValue("sport");
        //conf.setConfigComment("hello");
        int count = confService.updateConfById(conf);
        System.out.println("修改成功");

        assertThat(count,is(equalTo(1)));
    }

}
