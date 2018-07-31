package top.jplayer.baseprolibrary.ui.activity;

import android.annotation.SuppressLint;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.utils.SizeUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2018/4/11.
 * com.ilanchuang.xiaoi.ui
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

@SuppressLint("Registered")
public abstract class CommonToolBarActivity extends SuperBaseActivity {

    public Toolbar mToolBar;
    public TextView mTvToolTitle;
    public TextView mTvToolRight;
    public ImageView mIvToolRight;
    public ImageView mIvToolRightLeft;
    public ImageView mIvToolLeft;
    public FrameLayout mRootView;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_common_toolbar;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(mToolBar).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        initToolBar(view);
        mRootView = view.findViewById(R.id.rootView);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRootView.getLayoutParams();
        layoutParams.topMargin = toolMarginTop();
        mRootView.removeAllViews();
        mRootView.addView(View.inflate(this, initAddLayout(), null));
        initAddView(mRootView);
        initListener();
    }

    private void initToolBar(View view) {
        mToolBar = view.findViewById(R.id.toolbar);
        mIvToolLeft = view.findViewById(R.id.ivToolLeft);
        mIvToolRight = view.findViewById(R.id.ivToolRight);
        mTvToolRight = view.findViewById(R.id.tvToolRight);
        mTvToolTitle = view.findViewById(R.id.tvToolTitle);
        mTvToolTitle.setText(StringUtils.init().fixNullStr(getIntent().getStringExtra("title")));
        mIvToolRightLeft = view.findViewById(R.id.ivToolRightLeft);
        tooColor(false, color(R.color.whiteF8));
    }

    /**
     * 定义 toolBar 的颜色
     *
     * @param backIsWhite 返回按钮是否是白色调
     * @param barColor    toolBar 的颜色
     */
    public void tooColor(boolean backIsWhite, int barColor) {
        mToolBar.setBackgroundColor(barColor);
        if (backIsWhite) {
            mIvToolLeft.setImageResource(R.drawable.white_left_arrow);
            mTvToolTitle.setTextColor(color(R.color.whiteF8));
            mTvToolRight.setTextColor(color(R.color.whiteF8));
        } else {
            mIvToolLeft.setImageResource(R.drawable.black_left_arrow);
            mTvToolTitle.setTextColor(color(R.color.colorBlackGold));
            mTvToolRight.setTextColor(color(R.color.colorBlackGold));
        }
    }

    private void initListener() {
        mIvToolLeft.setOnClickListener(this::toolLeftBtn);
        mIvToolRight.setOnClickListener(this::toolRightBtn);
        mTvToolRight.setOnClickListener(this::toolRightBtn);
        toolRightLeft(View.GONE, null);
    }

    public void toolRightVisible(View view, Object value) {
        if (view instanceof TextView) {
            mTvToolRight.setVisibility(View.VISIBLE);
            mIvToolRight.setVisibility(View.GONE);
            mTvToolRight.setText((String) value);
        } else if (view instanceof ImageView) {
            mIvToolRight.setVisibility(View.VISIBLE);
            mTvToolRight.setVisibility(View.GONE);
            mIvToolRight.setImageResource((int) value);
        }
    }


    @LayoutRes
    public abstract int initAddLayout();

    public void initAddView(FrameLayout rootView) {
        initRefreshStatusView(rootView);
    }

    public void toolRightLeft(int isVisible, View.OnClickListener listener) {
        mIvToolRightLeft.setVisibility(isVisible);
        mIvToolRightLeft.setOnClickListener(listener);
    }


    public void toolRightBtn(View v) {
    }


    public void toolLeftBtn(View v) {
        finish();
    }

    public int toolMarginTop() {
        return SizeUtils.dp2px(56) + ScreenUtils.getStatusBar(this);
    }

    public int color(@ColorRes int idRes) {
        return getResources().getColor(idRes);
    }

    public String string(@StringRes int idRes) {
        return getResources().getString(idRes);
    }
}
