package com.ejushang.spider.taobao.api;

import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.taobao.api.ApiException;
import com.taobao.api.domain.User;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Baron.Zhang
 * Date: 13-12-16
 * Time: 下午5:15
 */

public class UserApiTest {

    private UserApi userApi;

    @Before
    public void init(){
        userApi = new UserApi(TaoBaoUtilsEjs.getSessionKey());
    }

    /**
     * taobao.user.buyer.get 查询买家信息API<br/>
     * @return
     */
    @Test
    public void testGetBuyer() throws ApiException {
        String fields = "nick,avatar";
        User user = userApi.getBuyer(fields);
        System.out.println(user.getNick() + "," + user.getAvatar());
    }

    /**
     * taobao.user.seller.get 查询卖家用户信息<br/>
     * @return
     */
    @Test
    public void testGetSeller() throws ApiException {
        String fields = "user_id,nick,sex,seller_credit,type,has_more_pic,item_img_num,item_img_size,prop_img_num," +
                "prop_img_size,auto_repost,promoted_type,status,alipay_bind,consumer_protection,avatar,liangpin," +
                "sign_food_seller_promise,has_shop,is_lightning_consignment,has_sub_stock,is_golden_seller,vip_info," +
                "magazine_subscribe,vertical_market,online_gaming";
        User user = userApi.getSeller(fields);
        if(user != null){
            System.out.println("用户id："+user.getUserId()+";用户昵称："+user.getNick());
        }
    }
}
