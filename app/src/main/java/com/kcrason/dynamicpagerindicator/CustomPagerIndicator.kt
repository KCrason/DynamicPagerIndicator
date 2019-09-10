package com.kcrason.dynamicpagerindicator

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.AttributeSet
import com.kcrason.dynamicpagerindicatorlibrary.BasePagerTabView
import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator

/**
 * @author KCrason
 * @date 2018/1/23
 */
class CustomPagerIndicator @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : DynamicPagerIndicator(context, attrs, defStyleAttr) {
    override fun createTabView(context: Context, pagerAdapter: PagerAdapter, position: Int): BasePagerTabView {
        return CustomPagerTabView(context)
    }
}
