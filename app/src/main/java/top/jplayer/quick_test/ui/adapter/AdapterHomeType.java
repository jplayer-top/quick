package top.jplayer.quick_test.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;

import java.util.List;

import top.jplayer.baseprolibrary.ui.adapter.VLayoutAdapter;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;

/**
 * Created by Obl on 2018/7/12.
 * top.jplayer.quick_test.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class AdapterHomeType extends VLayoutAdapter<RecyclerView.ViewHolder> {

    private List<HomeBean.ResponseBean.TypeBean> mTypeBeans;

    public AdapterHomeType(Context context, LayoutHelper helper, int count, int itemType) {
        super(context, helper, count, itemType);
    }

    @Override
    protected int addHolderLayout(ViewGroup parent, int viewType) {
        return R.layout.adapter_home_type;
    }

    @Override
    protected void onBindViewHolderWithOffset(RecyclerView.ViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
        RecyclerView recyclerView = holder.itemView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false));
        AdapterHomeTypeItem adapter = new AdapterHomeTypeItem(R.layout.adapter_home_type_item, mTypeBeans);
        recyclerView.setAdapter(adapter);
    }

    public void setBanner(List<HomeBean.ResponseBean.TypeBean> typeBeans) {
        mTypeBeans = typeBeans;
    }

}
