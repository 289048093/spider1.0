package com.ejushang.spider.erp.common.mappertb;

import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.domain.taobao.JdpTbTrade;
import com.ejushang.spider.query.taobao.JdpTbTradeQuery;

import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 14-2-27
 * Time: 下午2:40
 */
public interface JdpTbTradeMapper {

    public List<JdpTbTrade> findJdpTbTradeByJdpTbTradeQuery(JdpTbTradeQuery jdpTbTradeQuery);

    public List<Shop> findAllShop();
}
