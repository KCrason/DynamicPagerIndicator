package com.kcrason.dynamicpagerindicatorlibrary

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import kotlin.math.abs

/**
 * @author KCrason
 * @date 2018/1/21
 */
open class DynamicPagerIndicator @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {


    /**
     * 提供外部回调的OnPageChangeListener接口
     */
    private var mOnOutPageChangeListener: OnOutPageChangeListener? = null

    /**
     * 即整个指示器控件的显示模式,共有三种模式,默认为INDICATOR_MODE_FIXED
     */
    private var mPagerIndicatorMode: Int = 0

    /**
     * 即指示器的滚动模式，该模式只有当mPagerIndicatorMode = INDICATOR_MODE_SCROLLABLE有效。
     * 共有两种，第一种是滑动页面时，整个导航栏同步移动到居中的位置，用PAGER_INDICATOR_SCROLL_MODE_SYNC标识
     * 第一种是滑动页面完整后，才将需要居中显示的栏目滑动的居中的位置。用PAGER_INDICATOR_SCROLL_MODE_ASYNC标识
     */
    private var mPagerIndicatorScrollToCenterMode: Int = 0

    /**
     * tab的padding,内边距,默认30px
     */
    private var mTabPadding: Int = 0

    private var mTabPaddingTop: Int = 0

    private var mTabPaddingBottom: Int = 0

    /**
     * tab的正常的字体大小
     */
    private var mTabNormalTextSize: Float = 0.toFloat()

    /**
     * tab的选中后的字体大小
     */
    private var mTabSelectedTextSize: Float = 0.toFloat()

    /**
     * tab的正常字体颜色
     */
    private var mTabNormalTextColor: Int = 0

    /**
     * tab的选中的字体颜色
     */
    private var mTabSelectedTextColor: Int = 0

    /**
     * tab的正常字体是否加粗
     */
    private var isTabNormalTextBold: Boolean = false

    /**
     * tab的选中的字体是否加粗
     */
    private var isTabSelectedTextBold: Boolean = false

    /**
     * tab color是否渐变
     */
    private var mTabTextColorMode: Int = 0

    /**
     * tab size是否渐变
     */
    private var mTabTextSizeMode: Int = 0

    /**
     * 指示条移动的模式，共两种，默认INDICATOR_SCROLL_MODE_DYNAMIC
     */
    private var mIndicatorLineScrollMode: Int = 0

    /**
     * 导航条的高度，默认12px
     */
    private var mIndicatorLineHeight: Int = 0

    /**
     * 指示条的宽度，默认为60px
     */
    private var mIndicatorLineWidth: Int = 0

    /**
     * 指示条的是否为圆角，0为不绘制圆角。默认为0
     */
    private var mIndicatorLineRadius: Float = 0.toFloat()

    /**
     * 指示条变化的起始点颜色
     */
    private var mIndicatorLineStartColor: Int = 0

    /**
     * 指示条变化的结束点颜色
     */
    private var mIndicatorLineEndColor: Int = 0

    /**
     * 指示条上边距
     */
    private var mIndicatorLineMarginTop: Int = 0

    /**
     * 指示条下边距
     */
    private var mIndicatorLineMarginBottom: Int = 0


    /**
     * TabView的父控件
     */
    private var mTabParentView: LinearLayout? = null

    /**
     * 指示条
     */
    private var mScrollableLine: ScrollableLine? = null

    private var mViewPager: ViewPager? = null

    /**
     * 外部监听TabView的点击事件
     */
    private var mOnItemTabClickListener: OnItemTabClickListener? = null

    /**
     * INDICATOR_MODE_SCROLLABLE模式下的水平滑动条
     */
    private var mAutoScrollHorizontalScrollView: HorizontalScrollView? = null

    private var mCurrentPosition: Int = 0

