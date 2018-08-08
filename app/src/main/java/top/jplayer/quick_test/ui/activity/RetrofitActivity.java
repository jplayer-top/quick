package top.jplayer.quick_test.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.presenter.RetrofitPresenter;

/**
 * Created by Obl on 2018/8/3.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class RetrofitActivity extends CommonToolBarActivity {
    @BindView(R.id.btnGet)
    Button mBtnGet;
    @BindView(R.id.btnPost)
    Button mBtnPost;
    @BindView(R.id.btnFileUp)
    Button mBtnFileUp;
    @BindView(R.id.btnFileDown)
    Button mBtnFileDown;
    private Unbinder mUnbinder;
    private RetrofitPresenter mPresenter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_retrofit;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mUnbinder = ButterKnife.bind(this, rootView);
        mPresenter = new RetrofitPresenter(this);
        mBtnGet.setOnClickListener(v -> mPresenter.requestGet("rerwer"));
        mBtnPost.setOnClickListener(v -> mPresenter.requestPost("17600001111", "123456"));
        mBtnFileDown.setOnClickListener(v -> mPresenter.requestFileDown("123456"));
        mBtnFileUp.setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.WRITE_EXTERNAL_STORAGE)
                    .onGranted(permissions -> {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    })
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
    }

    private DialogLoading mDialogLoading;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    String path = StringUtils.getRealFilePath(mActivity, uri);
                    if (path != null) {
                        File file = new File(path);
                        mPresenter.requestFile("10001", file, "image");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void downloadSuccess(File file) {
        ToastUtils.init().showQuickToast("文件地址" + file.getAbsolutePath());
    }
}
