package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.GiftProd;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.query.GiftProdQuery;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.service.gift.IGiftProdService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.GiftProdVO;
import com.ejushang.spider.vo.ProdVo;
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
 * Date: 13-12-26
 * Time: 下午5:29
 */
@Controller
@RequestMapping("/giftProd")
public class GiftProdController {
    @Autowired
    private IGiftProdService giftProdService;

    private static final Logger logger = Logger.getLogger(GiftBrandController.class);

    /**
     * 根据对象 查找所有记录
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/list")
    public void findAllPage(HttpServletResponse response,GiftProdQuery giftProdQuery) throws IOException {
        if(logger.isInfoEnabled()){
            logger.info(String.format("GiftProdController中的list方法，参数giftProdQuery:%s",giftProdQuery));
        }
        if ("brandName".equals(giftProdQuery.getSearchType())) {
            giftProdQuery.setBrandName(giftProdQuery.getSearchValue());
        }
        if("prodName".equals(giftProdQuery.getSearchType())){
            giftProdQuery.setProdName(giftProdQuery.getSearchValue());
        }
        if("prodNo".equals(giftProdQuery.getSearchType())){
            giftProdQuery.setProdNo(giftProdQuery.getSearchValue());
        }
        Page page = giftProdService.findAllPage(giftProdQuery);
        new JsonResult(JsonResult.SUCCESS).setMsg("查询成功").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page).toJson(response);
    }


    /**
     * 查找 赠送商品的 id与name
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/giftProdIdName")
    public void findGiftProdIdAndName(HttpServletResponse response) throws IOException {
        List<GiftProdVO> giftProdVOList = giftProdService.findGiftProdIdAndName();
        new JsonResult(JsonResult.SUCCESS).setMsg("findGiftProdAll查询结果").addData(JsonResult.RESULT_TYPE_LIST, giftProdVOList).toJson(response);
    }

    @RequestMapping("/productDetail")
    public void findProductAll(HttpServletResponse response, ProductQuery productQuery, Integer limit) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("GiftProdController中的findProductAll操作,参数productQuery,limit分别是：" + productQuery.toString() + " " + limit);
        }
        List<ProdVo> prodVoList = giftProdService.findProductAllByPage(productQuery, limit);
        new JsonResult(JsonResult.SUCCESS).setMsg("findProductAll查询结果").addData(JsonResult.RESULT_TYPE_LIST, prodVoList).toJson(response);
    }

    /**
     * 增加一条GiftProd记录
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/add")
    public void saveGiftProd(HttpServletResponse response, Integer[] sellProdIds, GiftProd giftProd) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("GiftProdService的saveGiftProd操作，参数giftProd,sellProdIds分别是:" + giftProd.toString() + "," + sellProdIds.toString());
        }
        List<GiftProd> giftProdList = new ArrayList<GiftProd>();
        int countAll = sellProdIds.length;
        for (int i = 0; i < countAll; i++) {
            GiftProd saveGiftProd = new GiftProd();
            saveGiftProd.setSellProdId(sellProdIds[i]);
            saveGiftProd.setGiftProdId(giftProd.getGiftProdId());
            saveGiftProd.setGiftProdCount(giftProd.getGiftProdCount());
            saveGiftProd.setInUse(giftProd.getInUse());
            giftProdList.add(saveGiftProd);
        }
        try {
            int count = giftProdService.saveGiftProdList(giftProdList);
            new JsonResult(JsonResult.SUCCESS).setMsg("增加成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } catch (ErpBusinessException e) {
            logger.info(e.getMessage());
            new JsonResult(JsonResult.FAILURE).setMsg(e.getMessage()).toJson(response);
        }
    }

    /**
     * 根据id删除一条giftProd记录
     *
     * @param response
     * @param ids
     * @throws IOException
     */
    @RequestMapping("/delete")
    public void deleteGiftProdByIds(HttpServletResponse response, Integer[] ids) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("GiftProdService的deleteGiftProdById删除操作，参数ids=" + ids.toString());
        }
        Integer count = giftProdService.deleteGiftProdByIds(ids);

        String msg = "成功删除" + count + "条记录";
        new JsonResult(JsonResult.SUCCESS).setMsg(msg).addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }

    /**
     * 根据id 修改该记录的 其他字段值
     *
     * @param response
     * @param giftProd
     * @throws IOException
     */
    @RequestMapping("/update")
    public void updateGiftProdById(HttpServletResponse response, GiftProd giftProd) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("GiftProdService的deleteGiftProdById操作，参数giftProd=" + giftProd.toString());
        }
        int count = giftProdService.updateGiftProdById(giftProd);
        if (count != 0) {
            new JsonResult(JsonResult.SUCCESS).setMsg("修改成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
        } else {
            new JsonResult(JsonResult.FAILURE).setMsg("修改失败,此活动已经存在了").toJson(response);
        }
    }
}
