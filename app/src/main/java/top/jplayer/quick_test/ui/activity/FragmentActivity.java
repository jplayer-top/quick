package top.jplayer.quick_test.ui.activity;

import android.view.View;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.ui.fragment.FuncFragment;

/**
 * Created by Obl on 2018/8/15.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class FragmentActivity extends SuperBaseActivity {
    @Override
    protected int initRootLayout() {
        return R.layout.activity_fragment;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        FuncFragment fragment = new FuncFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.ffLayout, fragment).commit();
    }
}
