package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.GiftBrand;
import com.ejushang.spider.domain.GiftBrandItem;
import com.ejushang.spider.query.GiftBrandQuery;
import com.ejushang.spider.service.gift.IGiftBrandService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.web.util.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Blomer
 * Date : 13-12-26
 * Time: 下午3:26
 */
@Controller
@RequestMapping("/giftBrand")
public class GiftBrandController {
    private static final Logger logger = Logger.getLogger(GiftBrandController.class);

    @Autowired
    private IGiftBrandService giftBrandService;


    /**
     * 规则-品牌 查询
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/list")
    public void findAll(HttpServletResponse response,GiftBrandQuery giftBrandQuery) throws IOException {
        if(logger.isInfoEnabled()){
            logger.info(String.format("GiftBrandService中的list方法，参数giftBrandQuery:%s",giftBrandQuery.toString()));
        }
        Page page = giftBrandService.findByPage(giftBrandQuery);
        if (page != null) {
            new JsonResult(JsonResult.SUCCESS).setMsg("查询成功").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
        } else {
            new JsonResult(JsonResult.SUCCESS).setMsg("数据库没有记录");
        }
    }

    /**
     * 根据id 规则-品牌查询
     *
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping("/detail")
    public void findGiftBrandById(HttpServletResponse response, Integer id) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("GiftBrandController中的findGiftBrandById操作，参数id=" + id.toString());
        }
        GiftBrand giftBrand = giftBrandService.findGiftBrandById(id);
        new JsonResult(JsonResult.SUCCESS).setMsg("findGiftBrandById查询结果").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, giftBrand).toJson(response);
    }

    /**
     * 规则-品牌新增
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/add")
    public void saveGiftBrand(HttpServletResponse response, GiftBrand giftBrand,Integer[] giftProdId,Integer[] giftProdCount) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("GiftBrandController中的saveGiftBrand操作，参数giftBrand=" + giftBrand.toString());
        }
        JsonResult json = null;

        int count = giftBrandService.saveGiftBrand(this.load(giftBrand, giftProdId, giftProdCount));  //新增giftBrand记录的条数
        if (count == 0) {
            json = new JsonResult(JsonResult.FAILURE, "插入不成功，范围已经存在");
        } else {
            json = new JsonResult(JsonResult.SUCCESS, "插入成功");
        }
        json.addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }


    /**
     * 规则-品牌删除
     *
     * @param response
     * @param ids
     * @throws IOException
     */
    @RequestMapping("/delete")
    public void deleteGiftBrandByIds(HttpServletResponse response, Integer[] ids) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("GiftBrandController中的deleteGiftBrandById操作，参数ids=" + ids.toString());
        }
        Integer count = giftBrandService.deleteGiftBrandByIds(ids);   //删除giftBrand记录的条数

        String msg = "成功删除"+count+"条记录";
        new JsonResult(JsonResult.SUCCESS).setMsg(msg).addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }

    /**
     * 规则-品牌更新
     *
     * @param response
     * @param giftBrand
     * @throws IOException
     */
    @RequestMapping("/update")
    public void updateGiftBrandById(HttpServletResponse response, GiftBrand giftBrand,Integer[] giftProdId,Integer[] giftProdCount) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("GiftBrandController中的updateGiftBrandById操作，参数giftBrand=" + giftBrand.toString());
        }
        int count = giftBrandService.updateGiftBrandByGiftBrand(this.load(giftBrand,giftProdId,giftProdCount));   //更新giftBrand记录的条数
        if (count != 0) {
            new JsonResult(JsonResult.SUCCESS).setMsg("更新成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } else {
            new JsonResult(JsonResult.FAILURE).setMsg("更新失败，满足条件的金额范围有重叠").toJson(response);
        }
    }

    private GiftBrand load(GiftBrand giftBrand,Integer[] giftProdId,Integer[] giftProdCount){
        List<GiftBrandItem> giftBrandItemList = new ArrayList<GiftBrandItem>(giftProdId.length);
        for(int i = 0; i < giftProdId.length; i++){
            GiftBrandItem giftBrandItem = new GiftBrandItem();
            giftBrandItem.setGiftBrandId(giftBrand.getId());
            giftBrandItem.setGiftProdId(giftProdId[i]);
            giftBrandItem.setGiftProdCount(giftProdCount[i]);
            giftBrandItemList.add(giftBrandItem);
        }
        giftBrand.setGiftBrandItemList(giftBrandItemList);
        if(logger.isInfoEnabled()){
            logger.info(String.format("GiftBrandController中的load方法,优惠活动-品牌下的赠送商品:%s",giftBrand.toString()));
        }
        return giftBrand;
    }
}
