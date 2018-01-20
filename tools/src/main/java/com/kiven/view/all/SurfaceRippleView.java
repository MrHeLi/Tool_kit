package com.kiven.view.all;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kiven.tools.R;

/**
 * Created by Kiven on 2018/1/16.
 * Details:
 */

public class SurfaceRippleView extends SurfaceView {

    private SurfaceHolder holder;
    //    private boolean mRunning = false;
    private int[] mStrokeWidthArr;
    private int mMaxStrokeWidth;
    private int mRippleCount;
    private int mRippleSpacing;
    private Paint mPaint;
    //    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private boolean mExpand = false;
    private int mMixDiameter;
    private int mRingStrokeWidth = 2;
    private long mTimeFlash = 66;
    private int mCenterX = -1;
    private int mCenterY = -1;
    private DrawThread mThread;


    public SurfaceRippleView(Context context) {
        this(context, null);
    }

    public SurfaceRippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        holder = this.getHolder();
        holder.addCallback(callBack);
        holder.setFormat(PixelFormat.TRANSLUCENT);
        this.setZOrderOnTop(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterRippleView);
        int waveColor = typedArray.getColor(R.styleable.WaterRippleView_rippleColor,
                ContextCompat.getColor(context, R.color.colorPrimary));
        mRippleCount = typedArray.getInt(R.styleable.WaterRippleView_rippleCount, 2);
        mRippleSpacing = typedArray.getDimensionPixelSize(R.styleable.WaterRippleView_rippleSpacing,
                16);
//        mRunning = typedArray.getBoolean(R.styleable.WaterRippleView_rippleAutoRunning, false);
        mExpand = typedArray.getBoolean(R.styleable.WaterRippleView_rippleExpand, true);
        mMixDiameter = typedArray.getInt(R.styleable.WaterRippleView_rippleMixRadius, 10);
        mCenterX = typedArray.getInt(R.styleable.WaterRippleView_rippleCenterX, -1);
        mCenterY = typedArray.getInt(R.styleable.WaterRippleView_rippleCenterY, -1);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(waveColor);
    }

    SurfaceHolder.Callback callBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            int size = (mRippleCount * mRippleSpacing + mMixDiameter / 2) * 2;
            mWidth = size;
            mHeight = size;
            mMaxStrokeWidth = (mWidth - mMixDiameter) / 2;

            initArray();
            initCenter();
            mThread = new DrawThread(holder);
            mThread.setRun(true);
            mThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            synchronized (SurfaceRippleView.this) {  //这里需要加锁，否则doDraw中有可能会crash
                mThread.setRun(false);
            }
        }
    };

    private void doDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if (mExpand) {
            drawRippleExpand(canvas);
        } else {
            drawRippleDeExpand(canvas);
        }
    }

    private void drawRippleDeExpand(Canvas canvas) {
        for (int strokeWidth : mStrokeWidthArr) {
            if (strokeWidth > mMaxStrokeWidth) {
                continue;
            }
            mPaint.setStrokeWidth(mRingStrokeWidth);
            mPaint.setAlpha(255 - 255 * strokeWidth / mMaxStrokeWidth);
            canvas.drawCircle(mCenterX, mCenterY, (mMixDiameter  + strokeWidth) >> 1,
                    mPaint);
        }
        for (int i = 0; i < mStrokeWidthArr.length; i++) {
            if ((mStrokeWidthArr[i] -= 4) < 0) {
                mStrokeWidthArr[i] = mMaxStrokeWidth;
            }
        }
    }


    private void drawRippleExpand(Canvas canvas) {
        for (int strokeWidth : mStrokeWidthArr) {
            if (strokeWidth < 0) {
                continue;
            }
            mPaint.setStrokeWidth(mRingStrokeWidth);
            mPaint.setAlpha(255 - 255 * strokeWidth / mMaxStrokeWidth);
            canvas.drawCircle(mCenterX, mCenterY, (mMixDiameter + strokeWidth) >> 1,
                    mPaint);
        }
        for (int i = 0; i < mStrokeWidthArr.length; i++) {
            if ((mStrokeWidthArr[i] += 4) > mMaxStrokeWidth) {
                mStrokeWidthArr[i] = 0;
            }
        }
    }

    private void initCenter() {
        if (mCenterX <= 0) {
            mCenterX = mWidth / 2;
        }
        if (mCenterY <= 0) {
            mCenterY = mHeight / 2;
        }
    }

    private void initArray() {
        mStrokeWidthArr = new int[mRippleCount];
        if (mExpand) {
            for (int i = 0; i < mStrokeWidthArr.length; i++) {
                mStrokeWidthArr[i] = -mMaxStrokeWidth / mRippleCount * i;
            }
        } else {
            for (int i = 0; i < mStrokeWidthArr.length; i++) {
                mStrokeWidthArr[i] = mMaxStrokeWidth + mMaxStrokeWidth / mRippleCount * i;
            }
        }

    }

    public void setExpand(boolean expand) {
        this.mExpand = expand;
    }

    public void setTimeFlash(long time) {
        mTimeFlash = time;
    }

    private class DrawThread extends Thread {

        private boolean mIsRun = false;

        private SurfaceHolder mHolder;

        public void setRun(boolean isRun) {
            this.mIsRun = isRun;
        }

        public DrawThread(SurfaceHolder holder) {
            mHolder = holder;
            this.setName("ripple_view");
        }

        @Override
        public void run() {
            while (true) {
                synchronized (SurfaceRippleView.this) {
                    if (!mIsRun) {
                        return;
                    }
                    Canvas canvas = mHolder.lockCanvas();
                    if (canvas != null) {
                        doDraw(canvas);  //这里做真正绘制的事情
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                    try {
                        Thread.sleep(mTimeFlash);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
