package top.jplayer.baseprolibrary.ui.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/4/28.
 * top.jplayer.baseprolibrary.ui.decoration
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ItemDecorationFiltter extends RecyclerView.ItemDecoration {
    private int space = 0;
    private int pos;
    List<Integer> filtters;

    public ItemDecorationFiltter(int space, List<Integer> filtters) {
        this.space = space;
        this.filtters = filtters;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        outRect.top = space;
        //该View在整个RecyclerView中位置。
        pos = parent.getChildAdapterPosition(view);
        /**
         * 根据params.getSpanIndex()来判断左右边确定分割线
         * 第一列设置左边距为space，右边距为space/2  （第二列反之）
         */
        StaggeredGridLayoutManager.LayoutParams params =
                (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        if (filtters != null && filtters.contains(pos)) {
            outRect.right = 0;
            outRect.left = 0;
        } else {
            if (params.getSpanIndex() % 2 == 0) {
                outRect.left = space;
                outRect.right = space / 2;
            } else {
                outRect.left = space / 2;
                outRect.right = space;
            }
        }

    }
}
