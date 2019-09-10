package com.kcrason.dynamicpagerindicator

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.kcrason.dynamicpagerindicatorlibrary.BasePagerTabView
import com.kcrason.dynamicpagerindicatorlibrary.PagerTabView

/**
 * @author KCrason
 * @date 2018/1/23
 */
class CustomPagerTabView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BasePagerTabView(context, attrs, defStyleAttr) {

    private lateinit var mTabTextView: TextView

    override fun getTabTextView(): TextView {
        return mTabTextView
    }

    override fun onCreateTabView(context: Context): View {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.tab_view, this, false)
        mTabTextView = view.findViewById(R.id.title)
        return view
    }
}
