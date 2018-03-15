package com.yph.blockcollect;

import android.view.Choreographer;

/**
 * Created by _yph on 2018/3/14 0014.
 */

public class BlockDetect {

    public static void start(boolean toBugly,int timeBlock , int frequency) {
        LogMonitor.getInstance().setParam(timeBlock,frequency);
        start(toBugly);
    }

    public static void start(boolean toBugly) {
        LogMonitor.getInstance().setToBugly(toBugly);
        LogMonitor.getInstance().removeMonitor();
        LogMonitor.getInstance().startMonitor();
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                LogMonitor.getInstance().removeMonitor();
                LogMonitor.getInstance().startMonitor();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }
}
