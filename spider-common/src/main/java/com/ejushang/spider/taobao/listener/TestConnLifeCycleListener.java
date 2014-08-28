package com.ejushang.spider.taobao.listener;

import com.taobao.api.internal.stream.connect.ConnectionLifeCycleListener;

/**
 * User: Baron.Zhang
 * Date: 13-12-20
 * Time: 上午11:44
 */
public class TestConnLifeCycleListener implements ConnectionLifeCycleListener {

    @Override
    public void onBeforeConnect() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onException(Throwable throwable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onMaxReadTimeoutException() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
