package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Mealset;
import com.ejushang.spider.domain.MealsetItem;
import com.ejushang.spider.query.MealsetQuery;
import com.ejushang.spider.service.product.IMealSetService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:46
 * 赠品Controller
 */
@RequestMapping("/mealset")
@Controller
public class MealsetController {
    private static final Logger log = LoggerFactory.getLogger(MealsetController.class);

    @Autowired
    IMealSetService mealSetService;

    /**
     * 保存赠品信息
     *
     * @return 插入的条数
     */
    @RequestMapping("/add")
    public void saveMealset(Mealset mealset, String[] prodId, String[] mealPrice, String[] mealCount, HttpServletResponse response) throws IOException {
        int count = mealSetService.saveMealset(this.load(mealset, prodId, mealPrice, mealCount));
        new JsonResult(JsonResult.SUCCESS).setMsg("添加成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count)
                .toJson(response);
    }

    public Mealset load(Mealset mealset, String[] prodId, String[] mealPrice, String[] mealCount) {
        List<MealsetItem> mealsetItemList = new ArrayList<MealsetItem>();
        for (int i = 0; i < prodId.length; i++) {
            MealsetItem mealsetItem = new MealsetItem();
            mealsetItem.setProdId(Integer.parseInt(prodId[i]));
            mealsetItem.setMealCount(Integer.parseInt(mealCount[i]));
            mealsetItem.setMealPriceStr(mealPrice[i]);
            mealsetItemList.add(mealsetItem);
        }
        mealset.setMealsetItemList(mealsetItemList);
        if (log.isInfoEnabled()) {
            log.info("赠品保存的mealset全部信息" + mealset);
        }
        return mealset;
    }

    /**
     * 通过ID获得赠品信息
     *
     * @param id 赠品ID
     * @return 赠品信息实体
     */
    @RequestMapping("/findMealsetById")
    public void findMealsetById(Integer id, HttpServletResponse response) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
        if (log.isInfoEnabled()) {
            log.info("查询的赠品ID id：" + id);
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, mealSetService.findMealsetById(id))
                .toJson(response);

    }

    /**
     * 获得所有赠品技术信息
     */
    @RequestMapping("/list")

    public void findMealsetAll(MealsetQuery mealsetQuery, HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("查询的赠品query实体 mealsetQuery：" + mealsetQuery);
        }
        Page page = mealSetService.findMealsetAll(mealsetQuery);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, page)
                .toJson(response);
    }

    /**
     * 修改赠品信息
     *
     * @param mealset 赠品信息实体
     * @return 修改的条数
     */
    @RequestMapping("/update")

    public void updateMealset(Mealset mealset, String[] prodId, String[] mealPrice, String[] mealCount, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("修改赠品 的实体 mealset：" + mealset);
        }
        int count = mealSetService.updateMealset(this.load(mealset, prodId, mealPrice, mealCount));
        new JsonResult(JsonResult.SUCCESS).setMsg("修改成功").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count)
                .toJson(response);
    }

    /**
     * 删除赠品信息 通过ID
     *
     * @param idArray 赠品id数组
     * @return 删除的条数
     */
    @RequestMapping("/delete")
    public void deleteMealset(int[] idArray, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("删除赠品Int数组 idArray：" + idArray);
        }
        int count = mealSetService.deleteMealset(idArray);
        new JsonResult(JsonResult.SUCCESS).setMsg("删除成功!").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count)
                .toJson(response);
    }

}
