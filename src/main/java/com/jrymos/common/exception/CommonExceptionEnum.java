package com.jrymos.common.exception;

import java.util.Objects;

/**
 * Created by fly
 */
public enum CommonExceptionEnum {

    //系统异常前缀SYS,数值前两位00
    SYS_ERROR("JRYMOS000001", "系统异常"),
    PARAM_ERROR("JRYMOS000002", "参数错误"),
    INVOKE_THIRD_METHOD_ERROR("JRYMOS000003", "调用第三方系统异常"),
    NO_ELEMENT_ERROR("JRYMOS000004", "获取集合元素异常"),
    NOT_IMPLEMENT_ERROR("JRYMOS000005", "方法没有实现异常"),
    TRY_LOCK_TIME_OUT("JRYMOS000006", "加锁超时异常"),
    EX_INVOKE_EXCEPTION("JRYMOS000007", "外部系统调用异常"),
    ASPECT_ERROR("JRYMOS000008","切面异常,切面使用存在问题"),
    PARAM_TYPE_ERROR("JRYMOS000009", "参数类型错误");
    
    private String errorCode;

    private String errorMsg;

    CommonExceptionEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static CommonExceptionEnum getEnum(String code) {
        for (CommonExceptionEnum exceptionEnum : values()) {
            if (Objects.equals(exceptionEnum.errorCode, code)) {
                return exceptionEnum;
            }
        }

        return null;
    }

    /**
     * 异常描述
     *
     * @param thirdErrorCode 第三方返回错误码
     * @param thirdErrorMsg  第三方返回错误描述
     * @return
     */
    public String toExceptionDesc(String thirdErrorCode, String thirdErrorMsg) {
        return name() +
                "[errorCode=" + this.errorCode +
                ", errorMsg=" + errorMsg + "],[" +
                "thirdErrorCode=" + thirdErrorCode +
                ", thirdErrorMsg=" + thirdErrorMsg +
                "]";
    }
}
