package com.kcrason.dynamicpagerindicatorlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author KCrason
 * @date 2018/1/21
 */
public class DynamicPagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {


    /**
     * 提供外部回调的OnPageChangeListener接口
     */
    private OnOutPageChangeListener mOnOutPageChangeListener;

    /**
     * 指示器的模式：可滑动的
     */
    public final static int INDICATOR_MODE_SCROLLABLE = 1;

    /**
     * 指示器的模式：不可滑动的，且均分
     */
    public final static int INDICATOR_MODE_FIXED = 2;

    /**
     * 指示器的模式：不可滑动，居中模式
     */
    public final static int INDICATOR_MODE_SCROLLABLE_CENTER = 3;

    /**
     * 滑动条的滚动的模式：动态变化模式（Indicator长度动态变化）
     */
    public final static int INDICATOR_SCROLL_MODE_DYNAMIC = 1;

    /**
     * 滑动条的滚动的模式：固定长度的移动模式（Indicator长度不变，移动位置变化）
     */
    public final static int INDICATOR_SCROLL_MODE_TRANSFORM = 2;

    /**
     * tab view的文字变化模式  TAB_TEXT_COLOR_MODE_COMMON，普通模式
     */
    public final static int TAB_TEXT_COLOR_MODE_COMMON = 1;

    /**
     * tab view的文字变化模式  TAB_TEXT_COLOR_MODE_GRADIENT，渐变模式
     */
    public final static int TAB_TEXT_COLOR_MODE_GRADIENT = 1;

    /**
     * 即整个指示器控件的显示模式,共有三种模式,默认为INDICATOR_MODE_FIXED
     */
    public int mPagerIndicatorMode;

    /**
     * tab的padding,内边距,默认30px
     */
    public int mTabPadding;

    /**
     * tab的正常的字体大小
     */
    public float mTabNormalTextSize;

    /**
     * tab的选中后的字体大小
     */
    public float mTabSelectedTextSize;

    /**
     * tab的正常字体颜色
     */
    public int mTabNormalTextColor;

    /**
     * tab的选中的字体颜色
     */
    public int mTabSelectedTextColor;

    /**
     * tab color是否渐变
     */
    public int mTabTextColorMode;

    /**
     * 指示条移动的模式，共两种，默认INDICATOR_SCROLL_MODE_DYNAMIC
     */
    public int mIndicatorLineScrollMode;

    /**
     * 导航条的高度，默认12px
     */
    public int mIndicatorLineHeight;

    /**
     * 指示条的宽度，默认为60px
     */
    public int mIndicatorLineWidth;

    /**
     * 指示条的是否为圆角，0为不绘制圆角。默认为0
     */
    public float mIndicatorLineRadius;


    /**
     * 指示条变化的起始点颜色
     */
    public int mIndicatorLineStartColor;

    /**
     * 指示条变化的结束点颜色
     */
    public int mIndicatorLineEndColor;

    /**
     * 指示条上边距
     */
    public int mIndicatorLineMarginTop;

    /**
     * 指示条下边距
     */
    public int mIndicatorLineMarginBottom;


    public Context mContext;

    /**
     * TabView的父控件
     */
    public LinearLayout mTabParentView;

    /**
     * 指示条
     */
    public ScrollableLine mScrollableLine;

    public ViewPager mViewPager;

    /**
     * 外部监听TabView的点击事件
     */
    public OnItemTabClickListener mOnItemTabClickListener;

    /**
     * INDICATOR_MODE_SCROLLABLE模式下的水平滑动条
     */
    public HorizontalScrollView mAutoScrollHorizontalScrollView;

    public DynamicPagerIndicator(Context context) {
        super(context);
        initDynamicPagerIndicator(context, null);
    }

