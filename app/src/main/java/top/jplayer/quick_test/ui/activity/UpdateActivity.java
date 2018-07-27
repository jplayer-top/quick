package top.jplayer.quick_test.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.net.download.DownloadByManager;
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
    private DownloadByManager mDownloadByManager;
    private VersionBean.VerBean mVerBean;

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
        mDownloadByManager = new DownloadByManager(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void responseVersion(VersionBean response) {
        if (response != null && response.ver != null && response.ver.build_num != null && Integer.parseInt
                (response.ver.build_num) > BuildConfig.VERSION_CODE) {
            mVerBean = response.ver;
            DialogLogout dialog = new DialogLogout(this).setTitle("更新提示").setSubTitle(mVerBean.description);
            dialog.show(R.id.btnSure, view -> {
                dialog.dismiss();
                AndPermission.with(this)
                        .permission(Permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(permissions -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !getPackageManager()
                                    .canRequestPackageInstalls()) {// 8.0  安装问题 是否允许外部安装
                                Uri packageURI = Uri.parse("package:" + getPackageName());
                                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                        packageURI);
                                startActivityForResult(intent, 10000);
                            } else {
                                updateVersion(mVerBean);
                            }
                        })
                        .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                        .start();
            });
        }
    }

    private void updateVersion(VersionBean.VerBean verBean) {
        int newCode = Integer.parseInt(verBean.build_num);
        mDownloadByManager.bind(newCode, verBean.description, verBean.url)
                .download().listener((currentByte, totalByte) -> {
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 10000 && mVerBean != null) {
            updateVersion(mVerBean);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDownloadByManager != null)
            mDownloadByManager.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDownloadByManager != null)
            mDownloadByManager.onPause();
    }
}
