package com.ejushang.spider.erp.main;

import com.ejushang.spider.domain.OriginalOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 源订单队列持有者,管理抓取与分析线程的交互
 * User: liubin
 * Date: 13-12-27
 */
@Component
public class OriginalOrderHolder {

    private final Logger log = LoggerFactory.getLogger(OriginalOrderHolder.class);

    public static final int MAX_MESSAGE_COUNTS = 10000;

    private LinkedBlockingQueue<List<OriginalOrder>> queue = new LinkedBlockingQueue<List<OriginalOrder>>(MAX_MESSAGE_COUNTS);

    /**
     * 往队列放入元素
     * @param msg
     * @return
     */
    public boolean put(List<OriginalOrder> msg) {

        try {
            queue.offer(msg, 10, TimeUnit.SECONDS);
            return true;
        } catch (InterruptedException e) {
            log.warn("往队列放消息的阻塞过程被中断,队列已满?", e);
        }
        return false;
    }

    /**
     * 取出队列中的所有元素
     * @return
     */
    public List<List<OriginalOrder>> fetchAll() {
        List<List<OriginalOrder>> allMessages = new ArrayList<List<OriginalOrder>>();
        queue.drainTo(allMessages);
        return allMessages;
    }

}
