package com.ejushang.spider.exception;

/**
 * User: liubin
 * Date: 14-2-7
 */
public class ErpException extends Exception {
    public ErpException() {
        super();
    }

    public ErpException(String message) {
        super(message);
    }

    public ErpException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErpException(Throwable cause) {
        super(cause);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
