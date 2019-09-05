package com.kcrason.dynamicpagerindicatorlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * @author KCrason
 * @date 2018/1/21
 */
public class DynamicPagerIndicator extends RelativeLayout implements ViewPager.OnPageChangeListener {

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
    public final static int TAB_TEXT_COLOR_MODE_GRADIENT = 2;

    /**
     * 指示器滑动到居中位置的方式，PAGER_INDICATOR_SCROLL_MODE_SYNC ，联动模式，跟随页面一起移动到居中位置
     */
    public final static int PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_LINKAGE = 1;
    /**
     * 指示器滑动到居中位置的方式，PAGER_INDICATOR_SCROLL_MODE_SYNC ，异动模式，等页面滑动完成，再将对应的TabView移动到居中位置
     */
    public final static int PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_TRANSACTION = 2;

    /**
     * 即整个指示器控件的显示模式,共有三种模式,默认为INDICATOR_MODE_FIXED
     */
    public int mPagerIndicatorMode;

    /**
     * 即指示器的滚动模式，该模式只有当mPagerIndicatorMode = INDICATOR_MODE_SCROLLABLE有效。
     * 共有两种，第一种是滑动页面时，整个导航栏同步移动到居中的位置，用PAGER_INDICATOR_SCROLL_MODE_SYNC标识
     * 第一种是滑动页面完整后，才将需要居中显示的栏目滑动的居中的位置。用PAGER_INDICATOR_SCROLL_MODE_ASYNC标识
     */
    public int mPagerIndicatorScrollToCenterMode;

    /**
     * tab的padding,内边距,默认30px
     */
    public int mTabPadding;

    public int mTabPaddingTop;

    public int mTabPaddingBottom;

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
     * tab的正常字体是否加粗
     */
    public boolean isTabNormalTextBold;

