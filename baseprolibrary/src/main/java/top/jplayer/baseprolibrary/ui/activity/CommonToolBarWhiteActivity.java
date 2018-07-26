package top.jplayer.baseprolibrary.ui.activity;

import android.annotation.SuppressLint;
import android.support.annotation.LayoutRes;
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
public abstract class CommonToolBarWhiteActivity extends SuperBaseActivity {

    public Toolbar mToolBar;
    public TextView mTvToolTitle;
    public TextView mTvToolRight;
    public ImageView mIvToolRight;
    public ImageView mIvToolRightLeft;
    public ImageView mIvToolLeft;
    public FrameLayout mRootView;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_common_toolbar_white;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(mToolBar).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mToolBar = view.findViewById(R.id.toolbar);
        mIvToolLeft = view.findViewById(R.id.ivToolLeft);
        mIvToolRight = view.findViewById(R.id.ivToolRight);
        mTvToolRight = view.findViewById(R.id.tvToolRight);
        mTvToolTitle = view.findViewById(R.id.tvToolTitle);
        mTvToolTitle.setText(StringUtils.init().fixNullStr(getIntent().getStringExtra("title")));
        mIvToolRightLeft = view.findViewById(R.id.ivToolRightLeft);
        mRootView = view.findViewById(R.id.rootView);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRootView.getLayoutParams();
        layoutParams.topMargin = toMarginTop();
        mRootView.removeAllViews();
        mRootView.addView(View.inflate(this, initAddLayout(), null));
        initAddView(mRootView);
        initListener();
    }

    private void initListener() {
        mIvToolLeft.setOnClickListener(this::toolLeftBtn);
        mIvToolRight.setOnClickListener(this::toolRightBtn);
        mTvToolRight.setOnClickListener(this::toolRightBtn);
        showRightLeft(View.GONE, null);
    }

    public void toolRightVisiable(View view, Object value) {
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

    public void showRightLeft(int isVisible, View.OnClickListener listener) {
        mIvToolRightLeft.setVisibility(isVisible);
        mIvToolRightLeft.setOnClickListener(listener);
    }

    public void toolRightBtn(View v) {
    }


    public void toolLeftBtn(View v) {
        finish();
    }

    public int toMarginTop() {
        return SizeUtils.dp2px(56) + ScreenUtils.getStatusBar(this);
    }

    @LayoutRes
    public abstract int initAddLayout();

    public void initAddView(FrameLayout rootView) {
        initRefreshStatusView(rootView);
    }
}
