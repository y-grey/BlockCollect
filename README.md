简介
============

 BlockCollect是一个兼具卡顿监测和远程收集日志的工具.

 * 卡顿监测利用Android系统每隔16.6ms发出VSYNC信号，来通知界面进行重绘、渲染的原理.
 * 远程收集日志利用Bugly这趟顺风车.
 
![image](https://github.com/qq542391099/BlockCollect/blob/master/screenshot/locallog.png)
![image](https://github.com/qq542391099/BlockCollect/blob/master/screenshot/buglylog.png)

使用
--------
__1、去Bugly官网创建应用获取AppId__

__2、填写Bugly所需要的权限__
```xml
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_LOGS" />
```
__3、添加依赖__
```groovy
dependencies {
    compile 'com.tencent.bugly:crashreport:latest.release'
    compile 'com.yph:blockcollect:1.0.1'
}
```
__4、初始化__
```java
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(this, "你的AppId", false);
        BlockDetect.start(true);//是否开车
       // BlockDetect.start(true,800,6);//1是否开车，2阈值，3采样
    }
}
```
其中布尔值代表是否push到bugly,第二个参数为阈值,默认800ms,第三个采样频次，默认6次


原理
--------

 [卡顿监测之真正轻量级的卡顿监测工具BlockDetectUtil（仅一个类）](http://blog.csdn.net/u012874222/article/details/79400154)
 [卡顿监测之远程收集log（潜入Bugly这趟顺风车）](http://blog.csdn.net/u012874222/article/details/79417549)

Thanks
============
 [Bugly](http://blog.csdn.net/u012874222/article/details/79400154)
 
