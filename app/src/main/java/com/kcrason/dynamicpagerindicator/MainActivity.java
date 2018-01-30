package com.kcrason.dynamicpagerindicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KCrason
 * @date 2018/1/21
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager1 = findViewById(R.id.view_pager1);
        DynamicPagerIndicator dynamicPagerIndicator1 = findViewById(R.id.dynamic_pager_indicator1);
        setViewPagerContent(viewPager1, dynamicPagerIndicator1, 1);

        ViewPager viewPager2 = findViewById(R.id.view_pager2);
        DynamicPagerIndicator dynamicPagerIndicator2 = findViewById(R.id.dynamic_pager_indicator2);
        setViewPagerContent(viewPager2, dynamicPagerIndicator2, 2);

        ViewPager viewPager3 = findViewById(R.id.view_pager3);
        DynamicPagerIndicator dynamicPagerIndicator3 = findViewById(R.id.dynamic_pager_indicator3);
        setViewPagerContent(viewPager3, dynamicPagerIndicator3, 3);

        ViewPager viewPager4 = findViewById(R.id.view_pager4);
        DynamicPagerIndicator dynamicPagerIndicator4 = findViewById(R.id.dynamic_pager_indicator4);
        setViewPagerContent(viewPager4, dynamicPagerIndicator4, 4);

        ViewPager viewPager5 = findViewById(R.id.view_pager5);
        CustomPagerIndicator dynamicPagerIndicator5 = findViewById(R.id.dynamic_pager_indicator5);
        setViewPagerContent(viewPager5, dynamicPagerIndicator5, 5);

        ViewPager viewPager6 = findViewById(R.id.view_pager6);
        CustomPagerIndicator dynamicPagerIndicator6 = findViewById(R.id.dynamic_pager_indicator6);
        setViewPagerContent(viewPager6, dynamicPagerIndicator6, 6);
    }


    private void setViewPagerContent(ViewPager viewPager, DynamicPagerIndicator dynamicPagerIndicator, int index) {
        viewPager.setAdapter(new DynamicFragmentPagerAdapter(getSupportFragmentManager(), createFragments(index)));
        dynamicPagerIndicator.setViewPager(viewPager);
    }

    private List<Fragment> createFragments(int index) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < (index == 2 ? 12 : 4); i++) {
            fragments.add(PagerFragment.create(i));
        }
        return fragments;
    }
}
