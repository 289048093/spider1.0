package com.ejushang.spider.web.controller;

import com.ejushang.spider.service.gift.IGiftBrandItemService;
import com.ejushang.spider.service.gift.IGiftBrandService;
import com.ejushang.spider.web.util.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Blomer
 * Date : 13-12-26
 * Time: 下午3:26
 */
@Controller
@RequestMapping("/giftBrandItem")
public class GiftBrandItemController {
    private static final Logger logger = Logger.getLogger(GiftBrandItemController.class);

    @Autowired
    private IGiftBrandService giftBrandService;

    @Autowired
    private IGiftBrandItemService giftBrandItemService;

    /**
     * 查看并修改某个活动对应的赠品项
     * @param response
     * @param giftBrandId
     * @throws IOException
     */
    @RequestMapping("detail")
    public void findByGiftBrandId(HttpServletResponse response, Integer giftBrandId) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("GiftBrandItemController中的detail方法，参数giftBrandId:%d", giftBrandId));
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST, giftBrandItemService.findByGiftBrandId(giftBrandId)).toJson(response);

    }


}
