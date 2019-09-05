package com.kcrason.dynamicpagerindicator;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @author KCrason
 * @date 2018/1/21
 */
public class MainActivity extends AppCompatActivity {

    ViewPager viewPager2;

    DynamicPagerIndicator dynamicPagerIndicator2;

    DynamicFragmentPagerAdapter dynamicFragmentPagerAdapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager1 = findViewById(R.id.view_pager1);
        DynamicPagerIndicator dynamicPagerIndicator1 = findViewById(R.id.dynamic_pager_indicator1);
        setViewPagerContent(viewPager1, dynamicPagerIndicator1, 2);

        viewPager2 = findViewById(R.id.view_pager2);
        dynamicPagerIndicator2 = findViewById(R.id.dynamic_pager_indicator2);

        setViewPagerContent();

        ViewPager viewPager3 = findViewById(R.id.view_pager3);
        DynamicPagerIndicator dynamicPagerIndicator3 = findViewById(R.id.dynamic_pager_indicator3);
        setViewPagerContent(viewPager3, dynamicPagerIndicator3, 4);

        ViewPager viewPager4 = findViewById(R.id.view_pager4);
        DynamicPagerIndicator dynamicPagerIndicator4 = findViewById(R.id.dynamic_pager_indicator4);
        setViewPagerContent(viewPager4, dynamicPagerIndicator4, 4);

        ViewPager viewPager5 = findViewById(R.id.view_pager5);
        CustomPagerIndicator dynamicPagerIndicator5 = findViewById(R.id.dynamic_pager_indicator5);
        setViewPagerContent(viewPager5, dynamicPagerIndicator5, 4);

        ViewPager viewPager6 = findViewById(R.id.view_pager6);
        CustomPagerIndicator dynamicPagerIndicator6 = findViewById(R.id.dynamic_pager_indicator6);
        setViewPagerContent(viewPager6, dynamicPagerIndicator6, 4);


        ViewPager viewPager7 = findViewById(R.id.view_pager7);
        DynamicPagerIndicator dynamicPagerIndicator7 = findViewById(R.id.dynamic_pager_indicator7);
        setViewPagerContent(viewPager7, dynamicPagerIndicator7, 5);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.update) {
            dynamicFragmentPagerAdapter2.update(createFragments(3));
            dynamicPagerIndicator2.updateIndicator(true);
            viewPager2.setCurrentItem(0);
        }
        return super.onOptionsItemSelected(item);
    }


    private void setViewPagerContent() {
        dynamicFragmentPagerAdapter2 = new DynamicFragmentPagerAdapter(getSupportFragmentManager(), createFragments(14));
        viewPager2.setAdapter(dynamicFragmentPagerAdapter2);
        dynamicPagerIndicator2.setViewPager(viewPager2);
    }


    private void setViewPagerContent(final ViewPager viewPager, final DynamicPagerIndicator dynamicPagerIndicator, final int index) {
        viewPager.setAdapter(new DynamicFragmentPagerAdapter(getSupportFragmentManager(), createFragments(index)));
        dynamicPagerIndicator.setViewPager(viewPager);
    }

    private List<Fragment> createFragments(int index) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            fragments.add(PagerFragment.create(i));
        }
        return fragments;
    }
}
