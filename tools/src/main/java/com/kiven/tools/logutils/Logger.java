package com.kiven.tools.logutils;

import android.util.Log;

/**
 * Created by SuperLi on 2017/7/16.
 */

public class Logger {
    private static boolean debug = true;

    public static void i(String tag, String... msgs) {
        if (debug) {
            String[] messages = msgs;
            String message = "{";
            for (int i = 0; i < msgs.length; i++) {
                message += "[" + msgs[i] + "],";
            }
            message = message.substring(0, message.length() - 1);
            message += "}";
            Log.i(tag, message);
        }
    }

    public static void i(String tag, Object msg) {
        if (msg != null) {
            String msgs[] = {msg.toString()};
            i(tag, msgs);
        }
    }

    public static void i(String tag, String msg) {
        if (msg != null) {
            String msgs[] = {msg};
            i(tag, msgs);
        }
    }
}
