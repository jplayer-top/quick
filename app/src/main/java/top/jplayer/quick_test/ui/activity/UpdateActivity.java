package top.jplayer.quick_test.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.net.download.DownloadByChrome;
import top.jplayer.baseprolibrary.net.download.DownloadByManager;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.ui.dialog.DialogLogout;
import top.jplayer.baseprolibrary.utils.ToastUtils;
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

public class UpdateActivity extends CommonToolBarActivity {


    @BindView(R.id.btn01)
    Button mBtn01;
    @BindView(R.id.btn02)
    Button mBtn02;
    @BindView(R.id.btn03)
    Button mBtn03;
    private Unbinder mUnbinder;
    private DownloadByManager mDownloadByManager;
    private VersionBean.VerBean mVerBean;
    private int vClick = 0;
    private UpdatePresenter presenter;

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
        mBtn01.setOnClickListener(this::whoClick);
        mBtn02.setOnClickListener(this::whoClick);
        mBtn03.setOnClickListener(this::whoClick);
        mDownloadByManager = new DownloadByManager(this);
    }

    private void whoClick(View v) {
        if (v == mBtn01) {
            vClick = 0;
        } else if (v == mBtn02) {
            vClick = 1;
        } else if (v == mBtn03) {
            vClick = 2;
        }
        presenter = new UpdatePresenter(this);
        presenter.requestUpdate();
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
                            if (vClick == 0) {
                                downloadByManager();
                            } else if (vClick == 1) {
                                DownloadByChrome.byChrome(this, Uri.parse(mVerBean.url));
                            } else if (vClick == 2) {
                                presenter.requestApk(mVerBean.url);
                            }
                        })
                        .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                        .start();
            });
        }
    }

    private void downloadByManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !getPackageManager()
                .canRequestPackageInstalls()) {// 8.0  安装问题 是否允许外部安装
            Uri packageURI = Uri.parse("package:" + getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                    packageURI);
            startActivityForResult(intent, 10000);
        } else {
            updateVersion(mVerBean);
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
        registerReceiver(dynamicReceiver, new IntentFilter(getPackageName() + ".download"));
        if (mDownloadByManager != null)
            mDownloadByManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(dynamicReceiver);
        if (mDownloadByManager != null)
            mDownloadByManager.onPause();
    }

    private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ((context.getPackageName() + ".download").equals(intent.getAction())) {
                long progress = intent.getLongExtra("progress", 0L);
                long total = intent.getLongExtra("total", 0L);
                ToastUtils.init().showQuickToast(context, progress + "/" + total);
            }
        }
    };

}
