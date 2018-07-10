package com.even.oa.util;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author 11076
 */
public class HttpUtil {

    private static OkHttpClient okHttpClient;
    private static MediaType jsonType;

    static {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .build();
        jsonType = MediaType.parse("application/json; charset=utf-8");
    }


    public static String get() {

        return null;
    }

    public static void post(String url, String json, Callback callback) {

        RequestBody requestBody = RequestBody.create(jsonType, json);
        Request request = new Request.Builder()
                .get()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
