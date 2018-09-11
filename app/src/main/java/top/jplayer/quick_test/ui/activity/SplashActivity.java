package top.jplayer.quick_test.ui.activity;

import android.view.View;

import com.github.florent37.viewanimator.ViewAnimator;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/7/27.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class SplashActivity extends SuperBaseActivity {
    @Override
    protected int initRootLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        ViewAnimator.animate(view.findViewById(R.id.image))
                .fadeOut()
                .duration(1000)
                .onStop(() -> {
                    ActivityUtils.init().start(this, FragmentActivity.class);
                    finish();
                })
                .start();

    }
}
