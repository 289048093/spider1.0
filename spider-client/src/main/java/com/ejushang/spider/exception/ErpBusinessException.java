package com.ejushang.spider.exception;

/**
 * ERP项目业务异常
 * User: liubin
 * Date: 14-1-10
 */
public class ErpBusinessException extends RuntimeException {

    public ErpBusinessException() {
        super();
    }

    public ErpBusinessException(String message) {
        super(message);
    }

    public ErpBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErpBusinessException(Throwable cause) {
        super(cause);
    }

    /** 解决 Exception 所谓的性能差的问题. add by Athens on 2014.1.8 */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
