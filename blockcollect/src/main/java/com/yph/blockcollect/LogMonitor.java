package com.yph.blockcollect;

import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by _yph on 2018/3/14 0014.
 */

public class LogMonitor {

    private int timeBlock = 800;
    private int frequency = 6;
    private boolean toBugly;

    private AtomicBoolean isReStart = new AtomicBoolean();
    private static LogMonitor sInstance = new LogMonitor();
    private Handler mIoHandler;

    private LogMonitor() {
        HandlerThread mLogThread = new HandlerThread("yph");
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper());
    }

    private Runnable mLogRunnable = new Runnable() {
        private HashMap<String,StackTraceElement[]> stackMap = new HashMap<>();
        private String lastStackTraceStr;
        private int time = frequency;
        @Override
        public void run() {
            if(Debug.isDebuggerConnected())return;
            if(isReStart.compareAndSet(true,false)){
                stackMap.clear();
                lastStackTraceStr = null;
                time = frequency;
            }
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            String stackTraceStr = StackTraceUtil.toSimpleString(stackTrace);
            if (!TextUtils.isEmpty(stackTraceStr)
                    && !TextUtils.isEmpty(lastStackTraceStr)
                    && lastStackTraceStr.equals(stackTraceStr)
                    && !stackMap.containsKey(stackTraceStr)){//连续两次相同的堆栈才保存,捕捉耗时方法
                stackMap.put(stackTraceStr,stackTrace);
            }
            time -- ;
            if(time == 0) {
                if(!TextUtils.isEmpty(stackTraceStr)
                        && stackMap.isEmpty()){//未捕捉到耗时方法，说明发生了执行连续多个不耗时方法所造成的卡顿
                    stackMap.put(stackTraceStr,stackTrace);//取最后一个卡顿堆栈用于分析
                }
                if(!stackMap.isEmpty()) {
                    for (Map.Entry<String, StackTraceElement[]> entry : stackMap.entrySet()) {
                        Log.e("BlockCollect", entry.getKey());
                        if (toBugly) {
                            BuglyCar.push(entry.getValue());
                        }
                    }
                }
                isReStart.set(true);
            }else {
                lastStackTraceStr = stackTraceStr;
                mIoHandler.postDelayed(mLogRunnable, timeBlock / frequency);
            }
        }
    };

    static LogMonitor get() {
        return sInstance;
    }

    void reStartMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable);
        isReStart.set(true);
        mIoHandler.postDelayed(mLogRunnable, timeBlock / frequency);
    }

    void setParam(int timeBlock , int frequency) {
        this.timeBlock = timeBlock;
        this.frequency = frequency;
    }

    public void setToBugly(boolean toBugly) {
        this.toBugly = toBugly;
    }
}
