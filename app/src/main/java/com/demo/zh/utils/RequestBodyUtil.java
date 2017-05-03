package com.demo.zh.utils;

import com.demo.zh.API.APIService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author zhongzhen
 * @date 2017/5/3
 * @describe 当{@link APIService}中接口的注解为{@link retrofit2.http.Multipart}时，参数为{@link RequestBody}
 * 生成对应的RequestBody
 **/
public class RequestBodyUtil {
    public static RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public static RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public static RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    public static RequestBody createRequestBody(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }
}
