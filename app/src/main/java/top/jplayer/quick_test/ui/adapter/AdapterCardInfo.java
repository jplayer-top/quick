package top.jplayer.quick_test.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import top.jplayer.baseprolibrary.ui.adapter.BaseViewPagerViewAdapter;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.ui.activity.ViewActivity;

/**
 * Created by Obl on 2018/8/15.
 * top.jplayer.quick_test.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class AdapterCardInfo extends BaseViewPagerViewAdapter<ViewActivity.NoticesBean> {

    public AdapterCardInfo(List<ViewActivity.NoticesBean> list) {
        super(list);
        addItemType(ViewActivity.NoticesBean.INT_NOTICE, R.layout.adapter_cardinfo_notice);
        addItemType(ViewActivity.NoticesBean.INT_OTHER, R.layout.adapter_cardinfo_other);
    }

    @Override
    public int getItemType(int position) {
        return list.get(position).getType();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void bindView(View view, ViewActivity.NoticesBean bean, int position) {

        switch (getItemType(position)) {
            case ViewActivity.NoticesBean.INT_NOTICE:

                break;

        }

    }
}
