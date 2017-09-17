package com.kiven.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by SuperLi on 2017/9/17.
 */

public class TVFocusView extends View {
    private String TAG = "TVFocusView";
    private Paint mPaint;
    private int shineWidth = 50;
    public TVFocusView(Context context) {
        super(context);
        init();
    }

    public TVFocusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TVFocusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect viewRect = new Rect();
        getGlobalVisibleRect(viewRect);
        Log.i(TAG, "onDraw: rect" + viewRect);
        int height = viewRect.height() - shineWidth;
        int width = viewRect.width() - shineWidth;
        int x = viewRect.left + shineWidth;
        int y = viewRect.top + shineWidth;
        Log.i(TAG, "x: " + x + " y: " + y + " width: " +width + " height: " + height);
        RectF rect = new RectF(shineWidth, shineWidth, width, height);
        canvas.drawRoundRect(rect, 10, 10, mPaint);
        canvas.drawRoundRect(rect, 10, 10, mPaint);
    }
}