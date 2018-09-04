package top.jplayer.quick_test.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.ui.adapter.AdapterPagerTab;

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
        ArrayList<String> strings = new ArrayList<>();
        strings.add("待付款");
        strings.add("已付款");
        strings.add("已完成");
        AdapterPagerTab adapterPagerTab = new AdapterPagerTab(getSupportFragmentManager(), strings);
        mViewPager.setAdapter(adapterPagerTab);
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
