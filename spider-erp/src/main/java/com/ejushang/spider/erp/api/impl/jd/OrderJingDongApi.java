package com.ejushang.spider.erp.api.impl.jd;

import com.ejushang.spider.bean.ShopBean;
import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.erp.api.IOrderApi;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 关注于从京东抓取订单
 * User: Baron.Zhang
 * Date: 14-1-9
 * Time: 下午2:26
 */
@Service
public class OrderJingDongApi implements IOrderApi {
    /**
     * 从京东抓取订单
     * @param shopBean
     * @return
     */
    public List<OriginalOrder> fetchOrder(ShopBean shopBean) {

        return null;
    }

    @Override
    public List<OriginalOrder> fetchOrderByJst(ShopBean shopBean) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
