package com.ejushang.spider.taobao.api;

import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.taobao.api.ApiException;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.domain.Shop;
import com.taobao.api.domain.ShopCat;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 13-12-16
 * Time: 下午5:15
 */

public class ShopApiTest {

    private ShopApi shopApi;

    @Before
    public void init(){
        shopApi = new ShopApi(TaoBaoUtilsEjs.getSessionKey());
    }

    /**
     * taobao.sellercats.list.add 添加卖家自定义类目<br/>
     * 此API添加卖家店铺内自定义类目 父类目parent_cid值等于0：表示此类目为店铺下的一级类目，值不等于0：表示此类目有父类目<br/>
     * 注：因为缓存的关系,添加的新类目需8个小时后才可以在淘宝页面上正常显示，但是不影响在该类目下商品发布<br/>
     * @throws Exception
     */
    @Test
    public void testAddSellercatsList() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();

        argsMap.put(ConstantTaoBao.NAME,"ejs厨具");
        argsMap.put(ConstantTaoBao.PICT_URL,null);
        argsMap.put(ConstantTaoBao.PARENT_CID,0L);
        argsMap.put(ConstantTaoBao.SORT_ORDER,2L);

        SellerCat sellerCat = shopApi.addSellercatsList(argsMap);

        if(sellerCat != null){
            System.out.println("name : " + sellerCat.getName());
        }
    }

    /**
     * taobao.sellercats.list.get 获取前台展示的店铺内卖家自定义商品类目<br/>
     * 此API获取当前卖家店铺在淘宝前端被展示的浏览导航类目（面向买家）<br/>
     */
    @Test
    public void testGetSellercatsList() throws ApiException {
        String nick = "sandbox_c_1";

        List<SellerCat> sellerCats = shopApi.getSellercatsList(nick);

        if(sellerCats != null){
            for (SellerCat sellerCat : sellerCats){
                if(sellerCat.getName().contains("ejs厨具")){
                    System.out.println("类目编号："+sellerCat.getCid()+";类目名称："+sellerCat.getName());
                }
            }
        }
    }

    /**
     * taobao.sellercats.list.update 更新卖家自定义类目<br/>
     * 此API更新卖家店铺内自定义类目<br/>
     * 注：因为缓存的关系，添加的新类目需8个小时后才可以在淘宝页面上正常显示，但是不影响在该类目下商品发布<br/>
     * @throws Exception
     */
    @Test
    public void testUpdateSellercatsList() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.CID,410131272L);
        argsMap.put(ConstantTaoBao.NAME,"ejs厨具餐具");
        SellerCat sellerCat = shopApi.updateSellercatsList(argsMap);

        if(sellerCat != null){
            System.out.println("类目名："+sellerCat.getName());
        }
    }

    /**
     * taobao.shop.get 获取卖家店铺的基本信息<br/>
     * @throws Exception
     */
    @Test
    public void testGetShop() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "sid,cid,title,nick,desc,bulletin,pic_path,created,modified";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.NICK,"sandbox_c_1");
        Shop shop = shopApi.getShop(argsMap);

        if (shop != null){
            System.out.println("店铺昵称："+shop.getNick() +";店铺标题："+shop.getTitle());
            System.out.println("店铺公告：" + shop.getBulletin());
            System.out.println("店铺描述：" + shop.getDesc());
        }
    }

    /**
     * taobao.shop.remainshowcase.get 获取卖家店铺剩余橱窗数量<br/>
     * 获取卖家店铺剩余橱窗数量，已用橱窗数量，总橱窗数量<br/>
     */
    @Test
    public void testGetShopRemainshowcase() throws ApiException {
        Shop shop = shopApi.getShopRemainshowcase();
        if (shop != null){
            System.out.println("剩余橱窗数量 : " + shop.getRemainCount());
            System.out.println("总橱窗数量 : " + shop.getAllCount());
            System.out.println("已用的橱窗数量 : " + shop.getUsedCount());
        }
    }

    /**
     * taobao.shop.update 更新店铺基本信息<br/>
     * 目前只支持标题、公告和描述的更新<br/>
     * @throws Exception
     */
    @Test
    public void testUpdateShop() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TITLE,"sandbox_c_1的商城测试店铺");
        argsMap.put(ConstantTaoBao.BULLETIN,"本店商品全是用于测试的，都是免费的");
        argsMap.put(ConstantTaoBao.DESC,"全场免费，全场免费，全场免费888");
        Shop shop = shopApi.updateShop(argsMap);

        if(shop != null){
            System.out.println("店铺昵称："+shop.getNick() +";店铺标题："+shop.getTitle());
        }
    }

    /**
     * taobao.shopcats.list.get 获取前台展示的店铺类目<br/>
     * 获取淘宝面向买家的浏览导航类目（跟后台卖家商品管理的类目有差异）<br/>
     */
    @Test
    public void testGetShopcatsList() throws ApiException {
        String fields = "cid,parent_cid,name,is_parent";

        List<ShopCat> shopCats = shopApi.getShopcatsList(fields);

        if(shopCats != null){
            for (ShopCat shopCat : shopCats){
                if(shopCat.getName().contains("ejs")){
                    System.out.println("类目编号："+shopCat.getCid()+";类目名称："+shopCat.getName());
                }
            }
        }
    }
}
