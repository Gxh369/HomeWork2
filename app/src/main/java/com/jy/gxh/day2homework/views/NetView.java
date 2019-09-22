package com.jy.gxh.day2homework.views;

import com.jy.gxh.day2homework.beans.HomeBean;

import java.util.ArrayList;

/**
 * Created by GXH on 2019/9/19.
 */

public interface NetView {
    void addDatas(ArrayList<HomeBean.DataBean.DatasBean> datasBeans);
    void showToast(String string);

}
