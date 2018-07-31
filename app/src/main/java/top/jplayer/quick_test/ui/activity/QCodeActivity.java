package top.jplayer.quick_test.ui.activity;

import android.widget.Button;
import android.widget.FrameLayout;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.ui.activity.ActivityCustomCapture;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/7/30.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class QCodeActivity extends CommonToolBarActivity {
    @BindView(R.id.btnOpen)
    Button mBtnOpen;
    private Unbinder mUnbinder;

    @Override
    public int initAddLayout() {
        return R.layout.activity_qcode;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mUnbinder = ButterKnife.bind(this, rootView);
        mBtnOpen.setOnClickListener(v -> {
            startCamera();
        });
    }

    private void startCamera() {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> ActivityUtils.init().startForResult(this, ActivityCustomCapture.class,
                        "扫一扫"))
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                .start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
