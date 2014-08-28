package com.ejushang.spider.erp.service.product;

import com.ejushang.spider.domain.Mealset;
import com.ejushang.spider.domain.MealsetItem;
import com.ejushang.spider.erp.common.mapper.MealsetItemMapper;
import com.ejushang.spider.erp.common.mapper.MealsetMapper;
import com.ejushang.spider.erp.util.SequenceGenerator;
import com.ejushang.spider.query.MealsetItemQuery;
import com.ejushang.spider.query.MealsetQuery;
import com.ejushang.spider.service.product.IMealSetService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.MealsetVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-24
 * Time: 下午2:38
 */
@Transactional
@Service
public class MealsetService implements IMealSetService {
    private static final Logger log = LoggerFactory.getLogger(MealsetService.class);

    @Resource
    MealsetMapper mealsetMapper;
    @Resource
    MealsetItemService mealsetItemService;
    @Resource
    MealsetItemMapper mealsetItemMapper;

    @Override
    public int saveMealset(Mealset mealset) {
        mealset.setCode(SequenceGenerator.getInstance().getNextMealSetNo());
        if (log.isInfoEnabled()) {
            log.info("套餐!! mealset" + mealset);
        }
        int result = mealsetMapper.saveMealset(mealset);
        this.saveMealsetItem(mealset);
        return result;
    }

    //级联保存
    public void saveMealsetItem(Mealset mealset) {
        if (mealset.getMealsetItemList() != null) {
            for (MealsetItem mealsetItem : mealset.getMealsetItemList()) {
                mealsetItem.setMealId(mealset.getId());
                mealsetItemMapper.saveMealsetItem(mealsetItem);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page findMealsetAll(MealsetQuery mealsetQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("套餐 mealsetQuery:" + mealsetQuery);
        }
        // 构造分页信息
        Page page = new Page();
        // 设置当前页
        page.setPageNo(mealsetQuery.getPage());
        // 设置分页大小
        page.setPageSize(mealsetQuery.getLimit());
        List<Mealset> mealsetList = mealsetMapper.findMealsetAll(mealsetQuery, page);
        List<MealsetVo> mealsetVoList = new ArrayList<MealsetVo>();
        MealsetVo mealsetVo = null;
        for (int i = 0; i < mealsetList.size(); i++) {
            mealsetVo = new MealsetVo();
            PropertyUtils.copyProperties(mealsetVo, mealsetList.get(i));
            mealsetVoList.add(mealsetVo);
        }
        if (log.isInfoEnabled()) {
            log.info("套餐 mealsetVoList:" + mealsetVoList);
        }
        page.setResult(mealsetList);
        return page;
    }

    @Override
    public int updateMealset(Mealset mealset) {
        if (log.isInfoEnabled()) {
            log.info("套餐!! mealset" + mealset);
        }
        int[] idArray = {mealset.getId()};
        mealsetItemMapper.deleteMealsetItemByMealId(mealset.getId());
        int count = this.mealsetMapper.updateMealset(mealset);
        this.saveMealsetItem(mealset);
        return count;
    }

    @Override
    public int deleteMealset(int[] idArray) {
        if (log.isInfoEnabled()) {
            log.info("删除套餐数组!! logStr" + Arrays.toString(idArray));
        }
        for (int id : idArray) {
            mealsetMapper.deleteMealset(id);
            mealsetItemMapper.deleteMealsetItemByMealId(id);
        }
        return idArray.length;
    }

    @Transactional(readOnly = true)
    @Override
    public Mealset findMealsetBySKU(String sku) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("通过SKU获得套餐!! sku" + sku);
        }
        Mealset mealset = mealsetMapper.findMealsetBySKU(sku);
        if (log.isInfoEnabled()) {
            log.info("结果!! mealset" + mealset);
        }
        if (mealset != null) {
            MealsetItemQuery mealsetItemQuery = new MealsetItemQuery();
            mealsetItemQuery.setMealId(mealset.getId());
            mealset.setMealsetItemList(mealsetItemMapper.findMealsetItemAll(mealsetItemQuery));
        }
        return mealset;
    }

    @Transactional(readOnly = true)
    @Override
    public MealsetVo findMealsetById(Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("通过id获得套餐!! id" + id);
        }
        MealsetItemQuery mealsetItemQuery = new MealsetItemQuery();
        mealsetItemQuery.setMealId(id);
        Mealset mealset = mealsetMapper.findMealsetById(id);
        MealsetVo mealsetVo = new MealsetVo();
        PropertyUtils.copyProperties(mealsetVo, mealset);
        mealsetVo.setMealsetItemVoList(mealsetItemService.findMealsetItemAll(mealsetItemQuery));
        if (log.isInfoEnabled()) {
            log.info("返回的vo结果!! mealsetVo" + mealsetVo);
        }
        return mealsetVo;
    }
}
