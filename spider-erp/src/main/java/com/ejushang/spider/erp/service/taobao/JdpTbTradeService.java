package com.ejushang.spider.erp.service.taobao;

import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.domain.taobao.JdpTbTrade;
import com.ejushang.spider.erp.common.mappertb.JdpTbTradeMapper;
import com.ejushang.spider.query.taobao.JdpTbTradeQuery;
import com.ejushang.spider.service.taobao.IJdpTbTradeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 14-2-27
 * Time: 下午5:39
 */
@Service
public class JdpTbTradeService implements IJdpTbTradeService {

    @Resource
    private JdpTbTradeMapper jdpTbTradeMapper;

    @Override
    public List<JdpTbTrade> findJdpTbTradeByJdpTbTradeQuery(JdpTbTradeQuery jdpTbTradeQuery) {
        return jdpTbTradeMapper.findJdpTbTradeByJdpTbTradeQuery(jdpTbTradeQuery);
    }

    @Override
    public List<Shop> findAllShop() {
        return jdpTbTradeMapper.findAllShop();
    }

}
