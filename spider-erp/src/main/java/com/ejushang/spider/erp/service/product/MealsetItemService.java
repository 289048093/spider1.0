package com.ejushang.spider.erp.service.product;

import com.ejushang.spider.domain.MealsetItem;
import com.ejushang.spider.domain.Product;
import com.ejushang.spider.erp.common.mapper.MealsetItemMapper;
import com.ejushang.spider.erp.common.mapper.ProductMapper;
import com.ejushang.spider.query.MealsetItemQuery;
import com.ejushang.spider.service.product.IMealSetItemService;
import com.ejushang.spider.vo.MealsetItemVo;
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
public class MealsetItemService implements IMealSetItemService {
    private static final Logger log = LoggerFactory.getLogger(MealsetItemService.class);

    @Resource
    MealsetItemMapper mealSetItemMapper;

    @Resource
    ProductMapper productMapper;


    @Override
    public int saveMealsetItem(MealsetItem[] mealsetItemArray) {
        if (log.isInfoEnabled()) {
            log.info("保存：mealsetItemArray：----" + mealsetItemArray);
        }
        for (MealsetItem mealsetItem : mealsetItemArray) {
            mealSetItemMapper.saveMealsetItem(mealsetItem);
        }
        return mealsetItemArray.length;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MealsetItemVo> findMealsetItemAll(MealsetItemQuery mealsetItemQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("查询信息：mealsetItemQuery：----" + mealsetItemQuery);
        }
        List<MealsetItem> mealsetItemList = mealSetItemMapper.findMealsetItemAll(mealsetItemQuery);
        if (log.isInfoEnabled()) {
            log.info("查询实体结果：mealsetItemList：----" + mealsetItemList);
        }
        List<MealsetItemVo> mealsetItemVoList = new ArrayList<MealsetItemVo>();
        MealsetItemVo mealsetItemVo = null;
        for (int i = 0; i < mealsetItemList.size(); i++) {
            mealsetItemVo = new MealsetItemVo();
            PropertyUtils.copyProperties(mealsetItemVo, mealsetItemList.get(i));
            /*将套餐拥有的商品加入*/
            Product product = productMapper.findProductById(mealsetItemVo.getProdId());
//            if (log.isInfoEnabled()) {
//                log.info("通过ID："+mealsetItemVo.getProdId()+"获得套餐项对应的商品实体：product：----" + product);
//            }
            if (product != null) {
                mealsetItemVo.setProdName(product.getProdName());
                mealsetItemVo.setProdCode(product.getProdCode());
                mealsetItemVo.setProdNo(product.getProdNo());
                mealsetItemVo.setShopPriceStr(product.getShopPriceStr());
            }
            mealsetItemVoList.add(mealsetItemVo);
        }
        if (log.isInfoEnabled()) {
            log.info("转换后的结果：mealsetItemVoList：----" + mealsetItemVoList);
        }
        return mealsetItemVoList;
    }

    @Override
    public int updateMealsetItem(MealsetItem[] mealsetItemArray) {
        if (log.isInfoEnabled()) {
            log.info("修改参数 mealsetItemArray:-----" + mealsetItemArray);
        }
        for (MealsetItem mealsetItem : mealsetItemArray) {
            mealSetItemMapper.updateMealsetItem(mealsetItem);
        }
        return mealsetItemArray.length;
    }

    @Override
    public int deleteMealsetItem(int[] idArray) {
        if (log.isInfoEnabled()) {
            log.info("删除的套餐数组 logStr:-----" + Arrays.toString(idArray));
        }
        for (int id : idArray) {
            mealSetItemMapper.deleteMealsetItem(id);
        }
        return idArray.length;
    }
}
