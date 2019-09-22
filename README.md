# DynamicPagerIndicator
仿爱奇艺/腾讯视频ViewPager导航条实现，支持自定义导航条高度，宽度，颜色变化，字体大小变化。支持多种滚动模式，支持自定义每个TabView的样式。

[Demo体验：app-debug.apk](https://github.com/KCrason/DynamicPagerIndicator/blob/master/app/apk/app-debug.apk)

![dynamic.gif](http://upload-images.jianshu.io/upload_images/1860505-c4fcaaee373ce931.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 一、如何引入DynamicPagerIndicator？
```
在module的build.gradle 添加:
compile 'com.kcrason:dynamicpagerindicator:1.3.0'
3.0以上gradle版本为：
implementation 'com.kcrason:dynamicpagerindicator:1.3.0'
```
### 二、如何使用？
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
### 三、属性说明
- `pagerIndicatorMode : 指示器的显示模式，共有三种。`
`1、scrollable：适用于ViewPager的count较多时。可滑动。默认从左向右排列显示`
`2、scrollable_center：居中显示，适用于ViewPager的count较少时，且需要居中显示`
`3、fixed：均分模式，该模式下会平均分配TabView的宽度`

- `tabPadding：其为TabView的左右内边距。`

- `tabNormalTextSize：其为TabView中Title的文字正常状态文字大小。`

- `tabSelectedTextSize：其为TabView中Title的文字选中状态文字大小。`

- `tabNormalTextColor：其为TabView中Title的文字正常状态文字颜色。`

- `tabSelectedTextColor：其为TabView中Title的文字选中状态文字颜色。`

- `tabTextColorMode/tabTextSizeMode：其为TabView的Title文字颜色和字体变化模式，共两中：common：普通变化；gradient：渐变模式。 `

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

### 四、自定义TabView(即自定义指示器的Item的样式)
1、创建一个类继承`PagerTabView`，重写`initPagerTabView()`方法去将自定义的`View`加入`PagerTabView`。并复写`getTitleTextView()`返回自定义`View`的`TextView`（该`TextView`用于显示指示器的标题，必不可少）。
```java
public class CustomPagerTabView extends BasePagerTabView {

    private TextView mTextView;

    public PagerTabView(Context context) {
        super(context);
    }

    public PagerTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public TextView getTabTextView() {
        return mTextView;
    }

    @Override
    public View onCreateTabView(Context context) {
        mTextView = new TextView(context);
        return mTextView;
    }
}
```
2、创建一个类继承`DynamicPagerIndicator`并重写`createTabView()`。在`createTabView()`创建自定义的`PagerTabView`并将其设置给`DynamicPagerIndicator`。
```java
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
    public BasePagerTabView createTabView(PagerAdapter pagerAdapter, final int position) {
        return new CustomPagerTabView(mContext);
    }
}
```
3、在xml中使用自定义的`CustomPagerIndicator`，属性设置和`DynamicPagerIndicator`无区别。
```java
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
### 设置监听

1、设置单个TabView点击监听：通过DynamicPagerIndicator的对象设置`OnItemTabClickListener`即可。
```java
dynamicPagerIndicator.setOnItemTabClickListener(new DynamicPagerIndicator.OnItemTabClickListener() {
            @Override
            public void onItemTabClick(int position) {
                Toast.makeText(this, "you click " + position, Toast.LENGTH_SHORT).show();
            }
        });
```

2、设置ViewPager的监听，由于DynamicPagerIndicator内部已经设置了ViewPager的监听，所以外部如果需要ViewPager的监听，需要用到新的接口`OnOutPageChangeListener`。
```java
        dynamicPagerIndicator.setOnOutPageChangeListener(new DynamicPagerIndicator.OnOutPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        
        或者通过实现内部静态类
        
        dynamicPagerIndicator1.setOnOutPageChangeListener(new DynamicPagerIndicator.SimpleOnOutPageChangeListener(){
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
```

### 注意事项
- 务必先给viewPager设置adapter之后再将ViewPager对象设置给DynamicPagerIndicator


### 版本更新 

#### 1.3.0
- 修复部分bug。

#### 1.1.0
- 添加是否显示为粗体
- 添加字体滑动大小过程渐变。
- 修复部分bug,优化使用体验。

##### 1.0.6

- 修复某些情况下tab字体颜色出现不一致的情况。

##### 1.0.4

- 修复若干bug。
- 修改Indicator数量动态更新问题。

##### 1.0.3

- 增加`pagerIndicatorScrollToCenterMode`模式选择,意为导航条滚动居中的模式。该模式只针对`pagerIndicatorMode=scrollable`时有效。共有两种，第一种为linkage(联动模式)：滑动页面时，整个导航栏同步移动到居中的位置。第二种为transaction(异动模式)：滑动页面完整后，才将需要居中显示的栏目滑动的居中的位置。

- 修改更新`TabView`数量的方法为`updateTabView()`,不再使用`createOrUpdateTabView(ViewPager viewpager)`去更新`TabView`的数量。

##### 1.0.2

- 增加`tabTextColorMode`模式选择，共有两种。第一种为`common`，即普通文字变化模式。第二种为`gradient`，即从未选中的颜色渐变到选中的颜色。

- TabView的数量可动态配置。配合`ViewPager+Fragment(多个)`动态更新时的情况。当需要更新`TabView`的数量时。使用`DynamicPagerIndicator`对象的去调用`createOrUpdateTabView(ViewPager viewpager)`即可。

### 鉴于掘金和简书相关平台已不太关注了，有任何Bug欢迎提交issues。
