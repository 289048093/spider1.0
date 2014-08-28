package com.ejushang.spider.erp.service.shop;

import com.ejushang.spider.bean.ShopBean;
import com.ejushang.spider.constant.OutPlatformType;
import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.domain.ShopAuth;
import com.ejushang.spider.erp.api.IShopApi;
import com.ejushang.spider.erp.common.mapper.ShopAuthMapper;
import com.ejushang.spider.erp.common.mapper.ShopMapper;
import com.ejushang.spider.exception.TaoBaoApiException;
import com.ejushang.spider.query.ShopAuthQuery;
import com.ejushang.spider.query.ShopQuery;
import com.ejushang.spider.service.shop.IShopService;
import com.ejushang.spider.taobao.api.ShopApi;
import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.ShopVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 14-1-7
 * Time: 下午1:29
 */
@Service
public class ShopService implements IShopService {

    private static final Logger log = LoggerFactory.getLogger(ShopService.class);

    @Resource
    private ShopMapper shopMapper;

    @Resource
    private ShopAuthMapper shopAuthMapper;

    @Autowired
    private IShopApi shopTaoBaoApi;

    @Autowired
    private IShopApi shopJingDongApi;

    /**
     * 店铺查询
     *
     * @param shopQuery
     * @return
     */
    public Page findShopPageByQuery(ShopQuery shopQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        if(log.isInfoEnabled()){
            log.info("店铺一览查询："+shopQuery);
        }

        Page page = new Page();
        page.setPageNo(shopQuery.getPage());
        page.setPageSize(shopQuery.getLimit());

        shopMapper.findShopPageByQuery(shopQuery,page);

        List<Shop> shopList = page.getResult();

        if(CollectionUtils.isEmpty(shopList)){
            if(log.isInfoEnabled()){
                log.info("没有查询到店铺记录");
            }
            return new Page();
        }
        if(log.isInfoEnabled()){
            log.info("查询到"+shopList.size()+"条店铺记录");
        }
        // 根据店铺一览获取店铺授权一览
        List<ShopAuth> shopAuthList = shopAuthMapper.findShopAuthByShopList(shopList);
        // 数据转换
        List<ShopVo> shopVoList = convertShopAndShopAuth2ShopVo(shopList,shopAuthList);

        page.setResult(shopVoList);

        return page;
    }

    @Override
    public Shop findShopByShopId(String shopId) {
        ShopQuery shopQuery = new ShopQuery();
        shopQuery.setShopId(shopId);
        List<Shop> shopList = shopMapper.findShopByQuery(shopQuery);

        if(CollectionUtils.isEmpty(shopList)){
            return null;
        }
        return shopList.get(0);
    }

    /**
     * 将shop和shopAuth转换为shopVo
     * @param shopList
     * @param shopAuthList
     * @return
     */
    private List<ShopVo> convertShopAndShopAuth2ShopVo(List<Shop> shopList,List<ShopAuth> shopAuthList) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<ShopVo> shopVoList = new ArrayList<ShopVo>();
        // 店铺一览为空
        if(shopList == null || shopList.size() == 0){
            return shopVoList;
        }

