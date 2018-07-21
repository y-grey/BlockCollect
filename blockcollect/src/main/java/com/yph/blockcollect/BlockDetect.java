package com.yph.blockcollect;

import android.util.Log;
import android.view.Choreographer;

/**
 * Created by _yph on 2018/3/14 0014.
 */

public class BlockDetect {

    public static void start(boolean toBugly, int timeBlock, int frequency) {
        LogMonitor.get().setParam(timeBlock, frequency);
        start(toBugly);
    }

    public static void start(boolean toBugly) {
        LogMonitor.get().setToBugly(toBugly);
        LogMonitor.get().reStartMonitor();
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                LogMonitor.get().reStartMonitor();
                plusSM();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    private static long nowTime = 1;
    private static int sm = 1;

    private static void plusSM() {
        long t = System.currentTimeMillis();
        if (nowTime == 1) {
            nowTime = t;
        }
        if (nowTime / 1000 == t / 1000) {
            sm++;
        } else if (t / 1000 - nowTime / 1000 >= 1) {
            Log.e("BlockCollect", "sm：" + sm);
            sm = 1;
            nowTime = t;
        }
    }
}
