package com.jrymos.common.exception;

/**
 *
 */
public class BaseException extends RuntimeException {
    /**
     * @Fields errorCode : 错误码
     */
    private String errorCode;
    /**
     * @Fields errorMsg : 错误信息
     */
    private String errorMsg;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String errorCode, String errorMsg) {
        super(toExceptionDesc(errorCode, errorMsg));
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BaseException(String errorCode, String errorMsg, Throwable e) {
        super(toExceptionDesc(errorCode, errorMsg), e);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 异常描述
     * @param errorCode
     * @param errorMsg
     * @return
     */
    protected static String toExceptionDesc(String errorCode, String errorMsg) {
        return new StringBuilder()
                .append("[errorCode=").append(errorCode)
                .append(", errorMsg=").append(errorMsg)
                .append("]").toString();
    }
}
