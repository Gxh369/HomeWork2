package com.jy.gxh.day2homework.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jy.gxh.day2homework.R;
import com.jy.gxh.day2homework.adapters.MyRecyAdapter;
import com.jy.gxh.day2homework.beans.HomeBean;
import com.jy.gxh.day2homework.presenters.NetPresenter;
import com.jy.gxh.day2homework.views.NetView;

import java.util.ArrayList;

import io.reactivex.internal.util.ArrayListSupplier;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements NetView {


    private View view;
    /**
     * Home
     */
    private RecyclerView mRecy;
    private ArrayList<HomeBean.DataBean.DatasBean> mlist;
    private MyRecyAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //找控件
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        //初始化多布局
        mRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        mlist = new ArrayList<>();
        adapter = new MyRecyAdapter(getActivity(), mlist);
        mRecy.setAdapter(adapter);
        //调用P层
        NetPresenter presenter = new NetPresenter(this);
        presenter.setDatas();


    }

    @Override
    public void addDatas(ArrayList<HomeBean.DataBean.DatasBean> datasBeans) {
        //P层回传View层成功获得数据  添加到集合 刷新适配器
        this.mlist.addAll(datasBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String string) {
        //请求失败 吐司
        Toast.makeText(getActivity(),string,Toast.LENGTH_SHORT).show();
    }
}
