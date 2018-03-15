package com.yph.blockcollect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by yph on 2017/6/29.
 */

public class ReflectUtil {

    public static <T> T getField(Object object, String fieldName) throws Exception {
        Class<?> forName = object.getClass();
        Field field = forName.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(object);
    }
    public static <T> T getStaticField(String clsName, String fieldName) throws Exception {
        Class<?> forName = Class.forName(clsName);
        Field field = forName.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(null);
    }
    public static Object invokeMethod(Object owner, String methodName, Class[] argsClass, Object[] args) throws Exception {
        Class ownerClass = owner.getClass();
        Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(owner, args);
    }

    public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
        Class ownerClass = owner.getClass();
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
        }
        Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(owner, args);
    }

    public static Object invokeMethod(Object owner, String methodName) throws Exception {
        Class ownerClass = owner.getClass();
        Method method = ownerClass.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(owner);
    }

    public static Object invokeMethod(Object owner, String methodName, int args) throws Exception {
        Class ownerClass = owner.getClass();
        Method method = ownerClass.getDeclaredMethod(methodName, int.class);
        method.setAccessible(true);
        return method.invoke(owner, args);
    }
}
