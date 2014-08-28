package com.ejushang.spider.taobao.api;

import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;

/**
 * 退款API<br/>
 * 提供了卖家接收退款，退款留言等功能<br/>
 * User: Baron.Zhang
 * Date: 13-12-13
 * Time: 下午2:23
 */
public class RefundApi {
    private TaobaoClient client;
    public RefundApi(){
        client = new DefaultTaobaoClient(ConstantTaoBao.TAOBAO_API_URL, ConstantTaoBao.APP_KEY, ConstantTaoBao.APP_SECRET);
    }



}
