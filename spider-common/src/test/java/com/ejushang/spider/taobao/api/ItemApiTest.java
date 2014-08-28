package com.ejushang.spider.taobao.api;


import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.taobao.api.FileItem;
import com.taobao.api.domain.Sku;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * User: Alvin.long
 * Date: 13-12-24
 * Time: 下午1:57
 */
public class ItemApiTest {


    private ProductApi productApi;

    @Before
    public void init() {
        productApi = new ProductApi(TaoBaoUtilsEjs.getSessionKey());
    }

    @Test
//    @Rollback(false) //测试完以后提交事务
    public void testGetAfterSale() {
        try {
            productApi.getAfterSale();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     *  测试添加商品
     * @throws Exception
     */
    @Test
    public void testAddItem() throws Exception {
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM, 1L);
        argsMap.put(ConstantTaoBao.PRICE, "350");
        argsMap.put(ConstantTaoBao.TYPE, "auction");
        argsMap.put(ConstantTaoBao.STUFF_STATUS, "new");
        argsMap.put(ConstantTaoBao.TITLE, "沙箱测试 演示");
        argsMap.put(ConstantTaoBao.DESC, "沙箱测试ejs");
        argsMap.put(ConstantTaoBao.LOCATION_STATE, "广东");
        argsMap.put(ConstantTaoBao.LOCATION_CITY, "深圳");
        argsMap.put(ConstantTaoBao.APPROVE_STATUS, "onsale");
        argsMap.put(ConstantTaoBao.CID, 50068913L);
        argsMap.put(ConstantTaoBao.PROPS, "1627207:3232483;");
        argsMap.put(ConstantTaoBao.IS_EX, true);
        argsMap.put(ConstantTaoBao.IS_TAOBAO, true);
        argsMap.put(ConstantTaoBao.SELLER_CIDS, "410131250;410127985");   //店铺选择
        System.out.print("添加商品的结果" + productApi.addItem(argsMap));
    }

    /**
     * 测试taobao.item.anchor.get 获取可用宝贝描述规范化模块
     *
     * @throws Exception
     */
    @Test
    public void testGetItemAnchor() throws Exception {
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.CAT_ID, 50070166L);
        argsMap.put(ConstantTaoBao.TYPE, 0L);
        System.out.print("结果" + productApi.getItemAnchor(argsMap));
    }

