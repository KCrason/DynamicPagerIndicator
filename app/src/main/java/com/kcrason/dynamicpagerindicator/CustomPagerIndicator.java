package com.kcrason.dynamicpagerindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;

import com.kcrason.dynamicpagerindicatorlibrary.BasePagerTabView;
import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator;

/**
 * @author KCrason
 * @date 2018/1/23
 */
public class CustomPagerIndicator extends DynamicPagerIndicator {

    public CustomPagerIndicator(Context context) {
        super(context);
    }

    public CustomPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public BasePagerTabView createTabView(PagerAdapter pagerAdapter, final int position) {
        return new CustomPagerTabView(mContext);
    }
}
