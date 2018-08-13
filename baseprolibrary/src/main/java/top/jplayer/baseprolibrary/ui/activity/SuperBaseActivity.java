package top.jplayer.baseprolibrary.ui.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import io.reactivex.Observable;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.mvp.contract.IContract;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.baseprolibrary.widgets.MultipleStatusView;

/**
 * Created by Obl on 2018/1/9.
 * top.jplayer.baseprolibrary.ui
 * 支持设置状态栏入侵，eel视图，刷新，侧滑返回控制
 */

public abstract class SuperBaseActivity extends AppCompatActivity implements IContract.IView, BGASwipeBackHelper.Delegate {
    protected BGASwipeBackHelper mSwipeBackHelper;
    public ImmersionBar mImmersionBar;
    public View superRootView;
    public Activity mActivity;
    public Bundle mBundle;
    public SmartRefreshLayout mSmartRefreshLayout;
    public MultipleStatusView mMultipleStatusView;
    public RecyclerView mRecyclerView;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRootBundle(savedInstanceState);
        superRootView = initRootView();
        mActivity = this;
        setContentView(superRootView);
        initRootData(superRootView);
        initInject(superRootView);
        initImmersionBar();
        initSaveInstanceState(savedInstanceState);
    }

    protected void initSaveInstanceState(@Nullable Bundle savedInstanceState) {
    }

    protected void initImmersionBar() {
        if (isImmersionBarEnabled()) {
            mImmersionBar = ImmersionBar.with(this)
                    .statusBarDarkFont(true, 0.2f);
            mImmersionBar.init();
        }
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

    protected View initRootView() {
        int layout = initRootLayout();
        if (layout == 0) {
            layout = R.layout.layout_test;
        }
        return View.inflate(this, layout, null);
    }

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

    private void initRootRecyclerView() {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
    }

    public void refreshStart() {

    }

    /**
     * 依赖注入
     */
    public void initInject(View view) {
    }

    @LayoutRes
    protected abstract int initRootLayout();

    /**
     * 默认原始根布局下的FrameLayout,基于相同ToolBar 的视图
     *
     * @param view 根布局
     */
    public void initRootData(View view) {
        initRefreshStatusView(view);
    }

    /**
     * 1.保存状态
     * 2.该方法处于onCreate 最开始阶段可设置一些公共代码
     *
     * @param savedInstanceState 所保存的状态信息
     */
    public void initRootBundle(Bundle savedInstanceState) {
        mBundle = getIntent().getBundleExtra("bundle");
        initSwipeBackFinish();
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    private int doubleBack = 0;
    public boolean isOpenDoubleBack = false;

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        } else {
            if (isOpenDoubleBack) {
                if (doubleBack > 1) {
                    super.onBackPressed();
                } else {
                    ToastUtils.init().showQuickToast("再按一次退出应用");
                }
                Observable.timer(500, TimeUnit.MICROSECONDS).subscribe(aLong -> ++doubleBack);
            } else {
                super.onBackPressed();
            }
        }
        BGAKeyboardUtil.closeKeyboard(mActivity);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        saveInstanceState(outState, outPersistentState);
    }

    public void saveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreInstanceState(savedInstanceState);
    }

    public void restoreInstanceState(Bundle savedInstanceState) {

    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TODO: 2018/7/27  : 8.0 问题 不适配 screenOrientation 与 windowIsTranslucent 公用
            return false;
        }
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    /**
     * 是否检查点击空白处关闭软键盘
     */
    protected boolean isCheckKeyboard = true;

    /**
     * 点击空白处关闭软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isCheckKeyboard)
            KeyboardUtils.init().clickBound2CloseInput(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 错误视图
     */
    @Override
    public void showError() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showError();
        }
        autoRefreshFinish();
    }

    /**
     * 加载视图
     */
    @Override
    public void showLoading() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showLoading();
        }
    }

    /**
     * 空视图
     */
    @Override
    public void showEmpty() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showEmpty();
        }
        autoRefreshFinish();
    }

    /**
     * 网络请求成功后调用，消除其他视图
     */
    public void responseSuccess() {
        autoRefreshFinish();
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showContent();
        }
    }

    /**
     * 刷新完成调用
     */
    public void autoRefreshFinish() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
        }
    }
}
