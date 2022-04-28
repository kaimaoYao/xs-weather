package com.xs.weather.utils;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright (C)
 * FileName: OkHttpClientUtils
 * Author:   maokai
 * Date:     2020/12/3 8:53
 * Description:
 */
@Log4j2
public class OkHttpClientUtils {
    static final int CODE = 0;
    static final int CONSOLE_CODE =200;
    static final String MESSAGE = "success";
    public static OkHttpClient client = new OkHttpClient();

    /**
     * POST 请求
     * @param data 数据
     * @param url api地址
     * @param headers 请求头
     * @return
     * @throws IOException
     */
    public String post(Map<String, Object> data, String url, Headers headers) throws IOException {
        //此处添加签名
        if (ObjectUtil.isEmpty(data)) {
            data = Maps.newHashMap();
        }
        byte[] bytes = getParams(data);
        log.info("请求api-----> {}",url);
        log.info("data-----> {}",JSONObject.toJSONString(data));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), bytes);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(headers)
                .build();

        final Call call = client.newCall(request);
        Response response = null;
        response = call.execute();
        if (Objects.isNull(response)) {
            throw new RuntimeException("response返回空消息体");
        }
        ResponseBody body = response.body();
        if (Objects.isNull(body)) {
            throw new RuntimeException("responseBody返回空");
        }
        return body.string();
    }

    /**
     * POST BODY
     * @param data
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public String postBody(Map<String, Object> data, String url, Headers headers) throws IOException {
        //此处添加签名
        if (ObjectUtil.isEmpty(data)) {
            data = Maps.newHashMap();
        }
        String json = JSONObject.toJSONString(data);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(headers)
                .build();
        final Call call = client.newCall(request);
        Response response = null;
        response = call.execute();
        log.info("请求api-----> {}",url);
        log.info("请求headers-----> {}",JSONObject.toJSONString(headers));
        log.info("data-----> {}",JSONObject.toJSONString(data));
        if (Objects.isNull(response)) {
            throw new RuntimeException("response返回空消息体");
        }
        ResponseBody body = response.body();
        if (Objects.isNull(body)) {
            throw new RuntimeException("responseBody返回空");
        }
        return body.string();
    }
    /**
     * GET 提交
     * @param data 数据
     * @param url api地址
     * @param headers 请求头
     * @return
     * @throws IOException
     */
    public String get(Map<String, Object> data, String url, Headers headers) throws IOException {
        if (Objects.isNull(headers)) {
            headers.newBuilder().set("Content-type", "application/x-www-form-urlencoded");
        }
        String params = appendParams(data);
        if (StringUtils.isNoneBlank(params)) {
            url = url.concat("?").concat(params);
        }
        log.info("请求api-----> {}",url);
        log.info("请求headers-----> {}",JSONObject.toJSONString(headers));
        Request request = new Request.Builder()
                .url(url)
                .get()
                .headers(headers)
                .build();
        final Call call = client.newCall(request);
        Response response = null;
        response = call.execute();
        if (Objects.isNull(response)) {
            throw new RuntimeException("response返回空消息体");
        }
        ResponseBody body = response.body();
        if (Objects.isNull(body)) {
            throw new RuntimeException("ResponseBody返回空");
        }
        return body.string();
    }

    /**
     * 文件上传
     *
     * @param url api地址
     * @param headers 请求头
     * @param streamFile 上传的文件
     */
    public String upload(String url, Headers headers, File streamFile) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", streamFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), streamFile))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();
        final Call call = client.newCall(request);
        Response response = null;
        response = call.execute();
        return response.body().string();

    }


    private byte[] getParams(Map<String, Object> params) throws IOException {
        if (ObjectUtil.isEmpty(params)) {
            return new byte[]{};
        }
        String appendParams = appendParams(params);
        return appendParams.getBytes(StandardCharsets.UTF_8);

    }

    /**
     * GET参数拼接
     *
     * @param params
     * @return
     * @throws IOException
     */
    private String appendParams(Map<String, Object> params) throws IOException {
        if (ObjectUtil.isEmpty(params)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (builder.length() > 0) {
                builder.append('&');
            }
            builder.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8.name()));
            builder.append('=');
            builder.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8.name()));
        }
        return builder.toString();

    }

}
