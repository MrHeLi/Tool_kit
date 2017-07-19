package com.kiven.sample.utils;

import android.util.Log;

/**
 * Created by Kiven on 2017/6/13.
 * Details:
 */

public class Logger {
    public static boolean debug = true;
    public static String TAG = "Logger";
    public static void i(String tag, String message) {
        if (debug) {
            Log.i(TAG + " : " + tag, message);
        }
    }
}
