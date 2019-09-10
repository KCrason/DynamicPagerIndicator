package com.kcrason.dynamicpagerindicator

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import java.util.Locale

/**
 * @author KCrason
 * @date 2018/1/21
 */
class PagerFragment : Fragment() {

    private var mFragmentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentIndex = arguments!!.getInt("key_of_index")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val parentView = view.findViewById<LinearLayout>(R.id.parent_view)
        val textView = view.findViewById<TextView>(R.id.txt_content)
        textView.text = String.format(Locale.CHINA, "This is %d fragment", mFragmentIndex)
        when (mFragmentIndex) {
            0 -> parentView.setBackgroundColor(Color.RED)
            1 -> parentView.setBackgroundColor(Color.YELLOW)
            2 -> parentView.setBackgroundColor(Color.GREEN)
            3 -> parentView.setBackgroundColor(Color.BLACK)
            4 -> parentView.setBackgroundColor(Color.BLUE)
            else -> parentView.setBackgroundColor(Color.WHITE)
        }
    }

    companion object {

        fun create(index: Int): PagerFragment {
            val pagerFragment = PagerFragment()
            val bundle = Bundle()
            bundle.putInt("key_of_index", index)
            pagerFragment.arguments = bundle
            return pagerFragment
        }
    }
}
