package top.jplayer.quick_test.ui.activity;

import android.widget.Button;
import android.widget.FrameLayout;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.net.download.DownloadByDManager;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarWhiteActivity;
import top.jplayer.baseprolibrary.ui.dialog.DialogLogout;
import top.jplayer.quick_test.BuildConfig;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.VersionBean;
import top.jplayer.quick_test.mvp.presenter.UpdatePresenter;

/**
 * Created by Obl on 2018/4/3.
 * top.jplayer.baseprolibrary.live
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class UpdateActivity extends CommonToolBarWhiteActivity {


    @BindView(R.id.btn01)
    Button mBtn01;
    @BindView(R.id.btn02)
    Button mBtn02;
    private Unbinder mUnbinder;

    @Override
    public int initAddLayout() {
        return R.layout.activity_update;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar).init();
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mUnbinder = ButterKnife.bind(this, rootView);
        mBtn01.setOnClickListener(v -> new UpdatePresenter(this).requestUpdate());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void responseVersion(VersionBean response) {
        if (response != null && response.ver != null && response.ver.build_num != null && Integer.parseInt
                (response.ver.build_num) > BuildConfig.VERSION_CODE) {
            VersionBean.VerBean verBean = response.ver;
            DialogLogout dialog = new DialogLogout(this).setTitle("更新提示").setSubTitle(verBean.description);
            dialog.show(R.id.btnSure, view -> {
                dialog.dismiss();
                AndPermission.with(this)
                        .permission(Permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(permissions -> {
                            DownloadByDManager util = new DownloadByDManager(this);
                            util.download(verBean.url, "sada", "asdasd");
                        })
                        .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                        .start();
            });
        }
    }
}
