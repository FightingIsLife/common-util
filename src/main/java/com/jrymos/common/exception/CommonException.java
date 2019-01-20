package com.jrymos.common.exception;


import java.util.Arrays;

/**
 * Created by fly
 */
public class CommonException extends BaseException {
    /**
     * 第三方返回错误码
     */
    private String thirdErrorCode;
    /**
     * 第三方返回错误信息
     */
    private String thirdErrorMsg;

    public CommonException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public CommonException(String errorCode, String errorMsg, Throwable e) {
        super(errorCode, errorMsg, e);
    }

    public CommonException(CommonExceptionEnum exceptionEnum) {
        this(exceptionEnum.getErrorCode(), exceptionEnum.getErrorMsg());
    }

    public CommonException(CommonExceptionEnum exceptionEnum, String errorMsg) {
        this(exceptionEnum.getErrorCode(), errorMsg);
    }

    public CommonException(CommonExceptionEnum exceptionEnum, Throwable e) {
        this(exceptionEnum.getErrorCode(), exceptionEnum.getErrorMsg(), e);
    }

    public CommonException(CommonExceptionEnum exceptionEnum, String thirdErrorCode, String thirdErrorMsg) {
        super(exceptionEnum.toExceptionDesc(thirdErrorCode, thirdErrorMsg));
        this.thirdErrorCode = thirdErrorCode;
        this.thirdErrorMsg = thirdErrorMsg;
    }

    public static <V> V noElement(Object... msg) {
        throw new CommonException(CommonExceptionEnum.NO_ELEMENT_ERROR.getErrorCode(), Arrays.toString(msg));
    }

    public static <T> T tryCatchThrowable(TryCatch<T> doSomeThing, CommonExceptionEnum commonExceptionEnum) throws Exception {
        try {
            return doSomeThing.doSomeThing();
        } catch (Exception e) {
            throw e;
        } catch (Throwable throwable) {
            throw new CommonException(commonExceptionEnum, throwable);
        }
    }

    public static <T> T tryCatchThrowable(TryCatch<T> doSomeThing) throws Exception {
        return tryCatchThrowable(doSomeThing, CommonExceptionEnum.SYS_ERROR);
    }

    public static <T> T tryCatch(TryCatch<T> doSomeThing, CommonExceptionEnum commonExceptionEnum) {
        try {
            return doSomeThing.doSomeThing();
        } catch (CommonException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new CommonException(commonExceptionEnum, throwable);
        }
    }

    public static <T> T tryCatch(TryCatch<T> doSomeThing) {
        return tryCatch(doSomeThing, CommonExceptionEnum.SYS_ERROR);
    }

}
