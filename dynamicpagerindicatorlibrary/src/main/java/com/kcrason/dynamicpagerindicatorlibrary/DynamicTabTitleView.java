package com.kcrason.dynamicpagerindicatorlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * @author KCrason
 * @date 2018/1/24
 */
@SuppressLint("AppCompatCustomView")
public class DynamicTabTitleView extends TextView {

    private Paint mPaint;

    private Context mContext;

    public DynamicTabTitleView(Context context) {
        super(context);
        initDynamicTabTitleView(context);
    }

    public DynamicTabTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDynamicTabTitleView(context);
    }

    public DynamicTabTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDynamicTabTitleView(context);
    }

    private void initDynamicTabTitleView(Context context) {
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setTextSize(Utils.sp2px(context, 23));
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.clipRect(0, 0, 60, getHeight());
        canvas.drawText("abcdefg", 0, getHeight() / 2, mPaint);

        canvas.restore();
        mPaint.setColor(Color.BLACK);
        canvas.clipRect(60, 0, 180, getHeight());
        canvas.drawText("abcdefg", 0, getHeight() / 2, mPaint);
    }
}
