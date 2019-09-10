package com.kcrason.dynamicpagerindicatorlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

/**
 * @author KCrason
 * @date 2018/1/23
 */
class PagerTabView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BasePagerTabView(context, attrs, defStyleAttr) {
    private var mTextView: TextView? = null

    override fun getTabTextView(): TextView? {
        return mTextView
    }

    override fun onCreateTabView(context: Context): View {
        mTextView = TextView(context)
        return mTextView!!
    }

}
