package com.yph.blockcollect;

import android.os.Looper;
import android.util.Log;

/**
 * Created by _yph on 2018/3/14 0014.
 */

public class BuglyCar {

    public static void push(StackTraceElement[] stacks){
        Throwable throwable = new Throwable("卡顿监测");
        throwable.setStackTrace(stacks);
        try {
            Object c = ReflectUtil.getStaticField("com.tencent.bugly.crashreport.crash.c","q");
            Object e = ReflectUtil.getField(c,"r");
            Object b = ReflectUtil.getField(e,"b");
            Object crashDetailBean = ReflectUtil.invokeMethod(e, "b",
                    new Class[]{Thread.class,Throwable.class,boolean.class,String.class,byte[].class},
                    new Object[]{Looper.getMainLooper().getThread(),throwable,true,null,null});
            ReflectUtil.invokeMethod(b, "a",
                    new Class[]{crashDetailBean.getClass(),long.class,boolean.class},
                    new Object[]{crashDetailBean,3000L,true});
        } catch (Exception e) {
            Log.e("BlockCollect","BuglyCar翻车了："+e.toString());
            e.printStackTrace();
        }
    }
}
