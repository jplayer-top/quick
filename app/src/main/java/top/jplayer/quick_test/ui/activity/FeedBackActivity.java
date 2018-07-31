package top.jplayer.quick_test.ui.activity;

import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.FeedBackUtil;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/7/31.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class FeedBackActivity extends CommonToolBarActivity {
    @BindView(R.id.btnVibrator)
    Button mBtnVibrator;
    @BindView(R.id.btnVibrator1)
    Button mBtnVibrator1;
    @BindView(R.id.btnVibratorCancel)
    Button mBtnVibratorCancel;
    @BindView(R.id.btnRing)
    Button mBtnRing;
    @BindView(R.id.btnBeep)
    Button mBtnBeep;
    @BindView(R.id.btnMediaCancel)
    Button mBtnMediaCancel;
    @BindView(R.id.btnPool)
    Button mBtnPool;
    @BindView(R.id.btnPoolCancel)
    Button mBtnPoolCancel;
    private Unbinder mUnbinder;
    private List<Integer> mIntegersStream;
    private List<Integer> mIntegers;

    @Override
    public int initAddLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mUnbinder = ButterKnife.bind(this, rootView);
        mBtnVibrator.setOnClickListener(v -> FeedBackUtil.init(this).playVibrator(200));
        mBtnVibrator1.setOnClickListener(v -> {
            long[] patter = {1000, 1000, 2000, 50};
            FeedBackUtil.init(this).playVibrator(patter, 0);

        });
        mBtnVibratorCancel.setOnClickListener(v -> FeedBackUtil.init(this).stopVibrator());
        mBtnRing.setOnClickListener(v -> FeedBackUtil.init(this).playRing());
        mBtnBeep.setOnClickListener(v -> FeedBackUtil.init(this).playBeep(com.uuzuche.lib_zxing.R.raw.beep));

        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.raw.wl1);
        list.add(R.raw.wl2);
        //load  需要时间
        mIntegers = FeedBackUtil.init(this).loadSoundPool(list);
        mBtnPool.setOnClickListener(v -> mIntegersStream = FeedBackUtil.init(this).playSoundPool(mIntegers));
        mBtnPoolCancel.setOnClickListener(v -> {
            Observable.fromIterable(mIntegersStream).subscribe(integer -> FeedBackUtil.init(this).stopSoundPool(integer));
        });
        mBtnMediaCancel.setOnClickListener(v -> FeedBackUtil.init(this).stopMedia());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
