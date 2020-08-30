package com.gitee.nihaoa.utils;

import com.gitee.nihaoa.exception.M3u8Exception;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientUtil {
  private static OkHttpClient okHttpClient;


  public synchronized static OkHttpClient getOkHttpClient() {
    if (okHttpClient == null){
      okHttpClient = new OkHttpClient.Builder().build();
    }
    return okHttpClient;
  }

  public static Response sendGetRequest(String url, int responseCode) {
    Response response;
    try {
      Request request = new Request.Builder().url(url).build();
      response = getOkHttpClient().newCall(request).execute();
      if (response.isSuccessful()) {
        if (response.code() != responseCode){
          throw new M3u8Exception("响应码异常：" + response.code());
        }
      }else {
        throw new M3u8Exception("HTTP请求失败");
      }

    }catch (Exception e){
      e.printStackTrace();
      throw new M3u8Exception("请求失败：" + url);
    }
    return response;
  }
}
