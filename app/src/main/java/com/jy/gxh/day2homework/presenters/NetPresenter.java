package com.jy.gxh.day2homework.presenters;

import com.jy.gxh.day2homework.beans.HomeBean;
import com.jy.gxh.day2homework.model.NetModel;
import com.jy.gxh.day2homework.views.NetView;

import java.util.ArrayList;

/**
 * Created by GXH on 2019/9/19.
 */

public class NetPresenter implements NetModel.MyCallBack {
    private NetView netView;
    private NetModel netModel;
    public NetPresenter(NetView netView) {
        this.netView = netView;
        this.netModel=new NetModel(this);
    }

    public void setDatas() {
        netModel.setDatas();
    }

    @Override
    public void onSucceed(ArrayList<HomeBean.DataBean.DatasBean> datasBeans) {
        netView.addDatas(datasBeans);
    }

    @Override
    public void onFiled(String string) {
        netView.showToast(string);
    }
}
