package com.kcrason.dynamicpagerindicatorlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author KCrason
 * @date 2019/9/5 11:47
 * @description
 */
public abstract class BasePagerTabView extends LinearLayout implements IPagerTabView {

    public BasePagerTabView(Context context) {
        super(context);
        init();
    }


    public BasePagerTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasePagerTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        View itemTabView = onCreateTabView(getContext());
        addView(itemTabView);
    }
}
