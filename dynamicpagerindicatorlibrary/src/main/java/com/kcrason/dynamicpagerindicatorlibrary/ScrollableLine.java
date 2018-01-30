package com.kcrason.dynamicpagerindicatorlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author KCrason
 * @date 2018/1/21
 */
public class ScrollableLine extends View {

    private float mIndicatorLineRadius;

    private int mIndicatorLineHeight;

    private RectF mRectF;

    private Paint mPaint;

    private float mIndicatorStartX;

    private float mIndicatorEndX;


    public ScrollableLine(Context context) {
        super(context);
        initScrollableLine(context);
    }

    public ScrollableLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initScrollableLine(context);
    }

    public ScrollableLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScrollableLine(context);
    }

    private void initScrollableLine(Context context) {
        mPaint = new Paint();
        mRectF = new RectF();
    }

    /**
     * 设置导航条的高度
     *
     * @param indicatorLineHeight
     */
    public void setIndicatorLineHeight(int indicatorLineHeight) {
        mIndicatorLineHeight = indicatorLineHeight;
    }


    /**
     * 设置导航条的圆角
     *
     * @param indicatorLineRadius
     * @return
     */
    public ScrollableLine setIndicatorLineRadius(float indicatorLineRadius) {
        mIndicatorLineRadius = indicatorLineRadius;
        return this;
    }

    public void updateScrollLineWidth(float indicatorStartX, float indicatorEndX, int indicatorStartColor, int indicatorEndColor, float fraction) {
        this.mIndicatorStartX = indicatorStartX;
        this.mIndicatorEndX = indicatorEndX;
        mPaint.setColor(Utils.evaluateColor(indicatorStartColor, indicatorEndColor, fraction));
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.set(mIndicatorStartX, 0, mIndicatorEndX, mIndicatorLineHeight);
        canvas.drawRoundRect(mRectF, mIndicatorLineRadius, mIndicatorLineRadius, mPaint);
    }
}
