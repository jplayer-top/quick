package top.jplayer.baseprolibrary.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import top.jplayer.baseprolibrary.listener.NetNavigationBarListener;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;

/**
 * Created by Obl on 2018/7/5.
 * top.jplayer.commonmodule.quick
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 * 快速创建底部布局
 */

public class QuickNavigationBar {
    public static class NavihationInfo {
        String title;
        @DrawableRes
        int res;
        SuperBaseFragment fragment;

        public NavihationInfo(String title, int res, SuperBaseFragment fragment) {
            this.title = title;
            this.res = res;
            this.fragment = fragment;
        }
    }

    private List<NavihationInfo> dataList;
    private SuperBaseActivity activity;
    private @IdRes
    int rootId;
    private int index = 0;

    public QuickNavigationBar(SuperBaseActivity activity) {
        this.activity = activity;
    }

    public QuickNavigationBar dataList(List<NavihationInfo> infoList) {
        this.dataList = infoList;
        return this;
    }

    public QuickNavigationBar idRes(@IdRes int rootId) {
        this.rootId = rootId;
        return this;
    }

    public QuickNavigationBar index(int index) {
        this.index = index;
        return this;
    }

    public void create(NavigationTabBar bar) {
        if (this.rootId == 0 || this.dataList == null) {
            // ex : quickNavigationBar.dataList(xxx).idRes(xxx).create(xxx);
            throw new RuntimeException("你需要设置根布局或底部栏数据");
        }
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            models.add(new NavigationTabBar.Model.Builder(
                    activity.getResources().getDrawable(dataList.get(i).res),
                    activity.getResources().getColor(top.jplayer.baseprolibrary.R.color.trans))
                    .title(dataList.get(i).title).build());

        }
        bar.setModels(models);
        bar.setOnTabBarSelectedIndexListener(new NetNavigationBarListener() {
            @Override
            public void onceSelected(NavigationTabBar.Model model, int index) {
                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = manager.findFragmentByTag(index + "");
                if (fragment == null) {
                    fragment = dataList.get(index).fragment;
                    transaction.add(rootId, fragment, index + "");
                }
                for (Fragment fragmentItem : manager.getFragments()) {
                    if (!fragmentItem.isHidden()) {

                        transaction.hide(fragmentItem);
                    }
                }
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
                transaction.commitAllowingStateLoss();
            }
        });
        bar.setModelIndex(index, true);
    }
}
