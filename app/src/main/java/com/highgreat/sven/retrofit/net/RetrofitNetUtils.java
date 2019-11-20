package com.highgreat.sven.retrofit.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitNetUtils {

    private static final int TIME_OUT=60;
    public static final String SERVER_IP                      = "https://develop-api.hg-fly.net/";
    private static NetInterface netInterface;

    public RetrofitNetUtils(){
        OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().connectTimeout(TIME_OUT, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(SERVER_IP)//基础url 建议以/结尾
                .addConverterFactory(ScalarsConverterFactory.create())//转换器
                .client(OK_HTTP_CLIENT)
                .build();
        netInterface = retrofit.create(NetInterface.class);
    }

    public NetInterface getNetInterface(){
        return netInterface;
    }

}
