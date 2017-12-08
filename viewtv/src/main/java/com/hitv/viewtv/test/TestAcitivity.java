package com.hitv.viewtv.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.hitv.viewtv.R;

/**
 * Created by Kiven on 2017/11/18.
 * Details:
 */

public class TestAcitivity extends Activity {

    RecyclerView recyclerView;
    TestAdapter adapter;
    LinearLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        container = findViewById(R.id.container);
//        recyclerView = findViewById(R.id.rv);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new TestAdapter(this);
//        recyclerView.setAdapter(adapter);

        initRing();

    }

    private void initRing() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_ring, null);
        int x = 960;
        int y = 540;
        view.setX(x);
        view.setY(y);
        container.addView(view);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        animation.setFillAfter(true);
        view.startAnimation(animation);

//        AnimationSet animationSet = new AnimationSet(true);
//        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 3, 1, 3,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        //3秒完成动画
//        scaleAnimation.setDuration(2000);
//        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
//        animationSet.addAnimation(scaleAnimation);
//        //启动动画
//        view.startAnimation(animationSet);
    }
}
