package com.ejushang.spider.taobao.api;

import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.TaoBaoLogUtil;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.User;
import com.taobao.api.request.UserBuyerGetRequest;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.response.UserBuyerGetResponse;
import com.taobao.api.response.UserSellerGetResponse;

/**
 * 用户API<br/>
 * 提供了用户基本信息查询功能<br/>
 * User: Baron.Zhang
 * Date: 13-12-13
 * Time: 下午2:23
 */
public class UserApi {
    private TaobaoClient client;
    private String sessionKey;
    public UserApi(String sessionKey){
        client = new DefaultTaobaoClient(ConstantTaoBao.TAOBAO_API_URL, ConstantTaoBao.APP_KEY, ConstantTaoBao.APP_SECRET);
        this.sessionKey = sessionKey;
    }

    /**
     * taobao.user.buyer.get 查询买家信息API<br/>
     * @param fields 只返回nick, avatar参数
     * @return
     */
    public User getBuyer(String fields) throws ApiException {
        UserBuyerGetRequest req=new UserBuyerGetRequest();
        req.setFields(fields);
        UserBuyerGetResponse response = null;
        response = client.execute(req , sessionKey);
        TaoBaoLogUtil.logTaoBaoApi(response);
		return response.getUser();
    }

    /**
     * taobao.user.seller.get 查询卖家用户信息<br/>
     * @param fields 只返回user_id,nick,sex,seller_credit,type,has_more_pic,item_img_num,item_img_size,prop_img_num,
     *               prop_img_size,auto_repost,promoted_type,status,alipay_bind,consumer_protection,avatar,liangpin,
     *               sign_food_seller_promise,has_shop,is_lightning_consignment,has_sub_stock,is_golden_seller,vip_info,
     *               magazine_subscribe,vertical_market,online_gaming参数
     * @return
     */
    public User getSeller(String fields) throws ApiException {
        UserSellerGetRequest req= new UserSellerGetRequest();
        req.setFields(fields);
        UserSellerGetResponse response = null;
        response = client.execute(req , sessionKey);
        TaoBaoLogUtil.logTaoBaoApi(response);
		return response.getUser();
    }
}
