package top.jplayer.baseprolibrary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.mvp.contract.IContract;
import top.jplayer.baseprolibrary.widgets.MultipleStatusView;

/**
 * Created by Obl on 2018/1/19.
 * top.jplayer.baseprolibrary.ui.Fragment
 */

public abstract class SuperBaseFragment extends Fragment implements IContract.IView {
    protected View rootView;
    public SmartRefreshLayout mSmartRefreshLayout;
    public MultipleStatusView mMultipleStatusView;
    public RecyclerView mRecyclerView;
    public ImmersionBar mImmersionBar;
    public FragmentActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initLayout(), container, false);
        rootView = view;
        mActivity = getActivity();
        return view;
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        if (isImmersionBarEnabled()) {
            mImmersionBar = ImmersionBar.with(this)
                    .statusBarDarkFont(true, 0.2f);
            mImmersionBar.init();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(rootView);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isHidden()) {
            onHideFragment();
        } else {
            onShowFragment();
        }
    }

    protected void onShowFragment() {
        if (mImmersionBar != null)
            mImmersionBar.init();
    }

    protected void onHideFragment() {

    }

    public abstract int initLayout();

    protected abstract void initData(View rootView);


    /**
     * 刷新+多状态
     *
     * @param view
     */
    public void initRefreshStatusView(View view) {
        mSmartRefreshLayout = view.findViewById(R.id.smartRefreshLayout);
        mMultipleStatusView = view.findViewById(R.id.multipleStatusView);
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setOnRefreshListener(refresh -> refreshStart());
        }
        mRecyclerView = view.findViewById(R.id.recyclerView);
        initRootRecyclerView();

    }

    public void initRootRecyclerView() {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    public void refreshStart() {

    }

    @Override
    public void showError() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showError();
        }
        autoRefreshFinish();
    }

    @Override
    public void showLoading() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showLoading();
        }
    }

    @Override
    public void showEmpty() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showEmpty();
        }
        autoRefreshFinish();
    }

    public void responseSuccess() {
        autoRefreshFinish();
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showContent();
        }
    }

    public void autoRefreshFinish() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
        }
    }
}
