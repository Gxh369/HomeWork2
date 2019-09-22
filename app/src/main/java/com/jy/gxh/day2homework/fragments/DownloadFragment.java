package com.jy.gxh.day2homework.fragments;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.gxh.day2homework.ApkInstallUtil;
import com.jy.gxh.day2homework.MyService;
import com.jy.gxh.day2homework.R;
import com.jy.gxh.day2homework.SetupActivity;
import com.jy.gxh.day2homework.beans.MsgBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment implements View.OnClickListener {


    private View view;
    private ProgressBar mPb;
    private TextView mTvPlan;
    /**
     * 开始下载
     */
    private Button mStart;
    private String setUpPath;

    public DownloadFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setProgress(MsgBean msg){
        //接受EventBus 发送的消息
        switch (msg.getType()) {
            //判断类型 执行不同的操作
            case 0:
                //下载失败
                Toast.makeText(getActivity(),"下载失败",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                //下载中
                mPb.setMax((int)msg.getMax());
                mPb.setProgress(msg.getProgress());
                //计算当前下载进度
                float count=(float) msg.getProgress()/msg.getMax()*100;
                Log.i("tag", "setProgress: "+count);
                mTvPlan.setText("当前下载进度:"+(int)count+"%");
                break;
            case 2:
                //下载完成
                Toast.makeText(getActivity(),"下载成功",Toast.LENGTH_SHORT).show();
                mStart.setText("安装");
                //发送通知
                initNotification(msg.getPath());
                //下载完成后保存状态和 进度
                SharedPreferences giao = getActivity().getSharedPreferences("giao", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = giao.edit();
                edit.putString("path",msg.getPath());
                edit.putBoolean("stup",true);
                edit.putInt("plan",msg.getProgress());
                edit.commit();
                break;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //找控件
        mPb = (ProgressBar) view.findViewById(R.id.pb);
        mTvPlan = (TextView) view.findViewById(R.id.tv_plan);
        mStart = (Button) view.findViewById(R.id.start);
        mStart.setOnClickListener(this);
        //获取进度
        judgeProgress();
    }

    private void judgeProgress() {
        //获取保存的状态
        SharedPreferences giao = getActivity().getSharedPreferences("giao", Context.MODE_PRIVATE);
        boolean stup = giao.getBoolean("stup", false);
        //判断状态
        if (stup) {
            setUpPath = giao.getString("path", null);
            int plan = giao.getInt("plan", 0);
            //防止  文件被误删  再次判断文件是否存在
            File file = new File(setUpPath);
            if (file.exists()) {
                //根据 文件下载的状态 改变按钮的状态
                if (setUpPath != null&&plan>0) {
                    mStart.setText("安装");
                    mPb.setMax(plan);
                    mPb.setProgress(plan);
                    mTvPlan.setText("当前下载进度:100%");
                }
            }else {
                //不存在改变为下载状态
                mStart.setText("开始下载");
                mTvPlan.setText("当前下载进度:0%");
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if(mStart.getText().toString().equals("安装")) {
                    //判断按钮的状态  是安装 就执行安装
                    Intent intent = new Intent(getActivity(), SetupActivity.class);
                    //发送路径
                    intent.putExtra("path", setUpPath);
                    getActivity().startActivity(intent);
                }else {
                    //否则就开启服务下载
                    getActivity().startService(new Intent(getActivity(),MyService.class));
                }
                break;
        }
    }

    private void initNotification(String path) {
        //创建意图 指向安装界面
        Intent intent = new Intent(getActivity(), SetupActivity.class);
        //发送路径
        intent.putExtra("path",path);
        //封装为延时意图
        PendingIntent activity = PendingIntent.getActivity(getActivity(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //通知管理器
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId="1";
        String channelName="a";
        //通道
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        //创建通知
        Notification build = new NotificationCompat.Builder(getActivity(), channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("提示")
                .setContentIntent(activity)
                .setAutoCancel(true)
                .setContentText("下载完毕,点击安装")
                .build();
        //发送通知
        manager.notify(1,build);
    }

    @Override
    public void onDestroy() {
        //结束服务  和 EventBus
        getActivity().stopService(new Intent(getActivity(),MyService.class));
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
