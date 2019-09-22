package com.jy.gxh.day2homework;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.jy.gxh.day2homework.beans.MsgBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
//        ok下载
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://cdn.banmi.com/banmiapp/apk/banmi_330.apk")
                .get().build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("tag", "onFailure: "+e.getMessage());
                EventBus.getDefault().post(new MsgBean(0,0,0,null));
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                long max = response.body().contentLength();
                initDownLoad(inputStream,max);
            }
        });

//    Retrofit下载
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ApiService.DOWNLOAD_URL)
//                .build();
//        ApiService apiService = retrofit.create(ApiService.class);
//        apiService.getCall().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                final InputStream inputStream = response.body().byteStream();
//                final long max = response.body().contentLength();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            initDownLoad(inputStream,max);
//                        }
//                    }).start();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.i("tag", "onFailure: "+t.getMessage());
//                EventBus.getDefault().post(new MsgBean(0,0,0,null));
//            }
//        });
        super.onCreate();
    }

    private void initDownLoad(InputStream inputStream, long max) {
        String s = Environment.getExternalStorageDirectory().getPath() + "/a.apk";
        File file = new File(s);
        int len=0;
        byte bytf[]=new byte[4096];
        int count=0;
        FileOutputStream out=null;
        try {
           out= new FileOutputStream(file);
           while ((len=inputStream.read(bytf))!=-1){
                count+=len;
                out.write(bytf,0,len);
                EventBus.getDefault().post(new MsgBean(1,count,max,s));
               Thread.sleep(1);
           }
            EventBus.getDefault().post(new MsgBean(2,count,max,s));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (out != null) {
                    out.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
