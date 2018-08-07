package top.jplayer.quick_test.ui.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.ui.activity.ActivityCustomCapture;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.FlashLightUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;
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
    @BindView(R.id.btnDefOpen)
    Button mBtnDefOpen;
    @BindView(R.id.btnCreate)
    Button mBtnCreate;
    @BindView(R.id.btnFlash)
    Button mBtnFlash;
    @BindView(R.id.ivSrc)
    ImageView mIvSrc;
    private Unbinder mUnbinder;
    private boolean isLight = false;

    @Override

    public int initAddLayout() {
        return R.layout.activity_qcode;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mUnbinder = ButterKnife.bind(this, rootView);
        mBtnOpen.setOnClickListener(v -> startCamera(ActivityCustomCapture.class));
        mBtnDefOpen.setOnClickListener(v -> startCamera(CaptureActivity.class));
        mBtnCreate.setOnClickListener(v -> {
            String textContent = "I am Text";
            if (TextUtils.isEmpty(textContent)) {
                ToastUtils.init().showQuickToast(textContent);
                return;
            }
            Bitmap bitmap = CodeUtils.createImage(textContent, 400, 400, BitmapUtil.drawableToBitmap(getResources()
                    .getDrawable(R.drawable.ic_launcher_round)));
            mIvSrc.setImageBitmap(bitmap);
        });
        mBtnFlash.setOnClickListener(v -> {
            isLight = !isLight;
            FlashLightUtil.init(this).flashLight(isLight);
        });
    }

    private void startCamera(Class clazz) {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> ActivityUtils.init().startForResult(mActivity, clazz,
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
