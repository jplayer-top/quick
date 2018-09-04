package top.jplayer.quick_test.ui.activity;

import android.widget.FrameLayout;

import java.util.ArrayList;

import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.ui.adapter.SampleAdapter;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/9/4.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class CollapsingActivity extends CommonToolBarActivity {
    @Override
    public int initAddLayout() {
        return R.layout.activity_collapsing;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        ArrayList<String> data = new ArrayList<>();
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        data.add("8");
        data.add("9");
        mRecyclerView.setAdapter(new SampleAdapter(data));
    }
}
