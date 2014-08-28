package com.ejushang.spider.taobao.api;

import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.ejushang.spider.test.CommonTest;
import com.taobao.api.ApiException;
import com.taobao.api.domain.SellerAuthorize;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 13-12-24
 * Time: 下午1:57
 */
public class ItemcatsApiTest extends CommonTest {

    private ItemcatsApi itemcatsApi;

    @Before
    public void init(){
        itemcatsApi = new ItemcatsApi(TaoBaoUtilsEjs.getSessionKey());
    }

    /**
     * taobao.itemcats.authorize.get 查询商家被授权品牌列表和类目列表<br/>
     * @return
     */
    @Test
    public void testGetSellerAuthorize() throws ApiException {
        String fields = "brand.vid, brand.name, item_cat.cid, item_cat.name, item_cat.status,item_cat.sort_order," +
                "item_cat.parent_cid,item_cat.is_parent, xinpin_item_cat.cid, xinpin_item_cat.name, " +
                "xinpin_item_cat.status, xinpin_item_cat.sort_order, xinpin_item_cat.parent_cid," +
                "xinpin_item_cat.is_parent";
        SellerAuthorize sellerAuthorize = itemcatsApi.getSellerAuthorize(fields);
        System.out.println("");
    }

    /**
     * taobao.itemcats.get 获取后台供卖家发布商品的标准商品类目<br/>
     * @return
     */
    @Test
    public void testGetItemCats() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "cid,parent_cid,name,is_parent";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.PARENT_CID,0L);
        Map<String,Object> itemCatsMap = itemcatsApi.getItemCats(argsMap);

        System.out.println("");
    }

    /**
     * taobao.itemprops.get 获取标准商品类目属性
     * @return
     */
    @Test
    public void getItemprops() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "pid, name, must, multi, prop_values";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.CID,50004887L);
        Map<String,Object> itempropsMap = itemcatsApi.getItemprops(argsMap);

        System.out.println("");
    }

    /**
     * taobao.itempropvalues.get 获取标准类目属性值
     * @return
     */
    @Test
    public void testGetPropValues() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "cid,pid,prop_name,vid,name,name_alias,status,sort_order";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.CID,50004887L);
        Map<String,Object> propValuesMap = itemcatsApi.getPropValues(argsMap);

        System.out.println("");
    }

}
