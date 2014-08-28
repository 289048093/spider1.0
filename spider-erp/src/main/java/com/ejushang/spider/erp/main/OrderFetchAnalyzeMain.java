package com.ejushang.spider.erp.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 订单抓取分析程序启动类
 * User: liubin
 * Date: 13-12-26
 */
public class OrderFetchAnalyzeMain {

    private static final String[] SPRING_CFG_FILES = new String[]{"/spring-erp.xml", "/order-fetch.xml"};

    private static final Logger log = LoggerFactory.getLogger(OrderFetchAnalyzeMain.class);

    public static void main(String[] args) {
        int port = 8098;
        if(!available(port)) {
            log.error(String.format("端口[%d]已经被使用,有订单抓取分析进程没有关闭?", port));
            return;
        }

        ApplicationContext context = new ClassPathXmlApplicationContext(SPRING_CFG_FILES);
        OrderHouseKeeper orderHouseKeeper = context.getBean(OrderHouseKeeper.class);
        orderHouseKeeper.start();
        log.warn("订单抓取分析程序已经启动,可以通过浏览器访问端口[%d]进行程序的管理", port);
    }



    /**
     * The minimum server currentMinPort number. Set at 1100 to avoid returning privileged
     * currentMinPort numbers.
     */
    public static final int MIN_PORT_NUMBER = 1100;
    /**
     * The maximum server currentMinPort number.
     */
    public static final int MAX_PORT_NUMBER = 49151;
    /**
     * Checks to see if a specific port is available.
     *
     * @param port the port to check for availability
     */
    public static boolean available(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                /* should not be thrown */
                }
            }
        }

        return false;
    }

}