        ShopVo shopVo = null;
        for (Shop shop : shopList){
            shopVo = new ShopVo();
            // 复制属性
            BeanUtils.copyProperties(shopVo, shop);
            for (ShopAuth shopAuth : shopAuthList){
                if(StringUtils.equals(shop.getShopId(),shopAuth.getShopId())){
                    shopVo.setSessionKey(shopAuth.getSessionKey());
                    break;
                }
            }
            shopVoList.add(shopVo);
        }
        return shopVoList;
    }

    /**
     * 店铺明细查询
     * @param id
     * @return
     */
    public ShopVo findShopById(Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(log.isInfoEnabled()){
            log.info("店铺明细查询：id = "+id);
        }
        // 构造查询对象
        ShopQuery shopQuery = new ShopQuery();
        shopQuery.setId(id);
        // 查询店铺信息
        List<Shop> shopList = shopMapper.findShopByQuery(shopQuery);

        if(shopList == null || shopList.size() == 0){
            if(log.isErrorEnabled()){
                log.error("异常：没有查询到id = " + id +"的店铺信息");
            }
            return null;
        }

        // 查询对应的店铺授权信息
        // 构造查询对象
        ShopAuthQuery shopAuthQuery = new ShopAuthQuery();
        shopAuthQuery.setShopId(shopList.get(0).getShopId());
        List<ShopAuth> shopAuthList = shopAuthMapper.findShopAuthByQuery(shopAuthQuery);

        // 数据转换
        ShopVo shopVo = new ShopVo();
        BeanUtils.copyProperties(shopVo,shopList.get(0));
        if(shopAuthList != null && shopAuthList.size() > 0){
            shopVo.setSessionKey(shopAuthList.get(0).getSessionKey());
            shopVo.setRefreshToken(shopAuthList.get(0).getRefreshToken());
        }

        return shopVo;
    }

    /**
     * 店铺新增
     * @param shop
     */
    public void saveShop(Shop shop) {
        if(log.isInfoEnabled()){
            log.info("店铺新增："+shop);
        }
        shopMapper.saveShop(shop);
    }

    /**
     * 同时新增店铺和店铺授权信息
     * @param shop
     */
    @Transactional
    public void saveShopAndShopAuth(Shop shop,ShopAuth shopAuth) {
        if(log.isInfoEnabled()){
            log.info("同时新增店铺和店铺授权信息："+shop+","+shopAuth);
        }
        shopMapper.saveShop(shop);
        shopAuthMapper.saveShopAuth(shopAuth);
    }

    /**
     * 店铺更新
     * @param shop
     */
    public void updateShop(Shop shop,String sessionKey) throws Exception {
        if(log.isInfoEnabled()){
            log.info("店铺更新："+shop);
        }
        // 判断来自哪个平台
        if(StringUtils.equals(shop.getOutPlatformType(), OutPlatformType.TAO_BAO.toDesc())){
            // 将店铺信息更新至淘宝平台
            shopTaoBaoApi.updateShop(shop,sessionKey);
        }else if(StringUtils.equals(shop.getOutPlatformType(), OutPlatformType.JING_DONG.toDesc())){
            // 将店铺信息更新至京东平台
            shopJingDongApi.updateShop(shop,sessionKey);
        }
        // 所有操作成功，店铺更新
        shopMapper.updateShop(shop);
        if(log.isInfoEnabled()){
            log.info("店铺更新成功!");
        }
    }

    @Override
    public void updateShopAuth(ShopAuth shopAuth) {
        shopAuthMapper.updateShopAuth(shopAuth);
    }

    /**
     * 店铺删除
     * @param shopId
     */
    @Transactional
    public void deleteShop(String shopId) {
        if(log.isInfoEnabled()){
            log.info("店铺删除：shopId = "+shopId);
        }
        shopMapper.deleteShop(shopId);
        shopAuthMapper.deleteShopAuth(shopId);
    }

    /**
     * 动态获取评分
     * @param id
     * @return
     */
    public ShopVo dynamicGetScore(Integer id) throws Exception {
        if(log.isInfoEnabled()){
            log.info("动态获取评分：id = "+id);
        }
        // 获取店铺信息与店铺授权信息
        ShopVo shopVo = findShopById(id);

        if(log.isInfoEnabled()){
            log.info("从外部平台动态获取评分信息：id = "+id);
        }

        // 判断来自哪个平台
        if(StringUtils.equals(shopVo.getOutPlatformType(), OutPlatformType.TAO_BAO.toDesc())){
            // 来自淘宝平台
            Map<String,Object> argsMap = new HashMap<String, Object>();
            // 设置返回字段（必须）
            argsMap.put(ConstantTaoBao.FIELDS, "sid,cid,nick,title,desc,bulletin,pic_path,created,modified,shop_score");
            // 设置卖家昵称（必须）
            argsMap.put(ConstantTaoBao.NICK,shopVo.getNick());

            if(log.isInfoEnabled()){
                log.info("查询淘宝店铺信息，参数argsMap = "+ argsMap);
            }

            // 创建淘宝shopApi
            ShopApi shopApi = new ShopApi(shopVo.getSessionKey());
            // 获取淘宝店铺信息
            com.taobao.api.domain.Shop shop = shopApi.getShop(argsMap);
            if(shop == null){
                throw new TaoBaoApiException("调用淘宝 shopApi.getShop("+argsMap+")出现异常");
            }
            // 设置商品描述评分
            shopVo.setItemScore(shop.getShopScore().getItemScore());
            // 设置服务态度评分
            shopVo.setServiceScore(shop.getShopScore().getServiceScore());
            // 设置发货速度评分
            shopVo.setDeliveryScore(shop.getShopScore().getDeliveryScore());

        }else if(StringUtils.equals(shopVo.getOutPlatformType(), OutPlatformType.JING_DONG.toDesc())){
            // todo:来自京东平台
        }

        // 获取评分信息的同时将评分信息更新到本地
        Shop shop = new Shop();
        shop.setId(id);
        // 设置商品描述评分
        shop.setItemScore(shopVo.getItemScore());
        // 设置服务态度评分
        shop.setServiceScore(shopVo.getServiceScore());
        // 设置发货速度评分
        shop.setDeliveryScore(shopVo.getDeliveryScore());

        if(log.isInfoEnabled()){
            log.info("店铺：更新评分信息至本地，" + shop);
        }
        // 将评分信息更新到本地
        shopMapper.updateShop(shop);

        return shopVo;
    }

    @Override
    public Shop findShopByShopId(Integer shopId) {
        return shopMapper.findShopByShopId(shopId);
    }

    /**
     * 获取所有店铺及其授权信息
     * @return
     */
    public List<ShopBean> findAllShopBean() {
        // 创建保存店铺及其授权信息集合
        List<ShopBean> shopBeanList = new ArrayList<ShopBean>();

        // 获取所有店铺信息
        List<Shop> shopList = shopMapper.findShopByQuery(new ShopQuery());

        if(CollectionUtils.isEmpty(shopList)){
            return shopBeanList;
        }

        // 根据店铺信息获取店铺授权信息
        List<ShopAuth> shopAuthList = shopAuthMapper.findShopAuthByShopList(shopList);

        ShopBean shopBean = null;
        for(Shop shop : shopList){
            shopBean = new ShopBean();
            shopBean.setShopId(shop.getShopId());
            shopBean.setTitle(shop.getTitle());
            shopBean.setSellerNick(shop.getNick());
            shopBean.setOutPlatformType(shop.getOutPlatformType());
            for(ShopAuth shopAuth : shopAuthList){
                if(StringUtils.equals(shop.getShopId(),shopAuth.getShopId())){
                    shopBean.setSessionKey(shopAuth.getSessionKey());
                    shopBean.setRefreshToken(shopAuth.getRefreshToken());
                    shopBean.setUserId(shopAuth.getUserId());
                    break;
                }
            }
            shopBeanList.add(shopBean);
        }
        return shopBeanList;
    }

    @Override
    public List<Shop> findAllShop() {
        return shopMapper.findAllShop();
    }

    @Override
    public Shop findShopByNick(String nick) {
        ShopQuery shopQuery = new ShopQuery();
        shopQuery.setNick(nick);
        List<Shop> shopList = shopMapper.findShopByQuery(shopQuery);

        if(CollectionUtils.isEmpty(shopList)){
            return null;
        }
        return shopList.get(0);
    }
}
