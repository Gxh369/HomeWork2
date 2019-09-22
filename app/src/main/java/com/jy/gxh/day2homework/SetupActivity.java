package com.jy.gxh.day2homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        //获取意图 获取发送的路径  安装路径  关闭空白页面
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        ApkInstallUtil.installApk(this,path);
        finish();
    }
}
