package com.demo.zh.dto;

import com.demo.zh.utils.Constant;

/**
 * @author zhongzhen
 * @date 2017/5/3
 * @describe 返回数据对象
 **/
public class Response<T> {
    private String resultcode;
    private String reason;
    private T result;
    private String error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public boolean isSuccess() {
        return resultcode.equals(Constant.OK);
    }
}
