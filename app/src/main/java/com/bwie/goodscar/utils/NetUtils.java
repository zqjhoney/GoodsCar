package com.bwie.goodscar.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 张乔君 on 2017/10/8.
 */

public class NetUtils {

    public static void getResult(String url, final CallResult callResult){
//        OkHttpClient client = SingleOk.getInstence()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(false)
//                .build();
        OkHttpClient client =new OkHttpClient.Builder().build();
        FormBody.Builder build=new FormBody.Builder();
        RequestBody body=build.build();
        Request request=new Request.Builder().post(body).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(callResult!=null){
                    callResult.onFailure(call,e);
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(callResult!=null) {
                    callResult.onResponse(call, response);
                }
            }
        });


    }

    public interface CallResult{
        void onFailure(Call call, IOException e);
      void onResponse(Call call, Response response);
    }

}
