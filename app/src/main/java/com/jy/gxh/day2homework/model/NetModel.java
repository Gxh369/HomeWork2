package com.jy.gxh.day2homework.model;

import android.util.Log;

import com.google.gson.Gson;
import com.jy.gxh.day2homework.ApiService;
import com.jy.gxh.day2homework.beans.HomeBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GXH on 2019/9/19.
 */

public class NetModel {
    private MyCallBack myCallBack;

    public NetModel(MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
    }

    public void setDatas() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.DATAS_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeBean homeBean) {
                        Log.i("tag", "onNext: "+homeBean.toString());
                        ArrayList<HomeBean.DataBean.DatasBean> datas = (ArrayList<HomeBean.DataBean.DatasBean>) homeBean.getData().getDatas();
                        myCallBack.onSucceed(datas);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("tag", "onError: "+e.getMessage());
                        myCallBack.onFiled("请求失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public interface MyCallBack{
        void onSucceed(ArrayList<HomeBean.DataBean.DatasBean> datasBeans);
        void onFiled(String string);
    }
}
