package top.jplayer.quick_test.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import top.jplayer.baseprolibrary.ui.Fragment.SuperBaseFragment;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.construct.HomeConstruct;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;
import top.jplayer.quick_test.mvp.presenter.HomePresenter;

/**
 * Created by Obl on 2018/7/5.
 * top.jplayer.quick_test.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class HomeFragment extends SuperBaseFragment implements HomeConstruct.HomeIView {

    private VirtualLayoutManager mManager;
    private DelegateAdapter mDelegateAdapter;

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        initImmersionBar();
        initVLayoutRecyclerView();
    }

    /**
     * V-layout 初始化
     */
    private void initVLayoutRecyclerView() {
        mManager = new VirtualLayoutManager(mActivity, VirtualLayoutManager.VERTICAL, false);
        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        pool.setMaxRecycledViews(0, 10);
        mRecyclerView.setRecycledViewPool(pool);
        mDelegateAdapter = new DelegateAdapter(mManager, true);
        new HomePresenter(this).requestHome();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar).init();
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void responseHome(HomeBean bean) {

    }
}