package top.jplayer.quick_test.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.jplayer.baseprolibrary.listener.observer.CustomObservable;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;

/**
 * Created by Obl on 2018/8/1.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class EventActivity extends CommonToolBarActivity {

    @BindView(R.id.btnBroad)
    Button mBtnBroad;
    @BindView(R.id.btnObserver)
    Button mBtnObserver;
    private CustomReceiver mReceiver;
    private CustomObservable mObservable;

    @Override
    public int initAddLayout() {
        return R.layout.activity_event;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        ButterKnife.bind(this, rootView);
        mBtnBroad.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(getPackageName() + ".custom");
            sendBroadcast(intent);
        });
        mObservable = new CustomObservable();
        mObservable.addObserver("func");
        mBtnObserver.setOnClickListener(v -> {
            HomeBean.ResponseBean.TypeBean itg = new HomeBean.ResponseBean.TypeBean();
            itg.typeTitle = "title";
            itg.typeUrl = "www.???.com";
            mObservable.change(itg);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mObservable.deleteObservers();
    }

    @Override
    public void onResume() {
        super.onResume();
        mReceiver = new CustomReceiver();
        registerReceiver(mReceiver, new IntentFilter(getPackageName() + ".custom"));

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }


    public class CustomReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ToastUtils.init().showQuickToast("接受到广播");
        }
    }
}
