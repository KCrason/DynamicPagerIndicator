package com.kcrason.dynamicpagerindicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * @author KCrason
 * @date 2018/1/21
 */
public class PagerFragment extends Fragment {

    private int mFragmentIndex;

    public static PagerFragment create(int index) {
        PagerFragment pagerFragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key_of_index", index);
        pagerFragment.setArguments(bundle);
        return pagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentIndex = getArguments().getInt("key_of_index");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout parentView = view.findViewById(R.id.parent_view);
        TextView textView = view.findViewById(R.id.txt_content);
        textView.setText(String.format(Locale.CHINA, "This is %d fragment", mFragmentIndex));
        switch (mFragmentIndex) {
            case 0:
                parentView.setBackgroundColor(Color.RED);
                break;
            case 1:
                parentView.setBackgroundColor(Color.YELLOW);
                break;
            case 2:
                parentView.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                parentView.setBackgroundColor(Color.BLACK);
                break;
            case 4:
                parentView.setBackgroundColor(Color.BLUE);
                break;
            default:
                parentView.setBackgroundColor(Color.WHITE);
        }
    }
}
