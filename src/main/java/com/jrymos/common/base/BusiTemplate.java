package com.jrymos.common.base;

import com.jrymos.common.exception.CommonException;
import com.jrymos.common.util.CommonUtils;
import org.slf4j.Logger;

/**
 * 业务通用模板
 * Created by fly
 */
public class BusiTemplate {

    public static<DATA> CommonResponse<DATA> handle(String method, String desc, Logger logger,
                                        Object requestData, CallBack<DATA> callBack) {
        CommonResponse<DATA> commonResponse = null;
        long start = System.currentTimeMillis();
        try {
            if (logger.isInfoEnabled()) {
                logger.info("{}#{}#start,request：{}", desc, method, CommonUtils.toString(requestData));
            }
            return commonResponse = CommonResponse.success(callBack.call());
        } catch (CommonException e) {
            logger.error("业务异常", e);
            return commonResponse = CommonResponse.error(e.getErrorCode(), e.getErrorMsg());
        } catch (Throwable e) {
            logger.error("系统异常", e);
            return commonResponse = CommonResponse.error();
        } finally {
            logger.info("{}#{}#end,time:{}ms,response:{}", desc, method, System.currentTimeMillis() - start, commonResponse);
        }
    }

    public interface CallBack<DATA> {
        DATA call() throws Throwable;
    }

    public static class CommonResponse<T> {

        /**
         * 系统异常，统一错误码 999
         */
        public static final String FAIL = "999";
        /**
         * 返回成功，统一成功码 200
         */
        public static final String SUCCESS = "200";

        private String errorCode;
        private String errorMsg;
        private T data;


        public boolean success(){
            return SUCCESS.equals(errorCode);
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

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public static<DATA> CommonResponse<DATA> error(String defineCode, String chineseMsg) {
            CommonResponse<DATA> commonResponse = new CommonResponse<>();
            commonResponse.setErrorCode(defineCode);
            commonResponse.setErrorMsg(chineseMsg);
            return commonResponse;
        }

        public static<DATA> CommonResponse<DATA> error() {
            CommonResponse<DATA> commonResponse = new CommonResponse<>();
            commonResponse.setErrorCode(FAIL);
            commonResponse.setErrorMsg("系统异常");
            return commonResponse;
        }


        public static <DATA> CommonResponse<DATA> success(DATA t) {
            CommonResponse<DATA> commonResponse = new CommonResponse<>();
            commonResponse.data = t;
            commonResponse.errorCode = SUCCESS;
            commonResponse.errorMsg = "success";
            return commonResponse;
        }

        @Override
        public String toString() {
            return "CommonResponse{" +
                    "errorCode='" + errorCode + '\'' +
                    ", errorMsg='" + errorMsg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
