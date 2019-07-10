package top.jplayer.baseprolibrary.listener;

import android.support.v7.widget.RecyclerView;

import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.SizeUtils;

/**
 * Created by Obl on 2018/4/9.
 * top.jplayer.baseprolibrary.listener
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 * 滚动RecyclerView 实现ToolBar 渐变
 */

public class RecyclerViewChangeScrollListener extends RecyclerView.OnScrollListener {
    private int mDistance;
    private int maxHeight;

    public RecyclerViewChangeScrollListener(int scrollHeight) {
        maxHeight = SizeUtils.dp2px(scrollHeight);

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mDistance += dy;
        float percent = mDistance * 1f / maxHeight;//百分比

        int alpha = (int) (percent * 255);
        changeMethod(alpha, percent, maxHeight > mDistance);
        LogUtil.e(percent);
    }

    public void changeMethod(int alpha, float percent, boolean b) {
//        tvBarTitle.setAlpha(percent);
//        if (maxHeight > mDistance) {
//            mToolbar.setBackgroundColor(Color.argb(alpha, 35, 159, 132));
//        }
    }

}
