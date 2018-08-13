package top.jplayer.quick_test.ui.activity;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.robinhood.ticker.TickerView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.NumAnimUtil;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/8/8.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ViewActivity extends CommonToolBarActivity {
    @BindView(R.id.ticker)
    TickerView mTicker;
    @BindView(R.id.tvNum)
    TextView mTvNum;
    private Unbinder mUnbinder;
    protected static final Random RANDOM = new Random(System.currentTimeMillis());
    private Disposable mSubscribe;

    @Override
    public int initAddLayout() {
        return R.layout.activity_view;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mUnbinder = ButterKnife.bind(this, rootView);
        mTicker.setText(getRandomNumber(RANDOM.nextInt(2) + 6));
        mSubscribe = Observable.interval(2, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
            mTicker.setText(getRandomNumber(RANDOM.nextInt(2) + 6));
        });
        NumAnimUtil.startAnim(mTvNum, 9.99f, "9.99");
    }

    protected String getRandomNumber(int digits) {
        final int digitsInPowerOf10 = (int) Math.pow(10, digits);
        return Integer.toString(RANDOM.nextInt(digitsInPowerOf10) +
                digitsInPowerOf10 * (RANDOM.nextInt(8) + 1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (!mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }

}
