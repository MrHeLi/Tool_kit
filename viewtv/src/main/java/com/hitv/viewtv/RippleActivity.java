package com.hitv.viewtv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kiven.view.all.WaterRippleView;

/**
 * Created by Kiven on 2017/12/8.
 * Details:
 */

public class RippleActivity extends Activity {
    private WaterRippleView mWaterWrv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
        mWaterWrv = (WaterRippleView) findViewById(R.id.wrv_water);
        mWaterWrv.start();
    }
}
