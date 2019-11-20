package com.highgreat.sven.retrofit;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.highgreat.sven.retrofit.net.RetrofitNetUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("upobject", 2 + ""); //升级对象(0-android 1-IOS 2-固件 3-系统 4-遥控器)
        paramsMap.put("vname",0 +""); //当前dobby版本，即最近一次连接的飞机的固件版本

        RetrofitNetUtils utils = new RetrofitNetUtils();
        Call<String> stringCall = utils.getNetInterface().get("Apps/version", paramsMap,getHeaders());

        if(stringCall!=null){
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        if(call.isExecuted()){
                            Log.i("Sven",""+response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }

    }


    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("sendsessid", "0");
        headers.put("v", getVersionString());
        return headers;
    }

    public String getVersionString(){
        StringBuilder stringBuilder = new StringBuilder();
        int interfaceModule = 11 ;
        String currentapiVersion=android.os.Build.VERSION.RELEASE;
        String mobileModels = android.os.Build.MODEL.replace("-","");
        String versionStr = stringBuilder
                .append("2-")
                .append(1 + "-")
                .append(getVersionName(getApplicationContext()) + "-")
                .append(2 + "-")
                .append(interfaceModule+"-")
                .append(currentapiVersion+"-")//系统版本
                .append(mobileModels+"-")//手机型号
                .append(4)
                .toString();

        return  versionStr;
    }

    public String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    private  PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}
