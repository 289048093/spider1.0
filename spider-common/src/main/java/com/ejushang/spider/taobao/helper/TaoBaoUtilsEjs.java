package com.ejushang.spider.taobao.helper;

import com.ejushang.spider.taobao.listener.TestConnLifeCycleListener;
import com.ejushang.spider.taobao.listener.TestTopCometMessageListener;
import com.taobao.api.internal.stream.Configuration;
import com.taobao.api.internal.stream.TopCometStream;
import com.taobao.api.internal.stream.TopCometStreamFactory;
import com.taobao.api.internal.stream.TopCometStreamRequest;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Baron.Zhang
 * Date: 13-12-13
 * Time: 下午4:31
 */
public class TaoBaoUtilsEjs {

    /**
     * 建立主动通知连接<br/>
     * 连接方式1）：客户端只需要建立单个连接，不指定连接标识。
     */
    public static void notificate1(){
        Configuration configuration = new Configuration(ConstantTaoBao.APP_KEY, ConstantTaoBao.APP_SECRET,null);
        TopCometStream stream = new TopCometStreamFactory(configuration).getInstance();
        // 设置连接监听器
        stream.setConnectionListener(new TestConnLifeCycleListener());
        // 设置消息推送监听器
        stream.setMessageListener(new TestTopCometMessageListener());
        // 开始连接
        stream.start();
    }

    /**
     * 建立主动通知连接<br/>
     * 连接方式2）：客户端只需要建立单个连接，自己指定连接标识。
     * @param connId
     */
    public static void notificate2(String connId){
        Configuration configuration = new Configuration(ConstantTaoBao.APP_KEY, ConstantTaoBao.APP_SECRET,null,connId);
        TopCometStream stream = new TopCometStreamFactory(configuration).getInstance();
        // 设置连接监听器
        stream.setConnectionListener(new TestConnLifeCycleListener());
        // 设置消息推送监听器
        stream.setMessageListener(new TestTopCometMessageListener());
        // 开始连接
        stream.start();
    }

    /**
     * 建立主动通知连接<br/>
     * 连接方式3）：客户端需要多连接
     */
    public static void notificate3(){
        Set<TopCometStreamRequest> cometReqs = new HashSet<TopCometStreamRequest>();
        // 建立一个连接
        TopCometStreamRequest cometReq1 = new TopCometStreamRequest(ConstantTaoBao.APP_KEY, ConstantTaoBao.APP_SECRET,null,"连接标识1");
        /*
        可选
        为每个连接指定连接监听器和消息监听器，如果为每个连接指定了相对应的监听器，则对此连接会使用指定的监听器，
        否则会使用为TopCometStream 指定的全局监听器,建议为每个连接指定自己的监听器，方便将来查找问题，
        在指定监听器时最好能做到很容器的区分出来不同连接的监听器
        */
        cometReq1.setConnectListener(new TestConnLifeCycleListener());
        cometReq1.setMsgListener(new TestTopCometMessageListener());

        TopCometStreamRequest cometReq2 = new TopCometStreamRequest(ConstantTaoBao.APP_KEY, ConstantTaoBao.APP_SECRET,null,"连接标识2");
        cometReq2.setConnectListener(new TestConnLifeCycleListener());
        cometReq2.setMsgListener(new TestTopCometMessageListener());

        cometReqs.add(cometReq1);
        cometReqs.add(cometReq2);

        Configuration conf = new Configuration(cometReqs);
        TopCometStream stream = new TopCometStreamFactory(conf).getInstance();

        /*如果为每个连接指定了自己的监听器，则这里可以不指定，如果没有为每个连接指定自己的监听器，
        则在这里需要设定全局监听器，每个连接都将使用这个全局监听器*/
        stream.setConnectionListener(new TestConnLifeCycleListener());
        stream.setMessageListener(new TestTopCometMessageListener());
        stream.start();


    }

    /**
     * 获取session key
     * @return
     */
    public static String getSessionKey(){
        return "6100920318ce338cc348c53eb6b113afdb4604383c56dc12074082786";
    }
}
