package top.jplayer.quick_test.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/8/10.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class LoginGuideActivity extends SuperBaseActivity {
    @Override
    protected int initRootLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void initRootData(View view) {
        isOpenDoubleBack = true;
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.RECORD_AUDIO, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> {
                })
                .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                .start();
        BGABanner bgaBanner = view.findViewById(R.id.bgaBanner);
        Button btnToLogin = view.findViewById(R.id.btnToLogin);
        btnToLogin.setOnClickListener(v -> ToastUtils.init().showQuickToast("Login"));
        Button btnToRegister = view.findViewById(R.id.btnToRegister);
        btnToRegister.setOnClickListener(v -> {
            ToastUtils.init().showQuickToast("Register");

        });
        bgaBanner.setAdapter((banner, itemView, model, position) -> {
            if (model != null) {
                TextView tvTitle = itemView.findViewById(R.id.tvTitle);
                ImageView ivPic = itemView.findViewById(R.id.ivSrc);
                tvTitle.setText(((GuideBean) model).title);
                Glide.with(mActivity).load(((GuideBean) model).pic).into(ivPic);
            }
        });
        List<GuideBean> beans = new ArrayList<>();
        beans.add(new GuideBean("时尚家居+北欧风格", "http://file.jplayer.top/image/1.jpg"));
        beans.add(new GuideBean("空灵少女+动漫画风", "http://file.jplayer.top/image/2.jpg"));
        beans.add(new GuideBean("可爱灵动+灵气十足", "http://file.jplayer.top/image/3.png"));
        beans.add(new GuideBean("吸猫必备+萌宠小花", "http://file.jplayer.top/image/4.jpg"));
        bgaBanner.setData(R.layout.layout_bga_image_text, beans, null);
    }

    public class GuideBean {
        public String title;
        public String pic;

        public GuideBean(String title, String pic) {
            this.title = title;
            this.pic = pic;
        }
    }
}
