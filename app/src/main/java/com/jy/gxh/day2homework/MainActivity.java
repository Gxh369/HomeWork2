package com.jy.gxh.day2homework;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jy.gxh.day2homework.adapters.MyFPAdapte;
import com.jy.gxh.day2homework.fragments.DownloadFragment;
import com.jy.gxh.day2homework.fragments.HomeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTvToolbar;
    private Toolbar mToolbar;
    private ViewPager mVp;
    private TabLayout mTab;
    private ArrayList<Fragment> fragments;
    private ArrayList<Integer> selectores;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //权限
        checkPermission();
    }

    private void checkPermission() {
        //申请权限
        int i = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int j = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(i!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
        if(j!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
    }

    private void initView() {
        //找控件
        mTvToolbar = (TextView) findViewById(R.id.tv_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mVp = (ViewPager) findViewById(R.id.vp);
        mTab = (TabLayout) findViewById(R.id.tab);
        //初始化Toolbar
        mToolbar.setTitle("");
        mTvToolbar.setText("首页");
        setSupportActionBar(mToolbar);
        setTabItem();
    }

    private void setTabItem() {
        //初始化数据
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new DownloadFragment());
        //初始化选择器
        selectores = new ArrayList<>();
        selectores.add(R.drawable.home_selector);
        selectores.add(R.drawable.download_selector);
        //添加标题
        titles = new ArrayList<>();
        titles.add("首页");
        titles.add("下载");
        //创建FragmentPager 适配器
        MyFPAdapte adapte = new MyFPAdapte(getSupportFragmentManager(), fragments);
        mVp.setAdapter(adapte);
        mTab.setupWithViewPager(mVp);
        //循环设置Tabitem
        for (int i = 0; i < fragments.size(); i++) {
            mTab.getTabAt(i).setText(titles.get(i)).setIcon(selectores.get(i));
        }
        //设置Tabitem选中时 改变TextView
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mTvToolbar.setText("首页");
                        break;
                    case 1:
                        mTvToolbar.setText("下载");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
