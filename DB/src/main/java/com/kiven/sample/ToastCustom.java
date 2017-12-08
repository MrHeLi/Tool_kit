package com.kiven.sample;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kiven on 2017/7/20.
 * Details:
 */

public class ToastCustom {


    private static WindowManager wdm;
    private double time;
    private View mView;
    private WindowManager.LayoutParams params;
    private static Toast toast;
    private static Timer timer;
    private Context mContext;
    private int mDuration;
    private static boolean show = false;

    public ToastCustom(Context context) {
        wdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        timer = new Timer();
        toast = new Toast(context);
        mContext = context;
    }


    public void setView(View view) {
        toast.setView(view);
        mView = toast.getView();
        initLayoutParams();
    }

    /**
     * Set how long to show the view for.
     */
    public void setDuration(int duration) {
        mDuration = duration * 1000;
    }

    /**
     * Show the view for the specified duration.
     */
    public void show() {
        if (mView == null) {
            throw new RuntimeException("setView() must have been called");
        }
        show = true;
        wdm.addView(mView, params);
        long time = mDuration == 0 ? 1 * 1000 : mDuration;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                show = false;
                wdm.removeView(mView);
            }
        }, time);
    }

    public static boolean getShow() {
        return show;
    }

    private ToastCustom(Context context, String text, int time) {
        wdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        timer = new Timer();
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mView = toast.getView();
        mDuration = time * 1000;
        initLayoutParams();
    }


    public static ToastCustom makeText(Context context, String text, int time) {
        ToastCustom toastCustom = new ToastCustom(context, text, time);
        return toastCustom;
    }


    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this.  Normally view will disappear on its own
     * after the appropriate duration.
     */
    public void cancel() {
        wdm.removeView(mView);
        timer.cancel();
    }

    private void initLayoutParams() {
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.windowAnimations = mView.getAnimation().INFINITE;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.y = 100;
    }
}
