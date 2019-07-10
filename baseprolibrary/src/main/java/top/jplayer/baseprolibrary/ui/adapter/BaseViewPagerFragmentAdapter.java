package top.jplayer.baseprolibrary.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.ArrayMap;

import java.util.LinkedHashMap;

/**
 * Created by Obl on 2018/1/23.
 * top.jplayer.baseprolibrary.ui.adapter
 */

public class BaseViewPagerFragmentAdapter<T extends String, R extends Fragment> extends FragmentPagerAdapter {
    public LinkedHashMap<T, R> mLinkedHashMap;

    public BaseViewPagerFragmentAdapter(FragmentManager fm, LinkedHashMap<T, R> map) {
        super(fm);
        this.mLinkedHashMap = map;
    }

    @Override
    public int getCount() {
        return mLinkedHashMap.size();
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mLinkedHashMap.values().toArray()[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) mLinkedHashMap.keySet().toArray()[position];
    }
}
