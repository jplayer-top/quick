package top.jplayer.baseprolibrary.ui.dialog;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.SharePreUtil;
import top.jplayer.baseprolibrary.widgets.cardswipe.CardItemTouchHelperCallback;
import top.jplayer.baseprolibrary.widgets.cardswipe.CardLayoutManager;
import top.jplayer.baseprolibrary.widgets.cardswipe.OnSwipeListener;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialogFragment;

/**
 * Created by Obl on 2018/3/26.
 * com.ilanchuang.xiaoi.ui
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class DialogCard<T extends Parcelable> extends BaseCustomDialogFragment {
    private AdapterCard mAdapter;
    public ArrayList<T> mList;

    @Override
    protected void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Bundle arguments = getArguments();
        assert arguments != null;
        mList = getArguments().getParcelableArrayList("list");
        mAdapter = new AdapterCard(R.layout.adapter_card, mList);
        recyclerView.setAdapter(mAdapter);
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback<>(recyclerView.getAdapter(),
                mList, new OnSwipeListener<T>() {

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, T t, int direction) {
            }

            @Override
            public void onSwipedClear() {
                finishIt();
            }

        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            mAdapter.remove(position);
            if (mAdapter.getData().size() < 1) {
                finishIt();
            }
            return false;
        });
    }

    private void finishIt() {
        Observable.timer(300, TimeUnit.MILLISECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> dismiss());
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_invit;
    }

    @Override
    public int setHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    private class AdapterCard extends BaseQuickAdapter<T, BaseViewHolder> {


        public AdapterCard(int layoutResId, List<T> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {

        }
    }
}
