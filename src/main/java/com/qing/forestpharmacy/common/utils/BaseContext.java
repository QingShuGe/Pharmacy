package com.qing.forestpharmacy.common.utils;

//保存当前用户id
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long getCurrentThreadId() {
        return threadLocal.get();
    }
    public static void setCurrentThreadId(Long threadId) {
        threadLocal.set(threadId);
    }
}
