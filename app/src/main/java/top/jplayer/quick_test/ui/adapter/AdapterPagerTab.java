package top.jplayer.quick_test.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerFragmentAdapter;
import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.ui.fragment.TestFragment;

/**
 * Created by Obl on 2018/9/4.
 * top.jplayer.quick_test.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class AdapterPagerTab extends BaseViewPagerFragmentAdapter<String> {
    public AdapterPagerTab(FragmentManager supportFragmentManager, ArrayList<String> strings) {
        super(supportFragmentManager, strings);
        mFragmentList = new ArrayList<>();

        mFragmentList.add(new TestFragment());
        mFragmentList.add(new TestFragment());
        mFragmentList.add(new TestFragment());

    }

    public List<SuperBaseFragment> mFragmentList = null;


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position);
    }
}
