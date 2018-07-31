package top.jplayer.quick_test.ui.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.alive.service.WhiteService;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.NotificationUtil;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/4/3.
 * top.jplayer.baseprolibrary.live
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ALiveActivity extends CommonToolBarActivity {


    @BindView(R.id.btn_white)
    Button mBtnWhite;
    @BindView(R.id.btn_send_notice)
    Button mBtnSendNotice;
    @BindView(R.id.btn_back)
    Button mBtnBack;
    @BindView(R.id.btn_notice)
    Button mBtnNotice;
    private Unbinder mUnbinder;

    @Override
    public int initAddLayout() {
        return R.layout.activity_alive;
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
        mBtnBack.setOnClickListener(v -> NotificationUtil.intentBackGround());
        mBtnWhite.setOnClickListener(v -> startService(new Intent(getApplicationContext(), WhiteService.class)));
        mBtnNotice.setOnClickListener(v -> NotificationUtil.intentNotice());
        mBtnSendNotice.setOnClickListener(v -> NotificationUtil.init(this)
                .sendNotification("通知", "适配8.0手机通知发送", NotificationUtil.id2));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
