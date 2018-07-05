package top.jplayer.quick_test.fragment;

import android.view.View;

import top.jplayer.baseprolibrary.ui.Fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.ui.SampleActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/7/5.
 * top.jplayer.quick_test.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class HomeFragment extends SuperBaseFragment {
    @Override
    protected void initData(View rootView) {
        rootView.setOnClickListener(v -> ActivityUtils.init().start(getContext(), SampleActivity.class));
    }

    @Override
    public int initLayout() {
        return R.layout.layout_test;
    }
}
