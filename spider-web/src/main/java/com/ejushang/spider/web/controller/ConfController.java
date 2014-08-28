package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Conf;
import com.ejushang.spider.service.sysconfig.IConfService;
import com.ejushang.spider.web.util.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-26
 * Time: 上午11:55
 */
@Controller
@RequestMapping("/conf")
public class ConfController {
    @Autowired
    private IConfService confService;

    private static final Logger logger = Logger.getLogger(GiftBrandController.class);

    /**
     * 查找配置记录
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/list")
    public void findConfAll(HttpServletResponse response,Conf conf) throws IOException {
        if(logger.isInfoEnabled()){
            logger.info("ConfController中的findConfAll操作，参数conf="+conf.toString());
        }
        List<Conf> confs = confService.findConfAll(conf);
        new JsonResult(JsonResult.SUCCESS).setMsg("findConfAll查询成功").addData(JsonResult.RESULT_TYPE_LIST, confs).toJson(response);
    }

    /**
     * 根据id查找配置记录
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/findConfById")
    public void findConfById(HttpServletResponse response, Integer id) throws IOException {
        if(logger.isInfoEnabled()){
            logger.info("ConfController中的findConfById操作，参数id="+id.toString());
        }
        Conf conf = confService.findConfById(id);
        new JsonResult(JsonResult.SUCCESS).setMsg("findConfById查询成功").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, conf).toJson(response);
    }

    /**
     * 根据key查找配置记录
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/findConfByKey")
    public void findConfByKey(HttpServletResponse response, String configKey) throws IOException {
        if(logger.isInfoEnabled()){
            logger.info("ConfController中的findConfByKey操作，参数configKey="+configKey.toString());
        }
        Conf conf = confService.findConfByKey(configKey);
        new JsonResult(JsonResult.SUCCESS).setMsg("findConfByKey查询成功").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, conf).toJson(response);
    }

    /**
     * 配置新增
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/add")
    public void saveConf(HttpServletResponse response, Conf conf) throws IOException {
        if(logger.isInfoEnabled()){
            logger.info("ConfController中的saveConf操作，参数conf="+conf.toString());
        }
        int count = confService.saveConf(conf);  //新增Conf条数
        new JsonResult(JsonResult.SUCCESS).setMsg("增加成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }

    /**
     * 配置记录删除
     *
     * @param response
     * @param ids
     * @throws IOException
     */
    @RequestMapping("/delete")
    public void deleteConfById(HttpServletResponse response, int[] ids) throws IOException {
        if(logger.isInfoEnabled()){
            logger.info("ConfController中的deleteConfById操作，参数ids="+ids.toString());
        }
        Integer count = 0;
        for (int id1 : ids) {
            System.out.println(id1);
            confService.deleteConfById(id1); //删除条数
            count++;
        }
        new JsonResult(JsonResult.SUCCESS).setMsg("删除成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }

    /**
     * 根据id来修改value值
     *
     * @param response
     * @param conf
     * @throws IOException
     */
    @RequestMapping("/updateById")
    public void updateById(HttpServletResponse response, Conf conf) throws IOException {
        if(logger.isInfoEnabled()){
            logger.info("ConfController中的updateById操作，参数id="+conf.toString());
        }
        int count = confService.updateConfById(conf); //更新条数
        new JsonResult(JsonResult.SUCCESS).setMsg("修改成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }


}
