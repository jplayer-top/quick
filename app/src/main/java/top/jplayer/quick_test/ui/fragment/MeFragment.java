package top.jplayer.quick_test.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.CameraUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.widgets.polygon.PolygonImageView;
import top.jplayer.quick_test.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Obl on 2018/8/28.
 * top.jplayer.quick_test.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class MeFragment extends SuperBaseFragment {
    @BindView(R.id.ivUserAvatar)
    PolygonImageView mIvUserAvatar;
    private Unbinder mUnbinder;
    private File mFile;

    @Override
    public int initLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData(View rootView) {
        mUnbinder = ButterKnife.bind(this, rootView);
        mIvUserAvatar.setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(permissions -> CameraUtil.getInstance().openSingalCamer(this.mActivity))
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (String path : pathList) {
                mFile = new File(path);
                String fileName = mFile.getName() + mFile.getPath().substring(mFile.getPath().indexOf("."));
                LogUtil.e(fileName);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
