package com.kcrason.dynamicpagerindicator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/1/21
 */
public class DynamicFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mStrings = new String[]{"今日头条", "直播嗨", "必知", "赏学堂", "个股追踪", "日历"};

    private List<Fragment> mFragments;

    public DynamicFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    public void update(List<Fragment> fragments){
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mStrings != null && position < mStrings.length) {
            return mStrings[position];
        }
        return "默认栏目";
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
