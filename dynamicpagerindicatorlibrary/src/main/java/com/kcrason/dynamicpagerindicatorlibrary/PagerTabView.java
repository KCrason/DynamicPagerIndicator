package com.kcrason.dynamicpagerindicatorlibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * @author KCrason
 * @date 2018/1/23
 */
public class PagerTabView extends BasePagerTabView {
    private TextView mTextView;

    public PagerTabView(Context context) {
        super(context);
    }

    public PagerTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public TextView getTabTextView() {
        return mTextView;
    }

    @Override
    public View onCreateTabView(Context context) {
        mTextView = new TextView(context);
        return mTextView;
    }

}
