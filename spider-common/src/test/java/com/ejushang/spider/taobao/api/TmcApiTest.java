package com.ejushang.spider.taobao.api;

import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.taobao.api.ApiException;
import com.taobao.api.domain.TmcUser;
import com.taobao.api.domain.User;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.top.link.LinkException;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 13-12-16
 * Time: 下午5:15
 */

public class TmcApiTest {

    private TmcApi tmcApi;

    @Before
    public void init(){
        tmcApi = new TmcApi("6200302ebdfha36cb2e2f70995c6fa94274e158812f97393629362106");
    }

    @Test
    public void testPermitTmcUser() throws ApiException {
        Boolean isSuccess = tmcApi.permitTmcUser("taobao_trade_TradeChanged,taobao_trade_TradeLogisticsAddressChanged," +
                "taobao_trade_TradeSuccess");
        System.out.println(isSuccess);
    }

    @Test
    public void testGetTmcUser() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.FIELDS,"user_nick,user_id,is_valid,created,modified,topics,group_name");
        argsMap.put(ConstantTaoBao.NICK,"sandbox_ejs_sc_test2");
        TmcUser tmcUser = tmcApi.getTmcUser(argsMap);

        if(tmcUser == null){
            System.out.println("没有为当前用户开通消息服务");
            return;
        }
        List<String> topics = tmcUser.getTopics();
        if(!CollectionUtils.isEmpty(topics)){
            for (String topic : topics){
                System.out.println(topic);
            }
        }

        System.out.println(tmcUser.getUserId());
        System.out.println(tmcUser.getUserNick());
        System.out.println(tmcUser.getIsValid());
        System.out.println(tmcUser.getCreated());
        System.out.println(tmcUser.getModified());
        System.out.println(tmcUser.getGroupName());
    }

    @Test
    public void test() throws LinkException, InterruptedException {
        TmcClient client = new TmcClient(ConstantTaoBao.TAOBAO_MC_API_URL,ConstantTaoBao.APP_KEY,ConstantTaoBao.APP_SECRET,"default");
        client.setMessageHandler(new MessageHandler() {
            @Override
            public void onMessage(Message message, MessageStatus messageStatus) throws Exception {
                System.out.println(message.getContent());
                System.out.println(message.getTopic());
            }
        });

        client.connect();

        Thread.sleep(1000*60);
    }
}