    /**
     * tab的选中的字体是否加粗
     */
    public boolean isTabSelectedTextBold;

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
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DynamicPagerIndicator);
        if (typedArray != null) {
            mTabPadding = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabPadding, 30);
            mTabPaddingBottom = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabPaddingBottom, 0);
            mTabPaddingTop = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabPaddingTop, 0);
            mTabNormalTextColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_tabNormalTextColor, Color.parseColor("#999999"));
            mTabSelectedTextColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_tabSelectedTextColor, Color.parseColor("#222230"));
            isTabNormalTextBold = typedArray.getBoolean(R.styleable.DynamicPagerIndicator_isTabNormalTextBold, false);
            isTabSelectedTextBold = typedArray.getBoolean(R.styleable.DynamicPagerIndicator_isTabSelectedTextBold, false);
            mTabNormalTextSize = typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabNormalTextSize, Utils.sp2px(mContext, 18));
            mTabSelectedTextSize = typedArray.getDimension(R.styleable.DynamicPagerIndicator_tabSelectedTextSize, Utils.sp2px(mContext, 18));
            mTabTextColorMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_tabTextColorMode, TAB_TEXT_COLOR_MODE_COMMON);
            mIndicatorLineHeight = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineHeight, 12);
            mIndicatorLineWidth = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineWidth, 80);
            mIndicatorLineRadius = typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineRadius, 0);
            mIndicatorLineScrollMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_indicatorLineScrollMode, INDICATOR_SCROLL_MODE_DYNAMIC);
            mIndicatorLineStartColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_indicatorLineStartColor, Color.parseColor("#f4ce46"));
            mIndicatorLineEndColor = typedArray.getColor(R.styleable.DynamicPagerIndicator_indicatorLineEndColor, Color.parseColor("#ff00ff"));
            mIndicatorLineMarginTop = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineMarginTop, 0);
            mIndicatorLineMarginBottom = (int) typedArray.getDimension(R.styleable.DynamicPagerIndicator_indicatorLineMarginBottom, 0);
            mPagerIndicatorMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_pagerIndicatorMode, INDICATOR_MODE_FIXED);
            mPagerIndicatorScrollToCenterMode = typedArray.getInt(R.styleable.DynamicPagerIndicator_pagerIndicatorScrollToCenterMode, PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_LINKAGE);
            typedArray.recycle();
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
            float startX = positionOffset * (positionViewWidth - ((positionViewWidth - mIndicatorLineWidth) >> 1) + ((afterViewWith - mIndicatorLineWidth) >> 1)) + (positionViewWidth - mIndicatorLineWidth) / 2 + positionLeft;
            float endX = positionOffset * ((positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - (afterViewWith - mIndicatorLineWidth) / 2)) +
                    (positionView.getRight() - ((positionViewWidth - mIndicatorLineWidth) >> 1));
            mScrollableLine.updateScrollLineWidth(startX, endX, mIndicatorLineStartColor, mIndicatorLineStartColor, positionOffset);
        }
    }


    public BasePagerTabView getPagerTabView(int position) {
        if (mTabParentView != null && position < mTabParentView.getChildCount()) {
            return (BasePagerTabView) mTabParentView.getChildAt(position);
        }
        return null;
    }


    /**
     * 动态变化模式
     */
    public void dynamicScrollIndicator(int position, float positionOffset) {
        if (mTabParentView != null && position < mTabParentView.getChildCount()) {
            View positionView = mTabParentView.getChildAt(position);
            int positionLeft = 0, positionRight = 0, positionViewWidth = 0;
            if (positionView instanceof BasePagerTabView) {
                positionRight = positionView.getRight();
                positionLeft = positionView.getLeft();
                positionViewWidth = positionView.getWidth();
            }
            View afterView = mTabParentView.getChildAt(position + 1);
            int afterViewWith = 0;
            if (afterView != null) {
                afterViewWith = afterView.getWidth();
            }
            if (positionOffset <= 0.5) {
                float startX = (positionViewWidth - mIndicatorLineWidth) / 2 + positionLeft;
                float endX = (2 * positionOffset) * ((positionViewWidth - mIndicatorLineWidth) / 2 + (afterViewWith - (afterViewWith - mIndicatorLineWidth) / 2)) +
                        (positionRight - ((positionViewWidth - mIndicatorLineWidth) >> 1));
                mScrollableLine.updateScrollLineWidth(startX, endX, mIndicatorLineStartColor, mIndicatorLineEndColor, positionOffset);
            } else {
                float startX = positionLeft + ((positionViewWidth - mIndicatorLineWidth) >> 1) + (float) (positionOffset - 0.5) * 2 *
                        (positionViewWidth - ((positionViewWidth - mIndicatorLineWidth) >> 1) + ((afterViewWith - mIndicatorLineWidth) >> 1));
                float endX = afterViewWith + positionRight - (afterViewWith - mIndicatorLineWidth) / 2;
                mScrollableLine.updateScrollLineWidth(startX, endX, mIndicatorLineEndColor, mIndicatorLineStartColor, positionOffset);
            }
        }
    }

    public void tabTitleColorGradient(int position, float positionOffset) {
        if (mTabParentView != null && position < mTabParentView.getChildCount()) {
            BasePagerTabView basePagerTabView = (BasePagerTabView) mTabParentView.getChildAt(position);
            if (basePagerTabView != null) {
                basePagerTabView.getTabTextView().setTextColor(Utils.evaluateColor(mTabSelectedTextColor, mTabNormalTextColor, positionOffset));
            }
            BasePagerTabView afterPageTabView = (BasePagerTabView) mTabParentView.getChildAt(position + 1);
            if (afterPageTabView != null) {
                afterPageTabView.getTabTextView().setTextColor(Utils.evaluateColor(mTabNormalTextColor, mTabSelectedTextColor, positionOffset));
            }
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

        if (mTabTextColorMode == TAB_TEXT_COLOR_MODE_GRADIENT && mCurrentPosition == position + Math.round(positionOffset)) {
            tabTitleColorGradient(position, positionOffset);
        }

        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE &&
                mPagerIndicatorScrollToCenterMode == PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_LINKAGE) {
            linkageScrollTitleParentToCenter(position, positionOffset);
        }
    }

    private int mCurrentPosition;

    @Override
    public void onPageSelected(int position) {
        this.mCurrentPosition = position;
        if (mOnOutPageChangeListener != null) {
            mOnOutPageChangeListener.onPageSelected(position);
        }
        updateTitleStyle(position);
        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE &&
                mPagerIndicatorScrollToCenterMode == PAGER_INDICATOR_SCROLL_TO_CENTER_MODE_TRANSACTION) {
            transactionScrollTitleParentToCenter(position);
        }
    }


    public void updateIndicator(boolean isUpdateScrollLine) {
        if (mViewPager == null || mViewPager.getAdapter() == null) {
            return;
        }
        PagerAdapter pagerAdapter = mViewPager.getAdapter();
        int pageCount = pagerAdapter.getCount();
        if (mTabParentView == null) {
            mTabParentView = createTabParentView();
        }
        int oldCount = mTabParentView.getChildCount();
        if (oldCount > pageCount) {
            mTabParentView.removeViews(pageCount, oldCount - pageCount);
        }
        for (int i = 0; i < pageCount; i++) {
            boolean isOldChild = i < oldCount;
            View childView;
            if (isOldChild) {
                childView = mTabParentView.getChildAt(i);
            } else {
                childView = createTabView(pagerAdapter, i);
            }
            if (childView instanceof BasePagerTabView) {
                setTabTitleTextView(((BasePagerTabView) childView).getTabTextView(), i, pagerAdapter);
                setTabViewLayoutParams((BasePagerTabView) childView, i);
            } else {
                throw new IllegalArgumentException("childView must be PageTabView");
            }
        }
        if (isUpdateScrollLine) {
            post(new Runnable() {
                @Override
                public void run() {
                    onPageScrolled(mViewPager.getCurrentItem(), 0, 0);
                    onPageSelected(mViewPager.getCurrentItem());
                }
            });
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
     * INDICATOR_MODE_SCROLLABLE 模式下，滑动条目居中显示
     * 移动模式居中显示当前的条目
     */
    public void transactionScrollTitleParentToCenter(int position) {
        final int positionLeft = mTabParentView.getChildAt(position).getLeft();
        final int positionWidth = mTabParentView.getChildAt(position).getWidth();
        final int halfScreenWidth = (Utils.getScreenPixWidth(mContext) - getPaddingLeft() - getPaddingRight()) / 2;
        if (mAutoScrollHorizontalScrollView != null) {
            mAutoScrollHorizontalScrollView.smoothScrollTo(positionLeft + positionWidth / 2 - halfScreenWidth, 0);
        }
    }


    /**
     * INDICATOR_MODE_SCROLLABLE 模式下，滑动条目居中显示
     * 联动模式居中显示当前的条目
     */
    public void linkageScrollTitleParentToCenter(int position, float positionOffset) {
        if (mTabParentView != null && position < mTabParentView.getChildCount()) {
            View positionView = mTabParentView.getChildAt(position);
            int positionRight = 0, positionWidth = 0;
            if (positionView != null) {
                positionRight = positionView.getRight();
                positionWidth = positionView.getWidth();
            }
            View afterView = mTabParentView.getChildAt(position + 1);

            int afterViewWidth = 0;
            if (afterView != null) {
                afterViewWidth = afterView.getWidth();
            }
            final int halfScreenWidth = (Utils.getScreenPixWidth(mContext) - getPaddingLeft() - getPaddingRight()) / 2;
            int offsetStart = positionRight - positionWidth / 2 - halfScreenWidth;
            int scrollX = (int) ((afterViewWidth / 2 + positionWidth / 2) * positionOffset) + offsetStart;
            if (mAutoScrollHorizontalScrollView != null) {
                mAutoScrollHorizontalScrollView.scrollTo(scrollX, 0);
            }
        }
    }


    public void updateTitleStyle(int position) {
        if (mTabParentView == null) {
            throw new RuntimeException("TitleParentView is null");
        }
        for (int i = 0; i < mTabParentView.getChildCount(); i++) {
            View childView = mTabParentView.getChildAt(i);
            if (childView instanceof BasePagerTabView) {
                TextView textView = ((BasePagerTabView) childView).getTabTextView();
                if (textView != null) {
                    if (position == i) {
                        textView.setTextColor(mTabSelectedTextColor);
                        textView.setTypeface(isTabSelectedTextBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabSelectedTextSize);
                    } else {
                        textView.setTextColor(mTabNormalTextColor);
                        textView.setTypeface(isTabNormalTextBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
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
        updateIndicator(false);
        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE) {
            RelativeLayout relativeLayout = new RelativeLayout(mContext);
            relativeLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            relativeLayout.addView(mTabParentView);
            relativeLayout.addView(addScrollableLine());

            mAutoScrollHorizontalScrollView = new HorizontalScrollView(mContext);
            mAutoScrollHorizontalScrollView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mAutoScrollHorizontalScrollView.setHorizontalScrollBarEnabled(false);

            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.addView(relativeLayout);

            mAutoScrollHorizontalScrollView.addView(linearLayout);

            addView(mAutoScrollHorizontalScrollView);
        } else {
            addView(mTabParentView);
            addView(addScrollableLine());
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * 创建Indicator的View，即ScrollableLine，然后在ScrollableLine绘制Indicator
     * ScrollableLine的
     */
    public ScrollableLine addScrollableLine() {
        mScrollableLine = new ScrollableLine(mContext);
        calculateIndicatorLineWidth();
        LayoutParams scrollableLineParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mIndicatorLineHeight);
        scrollableLineParams.topMargin = mIndicatorLineMarginTop;
        scrollableLineParams.bottomMargin = mIndicatorLineMarginBottom;
        scrollableLineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
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
            float textWidth = calculateTextWidth(textView.getText().toString());
            return (int) (textWidth + 2 * mTabPadding);
        }
        return 0;
    }

    /**
     * 通过文字计算宽度
     */
    public int calculateTextWidth(String text) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(mTabSelectedTextColor);
        textPaint.setTextSize(mTabSelectedTextSize);
        return (int) textPaint.measureText(text);
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
                mIndicatorLineWidth = Utils.getScreenPixWidth(mContext) / mTabParentView.getChildCount();
            }
        }
    }


    /**
     * 创建TabView的父控件，用于装载TabView
     * <p>
     * tabParentView的高度,自适应
     *
     * @return tabParentView
     */
    public LinearLayout createTabParentView() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER || mPagerIndicatorMode == INDICATOR_MODE_FIXED) ?
                        LinearLayout.LayoutParams.MATCH_PARENT : LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setGravity(mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER ? Gravity.CENTER : Gravity.CENTER_VERTICAL);
        layoutParams.weight = 1;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }


    /**
     * 设置一个TextView，用于显示标题，这是必不可少的一个View
     */
    public void setTabTitleTextView(TextView textView, int position, PagerAdapter pagerAdapter) {
        if (position == mViewPager.getCurrentItem()) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabSelectedTextSize);
            textView.setTextColor(mTabSelectedTextColor);
            textView.setTypeface(isTabSelectedTextBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        } else {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabNormalTextSize);
            textView.setTextColor(mTabNormalTextColor);
            textView.setTypeface(isTabNormalTextBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        }
        textView.setGravity(Gravity.CENTER);
        String title = (String) pagerAdapter.getPageTitle(position);
        textView.setText(title);
    }

    /**
     * 设置tabView的layoutParams和点击监听，该TabView可以是任何一个View，但是必须包含一个TextView用于显示title
     */
    public void setTabViewLayoutParams(BasePagerTabView basePagerTabView, final int position) {
        LinearLayout.LayoutParams layoutParams;
        if (mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE || mPagerIndicatorMode == INDICATOR_MODE_SCROLLABLE_CENTER) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        }
        layoutParams.gravity = Gravity.CENTER;
        basePagerTabView.setLayoutParams(layoutParams);
        basePagerTabView.setPadding(mTabPadding, mTabPaddingTop, mTabPadding, mTabPaddingBottom);
        basePagerTabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemTabClickListener != null) {
                    mOnItemTabClickListener.onItemTabClick(position);
                }
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(position);
                }
            }
        });
        //如果沒有被添加过，则添加
        if (basePagerTabView.getParent() == null) {
            mTabParentView.addView(basePagerTabView);
        }
    }

    /**
     * 创建tab view
     */
    public BasePagerTabView createTabView(PagerAdapter pagerAdapter, final int position) {
        return new PagerTabView(mContext);
    }
}
