package top.jplayer.quick_test.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.robinhood.ticker.TickerView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;
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
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.CardTransformer;
import top.jplayer.baseprolibrary.widgets.ShSwitchView;
import top.jplayer.baseprolibrary.widgets.SlidingButtonView;
import top.jplayer.baseprolibrary.widgets.nineimageview.NineGridImageView;
import top.jplayer.baseprolibrary.widgets.nineimageview.NineGridImageViewAdapter;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.ui.adapter.AdapterCardInfo;

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
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.layout_content)
    RelativeLayout mLayoutContent;
    @BindView(R.id.slideButton)
    SlidingButtonView mSlideButton;
    @BindView(R.id.switchView)
    ShSwitchView mSwitchView;

    @BindView(R.id.viewFlipper)
    ViewFlipper mViewFlipper;
    private Unbinder mUnbinder;
    protected static final Random RANDOM = new Random(System.currentTimeMillis());
    private Disposable mSubscribe;
    private AdapterCardInfo mViewPagerAdapter;

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
        mViewPager.setPageTransformer(true, new CardTransformer(30));
        ArrayList<NoticesBean> listNotice = new ArrayList<>();
        listNotice.add(new NoticesBean(0));
        listNotice.add(new NoticesBean(1));
        listNotice.add(new NoticesBean(1));
        listNotice.add(new NoticesBean(0));
        mViewPagerAdapter = new AdapterCardInfo(listNotice);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mLayoutContent.getLayoutParams().width = ScreenUtils.getScreenWidth();
        mSlideButton.setSlidingButtonListener(new SlidingButtonView.IonSlidingButtonListener() {
            @Override
            public void onMenuIsOpen(View view) {
                mMenu = (SlidingButtonView) view;
            }

            @Override
            public void onDownOrMove(SlidingButtonView slidingButtonView) {
                if (mMenu != null && mMenu != slidingButtonView) {
                    mMenu.closeMenu();
                    mMenu = null;
                }
            }
        });
        if (mMenu != null) {
            mSlideButton.closeMenu();
        }
        mSwitchView.setOnSwitchStateChangeListener((shSwitchView, isOn) -> {
            ToastUtils.init().showQuickToast(isOn + "");
        });
        List<String> info = new ArrayList<>();
        info.add("大家好，我是Oblivion。");
        info.add("欢迎大家关注我哦！");
        info.add("GitHub帐号：oblivion0001");
        info.add("个人博客：jplayet.top");
        mViewFlipper.addView(getTextImageView(info.get(0)));
        mViewFlipper.addView(getTextImageView(info.get(1)));
        mViewFlipper.addView(getTextImageView(info.get(2)));
        mViewFlipper.addView(getTextImageView(info.get(3)));
    }

    private View getTextImageView(String s) {
        View view = View.inflate(this, R.layout.layout_marquee_text_image, null);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(s);
        return view;
    }

    private SlidingButtonView mMenu = null;

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

    public static class NoticesBean {

        public String content;
        public int type;

        public NoticesBean(int type) {
            this.type = type;
        }

        public static final int INT_NOTICE = 0;
        public static final int INT_OTHER = 1;

        public int getType() {
            if (type == INT_NOTICE) {
                return INT_NOTICE;
            } else {
                return INT_OTHER; //不受控制的类型《过滤》

            }
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
