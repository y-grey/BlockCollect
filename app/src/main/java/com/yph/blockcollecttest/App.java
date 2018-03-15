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
        CrashReport.initCrashReport(this, "*****", false);
        BlockDetect.start(true);//BlockDetect.start(true,800,6);
    }
}
