package com.ejushang.spider.exception;

/**
 * User: liubin
 * Date: 13-12-30
 */
public class OrderAnalyzeException extends ErpException {

    public OrderAnalyzeException(String msg, Exception e) {
        super(msg, e);
    }

    public OrderAnalyzeException(String msg) {
        super(msg);
    }

    /** 解决 Exception 所谓的性能差的问题. add by Athens on 2014.1.8 */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