    /**
     * 获取单个商品的信息测试           ok
     * @throws Exception
     */
    @Test
    public void testGetItem() throws Exception {
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.FIELDS, "detail_url,num_iid,title,nick,type,cid,seller_cids," +
                "props,input_pids,input_str,desc,pic_url,num,valid_thru,list_time,delist_time," +
                "stuff_status,location,price,post_fee,express_fee,ems_fee,has_discount,freight_payer," +
                "has_invoice,has_warranty,has_showcase,modified,increment,approve_status,postage_id," +
                "product_id,auction_point,property_alias,item_img,prop_img,sku,video,outer_id,is_virtual");
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        System.out.print("结果" + productApi.getItem(argsMap));
    }

    /**
     * 删除单个商品测试      ok
     * @throws Exception
     */
    @Test
    public void testDeleteItem() throws Exception {
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505510783L);
        System.out.print("结果" + productApi.deleteItem(argsMap));
    }


    /**
     * 商品发布规则信息获取接口测试     OK
     * CATEGORY_ID 其实就是类目ID
     * @throws Exception
     */
    @Test
    public void testGetAddRules() throws Exception {
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.CATEGORY_ID,50004889L);
        System.out.print("结果" + productApi.getAddRules(argsMap));
    }


    @Test
    public void testAddItemBseller()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM, 1L);
        argsMap.put(ConstantTaoBao.SECONDKILL, "web_and_wap");
        argsMap.put(ConstantTaoBao.PRICE, "950");
        argsMap.put(ConstantTaoBao.TYPE, "fixed");
        argsMap.put(ConstantTaoBao.STUFF_STATUS, "new");
        argsMap.put(ConstantTaoBao.TITLE, "沙箱测试 测试助理1");
        argsMap.put(ConstantTaoBao.DESC, "2013年12月19日 13:30:12测试");
        argsMap.put(ConstantTaoBao.LOCATION_STATE, "广东");
        argsMap.put(ConstantTaoBao.LOCATION_CITY, "深圳");
        argsMap.put(ConstantTaoBao.APPROVE_STATUS, "宝安");
        argsMap.put(ConstantTaoBao.CID, 50068913L);
        argsMap.put(ConstantTaoBao.PROPS, "1627207:3232483");
        argsMap.put(ConstantTaoBao.FREIGHT_PAYER, "seller");
        argsMap.put(ConstantTaoBao.AUCTION_POINT, 90L);
        productApi.addItemBseller(argsMap);
    }

    @Test
    public void testAddItemCseller()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM,1L);
        argsMap.put(ConstantTaoBao.SECONDKILL,"web_and_wap");
        argsMap.put(ConstantTaoBao.PRICE,"950");
        argsMap.put(ConstantTaoBao.TYPE,"fixed");
        argsMap.put(ConstantTaoBao.STUFF_STATUS,"new");
        argsMap.put(ConstantTaoBao.TITLE,"沙箱测试 测试助理1");
        argsMap.put(ConstantTaoBao.DESC,"2013年12月19日 13:30:12测试");
        argsMap.put(ConstantTaoBao.LOCATION_STATE,"广东");
        argsMap.put(ConstantTaoBao.LOCATION_CITY,"深圳");
        argsMap.put(ConstantTaoBao.APPROVE_STATUS,"宝安");
        argsMap.put(ConstantTaoBao.CID,50068913L);
        argsMap.put(ConstantTaoBao.PROPS,"1627207:3232483");
        argsMap.put(ConstantTaoBao.FREIGHT_PAYER,"seller");

        productApi.addItemCseller(argsMap);
    }

    /**
     *    当前用户不是数字阅读用户，用户ID:2074082786;用户昵称:sandbox_c_1" 不OK
     * @throws Exception
     */
    @Test
    public void testAddItemEbookSerial()throws Exception{
        File file=new File("C:\\Users\\Administrator\\Desktop\\EJSQQtu\\1.jpg");
        File files=new File("C:\\Users\\Administrator\\Desktop\\EJSQQtu\\1.jpg");
        SimpleDateFormat format =   new SimpleDateFormat( "yyyy/MM/dd" );
        Date dateTime = (Date)format.parseObject("2015/12/19".toString());
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.COVER,new FileItem(files));
        argsMap.put(ConstantTaoBao.COPYRIGHT_FILES,new FileItem(file));
        argsMap.put(ConstantTaoBao.PRICE,"99");
        argsMap.put(ConstantTaoBao.TITLE,"沙箱测试 java核心技术");
        argsMap.put(ConstantTaoBao.DESC,"沙箱测试 还是比较基础");
        argsMap.put(ConstantTaoBao.CID,50068913L);
        argsMap.put(ConstantTaoBao.NAME,"JAVA核心技术");
        argsMap.put(ConstantTaoBao.AUTHOR,"一只无名的人");
        argsMap.put(ConstantTaoBao.COPYRIGHT_END,dateTime);
        argsMap.put(ConstantTaoBao.SELL_WAY,1L);
        argsMap.put(ConstantTaoBao.RELATION_LINK,"ccxxxcc");
        productApi.addItemEbookSerial(argsMap);


    }
    /**
     *   添加不了没ID 不OK
     * @throws Exception
     */
    @Test
    public void testUpdateItemEbookSerial()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.ITEM_ID,"12321312");
        argsMap.put(ConstantTaoBao.PRICE,"89");
        argsMap.put(ConstantTaoBao.TITLE,"沙箱测试这个一个网络原创商品");
        argsMap.put(ConstantTaoBao.DESC,"沙箱测试这是一个好商品");
        productApi.updateItemEbookSerial(argsMap);
    }

    /**
     *    添加或者修改图片 ok     但是只能传一张图片 cc
     *
     *    如果image 不存在	通过taobao.item.get获取到图片id之后再对其进行更新
     * @throws Exception
     */
    @Test
    public void testUploadItemImg()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        File files=new File("C:\\Users\\Administrator\\Desktop\\EJSQQtu\\3.jpg");
