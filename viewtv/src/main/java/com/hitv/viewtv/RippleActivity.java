package com.hitv.viewtv;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.kiven.view.all.SurfaceRippleView;
import com.kiven.view.all.WaterRippleView;

/**
 * Created by Kiven on 2017/12/8.
 * Details:
 */

public class RippleActivity extends Activity {
    private WaterRippleView mWaterWrv;
    private WaterRippleView mWaterWrv1;

    private SurfaceRippleView mSrcView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
//        mWaterWrv = (WaterRippleView) findViewById(R.id.wrv_water);
//        mWaterWrv.setTimeFlash((long) 0.5);
//        mWaterWrv.setExpand(true);
//        mWaterWrv.start();

        mSrcView = findViewById(R.id.srv_water);
        mSrcView.setTimeFlash((long) 30);
        mSrcView.setExpand(false);

        mSrcView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSrcView.setVisibility(View.GONE);
            }
        }, 10 * 1000);

    }
}
