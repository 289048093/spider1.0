package com.ejushang.spider.exception;

/**
 * User: amos.zhou
 * Date: 13-10-15
 * Time: 上午9:49
 */
public class GenerateException extends ErpException {



    public GenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
