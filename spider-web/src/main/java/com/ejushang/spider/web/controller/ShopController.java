package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.query.ShopQuery;
import com.ejushang.spider.service.shop.IShopService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.ShopVo;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 店铺管理控制类
 * User: Baron.Zhang
 * Date: 14-1-7
 * Time: 下午1:30
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    private static final Logger log = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private IShopService shopService;

    /**
     * 店铺一览
     * @param shopQuery
     * @param response
     */
    @RequestMapping("/list")
    public void listShop(ShopQuery shopQuery,HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(log.isInfoEnabled()){
            log.info("店铺：查询店铺一览，" + shopQuery);
        }
        // 查询店铺一览
        Page page = shopService.findShopPageByQuery(shopQuery);

        new JsonResult(JsonResult.SUCCESS,"查询店铺一览成功！")
                .addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT,page)
                .toJson(response);
        return;
    }


    /**
     * 店铺明细
     * @param id
     * @param response
     */
    @RequestMapping("/detail")
    public void detailShop(Integer id,HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(log.isInfoEnabled()){
            log.info("店铺：查询店铺明细，id = " + id);
        }
        // 查询店铺明细
        ShopVo shopVo = shopService.findShopById(id);

        new JsonResult(JsonResult.SUCCESS,"店铺明细查询成功！")
                .addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT,shopVo)
                .toJson(response);
        return;
    }

    /**
     * 店铺更新
     * @param shop
     * @param sessionKey
     * @param response
     */
    @RequestMapping("/update")
    public void updateShop(Shop shop,String sessionKey,HttpServletResponse response) throws Exception {
        if(log.isInfoEnabled()){
            log.info("店铺：更新店铺，" + shop);
        }
        // 更新店铺
        shopService.updateShop(shop,sessionKey);
        new JsonResult(JsonResult.SUCCESS,"更新成功！").toJson(response);
        return;
    }

    /**
     * 店铺删除
     * @param shopId
     * @param response
     */
    @RequestMapping("/delete")
    public void deleteShop(String shopId,HttpServletResponse response) throws IOException {
        if(log.isInfoEnabled()){
            log.info("店铺：删除店铺，shopId = " + shopId);
        }
        // 删除店铺
        shopService.deleteShop(shopId);
        new JsonResult(JsonResult.SUCCESS,"删除成功！").toJson(response);
        return;
    }

    /**
     * 动态获取评分
     * @param id
     * @param response
     */
    @RequestMapping("/dynamicGetScore")
    public void dynamicGetScore(Integer id,HttpServletResponse response) throws Exception {
        if(log.isInfoEnabled()){
            log.info("店铺：动态获取评分，id = " + id);
        }
        // 动态获取评分
        ShopVo shopVo = shopService.dynamicGetScore(id);

        new JsonResult(JsonResult.SUCCESS,"动态获取评分成功！")
                .addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT,shopVo)
                .toJson(response);
        return;
    }
}
