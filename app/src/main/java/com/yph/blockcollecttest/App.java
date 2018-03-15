package com.yph.blockcollecttest;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.yph.blockcollect.BlockDetect;

/**
 * Created by yph on 2018/3/15.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(this, "你的AppId", false);
        BlockDetect.start(true);//是否开车
       // BlockDetect.start(true,1000,6);//1是否开车，2阈值，3采样
    }
}
