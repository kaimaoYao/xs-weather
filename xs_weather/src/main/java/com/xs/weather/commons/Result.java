package com.xs.weather.commons;

/**
 * Copyright (C)
 * FileName: Result
 * Author:   maokai
 * Date:     2022/4/28 11:56
 * Description:
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 返回结果
 */
@ApiModel("返回结果")
public class Result extends HashMap<String, Object> implements Serializable {
    static final String dataKey = "data";
    @ApiModelProperty(hidden = true)
    static final String codeKey = "code";
    @ApiModelProperty(hidden = true)
    static final String messageKey = "message";
    @ApiModelProperty(hidden = true)
    static final String successKey = "success";
    @ApiModelProperty(hidden = true)
    static final String exceptionKey = "exception";
    @ApiModelProperty(hidden = true)
    static final int stateCode = 200;
    @ApiModelProperty(hidden = true)
    static final String successDesc = "成功";
    @ApiModelProperty(hidden = true)
    static final String failDesc = "失败";

    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "是否正常返回")
    private boolean success;
    @ApiModelProperty(value = "异常信息")
    private RuntimeException runtimeException;

    public Result() {
        this.code = stateCode;
        this.success = true;
        this.message = successDesc;
        this.put(codeKey,stateCode);
        this.put(messageKey,successDesc);
        this.put(successKey,true);
    }
    public Result(boolean result) {
        if(result) {
            this.code = stateCode;
            this.message = successDesc;
        }else{
            this.code = stateCode;
            this.message = failDesc;
        }
        this.success = result;

        this.put(codeKey,code);
        this.put(messageKey,success);
        this.put(successKey,result);
    }
    public Result(String message) {
        this.code = stateCode;
        this.success = true;
        this.message = message;
        this.put(codeKey,stateCode);
        this.put(messageKey,message);
        this.put(successKey,true);
    }

    public Result(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.put(codeKey,code);
        this.put(messageKey,message);
        this.put(successKey,success);
    }

    public Result(RuntimeException runtimeException) {
        this.code = 500;
        this.success = false;
        if (runtimeException != null) {
            this.message = runtimeException.getMessage();
        }
        this.runtimeException = runtimeException;

        this.put(codeKey, this.code);
        this.put(successKey,this.success);
        this.put(messageKey, this.message);
        this.put(exceptionKey,runtimeException);
    }

    public Result(String message, RuntimeException runtimeException) {
        this.code = 500;
        this.success = false;
        if (StringUtils.isEmpty(message) && runtimeException != null) {
            this.message = runtimeException.getMessage();
        } else {
            this.message = message;
        }
        this.runtimeException = runtimeException;

        this.put(codeKey, this.code);
        this.put(successKey,this.success);
        this.put(messageKey, this.message);
        this.put(exceptionKey,this.runtimeException);
    }

    public static Result success() {
        return new Result(stateCode, successDesc, true);
    }

    public static Result success(Object data) {
        return new Result(stateCode, successDesc, true).data(data);
    }

    public static Result success(String message) {
        return new Result(stateCode, message, true);
    }

    public static Result fail() {
        return new Result(stateCode, failDesc, false);
    }

    public static Result status(boolean result) {
        return new Result(result);
    }

    public static Result fail(String message) {
        return new Result(stateCode, message, false);
    }

    public static Result fail(RuntimeException runtimeException) {
        return new Result(runtimeException);
    }

    public Result data(Object data) {
        this.put(dataKey, data);
        return this;
    }
    public Result data(String key, Object data) {
        this.put(key, data);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RuntimeException getRuntimeException() {
        return runtimeException;
    }

    public void setRuntimeException(RuntimeException runtimeException) {
        this.runtimeException = runtimeException;
    }

}
