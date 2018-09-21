package top.jplayer.baseprolibrary.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.ArrayMap;

/**
 * Created by Obl on 2018/1/23.
 * top.jplayer.baseprolibrary.ui.adapter
 */

public class BaseViewPagerFragmentAdapter<T extends String, R extends Fragment> extends FragmentPagerAdapter {
    protected ArrayMap<T, R> mTRMap;

    public BaseViewPagerFragmentAdapter(FragmentManager fm, ArrayMap<T, R> map) {
        super(fm);
        this.mTRMap = map;
    }

    @Override
    public int getCount() {
        return mTRMap.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mTRMap.valueAt(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTRMap.keyAt(position);
    }
}
