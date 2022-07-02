package com.jiangfeixiang.mpdemo.core.exception;

/**
 * @author zl
 * @create 2022-07-02 10:19
 */
public class ServiceException extends BaseException {

    public ServiceException(String message) {
        this(CommMsgCode.SERVER_ERROR, message);
    }

    public ServiceException(String message, Throwable cause) {
        this(CommMsgCode.SERVER_ERROR, message, cause);
    }

    public ServiceException(MsgCode code) {
        super(code);
    }

    public ServiceException(MsgCode code, String message) {
        super(code, message);
    }

    public ServiceException(MsgCode code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ServiceException(MsgCode code, Throwable cause) {
        super(code, cause);
    }

    public ServiceException(MsgCode code, String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
