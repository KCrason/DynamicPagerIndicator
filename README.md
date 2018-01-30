# DynamicPagerIndicator
仿爱奇艺/腾讯视频ViewPager导航条实现，支持自定义导航条高度，宽度，颜色变化，字体大小变化。支持多种滚动模式，支持自定义每个TabView的样式。

![dynamic.gif](http://upload-images.jianshu.io/upload_images/1860505-c4fcaaee373ce931.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 一、如何引入DynamicPagerIndicator？
```
在module的build.gradle 添加:
compile 'com.kcrason:dynamicpagerindicator:1.0.2'
3.0以上gradle版本为：
implementation 'com.kcrason:dynamicpagerindicator:1.0.2'
```
#### 二、如何使用？
1、将DynamicPagerIndicator 添加到指定xml
```
<com.kcrason.dynamicpagerindicatorlibrary.DynamicPagerIndicator
            android:id="@+id/dynamic_pager_indicator1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:indicatorLineScrollMode="dynamic"
            app:pagerIndicatorMode="scrollable_center"
            />
```
2、将ViewPager对象设置给DynamicPagerIndicator
```
ViewPager viewPager = findViewById(R.id.view_pager);
DynamicPagerIndicator dynamicPagerIndicator = findViewById(R.id.dynamic_pager_indicator);
dynamicPagerIndicator.setViewPager(viewPager);
```
#### 三、属性说明
- `pagerIndicatorMode : 指示器的显示模式，共有三种。`
`1、scrollable：适用于ViewPager的count较多时。可滑动。默认从左向右排列显示`
`2、scrollable_center：居中显示，适用于ViewPager的count较少时，且需要居中显示`
`3、fixed：均分模式，该模式下会平均分配TabView的宽度`

- `tabPadding：其为TabView的左右内边距。`

- `tabNormalTextSize：其为TabView中Title的文字正常状态文字大小。`

- `tabSelectedTextSize：其为TabView中Title的文字选中状态文字大小。`

- `tabNormalTextColor：其为TabView中Title的文字正常状态文字颜色。`

- `tabSelectedTextColor：其为TabView中Title的文字选中状态文字颜色。`

- `indicatorLineHeight：其为TabView下的导航条的高度。`

- `indicatorLineWidth：其为TabView下的导航条的宽度。`

- `indicatorLineRadius：其为TabView下的导航条的圆角，默认为0，即不绘制圆角。`

- `indicatorLineStartColor：其为TabView下的导航条变化的开始颜色。如果不需要颜色变换效果，将indicatorLineStartColor和indicatorLineEndColor设置成一致即可。`

- `indicatorLineEndColor：其为TabView下的导航条变化的结束颜色。如果不需要颜色变换效果，将indicatorLineStartColor和indicatorLineEndColor设置成一致即可。`

- `indicatorLineMarginTop：其为TabView下的导航条的上边距。`

- `indicatorLineMarginBottom：其为TabView下的导航条的下边距。`

- `indicatorLineScrollMode：其为TabView下的导航条的滚动模式，共有两种`
`1、dynamic：即爱奇艺/腾讯视频那种可变化长度的效果。导航条长度、位置均变化。`
`2、transform：普通移动效果，导航条长度不变，位置变化。`

#### 四、自定义TabView(即自定义指示器的Item的样式)
1、创建一个类继承`PagerTabView`，重写`initPagerTabView()`方法去将自定义的`View`加入`PagerTabView`。并复写`getTitleTextView()`返回自定义`View`的`TextView`（该`TextView`用于显示指示器的标题，必不可少）。
```
public class CustomPagerTabView extends PageTabView {

    private TextView mTextView;

    public CustomPagerTabView(Context context) {
        super(context);
    }

    .....省略部分构造方法....

    /**
     *自定义PagerTabView必须复写该方法返回一个TextView用于显示标题
     * @return
     */
    @Override
    public TextView getTitleTextView() {
        return mTextView;
    }

    @Override
    public void initPagerTabView(Context context) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_view, this, false);
        mTextView = view.findViewById(R.id.title);
        addView(view);
    }
}
```
2、创建一个类继承`DynamicPagerIndicator`并重写`createTabView()`。在`createTabView()`创建自定义的`PagerTabView`并将其设置给`DynamicPagerIndicator`。
```
public class CustomPagerIndicator extends DynamicPagerIndicator {

    public CustomPagerIndicator(Context context) {
        super(context);
    }

    public CustomPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void createTabView(PagerAdapter pagerAdapter, final int position) {
        CustomPagerTabView customPagerTabView = new CustomPagerTabView(mContext);
        setTabTitleTextView(customPagerTabView.getTitleTextView(), position, pagerAdapter);
        setTabViewLayoutParams(customPagerTabView, position);
    }
}
```
3、在xml中使用自定义的`CustomPagerIndicator`，属性设置和`DynamicPagerIndicator`无区别。
```
  <com.kcrason.dynamicpagerindicator.CustomPagerIndicator
            android:id="@+id/dynamic_pager_indicator5"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:indicatorLineHeight="20dp"
            app:indicatorLineRadius="8dip"
            app:indicatorLineScrollMode="dynamic"
            app:pagerIndicatorMode="fixed"
            />
```
#### 版本更新 1.0.2

- 增加`tabTextColorMode`模式选择，共有两种。第一种为`common`，即普通文字变化模式。第二种为`gradient`，即从未选中的颜色渐变到选中的颜色。

- TabView的数量可动态配置。配合`ViewPager+Fragment(多个)`动态更新时的情况。当需要更新`TabView`的数量时。使用`DynamicPagerIndicator`对象的去调用`createOrUpdateTabView(ViewPager viewpager)`即可。
