package com.ejushang.spider.erp.api.impl.taobao;

import com.ejushang.spider.bean.LogisticsBean;
import com.ejushang.spider.constant.DeliveryType;
import com.ejushang.spider.constant.OriginalOrderStatus;
import com.ejushang.spider.erp.api.ILogisticsApi;
import com.ejushang.spider.erp.service.delivery.LogisticsInfoService;
import com.ejushang.spider.exception.TaoBaoApiException;
import com.ejushang.spider.service.delivery.ILogisticsInfoService;
import com.ejushang.spider.taobao.api.LogisticsApi;
import com.ejushang.spider.taobao.api.TradeApi;
import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.taobao.api.domain.Shipping;
import com.taobao.api.domain.Trade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 14-1-16
 * Time: 下午3:36
 */
@Service
public class LogisticsTaoBaoApi implements ILogisticsApi {

    private static final Logger log = LoggerFactory.getLogger(LogisticsTaoBaoApi.class);

    /**
     * 在线订单发货处理（对淘宝平台订单进行在线发货处理）
     * @param logisticsBean
     * @return
     */
    public Boolean sendLogisticsOnline(LogisticsBean logisticsBean) throws Exception {

        if(log.isInfoEnabled()){
            log.info("在线订单发货处理：对淘宝平台订单进行在线发货处理。参数："+logisticsBean+",sessionKey" + logisticsBean.getSessionKey());
        }

        // 淘宝API初始化
        // 物流API
        LogisticsApi logisticsApi = new LogisticsApi(logisticsBean.getSessionKey());
        // 交易API
        TradeApi tradeApi = new TradeApi(logisticsBean.getSessionKey());

        // 创建保存交易参数的map
        Map<String,Object> tradeArgsMap = new HashMap<String, Object>();
        // 交易字段
        tradeArgsMap.put(ConstantTaoBao.FIELDS,getTradeFields());
        // 交易id
        tradeArgsMap.put(ConstantTaoBao.TID,Long.valueOf(logisticsBean.getOutOrderNo()));

        // 查询交易状态
        Trade trade = tradeApi.getTradeFullinfo(tradeArgsMap);

        if(trade == null){
            throw new TaoBaoApiException("调用淘宝tradeApi.getTradeFullinfo("+tradeArgsMap+")出现异常");
        }
        // 如果订单状态不为买家已付款，跳过发货操作
        if(!StringUtils.equals(OriginalOrderStatus.WAIT_SELLER_SEND_GOODS.toDesc(),trade.getStatus())){
            return true;
        }

        // 创建保存物流参数的map
        Map<String,Object> logisticsArgsMap = new HashMap<String, Object>();
        // 淘宝交易ID
        logisticsArgsMap.put(ConstantTaoBao.TID, Long.valueOf(logisticsBean.getOutOrderNo()));
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        logisticsArgsMap.put(ConstantTaoBao.OUT_SID,logisticsBean.getExpressNo());
        // 物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送.调用 taobao.logistics.companies.get 获取。
        // 如果是货到付款订单，选择的物流公司必须支持货到付款发货方式
        logisticsArgsMap.put(ConstantTaoBao.COMPANY_CODE, DeliveryType.valueOf(logisticsBean.getExpressCompany()).getTmallCode());

        // 淘宝在线发货处理
        Shipping shipping = logisticsApi.sendLogisticsOnline(logisticsArgsMap);

        if(shipping == null){
            throw new TaoBaoApiException("调用淘宝logisticsApi.sendLogisticsOnline("+logisticsArgsMap+")出现异常");
        }

        // 操作成功
        if(shipping.getIsSuccess()){
            if(log.isInfoEnabled()){
                log.info("在线订单发货处理成功！");
            }
            return true;
        }

        if(log.isInfoEnabled()){
            log.info("在线订单发货处理失败！");
        }

        return false;
    }

    /**
     * 获取交易字段
     * @return
     */
    private String getTradeFields(){
        StringBuffer stringBuffer = new StringBuffer();
        // 交易id
        stringBuffer.append("tid,");
        // 交易状态
        stringBuffer.append("status");

        return stringBuffer.toString();
    }
}
