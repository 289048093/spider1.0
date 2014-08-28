package com.ejushang.spider.erp.api.impl.taobao;

import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.erp.api.IShopApi;
import com.ejushang.spider.exception.TaoBaoApiException;
import com.ejushang.spider.taobao.api.ShopApi;
import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 14-1-9
 * Time: 下午5:41
 */
@Service
public class ShopTaoBaoApi implements IShopApi {

    private static final Logger log = LoggerFactory.getLogger(ShopTaoBaoApi.class);

    /**
     * 同步店铺信息至淘宝平台
     * @param shop
     * @return
     */
    public Boolean updateShop(Shop shop,String sessionKey) throws Exception {
        if(log.isInfoEnabled()){
            log.info("将信息同步到淘宝平台："+shop);
        }
        // 来自淘宝平台
        Map<String,Object> argsMap = new HashMap<String, Object>();
        // 设置店铺标题
        argsMap.put(ConstantTaoBao.TITLE,shop.getTitle());
        // 设置店铺公告
        argsMap.put(ConstantTaoBao.BULLETIN,shop.getBulletin());
        // 设置店铺描述
        argsMap.put(ConstantTaoBao.DESC,shop.getDescription());

        if(log.isInfoEnabled()){
            log.info("店铺信息同步至淘宝，参数argsMap = "+ argsMap);
        }
        // 创建淘宝shopApi
        ShopApi shopApi = new ShopApi(sessionKey);
        // 将店铺信息更新至淘宝平台
        com.taobao.api.domain.Shop shop1 = shopApi.updateShop(argsMap);
        if(shop1 == null){
            throw new TaoBaoApiException("调用淘宝shopApi.updateShop("+argsMap+")出现异常");
        }
        if(log.isInfoEnabled()){
            log.info("成功将店铺信息同步至淘宝平台："+shop);
        }

        return true;
    }
}
