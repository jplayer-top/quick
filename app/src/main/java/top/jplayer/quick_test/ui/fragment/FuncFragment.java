package top.jplayer.quick_test.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import skin.support.SkinCompatManager;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.listener.observer.CustomObservable;
import top.jplayer.baseprolibrary.listener.observer.CustomObserver;
import top.jplayer.baseprolibrary.mvp.contract.IContract;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;
import top.jplayer.quick_test.mvp.presenter.FuncPresenter;
import top.jplayer.quick_test.ui.adapter.AdapterFunc;

/**
 * Created by Obl on 2018/7/25.
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FuncFragment extends SuperBaseFragment implements IContract.IView, CustomObserver {
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
    private int theme = 0;

    @Override
    public int initLayout() {
        return R.layout.fragment_func;
    }


    @Override
    protected void initData(View rootView) {
        initImmersionBar();
        initRefreshStatusView(rootView);
        BaseInitApplication.mObserverMap.put("func", this);
        mUnbinder = ButterKnife.bind(this, rootView);
        mIvToolLeft.setVisibility(View.INVISIBLE);
        mTvToolTitle.setText("发现");
        mTvToolTitle.setTextColor(getResources().getColor(R.color.white));
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        mAdapter = new AdapterFunc(null);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            FuncBean.ResponseBean.TypeBean typeBean = mAdapter.getData().get(position);
            if (typeBean.typeTitle.equals("主题切换")) {
                theme += 1;
                if (theme > 2) {
                    theme = 0;
                }
                //只需在color 文件中配置 主题颜色
                if (theme == 1) {
                    SkinCompatManager.getInstance().loadSkin("night",
                            SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
                } else if (theme == 2) {
                    SkinCompatManager.getInstance().loadSkin("orange",
                            SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
                } else {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                }
            } else {
                if (typeBean.typeTitle.equals("WebView")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", "https://vr.justeasy.cn/Pano/Vr/dealvr/id/3980435.html");
                    ActivityUtils.init().start(mActivity, typeBean.typeClass, typeBean.typeTitle, bundle);
                } else {
                    ActivityUtils.init().start(mActivity, typeBean.typeClass, typeBean.typeTitle);
                }
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
        BaseInitApplication.mObserverMap.remove("func");
    }


    public void responseFunc(FuncBean bean) {
        mAdapter.setNewData(bean.response.type);
    }

    @Override
    public void update(CustomObservable o, Object arg) {
        LogUtil.e(arg);
        HomeBean.ResponseBean.TypeBean bean = (HomeBean.ResponseBean.TypeBean) arg;
        ToastUtils.init().showQuickToast(bean.typeUrl);
    }
}
