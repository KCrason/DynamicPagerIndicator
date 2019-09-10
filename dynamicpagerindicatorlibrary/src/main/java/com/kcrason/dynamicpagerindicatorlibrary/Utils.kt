package com.kcrason.dynamicpagerindicatorlibrary

import android.content.Context

/**
 * @author KCrason
 * @date 2018/1/24
 */
object Utils {

    /**
     * 将sp值转换为px值
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * dip转px
     */
    fun dipToPx(context: Context, dip: Float): Int {
        return (dip * context.resources.displayMetrics.density + 0.5f).toInt()
    }


    fun getScreenPixWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }


    /**
     * 颜色渐变，需要把ARGB分别拆开进行渐变
     */
    fun evaluateColor(startValue: Int, endValue: Int, fraction: Float): Int {
        if (fraction <= 0) {
            return startValue
        }
        if (fraction >= 1) {
            return endValue
        }
        val startA = startValue shr 24 and 0xff
        val startR = startValue shr 16 and 0xff
        val startG = startValue shr 8 and 0xff
        val startB = startValue and 0xff

        val endA = endValue shr 24 and 0xff
        val endR = endValue shr 16 and 0xff
        val endG = endValue shr 8 and 0xff
        val endB = endValue and 0xff

        return (startA + (fraction * (endA - startA)).toInt() shl 24
                or (startR + (fraction * (endR - startR)).toInt() shl 16)
                or (startG + (fraction * (endG - startG)).toInt() shl 8)
                or startB + (fraction * (endB - startB)).toInt())
    }

}
