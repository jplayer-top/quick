package top.jplayer.quick_test.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.LinkedList;
import java.util.List;

import top.jplayer.baseprolibrary.ui.Fragment.SuperBaseFragment;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.construct.HomeConstruct;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;
import top.jplayer.quick_test.mvp.presenter.HomePresenter;
import top.jplayer.quick_test.ui.adapter.AdapterHomeBanner;
import top.jplayer.quick_test.ui.adapter.AdapterHomeType;

/**
 * Created by Obl on 2018/7/5.
 * top.jplayer.quick_test.ui.fragment
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class HomeFragment extends SuperBaseFragment implements HomeConstruct.HomeIView {

    private VirtualLayoutManager mManager;
    private DelegateAdapter mDelegateAdapter;
    private HomePresenter mPresenter;

    @Override
    public int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(View rootView) {
        initRefreshStatusView(rootView);
        initImmersionBar();
        initVLayoutRecyclerView();
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.trans));
        toolbar.setAlpha(0.5f);

    }

    @Override
    public void initRefreshStatusView(View view) {
        super.initRefreshStatusView(view);
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
        mPresenter = new HomePresenter(this);
        mPresenter.requestHome();
        showLoading();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar).init();
    }


    @Override
    public void refreshStart() {
        super.refreshStart();
        mPresenter.requestHome();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void responseHome(HomeBean bean) {
        responseSuccess();
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        /**
         * banner 数据
         *
         */
        List<String> banner = bean.response.banner;

        if (banner != null && banner.size() > 0) {
            AdapterHomeBanner heardLayoutAdapter = new AdapterHomeBanner(getContext(), new LinearLayoutHelper(), 1, HomeBean
                    .BANNER);
            heardLayoutAdapter.setBanner(banner);
            adapters.add(heardLayoutAdapter);
        }
        List<HomeBean.ResponseBean.TypeBean> typeBeans = bean.response.type;
        if (typeBeans != null && typeBeans.size() > 0) {
            AdapterHomeType adapterHomeType = new AdapterHomeType(getContext(), new LinearLayoutHelper(), 1, HomeBean.TYPE);
            adapterHomeType.setBanner(typeBeans);
            adapters.add(adapterHomeType);

        }
        mDelegateAdapter.clear();
        mDelegateAdapter.setAdapters(adapters);
        mRecyclerView.setAdapter(mDelegateAdapter);
    }
}