    var viewPager: ViewPager?
        get() = mViewPager
        set(viewPager) {
            if (viewPager == null || viewPager.adapter == null) {
                throw RuntimeException("viewpager or pager adapter is null")
            }
            this.mViewPager = viewPager
            viewPager.addOnPageChangeListener(this)
            updateIndicator(false)
            if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE) {
                val relativeLayout = RelativeLayout(context)
                relativeLayout.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                relativeLayout.addView(mTabParentView)
                relativeLayout.addView(addScrollableLine())

                mAutoScrollHorizontalScrollView = HorizontalScrollView(context)
                mAutoScrollHorizontalScrollView!!.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                mAutoScrollHorizontalScrollView!!.isHorizontalScrollBarEnabled = false

                val linearLayout = LinearLayout(context)
                linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                linearLayout.orientation = LinearLayout.VERTICAL
                linearLayout.addView(relativeLayout)

                mAutoScrollHorizontalScrollView!!.addView(linearLayout)

                addView(mAutoScrollHorizontalScrollView)
            } else {
                addView(mTabParentView)
                addView(addScrollableLine())
            }
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DynamicPagerIndicator)
        mTabPadding = typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabPadding, Utils.dipToPx(context, 16f).toFloat()).toInt()
        mTabPaddingBottom = typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabPaddingBottom, 0f).toInt()
        mTabPaddingTop = typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabPaddingTop, 0f).toInt()
        mTabNormalTextColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_tabNormalTextColor, Color.parseColor("#999999"))
        mTabSelectedTextColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_tabSelectedTextColor, Color.parseColor("#222230"))
        isTabNormalTextBold = typedArray.getBoolean(R.styleable.DynamicPagerIndicator_isTabNormalTextBold, false)
        isTabSelectedTextBold = typedArray.getBoolean(R.styleable.DynamicPagerIndicator_isTabSelectedTextBold, false)
        mTabNormalTextSize = typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabNormalTextSize, Utils.sp2px(context, 18f).toFloat())
        mTabSelectedTextSize = typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabSelectedTextSize, Utils.sp2px(context, 18f).toFloat())
        mTabTextColorMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_tabTextColorMode, TAB_TEXT_COLOR_MODE_COMMON)
        mTabTextSizeMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_tabTextSizeMode, TAB_TEXT_SIZE_MODE_COMMON)
        mIndicatorLineHeight = typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineHeight, Utils.dipToPx(context, 4f).toFloat()).toInt()
        mIndicatorLineWidth = typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineWidth, Utils.dipToPx(context, 40f).toFloat()).toInt()
        mIndicatorLineRadius = typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineRadius, 0f)
        mIndicatorLineScrollMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_indicatorLineScrollMode, INDICATOR_SCROLL_MODE_DYNAMIC)
        mIndicatorLineStartColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_indicatorLineStartColor, Color.parseColor("#f4ce46"))
        mIndicatorLineEndColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_indicatorLineEndColor, Color.parseColor("#ff00ff"))
        mIndicatorLineMarginTop = typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineMarginTop, 0f).toInt()
        mIndicatorLineMarginBottom = typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineMarginBottom, 0f).toInt()
        mPagerIndicatorMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_pagerIndicatorMode, INDICATOR_MODE_FIXED)
        mPagerIndicatorScrollToCenterMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_pagerIndicatorScrollToCenterMode, PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_LINKAGE)
        typedArray.recycle()

    }


    /**
     * 移动模式,该模式下，不支持颜色变换，默认颜色为mIndicatorLineStartColor
     */
    private fun transformScrollIndicator(position: Int, positionOffset: Float) {
        if (mTabParentView != null) {
            val positionView = mTabParentView!!.getChildAt(position)
            val positionLeft = positionView.left
            val positionViewWidth = positionView.width
            val afterView = mTabParentView!!.getChildAt(position + 1)
            var afterViewWith = 0
            if (afterView != null) {
                afterViewWith = afterView.width
            }
            val startX = positionOffset * (positionViewWidth - (positionViewWidth - mIndicatorLineWidth shr 1) + (afterViewWith - mIndicatorLineWidth shr 1)) + ((positionViewWidth - mIndicatorLineWidth) / 2).toFloat() + positionLeft.toFloat()
            val endX = positionOffset * ((positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - (afterViewWith - mIndicatorLineWidth) / 2)) + (positionView.right - (positionViewWidth - mIndicatorLineWidth shr 1))
            mScrollableLine?.updateScrollLineWidth(startX, endX, mIndicatorLineStartColor, mIndicatorLineStartColor, positionOffset)
        }
    }


    fun getPagerTabView(position: Int): BasePagerTabView? {
        return if (mTabParentView != null && position < mTabParentView!!.childCount) {
            mTabParentView!!.getChildAt(position) as BasePagerTabView
        } else null
    }


    /**
     * 动态变化模式
     */
    private fun dynamicScrollIndicator(position: Int, positionOffset: Float) {
        if (mTabParentView != null && position < mTabParentView!!.childCount) {
            val positionView = mTabParentView!!.getChildAt(position)
            var positionLeft = 0
            var positionRight = 0
            var positionViewWidth = 0
            if (positionView is BasePagerTabView) {
                positionRight = positionView.getRight()
                positionLeft = positionView.getLeft()
                positionViewWidth = positionView.getWidth()
            }
            val afterView = mTabParentView!!.getChildAt(position + 1)
            var afterViewWith = 0
            if (afterView != null) {
                afterViewWith = afterView.width
            }
            if (positionOffset <= 0.5) {
                val startX = ((positionViewWidth - mIndicatorLineWidth) / 2 + positionLeft).toFloat()
                val endX = 2 * positionOffset * ((positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - (afterViewWith - mIndicatorLineWidth) / 2)) + (positionRight - (positionViewWidth - mIndicatorLineWidth shr 1))
                mScrollableLine?.updateScrollLineWidth(startX, endX, mIndicatorLineStartColor, mIndicatorLineEndColor, positionOffset)
            } else {
                val startX = positionLeft.toFloat() + (positionViewWidth - mIndicatorLineWidth shr 1).toFloat() + (positionOffset - 0.5).toFloat() * 2f *
                        (positionViewWidth - (positionViewWidth - mIndicatorLineWidth shr 1) + (afterViewWith - mIndicatorLineWidth shr 1)).toFloat()
                val endX = (afterViewWith + positionRight - (afterViewWith - mIndicatorLineWidth) / 2).toFloat()
                mScrollableLine?.updateScrollLineWidth(startX, endX, mIndicatorLineEndColor, mIndicatorLineStartColor, positionOffset)
            }
        }
    }

    private fun tabTitleColorGradient(position: Int, positionOffset: Float) {
        if (mTabParentView != null && position < mTabParentView!!.childCount) {
            val basePagerTabView = mTabParentView!!.getChildAt(position)
            if (basePagerTabView is BasePagerTabView) {
                basePagerTabView.getTabTextView()?.setTextColor(Utils.evaluateColor(mTabSelectedTextColor, mTabNormalTextColor, positionOffset))
            }

            val afterPageTabView = mTabParentView!!.getChildAt(position + 1)
            if (afterPageTabView is BasePagerTabView) {
                afterPageTabView.getTabTextView()?.setTextColor(Utils.evaluateColor(mTabNormalTextColor, mTabSelectedTextColor, positionOffset))
            }
        }
    }

    private fun tabTitleSizeGradient(position: Int, positionOffset: Float) {
        if (mTabParentView != null && position < mTabParentView!!.childCount) {
            val basePagerTabView = mTabParentView!!.getChildAt(position)
            if (basePagerTabView is BasePagerTabView) {
                basePagerTabView.getTabTextView()?.setTextSize(TypedValue.COMPLEX_UNIT_PX, (mTabSelectedTextSize - abs(mTabSelectedTextSize - mTabNormalTextSize) * positionOffset))
            }
            val afterPageTabView = mTabParentView!!.getChildAt(position + 1)
            if (afterPageTabView is BasePagerTabView) {
                afterPageTabView.getTabTextView()?.setTextSize(TypedValue.COMPLEX_UNIT_PX, (abs(mTabSelectedTextSize - mTabNormalTextSize) * positionOffset + mTabNormalTextSize))
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (mOnOutPageChangeListener != null) {
            mOnOutPageChangeListener!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
        if (mIndicatorLineScrollMode == INDICATOR_SCROLL_MODE_DYNAMIC) {
            dynamicScrollIndicator(position, positionOffset)
        } else {
            transformScrollIndicator(position, positionOffset)
        }

        if (mTabTextColorMode == TAB_TEXT_COLOR_MODE_GRADIENT) {
            tabTitleColorGradient(position, positionOffset)
        }

        if (mTabTextSizeMode == TAB_TEXT_SIZE_MODE_GRADIENT) {
            tabTitleSizeGradient(position, positionOffset)
        }

        if (mCurrentPosition == position && positionOffset == 0f) {
            updateTitleStyle(position)
        }

        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE && mPagerIndicatorScrollToCenterMode == PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_LINKAGE) {
            linkageScrollTitleParentToCenter(position, positionOffset)
        }
    }

    override fun onPageSelected(position: Int) {
        this.mCurrentPosition = position
        if (mOnOutPageChangeListener != null) {
            mOnOutPageChangeListener!!.onPageSelected(position)
        }
        updateTitleStyle(position)
        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE && mPagerIndicatorScrollToCenterMode == PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_TRANSACTION) {
            transactionScrollTitleParentToCenter(position)
        }
    }


    fun updateIndicator(isUpdateScrollLine: Boolean) {
        if (mViewPager == null || mViewPager!!.adapter == null) {
            return
        }
        val pagerAdapter = mViewPager!!.adapter
        val pageCount = pagerAdapter!!.count
        if (mTabParentView == null) {
            mTabParentView = createTabParentView()
        }
        val oldCount = mTabParentView!!.childCount
        if (oldCount > pageCount) {
            mTabParentView!!.removeViews(pageCount, oldCount - pageCount)
        }
        for (i in 0 until pageCount) {
            val isOldChild = i < oldCount
            val childView: View
            childView = if (isOldChild) {
                mTabParentView!!.getChildAt(i)
            } else {
                createTabView(context, pagerAdapter, i)
            }
            if (childView is BasePagerTabView) {
                setTabTitleTextView(childView.getTabTextView(), i, pagerAdapter)
                setTabViewLayoutParams(childView, i)
            } else {
                throw IllegalArgumentException("childView must be BasePagerTabView")
            }
        }
        if (isUpdateScrollLine) {
            post {
                onPageScrolled(mViewPager!!.currentItem, 0f, 0)
                onPageSelected(mViewPager!!.currentItem)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (mOnOutPageChangeListener != null) {
            mOnOutPageChangeListener!!.onPageScrollStateChanged(state)
        }
    }

    fun setOnOutPageChangeListener(onOutPageChangeListener: OnOutPageChangeListener) {
        this.mOnOutPageChangeListener = onOutPageChangeListener
    }

    class SimpleOnOutPageChangeListener : OnOutPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            // This space for rent

        }

        override fun onPageSelected(position: Int) {
            // This space for rent

        }

        override fun onPageScrollStateChanged(state: Int) {
            // This space for rent
        }
    }


    /**
     * INDICATOR_MODE_SCROLLABLE 模式下，滑动条目居中显示
     * 移动模式居中显示当前的条目
     */
    private fun transactionScrollTitleParentToCenter(position: Int) {
        val positionLeft = mTabParentView!!.getChildAt(position).left
        val positionWidth = mTabParentView!!.getChildAt(position).width
        val halfScreenWidth = (Utils.getScreenPixWidth(context) - paddingLeft - paddingRight) / 2
        if (mAutoScrollHorizontalScrollView != null) {
            mAutoScrollHorizontalScrollView!!.smoothScrollTo(positionLeft + positionWidth / 2 - halfScreenWidth, 0)
        }
    }


    /**
     * INDICATOR_MODE_SCROLLABLE 模式下，滑动条目居中显示
     * 联动模式居中显示当前的条目
     */
    private fun linkageScrollTitleParentToCenter(position: Int, positionOffset: Float) {
        if (mTabParentView != null && position < mTabParentView!!.childCount) {
            val positionView = mTabParentView!!.getChildAt(position)
            var positionRight = 0
            var positionWidth = 0
            if (positionView != null) {
                positionRight = positionView.right
                positionWidth = positionView.width
            }
            val afterView = mTabParentView!!.getChildAt(position + 1)

            var afterViewWidth = 0
            if (afterView != null) {
                afterViewWidth = afterView.width
            }
            val halfScreenWidth = (Utils.getScreenPixWidth(context) - paddingLeft - paddingRight) / 2
            val offsetStart = positionRight - positionWidth / 2 - halfScreenWidth
            val scrollX = ((afterViewWidth / 2 + positionWidth / 2) * positionOffset).toInt() + offsetStart
            if (mAutoScrollHorizontalScrollView != null) {
                mAutoScrollHorizontalScrollView!!.scrollTo(scrollX, 0)
            }
        }
    }


    private fun updateTitleStyle(position: Int) {
        if (mTabParentView == null) {
            throw RuntimeException("TitleParentView is null")
        }
        for (i in 0 until mTabParentView!!.childCount) {
            val childView = mTabParentView!!.getChildAt(i)
            if (childView is BasePagerTabView) {
                childView.getTabTextView()?.let { tabTextView ->
                    if (position == i) {
                        tabTextView.setTextColor(mTabSelectedTextColor)
                        tabTextView.typeface = if (isTabSelectedTextBold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                        tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabSelectedTextSize)
                    } else {
                        tabTextView.setTextColor(mTabNormalTextColor)
                        tabTextView.typeface = if (isTabNormalTextBold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                        tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabNormalTextSize)
                    }
                }
            }
        }
    }


    interface OnItemTabClickListener {
        fun onItemTabClick(position: Int)
    }


    interface OnOutPageChangeListener {
        fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)

        fun onPageSelected(position: Int)

        fun onPageScrollStateChanged(state: Int)
    }


    fun setOnItemTabClickListener(onItemTabClickListener: OnItemTabClickListener) {
        mOnItemTabClickListener = onItemTabClickListener
    }

    /**
     * 创建Indicator的View，即ScrollableLine，然后在ScrollableLine绘制Indicator
     * ScrollableLine的
     */
    private fun addScrollableLine(): ScrollableLine {
        mScrollableLine = ScrollableLine(context)
        calculateIndicatorLineWidth()
        val scrollableLineParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mIndicatorLineHeight)
        scrollableLineParams.topMargin = mIndicatorLineMarginTop
        scrollableLineParams.bottomMargin = mIndicatorLineMarginBottom
        scrollableLineParams.addRule(ALIGN_PARENT_BOTTOM)
        mScrollableLine?.layoutParams = scrollableLineParams
        mScrollableLine?.setIndicatorLineRadius(mIndicatorLineRadius)?.setIndicatorLineHeight(mIndicatorLineHeight)
        return mScrollableLine!!
    }


    /**
     * 计算第一个Item的宽度，用于当未设置Indicator的宽度时
     */
    private fun calculateFirstItemWidth(): Int {
        val view = mTabParentView!!.getChildAt(0)
        if (view is TextView) {
            val textWidth = calculateTextWidth(view.text.toString()).toFloat()
            return (textWidth + 2 * mTabPadding).toInt()
        }
        return 0
    }

    /**
     * 通过文字计算宽度
     */
    private fun calculateTextWidth(text: String): Int {
        val textPaint = TextPaint()
        textPaint.color = mTabSelectedTextColor
        textPaint.textSize = mTabSelectedTextSize
        return textPaint.measureText(text).toInt()
    }


    /**
     * 计算导航条的宽度，如果未设置宽度，则默认为第一个Title Item的宽度
     */
    private fun calculateIndicatorLineWidth() {
        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE || mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER) {
            if (mIndicatorLineWidth == 0) {
                mIndicatorLineWidth = calculateFirstItemWidth()
            }
        } else {
            if (mIndicatorLineWidth == 0) {
                mIndicatorLineWidth = Utils.getScreenPixWidth(context) / mTabParentView!!.childCount
            }
        }
    }


    /**
     * 创建TabView的父控件，用于装载TabView
     *
     *
     * tabParentView的高度,自适应
     *
     * @return tabParentView
     */
    private fun createTabParentView(): LinearLayout {
        val linearLayout = LinearLayout(context)
        val layoutParams = LayoutParams(
                if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER || mPagerIndicatorMode == INDICATOR_MODE_FIXED)
                    LinearLayout.LayoutParams.MATCH_PARENT
                else
                    LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        linearLayout.gravity = if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER) Gravity.CENTER else Gravity.CENTER_VERTICAL
        linearLayout.layoutParams = layoutParams
        linearLayout.orientation = LinearLayout.HORIZONTAL
        return linearLayout
    }


    /**
     * 设置一个TextView，用于显示标题，这是必不可少的一个View
     */
    private fun setTabTitleTextView(textView: TextView?, position: Int, pagerAdapter: PagerAdapter) {
        textView?.let { tabTextView ->
            if (position == mViewPager!!.currentItem) {
                tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabSelectedTextSize)
                tabTextView.setTextColor(mTabSelectedTextColor)
                tabTextView.typeface = if (isTabSelectedTextBold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            } else {
                tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabNormalTextSize)
                tabTextView.setTextColor(mTabNormalTextColor)
                tabTextView.typeface = if (isTabNormalTextBold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            }
            tabTextView.gravity = Gravity.CENTER
            val title = pagerAdapter.getPageTitle(position) as String?
            tabTextView.text = title
        }
    }

    /**
     * 设置tabView的layoutParams和点击监听，该TabView可以是任何一个View，但是必须包含一个TextView用于显示title
     */
    private fun setTabViewLayoutParams(basePagerTabView: BasePagerTabView, position: Int) {
        val layoutParams: LinearLayout.LayoutParams = if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE || mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER) {
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        } else {
            LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f)
        }
        layoutParams.gravity = Gravity.CENTER
        basePagerTabView.layoutParams = layoutParams
        basePagerTabView.setPadding(mTabPadding, mTabPaddingTop, mTabPadding, mTabPaddingBottom)
        basePagerTabView.setOnClickListener {
            if (mOnItemTabClickListener != null) {
                mOnItemTabClickListener!!.onItemTabClick(position)
            }
            if (mViewPager != null) {
                mViewPager!!.currentItem = position
            }
        }
        //如果沒有被添加过，则添加
        if (basePagerTabView.parent == null) {
            mTabParentView!!.addView(basePagerTabView)
        }
    }

    /**
     * 创建tab view
     */
    open fun createTabView(context: Context, pagerAdapter: PagerAdapter, position: Int): BasePagerTabView {
        return PagerTabView(context)
    }

    companion object {

        /**
         * 指示器的模式：可滑动的
         */
        const val INDICATOR_MODE_SCROLLABLE = 1

        /**
         * 指示器的模式：不可滑动的，且均分
         */
        const val INDICATOR_MODE_FIXED = 2

        /**
         * 指示器的模式：不可滑动，居中模式
         */
        const val INDICATOR_MODE_SCROLLABLE_CENTER = 3

        /**
         * 滑动条的滚动的模式：动态变化模式（Indicator长度动态变化）
         */
        const val INDICATOR_SCROLL_MODE_DYNAMIC = 1

        /**
         * 滑动条的滚动的模式：固定长度的移动模式（Indicator长度不变，移动位置变化）
         */
        const val INDICATOR_SCROLL_MODE_TRANSFORM = 2

        /**
         * tab view的文字颜色变化模式  TAB_TEXT_COLOR_MODE_COMMON，普通模式
         */
        const val TAB_TEXT_COLOR_MODE_COMMON = 1

        /**
         * tab view的文字颜色变化模式  TAB_TEXT_COLOR_MODE_GRADIENT，渐变模式
         */
        const val TAB_TEXT_COLOR_MODE_GRADIENT = 2


        /**
         * tab view的文字字体变化模式  TAB_TEXT_SIZE_MODE_COMMON，普通模式
         */
        const val TAB_TEXT_SIZE_MODE_COMMON = 1

        /**
         * tab view的文字字体变化模式  TAB_TEXT_SIZE_MODE_GRADIENT，渐变模式
         */
        const val TAB_TEXT_SIZE_MODE_GRADIENT = 2

        /**
         * 指示器滑动到居中位置的方式，PAGER_INDICATOR_SCROLL_MODE_SYNC ，联动模式，跟随页面一起移动到居中位置
         */
        const val PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_LINKAGE = 1
        /**
         * 指示器滑动到居中位置的方式，PAGER_INDICATOR_SCROLL_MODE_SYNC ，异动模式，等页面滑动完成，再将对应的TabView移动到居中位置
         */
        const val PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_TRANSACTION = 2
    }
}
