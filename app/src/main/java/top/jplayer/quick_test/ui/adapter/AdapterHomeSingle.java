package top.jplayer.quick_test.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bumptech.glide.Glide;

import java.util.List;

import top.jplayer.baseprolibrary.ui.adapter.VLayoutAdapter;
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;

/**
 * Created by Obl on 2018/7/24.
 * top.jplayer.quick_test.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class AdapterHomeSingle extends VLayoutAdapter<RecyclerView.ViewHolder> {
    public AdapterHomeSingle(Context context, GridLayoutHelper gridLayoutHelper, int size, int bodySingle) {
        super(context, gridLayoutHelper, size, bodySingle);
    }

    private List<HomeBean.ResponseBean.SingleBean> mSingleBeans;

    public void setSingleBeans(List<HomeBean.ResponseBean.SingleBean> singleBeans) {
        mSingleBeans = singleBeans;
    }

    @Override
    protected int addHolderLayout(ViewGroup parent, int viewType) {
        return R.layout.adapter_home_single;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = super.onCreateViewHolder(parent, viewType);
        ImageView ivSrc = holder.itemView.findViewById(R.id.ivSrc);
        int i = ScreenUtils.getWidthOfScreen(context, 2, 2);
        ivSrc.getLayoutParams().width = i;
        ivSrc.getLayoutParams().height = i;
        return holder;
    }

    @Override
    protected void onBindViewHolderWithOffset(RecyclerView.ViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
        ImageView ivSrc = holder.itemView.findViewById(R.id.ivSrc);
        Glide.with(ivSrc).load(mSingleBeans.get(position).typeUrl).into(ivSrc);
        TextView tvTip = holder.itemView.findViewById(R.id.tvTip);
        tvTip.setText(mSingleBeans.get(position).typeTitle);
    }

}
