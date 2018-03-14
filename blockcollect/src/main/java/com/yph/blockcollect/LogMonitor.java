package com.yph.blockcollect;

import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by _yph on 2018/3/14 0014.
 */

public class LogMonitor {

    private static final int TIME_BLOCK = 600;//阈值
    private static final int FREQUENCY = 6;//采样频率

    private static LogMonitor sInstance = new LogMonitor();
    private HandlerThread mLogThread = new HandlerThread("yph");
    private Handler mIoHandler;

    private LogMonitor() {
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper());
    }

    private Runnable mLogRunnable = new Runnable() {

        int time = FREQUENCY;
        List<String> list = new ArrayList();
        HashMap<String,StackTraceElement[]> hashMap = new HashMap();
        @Override
        public void run() {
            if(Debug.isDebuggerConnected())return;
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            list.add(sb.toString());
            hashMap.put(sb.toString(),stackTrace);
            time -- ;
            if(time == 0) {
                time = FREQUENCY;
                reList(list);
                for(String s : list) {
                    Log.e("BlockDetectUtil", s);
                    BuglyCar.push(hashMap.get(s));
                }
                list.clear();
                hashMap.clear();
            }else
                mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK/FREQUENCY);
        }
    };
    private static void reList(List<String> list){
        List<String> reList = new ArrayList<>();
        String lastLog = "";
        for(String s : list){
            if(s.equals(lastLog) && !reList.contains(s)) {
                reList.add(s);
            }
            lastLog = s;
        }
        list.clear();
        list.addAll(reList);
    }
    public static LogMonitor getInstance() {
        return sInstance;
    }

    public void startMonitor() {
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK/FREQUENCY);
    }

    public void removeMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable);
    }
}