    public DynamicPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDynamicPagerIndicator(context, attrs);
    }

    public DynamicPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDynamicPagerIndicator(context, attrs);
    }

    public void initDynamicPagerIndicator(Context context, AttributeSet attributeSet) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DynamicPagerIndicator);
        if (typedArray != null) {
            mTabPadding = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabPadding, 30);
            mTabNormalTextColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_tabNormalTextColor, Color.parseColor("#999999"));
            mTabSelectedTextColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_tabSelectedTextColor, Color.parseColor("#2e2e37"));
            mTabNormalTextSize = typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabNormalTextSize, Utils.sp2px(mContext, 18));
            mTabSelectedTextSize = typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabSelectedTextSize, Utils.sp2px(mContext, 18));
            mTabTextColorMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_tabTextColorMode, TAB_TEXT_COLOR_MODE_COMMON);
            mIndicatorLineHeight = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineHeight, 12);
            mIndicatorLineWidth = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineWidth, 60);
            mIndicatorLineRadius = typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineRadius, 0);
            mIndicatorLineScrollMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_indicatorLineScrollMode, INDICATOR_SCROLL_MODE_DYNAMIC);
            mIndicatorLineStartColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_indicatorLineStartColor, Color.parseColor("#f4ce46"));
            mIndicatorLineEndColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_indicatorLineEndColor, Color.parseColor("#ff00ff"));
            mIndicatorLineMarginTop = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineMarginTop, 8);
            mIndicatorLineMarginBottom = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineMarginBottom, 8);
            mPagerIndicatorMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_pagerIndicatorMode, INDICATOR_MODE_FIXED);
            typedArray.recycle();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnOutPageChangeListener != null) {
            mOnOutPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
        if (mIndicatorLineScrollMode == INDICATOR_SCROLL_MODE_DYNAMIC) {
            dynamicScrollIndicator(position, positionOffset);
        } else {
            transformScrollIndicator(position, positionOffset);
        }

        if (mTabTextColorMode == TAB_TEXT_COLOR_MODE_GRADIENT) {
            tabTitleColorGradient(position, positionOffset);
        }
    }


    /**
     * 移动模式,该模式下，不支持颜色变换，默认颜色为mIndicatorLineStartColor
     */
    public void transformScrollIndicator(int position, float positionOffset) {
        if (mTabParentView != null) {
            View positionView = mTabParentView.getChildAt(position);
            int positionLeft = positionView.getLeft();
            int positionViewWidth = positionView.getWidth();
            View afterView = mTabParentView.getChildAt(position + 1);
            int afterViewWith = 0;
            if (afterView != null) {
                afterViewWith = afterView.getWidth();
            }
            float startX = positionOffset * (positionViewWidth - (positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - mIndicatorLineWidth) / 2) + (positionViewWidth - mIndicatorLineWidth) / 2 + positionLeft;
            float endX = positionOffset * ((positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - (afterViewWith - mIndicatorLineWidth) / 2)) +
                    (positionView.getRight() - (positionViewWidth - mIndicatorLineWidth) / 2);
            mScrollableLine.updateScrollLineWidth(startX, endX, mIndicatorLineStartColor, mIndicatorLineStartColor, positionOffset);
        }
    }


    /**
     * 动态变化模式
     */
    public void dynamicScrollIndicator(int position, float positionOffset) {
        if (mTabParentView != null) {
            View positionView = mTabParentView.getChildAt(position);
            int positionLeft = positionView.getLeft();
            int positionViewWidth = positionView.getWidth();
            View afterView = mTabParentView.getChildAt(position + 1);
            int afterViewWith = 0;
            if (afterView != null) {
                afterViewWith = afterView.getWidth();
            }
            if (positionOffset <= 0.5) {
                float startX = (positionViewWidth - mIndicatorLineWidth) / 2 + positionLeft;
                float endX = (2 * positionOffset) * ((positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - (afterViewWith - mIndicatorLineWidth) / 2)) +
                        (positionView.getRight() - (positionViewWidth - mIndicatorLineWidth) / 2);
                mScrollableLine.updateScrollLineWidth(startX, endX, mIndicatorLineStartColor, mIndicatorLineEndColor, positionOffset);
            } else {
                float startX = positionLeft + (positionViewWidth - mIndicatorLineWidth) / 2 + (float) (positionOffset - 0.5) * 2 *
                        (positionViewWidth - (positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - mIndicatorLineWidth) / 2);
                float endX = afterViewWith + positionView.getRight() - (afterViewWith - mIndicatorLineWidth) / 2;
                mScrollableLine.updateScrollLineWidth(startX, endX, mIndicatorLineEndColor, mIndicatorLineStartColor, positionOffset);
            }
        }
    }

    public void tabTitleColorGradient(int position, float positionOffset) {
        PageTabView pageTabView = (PageTabView) mTabParentView.getChildAt(position);
        PageTabView afterPageTabView = (PageTabView) mTabParentView.getChildAt(position + 1);
        pageTabView.getTitleTextView().setTextColor(Utils.evaluateColor(mTabSelectedTextColor, mTabNormalTextColor, positionOffset));
        if (afterPageTabView != null) {
            afterPageTabView.getTitleTextView().setTextColor(Utils.evaluateColor(mTabNormalTextColor, mTabSelectedTextColor, positionOffset));
        }
    }


    @Override
    public void onPageSelected(int position) {
        if (mOnOutPageChangeListener != null) {
            mOnOutPageChangeListener.onPageSelected(position);
        }
        updateTitleStyle(position);
        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE) {
            scrollTitleParentToCenter(position);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnOutPageChangeListener != null) {
            mOnOutPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    public void setOnOutPageChangeListener(OnOutPageChangeListener onOutPageChangeListener) {
        this.mOnOutPageChangeListener = onOutPageChangeListener;
    }

    public static class SimpleOnOutPageChangeListener implements OnOutPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // This space for rent

        }

        @Override
        public void onPageSelected(int position) {
            // This space for rent

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // This space for rent
        }
    }


    /**
     * INDICATOR_MODE_SCROLLABLE模式下，滑动条目居中显示
     */
    public void scrollTitleParentToCenter(int position) {
        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE) {
            int positionLeft = mTabParentView.getChildAt(position).getLeft();
            int positionWidth = mTabParentView.getChildAt(position).getWidth();
            int halfScreenWidth = Utils.calculateScreenWidth(mContext) / 2;
            if (mAutoScrollHorizontalScrollView != null) {
                mAutoScrollHorizontalScrollView.smoothScrollTo(positionLeft + positionWidth / 2 - halfScreenWidth, 0);
            }
        }
    }


    public void updateTitleStyle(int position) {
        if (mTabParentView == null) {
            throw new RuntimeException("TitleParentView is null");
        }
        for (int i = 0; i < mTabParentView.getChildCount(); i++) {
            View childView = mTabParentView.getChildAt(i);
            if (childView instanceof PageTabView) {
                TextView textView = ((PageTabView) childView).getTitleTextView();
                if (textView != null) {
                    if (position == i) {
                        textView.setTextColor(mTabSelectedTextColor);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabSelectedTextSize);
                    } else {
                        textView.setTextColor(mTabNormalTextColor);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabNormalTextSize);
                    }
                }
            }
        }
    }

    public interface OnItemTabClickListener {
        void onItemTabClick(int position);
    }


    public interface OnOutPageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }


    public void setOnItemTabClickListener(OnItemTabClickListener onItemTabClickListener) {
        mOnItemTabClickListener = onItemTabClickListener;
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null || viewPager.getAdapter() == null) {
            throw new RuntimeException("viewpager or pager adapter is null");
        }
        this.mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        int pageCount = pagerAdapter.getCount();
        if (mTabParentView == null) {
            mTabParentView = createTabParentView(viewPager.getHeight());
        }
        if (mTabParentView != null) {
            if (mTabParentView.getChildCount() > 0) {
                mTabParentView.removeAllViews();
            }
            for (int i = 0; i < pageCount; i++) {
                createTabView(pagerAdapter, i);
            }
        }

        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams linearLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(linearLayoutParams);
            linearLayout.setOrientation(VERTICAL);
            linearLayout.addView(mTabParentView);
            linearLayout.addView(addScrollableLine());

            mAutoScrollHorizontalScrollView = new HorizontalScrollView(mContext);
            LinearLayout.LayoutParams scrollLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mAutoScrollHorizontalScrollView.setLayoutParams(scrollLayoutParams);
            mAutoScrollHorizontalScrollView.setHorizontalScrollBarEnabled(false);
            mAutoScrollHorizontalScrollView.addView(linearLayout);

            addView(mAutoScrollHorizontalScrollView);
        } else {
            addView(mTabParentView);
            addView(addScrollableLine());
        }
    }


    /**
     * 创建Indicator的View，即ScrollableLine，然后在ScrollableLine绘制Indicator
     * ScrollableLine的
     */
    public ScrollableLine addScrollableLine() {
        mScrollableLine = new ScrollableLine(mContext);
        calculateIndicatorLineWidth();
        LinearLayout.LayoutParams scrollableLineParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mIndicatorLineHeight);
        scrollableLineParams.topMargin = mIndicatorLineMarginTop;
        scrollableLineParams.bottomMargin = mIndicatorLineMarginBottom;
        mScrollableLine.setLayoutParams(scrollableLineParams);
        mScrollableLine.setIndicatorLineRadius(mIndicatorLineRadius).setIndicatorLineHeight(mIndicatorLineHeight);
        return mScrollableLine;
    }


    /**
     * 计算第一个Item的宽度，用于当未设置Indicator的宽度时
     */
    public int calculateFirstItemWidth() {
        View view = mTabParentView.getChildAt(0);
        if (view instanceof TextView) {
            TextView textView = ((TextView) view);
            float textWidth = calculateTextWidth(textView, textView.getText().toString());
            return (int) (textWidth + 2 * mTabPadding);
        }
        return 0;
    }

    /**
     * 通过文字计算宽度
     */
    public float calculateTextWidth(TextView textView, String text) {
        if (textView != null) {
            return textView.getPaint().measureText(text);
        }
        return 0f;
    }


    /**
     * 计算导航条的宽度，如果未设置宽度，则默认为第一个Title Item的宽度
     */
    public void calculateIndicatorLineWidth() {
        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE || mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER) {
            if (mIndicatorLineWidth == 0) {
                mIndicatorLineWidth = calculateFirstItemWidth();
            }
        } else {
            if (mIndicatorLineWidth == 0) {
                mIndicatorLineWidth = Utils.calculateScreenWidth(mContext) / mTabParentView.getChildCount();
            }
        }
    }


    /**
     * 创建TabView的父控件，用于装载TabView
     *
     * @param height tabParentView的高度，与DynamicPagerIndicator的高度一致
     * @return tabParentView
     */
    public LinearLayout createTabParentView(int height) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER ? Utils.calculateScreenWidth(mContext) : LinearLayout.LayoutParams.MATCH_PARENT,
                height == 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : height);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }


    /**
     * 设置一个TextView，用于显示标题，这是必不可少的一个View
     */
    public void setTabTitleTextView(TextView textView, int position, PagerAdapter pagerAdapter) {
        if (position == 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabSelectedTextSize);
            textView.setTextColor(mTabSelectedTextColor);
        } else {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabNormalTextSize);
            textView.setTextColor(mTabNormalTextColor);
        }
        textView.setGravity(Gravity.CENTER);
        String title = pagerAdapter.getPageTitle(position).toString();
        textView.setText(title);
    }

    /**
     * 设置tabView的layoutParams和点击监听，该TabView可以是任何一个View，但是必须包含一个TextView用于显示title
     */
    public void setTabViewLayoutParams(PageTabView pageTabView, final int position) {
        LinearLayout.LayoutParams layoutParams;
        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE || mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        }
        pageTabView.setLayoutParams(layoutParams);
        pageTabView.setPadding(mTabPadding, 0, mTabPadding, 0);
        pageTabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(position);
                }
                if (mOnItemTabClickListener != null) {
                    mOnItemTabClickListener.onItemTabClick(position);
                }
            }
        });
        mTabParentView.addView(pageTabView);
    }

    /**
     * 创建tab view
     */
    public void createTabView(PagerAdapter pagerAdapter, final int position) {
        PageTabView pageTabView = new PageTabView(mContext);
        setTabTitleTextView(pageTabView.getTitleTextView(), position, pagerAdapter);
        setTabViewLayoutParams(pageTabView, position);
    }
}
