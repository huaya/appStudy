package com.example.appstduy.util;

import android.util.Log;

public class LogUtils {

    public static void info(String tag, String str) {
        Log.i(tag, str);
    }

    public static void info(String tag, String str, Object... params) {
        Log.i(tag, String.format(str, params));
    }

    public static void debug(String tag, String str) {
        Log.d(tag, str);
    }

    public static void debug(String tag, String str, Object... params) {
        Log.d(tag, String.format(str, params));
    }
}
