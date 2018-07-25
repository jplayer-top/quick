package top.jplayer.quick_test.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.mvp.contract.IContract;
import top.jplayer.baseprolibrary.ui.Fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.ui.LiveStartActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;
import top.jplayer.quick_test.mvp.presenter.FuncPresenter;
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
        mIvToolRight.setVisibility(View.INVISIBLE);
        mTvToolTitle.setText("发现");


        mAdapter = new AdapterFunc(null);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (position == 0) {
                ActivityUtils.init().start(mActivity, LiveStartActivity.class);
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
