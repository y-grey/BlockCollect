package com.yph.blockcollect;

/**
 * Created by _yph on 2018/9/14 0014.
 */
public class StackTraceUtil {

    public static String getCurThread() {
        return toString(Thread.currentThread().getStackTrace());
    }

    /**
     * 前三行为本工具堆栈应过滤掉
     */
    public static String getCurThreadSimplified() {
        return toSimpleString(Thread.currentThread().getStackTrace(), 3);
    }

    public static String toString(StackTraceElement[] stacktrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement s : stacktrace) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }

    public static String toSimpleString(StackTraceElement[] stacktrace) {
        return toSimpleString(stacktrace, 0);
    }

    /**
     * 简化堆栈，去除以下字段的开头连续几行，但保留这连续几行中的最后一行
     */
    private static String toSimpleString(StackTraceElement[] stacktrace, int startIndex) {
        StringBuilder sb = new StringBuilder();
        boolean start = false;
        String lastLine = "";
        for (; startIndex < stacktrace.length; startIndex++) {
            String line = stacktrace[startIndex].toString();
            if (!start) {
                if (line.startsWith("com.android.")
                        || line.startsWith("java")
                        || line.startsWith("android")
                        || line.startsWith("sun.")
                        || line.startsWith("org.")
                        || line.startsWith("dalvik.")
                        || line.startsWith("com.google.")
                        || line.startsWith("libcore.")) {
                    lastLine = line;
                    continue;
                }
                sb.append(lastLine).append("\n");
                start = true;
            }
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * 取第二行堆栈，相同则认为是相同堆栈,需确保传入的堆栈经过简化，即调用了
     *
     * @see #toSimpleString(StackTraceElement[], int)
     */
    public static String getFeature(String stacktrace) {
        String[] arr = stacktrace.split("\n");
        if (arr.length > 1) {
            return arr[0] + arr[1];
        }else if(arr.length == 1){
            return arr[0];
        }
        return "";
    }
}
