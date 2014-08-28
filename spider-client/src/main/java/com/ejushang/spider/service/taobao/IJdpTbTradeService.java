package com.ejushang.spider.service.taobao;

import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.domain.taobao.JdpTbTrade;
import com.ejushang.spider.query.taobao.JdpTbTradeQuery;

import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 14-2-27
 * Time: 下午5:39
 */
public interface IJdpTbTradeService {
    /**
     * 查询jdp淘宝交易信息
     * @param jdpTbTradeQuery
     * @return
     */
    public List<JdpTbTrade> findJdpTbTradeByJdpTbTradeQuery(JdpTbTradeQuery jdpTbTradeQuery);

    List<Shop> findAllShop();
}