//        argsMap.put(ConstantTaoBao.ID,1L);
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.POSITION,12321312L);
        argsMap.put(ConstantTaoBao.IMAGE,new FileItem(files));
        argsMap.put(ConstantTaoBao.IS_MAJOR,true);
        productApi.uploadItemImg(argsMap);
    }

    /**添加添加关联图片
     * 暂时没用，不能添加多个图片
     * @throws Exception
     */
    @Test
    public void testItemJointImg()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.ID,1L);
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        productApi.deleteItemImg(argsMap);
    }

    /**删除图片测试
     * 不能删除主图片
     * @throws Exception
     */
    @Test
    public void testDeleteItemImg()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.ID,1L);
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        productApi.deleteItemImg(argsMap);
    }

    /**
     * 测试商品关联属性图，但是好像没起作用
     * @throws Exception
     */
    @Test
    public void testItemJointPropimg()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.PROPERTIES,"1627207:3232483;");
//        argsMap.put(ConstantTaoBao.ID,"");   新增的话不需要图片ID
        argsMap.put(ConstantTaoBao.CID,50068913L);
        argsMap.put(ConstantTaoBao.PIC_PATH,"http://img01.tbsandbox.com/bao/uploaded/i1/T1HTJeXbFhXXXXXXXX_!!0-item_pic.jpg");
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.POSITION,22);
    }

    /**
     * 修改商品价格
     * 可以通过
     *
     * @throws Exception
     */
    @Test
    public void testUpdateItemPrice()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.PRICE,"6300");
        argsMap.put(ConstantTaoBao.TITLE,"沙箱测试 该价格测试 希维尔涨价了");
        productApi.updateItemPrice(argsMap);
    }

    /**删除属性图片测试，可以用，但是没效果
     *
     * @throws Exception
     */
    @Test
    public void testDeleteItemPropimg()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.ID,1L);
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        productApi.deleteItemPropimg(argsMap);
    }

    /**添加或者修改属性图片     测试不通过，图片问题
     *
     * @throws Exception
     */
    @Test
    public void testUploadItemPropimg()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        File files=new File("C:\\Users\\Administrator\\Desktop\\EJSQQtu\\3.jpg");
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.PROPERTIES,"1627207:3232483;");
        argsMap.put(ConstantTaoBao.IMAGE,new FileItem(files));
        argsMap.put(ConstantTaoBao.POSITION,2L);
        productApi.uploadItemPropimg(argsMap);
    }

    /**
     * 测试
     * taobao.item.quantity.update 宝贝/SKU库存修改
     *
     * 可以用来修改库存 用quantity字段
     * @throws Exception
     */
    @Test
    public void testUpdateItemQuantity()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.QUANTITY,6300L);
        argsMap.put(ConstantTaoBao.TYPE,1L);
        productApi.updateItemQuantity(argsMap);
    }

    /**
     * 橱窗推荐     ok
     * @throws Exception
     */
    @Test
    public void testAddItemRecommend()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        productApi.addItemRecommend(argsMap);
    }


    /**
     * 删除橱窗推荐     ok
     * @throws Exception
     */
    @Test
    public void testDeleteItemRecommend()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        productApi.deleteItemRecommend(argsMap);
    }


    /**
     * 添加SKU码     ok
     * 也就是设置商品拥有的库存，颜色，码数，之类的属性，价格
     * @throws Exception
     */
    @Test
    public void tetsAddItemSku()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.PROPERTIES,"1627207:3232483;1627207:28332;");
        argsMap.put(ConstantTaoBao.QUANTITY,99L);
        argsMap.put(ConstantTaoBao.PRICE,"6300");
        productApi.addItemSku(argsMap);
    }
    /**
     * 删除SKU码     ok
     *
     * @throws Exception
     */
    @Test
    public void testDeleteItemSku()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.PROPERTIES,"1627207:3232483;1627207:28332;");

        productApi.deleteItemSku(argsMap);
    }


    /**
     * 获得SKU     ok
     *
     * @throws Exception
     */
    @Test
    public void testGetItemSku()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.FIELDS,"sku_id,num_iid,properties,quantity,price,outer_id,created,modified,status");
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.SKU_ID,31051848322L);
        productApi.getItemSku(argsMap);
    }
    /**
     * 更新商品SKU的价格     ok
     *
     * @throws Exception
     */
    @Test
    public void testUpdateItemSkuPrice()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.PROPERTIES,"1627207:3232483;1627207:28332;");
        argsMap.put(ConstantTaoBao.PRICE,"998");
        productApi.updateItemSkuPrice(argsMap);
    }

    /**
     * 更新商品SKU的信息     ok
     *
     * @throws Exception
     */
    @Test
    public void testUpdateItemSku()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.PROPERTIES,"1627207:3232483;1627207:28332;");
        argsMap.put(ConstantTaoBao.PRICE,"6300");
        productApi.updateItemSku(argsMap);
    }
    /**
     * 根据商品ID列表获取SKU信息     ok
     *
     * @throws Exception
     */
    @Test
    public void testGetItemSkus()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.FIELDS,"properties_name,sku_spec_id,with_hold_quantity,outer_id,sku_id,iid,num_iid,properties,quantity,price,created,modified,status");
        //argsMap.put(ConstantTaoBao.NUM_IIDS,"2100505481640");
        argsMap.put(ConstantTaoBao.SKU_ID,31052224872L);
        argsMap.put(ConstantTaoBao.NUM_IID,2100505141230L);
        //productApi.getItemSkus(argsMap);

        Sku sku = productApi.getItemSku(argsMap);

        System.out.println("");
    }

    /**
     * 查询当前登录用户的店铺的宝贝详情页的模板名称     ok
     *
     * @throws Exception
     */
    @Test
    public void testGetItemTemplates()throws Exception{
        productApi.getItemTemplates();
    }
    /**
     * 只要知道num_iid随意修改     ok
     *
     * @throws Exception
     */
    @Test
    public void testUpdateItem()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.TITLE,"沙箱测试 998只要998");
        productApi.updateItem(argsMap);
    }


    /**
     * 商品下架    ok
     *
     * @throws Exception
     */
    @Test
    public void testDelistingItemUpdate()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);

        productApi.updateItem(argsMap);
    }

    /**
     * 一口价商品上架     ok
     *
     * @throws Exception
     */
    @Test
    public void testItemUpdateListing()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.NUM_IID,2100505481640L);
        argsMap.put(ConstantTaoBao.NUM,40L);

        productApi.itemUpdateListing(argsMap);
    }

    /**
     * taobao.items.custom.get 根据外部ID取商品 ok
     * @throws Exception
     */
    @Test
    public void testGetItemsCustom()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.OUTER_ID,"hz$自由港$12$1202$233$rUZaLOlIxcc=");
        argsMap.put(ConstantTaoBao.FIELDS,"num_iid,sku,item_img,prop_img");
        productApi.getItemsCustom(argsMap);
    }

    /**
     * 得到当前会话用户库存中的商品列表    OK
     * @throws Exception
     */
    @Test
    public void testGetItemsInventory()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.FIELDS,"approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id");
        productApi.getItemsInventory(argsMap);
    }

    /**
     * 得到当前会话用户库存中的商品列表
     * @throws Exception
     */
    @Test
    public void testGetItemsList()throws Exception{
        Map<String, Object> argsMap = new HashMap();
        argsMap.put(ConstantTaoBao.FIELDS,"approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id");
        productApi.getItemsInventory(argsMap);
    }


}
