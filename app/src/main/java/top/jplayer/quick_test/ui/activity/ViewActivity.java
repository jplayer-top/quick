package top.jplayer.quick_test.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robinhood.ticker.TickerView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.ui.activity.CityActivity;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.ui.activity.ContactActivity;
import top.jplayer.baseprolibrary.ui.dialog.DialogCard;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.NumAnimUtil;
import top.jplayer.baseprolibrary.widgets.nineimageview.NineGridImageView;
import top.jplayer.baseprolibrary.widgets.nineimageview.NineGridImageViewAdapter;
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
    @BindView(R.id.btn01)
    Button mBtn01;
    @BindView(R.id.nineGridImage)
    NineGridImageView mNineGridImage;
    @BindView(R.id.btn02)
    Button mBtn02;
    @BindView(R.id.btn03)
    Button mBtn03;
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
        mBtn01.setOnClickListener(v -> {
            DialogCard<StrList> strListDialogCard = new DialogCard<>();
            Bundle args = new Bundle();
            ArrayList<StrList> value = new ArrayList<>();
            value.add(new StrList());
            value.add(new StrList());
            value.add(new StrList());
            value.add(new StrList());
            args.putParcelableArrayList("list", value);
            strListDialogCard.setArguments(args);
            strListDialogCard.show(getSupportFragmentManager(), "card");
        });
        mTvNum.setOnClickListener(v -> NumAnimUtil.startAnim(mTvNum, 9.99f, "9.99"));
        ArrayList<String> list = new ArrayList<>();
        list.add("http://file.jplayer.top/image/1.jpg");
        list.add("http://file.jplayer.top/image/2.jpg");
        list.add("http://file.jplayer.top/image/3.png");
        list.add("http://file.jplayer.top/image/4.jpg");
        list.add("http://file.jplayer.top/image/1.jpg");
        list.add("http://file.jplayer.top/image/2.jpg");
        list.add("http://file.jplayer.top/image/3.png");
        list.add("http://file.jplayer.top/image/4.jpg");
        mNineGridImage.setAdapter(new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                Glide.with(context).load(s).apply(GlideUtils.init().options()).into(imageView);
            }
        });
        mNineGridImage.setImagesData(list);
        mBtn02.setOnClickListener(v -> {
            ActivityUtils.init().start(this, CityActivity.class, "城市定位");
        });
        mBtn03.setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.READ_CONTACTS)
                    .onGranted(permissions -> {
                        ActivityUtils.init().start(this, ContactActivity.class, "联系人");
                    })
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
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


    public class StrList implements Parcelable {

        protected StrList(Parcel in) {
        }

        public StrList() {
        }

        public final Creator<StrList> CREATOR = new Creator<StrList>() {
            @Override
            public StrList createFromParcel(Parcel in) {
                return new StrList(in);
            }

            @Override
            public StrList[] newArray(int size) {
                return new StrList[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }
}
