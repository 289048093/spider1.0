package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.MealsetItem;
import com.ejushang.spider.query.MealsetItemQuery;
import com.ejushang.spider.service.product.IMealSetItemService;
import com.ejushang.spider.vo.MealsetItemVo;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:46
 * 赠品项设计Controller
 */
@RequestMapping("/mealsetItem")
@Controller
public class MealsetItemController {
    private static final Logger log = LoggerFactory.getLogger(MealsetItemController.class);

    @Autowired
    IMealSetItemService mealsetItemService;

    /**
     * 保存赠品项信息
     *
     * @param mealsetArray 赠品项信息实体
     * @return 插入的条数
     */
    @RequestMapping("/add")
    public void saveMealsetItem(MealsetItem[] mealsetArray, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("赠品项保存的mealsetItem：" + mealsetArray);
        }
        int count = mealsetItemService.saveMealsetItem(mealsetArray);
        new JsonResult(JsonResult.SUCCESS).setMsg("保存成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count)
                .toJson(response);
    }

    /**
     * 获得所有赠品项技术信息
     */
    @RequestMapping("/list")

    public void findMealsetItemAll(MealsetItemQuery mealsetItemQuery, HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("查询的赠品项query实体 mealsetItemQuery：" + mealsetItemQuery);
        }
        List<MealsetItemVo> mealsetItemVoList = mealsetItemService.findMealsetItemAll(mealsetItemQuery);
        log.info("赠品项查询结果  mealsetItemVoList：" + mealsetItemVoList);
        new JsonResult(JsonResult.SUCCESS)
                .addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, mealsetItemVoList)
                .toJson(response);
    }

    /**
     * 修改赠品项信息
     *
     * @param mealsetItemArray 赠品项信息实体
     * @return 修改的条数
     */
    @RequestMapping("/update")

    public void updateMealsetItem(MealsetItem[] mealsetItemArray, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("修改的赠品项的实体 mealsetItem：" + mealsetItemArray);
        }
        int count=mealsetItemService.updateMealsetItem(mealsetItemArray);
        new JsonResult(JsonResult.SUCCESS).setMsg("修改成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count)
                .toJson(response);

    }

    /**
     * 删除赠品项信息 通过ID
     *
     * @param idArray 赠品项id
     * @return 删除的条数
     */
    @RequestMapping("/delete")
    public void deleteMealsetItem(int[] idArray, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("需要删除的ID数组 idArray：" + idArray);
        }
        int count=mealsetItemService.deleteMealsetItem(idArray);
        log.info("删除的个数"+count);
        new JsonResult(JsonResult.SUCCESS).setMsg("删除成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count)
                .toJson(response);
    }

}
