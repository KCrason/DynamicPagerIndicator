package com.kcrason.dynamicpagerindicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kcrason.dynamicpagerindicatorlibrary.BasePagerTabView;
import com.kcrason.dynamicpagerindicatorlibrary.PagerTabView;

/**
 * @author KCrason
 * @date 2018/1/23
 */
public class CustomPagerTabView extends BasePagerTabView {

    private TextView mTextView;

    public CustomPagerTabView(Context context) {
        super(context);
    }

    public CustomPagerTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPagerTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View onCreateTabView(Context context) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_view, this, false);
        mTextView = view.findViewById(R.id.title);
        return view;
    }

    @Override
    public TextView getTabTextView() {
        return mTextView;
    }
}
