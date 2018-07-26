package top.jplayer.quick_test;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import top.jplayer.baseprolibrary.ui.Fragment.TestFragment;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.utils.QuickNavigationBar;
import top.jplayer.quick_test.ui.fragment.FuncFragment;
import top.jplayer.quick_test.ui.fragment.HomeFragment;

public class MainActivity extends SuperBaseActivity {


    @Override
    protected int initRootLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        NavigationTabBar navigationBar = view.findViewById(R.id.navigationBar);
        new QuickNavigationBar(this)
                .idRes(R.id.flRoot)
                .dataList(initBarList())
                .create(navigationBar);
    }

    /**
     * 设置底部栏数据图片
     *
     * @return list
     */
    @NonNull
    private List<QuickNavigationBar.NavihationInfo> initBarList() {
        List<QuickNavigationBar.NavihationInfo> list = new ArrayList<>();
        list.add(new QuickNavigationBar.NavihationInfo("首页", R.drawable.main_home, new HomeFragment()));
        list.add(new QuickNavigationBar.NavihationInfo("发现", R.drawable.main_find, new FuncFragment()));
        list.add(new QuickNavigationBar.NavihationInfo("我的", R.drawable.main_me, new TestFragment()));
        return list;
    }

}
