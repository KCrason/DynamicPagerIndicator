package com.kcrason.dynamicpagerindicatorlibrary

import android.content.Context
import android.view.View
import android.widget.TextView

/**
 * @author KCrason
 * @date 2019/9/5 11:43
 * @description
 */
interface IPagerTabView {

    fun getTabTextView(): TextView?

    fun onCreateTabView(context: Context): View
}
