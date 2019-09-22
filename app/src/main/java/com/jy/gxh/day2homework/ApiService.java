package com.jy.gxh.day2homework;

import com.jy.gxh.day2homework.beans.HomeBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by GXH on 2019/9/19.
 */

public interface ApiService {
    String DATAS_URL="https://www.wanandroid.com/";
    @GET("project/list/1/json?cid=294")
    Observable<HomeBean> getData();

    String DOWNLOAD_URL="https://cdn.banmi.com/";
    @GET("banmiapp/apk/banmi_330.apk")
    Call<ResponseBody> getCall();


}
