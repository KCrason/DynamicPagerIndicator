package com.kcrason.dynamicpagerindicator

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter

/**
 * @author KCrason
 * @date 2018/1/21
 */
class DynamicFragmentPagerAdapter(fm: FragmentManager, private var mFragments: List<Fragment>?) : FragmentPagerAdapter(fm) {

    private val mStrings = arrayOf("今日头条", "直播嗨", "必知", "赏学堂", "个股追踪", "日历")

    fun update(fragments: List<Fragment>) {
        this.mFragments = fragments
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getItem(position: Int): Fragment {
        return mFragments!![position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position < mStrings.size) {
            mStrings[position]
        } else "默认栏目"
    }

    override fun getCount(): Int {
        return if (mFragments == null) 0 else mFragments!!.size
    }
}
