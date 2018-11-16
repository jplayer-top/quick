package top.jplayer.quick_test.ui.fragment;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/11/16.
 * top.jplayer.quick_test.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ServerFragment extends SuperBaseFragment {
    public ServerFragment() {
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_server;
    }

    @Override
    protected void initData(View rootView) {
        rootView.findViewById(R.id.btnServer).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });
    }
}
