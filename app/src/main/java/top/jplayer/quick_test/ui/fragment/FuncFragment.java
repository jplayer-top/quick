package top.jplayer.quick_test.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.mvp.contract.IContract;
import top.jplayer.baseprolibrary.ui.Fragment.SuperBaseFragment;
import top.jplayer.quick_test.ui.activity.ALiveActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;
import top.jplayer.quick_test.mvp.presenter.FuncPresenter;
import top.jplayer.quick_test.ui.activity.DialogActivity;
import top.jplayer.quick_test.ui.activity.QCodeActivity;
import top.jplayer.quick_test.ui.activity.UpdateActivity;
import top.jplayer.quick_test.ui.adapter.AdapterFunc;

/**
 * Created by Obl on 2018/7/25.
 * top.jplayer.quick_test.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FuncFragment extends SuperBaseFragment implements IContract.IView {
    @BindView(R.id.tvToolTitle)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ivToolLeft)
    ImageView mIvToolLeft;
    @BindView(R.id.ivToolRight)
    ImageView mIvToolRight;
    private Unbinder mUnbinder;
    private AdapterFunc mAdapter;
    private FuncPresenter mPresenter;

    @Override
    public int initLayout() {
        return R.layout.fragment_func;
    }


    @Override
    protected void initData(View rootView) {
        initImmersionBar();
        initRefreshStatusView(rootView);

        mUnbinder = ButterKnife.bind(this, rootView);
        mIvToolLeft.setVisibility(View.INVISIBLE);
        mTvToolTitle.setText("发现");
        mTvToolTitle.setTextColor(getResources().getColor(R.color.white));
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        mAdapter = new AdapterFunc(null);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (position == 0) {
                ActivityUtils.init().start(mActivity, ALiveActivity.class, "后台保活");
            } else if (position == 1) {
                ActivityUtils.init().start(mActivity, DialogActivity.class, "常见弹窗");
            } else if (position == 2) {
                ActivityUtils.init().start(mActivity, UpdateActivity.class, "应用更新");
            } else if (position == 3) {
                ActivityUtils.init().start(mActivity, QCodeActivity.class, "二维码");
            }
        });

        mPresenter = new FuncPresenter(this);
        mPresenter.requestFunc("111");
        showLoading();
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.requestFunc("111");
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.fixBar).init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    public void responseFunc(FuncBean bean) {
        mAdapter.setNewData(bean.response.type);
    }
}
