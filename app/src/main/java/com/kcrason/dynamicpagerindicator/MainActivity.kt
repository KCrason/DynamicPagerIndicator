package com.kcrason.dynamicpagerindicator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator

import java.util.ArrayList

/**
 * @author KCrason
 * @date 2018/1/21
 */
class MainActivity : AppCompatActivity() {

    private var viewPager2: ViewPager? = null

    private var dynamicPagerIndicator2: DynamicPagerIndicator? = null

    private var dynamicFragmentPagerAdapter2: DynamicFragmentPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager1 = findViewById<ViewPager>(R.id.view_pager1)
        val dynamicPagerIndicator1 = findViewById<DynamicPagerIndicator>(R.id.dynamic_pager_indicator1)
        setViewPagerContent(viewPager1, dynamicPagerIndicator1, 2)

        viewPager2 = findViewById(R.id.view_pager2)
        dynamicPagerIndicator2 = findViewById(R.id.dynamic_pager_indicator2)
        dynamicPagerIndicator2?.setOnOutPageChangeListener(object : DynamicPagerIndicator.SimpleOnOutPageChangeListener() {

        })

        setViewPagerContent()

        val viewPager3 = findViewById<ViewPager>(R.id.view_pager3)
        val dynamicPagerIndicator3 = findViewById<DynamicPagerIndicator>(R.id.dynamic_pager_indicator3)
        setViewPagerContent(viewPager3, dynamicPagerIndicator3, 4)

        val viewPager4 = findViewById<ViewPager>(R.id.view_pager4)
        val dynamicPagerIndicator4 = findViewById<DynamicPagerIndicator>(R.id.dynamic_pager_indicator4)
        setViewPagerContent(viewPager4, dynamicPagerIndicator4, 4)

        val viewPager5 = findViewById<ViewPager>(R.id.view_pager5)
        val dynamicPagerIndicator5 = findViewById<CustomPagerIndicator>(R.id.dynamic_pager_indicator5)
        setViewPagerContent(viewPager5, dynamicPagerIndicator5, 4)

        val viewPager6 = findViewById<ViewPager>(R.id.view_pager6)
        val dynamicPagerIndicator6 = findViewById<CustomPagerIndicator>(R.id.dynamic_pager_indicator6)
        setViewPagerContent(viewPager6, dynamicPagerIndicator6, 4)


        val viewPager7 = findViewById<ViewPager>(R.id.view_pager7)
        val dynamicPagerIndicator7 = findViewById<DynamicPagerIndicator>(R.id.dynamic_pager_indicator7)
        setViewPagerContent(viewPager7, dynamicPagerIndicator7, 5)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update) {
            dynamicFragmentPagerAdapter2?.update(createFragments(3))
            dynamicPagerIndicator2?.updateIndicator(true)
            viewPager2?.currentItem = 0
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setViewPagerContent() {
        dynamicFragmentPagerAdapter2 = DynamicFragmentPagerAdapter(supportFragmentManager, createFragments(14))
        viewPager2?.adapter = dynamicFragmentPagerAdapter2
        dynamicPagerIndicator2?.viewPager = viewPager2
    }


    private fun setViewPagerContent(viewPager: ViewPager, dynamicPagerIndicator: DynamicPagerIndicator, index: Int) {
        viewPager.adapter = DynamicFragmentPagerAdapter(supportFragmentManager, createFragments(index))
        dynamicPagerIndicator.viewPager = viewPager
    }

    private fun createFragments(index: Int): List<Fragment> {
        val fragments = ArrayList<Fragment>()
        for (i in 0 until index) {
            fragments.add(PagerFragment.create(i))
        }
        return fragments
    }
}
