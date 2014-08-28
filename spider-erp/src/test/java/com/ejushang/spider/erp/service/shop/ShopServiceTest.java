package com.ejushang.spider.erp.service.shop;

import com.ejushang.spider.bean.ShopBean;
import com.ejushang.spider.constant.OutPlatformType;
import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.domain.ShopAuth;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.service.shop.IShopService;
import com.ejushang.spider.service.taobao.IJdpTbTradeService;
import com.ejushang.spider.taobao.api.ShopApi;
import com.ejushang.spider.taobao.api.UserApi;
import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.ConvertUtils;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.taobao.api.domain.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 14-1-7
 * Time: 下午5:56
 */
public class ShopServiceTest extends ErpTest {

    private static final Logger log = LoggerFactory.getLogger(ShopServiceTest.class);

    @Autowired
    private IShopService shopService;

    @Autowired
    private IJdpTbTradeService jdpTbTradeService;


    @Test
    @Rollback(false)
    public void testFindAllShop(){
       List<Shop> shopList = jdpTbTradeService.findAllShop();
        for(Shop shop : shopList){
            System.out.println(shop);
        }

        List<Shop> shopList1 = shopService.findAllShop();
        for(Shop shop : shopList1){
            System.out.println(shop);
        }

    }

    @Test
    @Rollback(false)
    public void testSaveShopAndShopAuth() throws Exception {
        log.info("获取用户信息");

        for(int i = 0 ; i < 70; i++){
            UserApi userApi = new UserApi(TaoBaoUtilsEjs.getSessionKey());
            User user = userApi.getSeller("user_id,nick");

            // 来自淘宝平台
            Map<String,Object> argsMap = new HashMap<String, Object>();
            // 设置返回字段（必须）
            argsMap.put(ConstantTaoBao.FIELDS, "sid,cid,nick,title,desc,bulletin,pic_path,created,modified,shop_score");
            // 设置卖家昵称（必须）
            argsMap.put(ConstantTaoBao.NICK,user.getNick());

            if(log.isInfoEnabled()){
                log.info("查询淘宝店铺信息，参数argsMap = "+ argsMap);
            }

            // 创建淘宝shopApi
            ShopApi shopApi = new ShopApi(TaoBaoUtilsEjs.getSessionKey());

            log.info("获取店铺信息信息");
            com.taobao.api.domain.Shop shop = shopApi.getShop(argsMap);

            Shop shop1 = new Shop();
            shop1.setShopId(ConvertUtils.convertLong2String(shop.getSid()));
            shop1.setCatId(ConvertUtils.convertLong2String(shop.getCid()));
            shop1.setNick(shop.getNick());
            shop1.setTitle(shop.getTitle());
            shop1.setDescription(shop.getDesc());
            shop1.setBulletin(shop.getBulletin());
            shop1.setPicPath(shop.getPicPath());
            shop1.setItemScore(shop.getShopScore().getItemScore());
            shop1.setServiceScore(shop.getShopScore().getServiceScore());
            shop1.setDeliveryScore(shop.getShopScore().getDeliveryScore());
            shop1.setOutPlatformType(OutPlatformType.TAO_BAO.toDesc());
            shop1.setCreateTime(shop.getCreated());
            shop1.setUpdateTime(shop.getModified());

            ShopAuth shopAuth = new ShopAuth();
            shopAuth.setShopId(ConvertUtils.convertLong2String(shop.getSid()));
            shopAuth.setSessionKey(TaoBaoUtilsEjs.getSessionKey());
            shopAuth.setRefreshToken(TaoBaoUtilsEjs.getSessionKey());
            shopAuth.setUserId(ConvertUtils.convertLong2String(user.getUserId()));
            shopAuth.setUserNick(user.getNick());
            shopAuth.setOutPlatformType(OutPlatformType.TAO_BAO.toDesc());

            shopService.saveShopAndShopAuth(shop1,shopAuth);
        }
    }

    @Test
    @Rollback(false)
    public void testFindAllShopBean(){
        List<ShopBean> shopBeanList = shopService.findAllShopBean();
        for (ShopBean shopBean : shopBeanList){
            System.out.println(shopBean);
        }
    }
}
