package com.kiven.view.all;

import android.annotation.Nullable;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.kiven.tools.R;

/**
 * Created by Kiven on 2017/12/8.
 * Details:
 */

public class WaterRippleView extends View {
    private boolean mRunning = false;
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
    private int mRingStrokeWidth;
    private long mTimeFlash = 50;
    private int mCenterX = -1;
    private int mCenterY = -1;

    public WaterRippleView(Context context) {
        this(context, null);
    }

    public WaterRippleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterRippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterRippleView);
        int waveColor = typedArray.getColor(R.styleable.WaterRippleView_rippleColor,
                ContextCompat.getColor(context, R.color.colorPrimary));
        mRippleCount = typedArray.getInt(R.styleable.WaterRippleView_rippleCount, 2);
        mRippleSpacing = typedArray.getDimensionPixelSize(R.styleable.WaterRippleView_rippleSpacing,
                16);
        mRunning = typedArray.getBoolean(R.styleable.WaterRippleView_rippleAutoRunning, false);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = (mRippleCount * mRippleSpacing + mMixDiameter / 2) * 2;
        mWidth = resolveSize(size, widthMeasureSpec);
        mHeight = resolveSize(size, heightMeasureSpec);
//        setMeasuredDimension(mWidth, mHeight);
        mMaxStrokeWidth = (mWidth - mMixDiameter) / 2;
        initArray();
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

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRunning) {
            initCenter();
            if (mExpand) {
                drawRippleExpand(canvas);
            } else {
                drawRippleDeExpand(canvas);
            }
            postInvalidateDelayed(mTimeFlash);
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

    private void drawRippleDeExpand(Canvas canvas) {
        for (int strokeWidth : mStrokeWidthArr) {
            if (strokeWidth > mMaxStrokeWidth) {
                continue;
            }
            mPaint.setStrokeWidth(mRingStrokeWidth);
            mPaint.setAlpha(255 - 255 * strokeWidth / mMaxStrokeWidth);
            canvas.drawCircle(mCenterX, mCenterY, mMixDiameter / 2 + strokeWidth / 2,
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
            canvas.drawCircle(mCenterX, mCenterY, mMixDiameter / 2 + strokeWidth / 2,
                    mPaint);
        }
        for (int i = 0; i < mStrokeWidthArr.length; i++) {
            if ((mStrokeWidthArr[i] += 4) > mMaxStrokeWidth) {
                mStrokeWidthArr[i] = 0;
            }
        }
    }

    public void setRingStrokeWidth(int width) {
        mRingStrokeWidth = width;
    }

    public void setExpand(boolean expand) {
        this.mExpand = expand;
    }

    public boolean getExpand() {
        return mExpand;
    }

    public void setRippleCount(int RippleCount) {
        this.mRippleCount = RippleCount;
    }

    public void setmRippleSpacing(int rippleSpace) {
        this.mRippleSpacing = rippleSpace;
    }

    public void setTimeFlash(long time) {
        mTimeFlash = time;
    }

    public void start() {
        mRunning = true;
        initArray();
        postInvalidate();
    }

    public void stop() {
        mRunning = false;
        initArray();
        postInvalidate();
    }

}
