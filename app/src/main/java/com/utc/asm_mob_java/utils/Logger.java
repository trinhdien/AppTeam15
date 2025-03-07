package com.utc.asm_mob_java.utils;

import android.text.TextUtils;

public class Logger {
    public static void log(Class<?> cls, Exception e) {

        for (StackTraceElement s : e.getStackTrace()) {
            System.out.println(s);
        }
    }
}