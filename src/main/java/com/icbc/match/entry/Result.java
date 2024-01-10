package com.icbc.match.entry;

import java.io.Serializable;
import java.util.Map;

/**
 * 通用返回对象
 */
public class Result<T> implements Serializable {

    private String retCode;
    private String retMsg;
    private Map<String, Object> params;

    protected Result() {
    }

    protected Result(String code, String message, Map<String, Object> data) {
        this.retCode = code;
        this.retMsg = message;
        this.params = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> Result<T> success() {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(Map<String, Object> data) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }


    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> Result<T> success(Map<String, Object> data, String message) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> Result<T> failed(ResultCode errorCode) {
        return new Result<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param errorMsg  提示信息
     */
    public static <T> Result<T> failed(String errorCode, String errorMsg) {
        return new Result<T>(errorCode, errorMsg, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> failed(String message) {
        return new Result<T>(ResultCode.FAILURE.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> failed() {
        return failed(ResultCode.FAILURE);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> Result<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILURE);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> validateFailed(String message) {
        return new Result<T>(ResultCode.VALIDATE_FAILURE.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> Result<T> unauthorized(Map<String, Object> data) {
        return new Result<T>(ResultCode.AUTH_FAIL.getCode(), ResultCode.AUTH_FAIL.getMessage(), data);
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}