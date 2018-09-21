package top.jplayer.quick_test.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerFragmentAdapter;
import top.jplayer.baseprolibrary.ui.fragment.TestFragment;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/9/4.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ViewPagerTabActivity extends CommonToolBarActivity {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private Unbinder mBind;

    @Override
    public int initAddLayout() {
        return R.layout.activity_viewpager_tab;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mBind = ButterKnife.bind(this, rootView);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        ArrayMap<String, Fragment> map = new ArrayMap<>();
        map.put("待付款", new TestFragment());
        map.put("已付款", new TestFragment());
        map.put("已完成", new TestFragment());
        mViewPager.setAdapter(new BaseViewPagerFragmentAdapter<>(getSupportFragmentManager(), map));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ToastUtils.init().showQuickToast("当前位置：" + position);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
