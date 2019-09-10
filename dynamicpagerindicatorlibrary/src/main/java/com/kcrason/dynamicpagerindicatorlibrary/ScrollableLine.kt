package com.kcrason.dynamicpagerindicatorlibrary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


/**
 * @author KCrason
 * @date 2018/1/21
 */
class ScrollableLine @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mIndicatorLineRadius: Float = 0.toFloat()

    private var mIndicatorLineHeight: Int = 0

    private var mRectF: RectF? = null

    private var mPaint: Paint? = null

    private var mIndicatorStartX: Float = 0.toFloat()

    private var mIndicatorEndX: Float = 0.toFloat()

    init {
        initScrollableLine()
    }


    private fun initScrollableLine() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.FILL
        mRectF = RectF()
    }

    /**
     * 设置导航条的高度
     *
     * @param indicatorLineHeight
     */
    fun setIndicatorLineHeight(indicatorLineHeight: Int) {
        mIndicatorLineHeight = indicatorLineHeight
    }


    /**
     * 设置导航条的圆角
     *
     * @param indicatorLineRadius
     * @return
     */
    fun setIndicatorLineRadius(indicatorLineRadius: Float): ScrollableLine {
        mIndicatorLineRadius = indicatorLineRadius
        return this
    }

    fun updateScrollLineWidth(indicatorStartX: Float, indicatorEndX: Float, indicatorStartColor: Int, indicatorEndColor: Int, fraction: Float) {
        this.mIndicatorStartX = indicatorStartX
        this.mIndicatorEndX = indicatorEndX
        mPaint!!.color = Utils.evaluateColor(indicatorStartColor, indicatorEndColor, fraction)
        postInvalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mRectF!!.set(mIndicatorStartX, 0f, mIndicatorEndX, mIndicatorLineHeight.toFloat())
        canvas.drawRoundRect(mRectF!!, mIndicatorLineRadius, mIndicatorLineRadius, mPaint!!)
    }
}
