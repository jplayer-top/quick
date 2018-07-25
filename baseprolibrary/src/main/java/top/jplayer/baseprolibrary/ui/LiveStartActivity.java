package top.jplayer.baseprolibrary.ui;

import android.content.Intent;
import android.view.View;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.alive.service.WhiteService;
import top.jplayer.baseprolibrary.utils.NotificationUtil;

/**
 * Created by Obl on 2018/4/3.
 * top.jplayer.baseprolibrary.live
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class LiveStartActivity extends SuperBaseActivity implements View.OnClickListener {
    @Override
    protected int initRootLayout() {
        return R.layout.activity_live;
    }

    @Override
    public void initRootData(View view) {
        view.findViewById(R.id.btn_white).setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);
        view.findViewById(R.id.btn_notice).setOnClickListener(this);
        view.findViewById(R.id.btn_send_notice).setOnClickListener(this);

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar).init();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_white) { //系统正常的前台Service，白色保活手段
            Intent whiteIntent = new Intent(getApplicationContext(), WhiteService.class);
            startService(whiteIntent);
        } else if (viewId == R.id.btn_back) {// 定制rom 手机 开启手机管家，用户手动保活
            NotificationUtil.intentBackGround();
        } else if (viewId == R.id.btn_notice) {// 开启通知 管理
            NotificationUtil.intentNotice();
        } else if (viewId == R.id.btn_send_notice) {// 发送通知
            new NotificationUtil(this).sendNotification("通知", "适配8.0手机通知发送", NotificationUtil.id2);
        }
    }

}
