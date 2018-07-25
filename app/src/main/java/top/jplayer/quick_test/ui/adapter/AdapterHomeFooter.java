package top.jplayer.quick_test.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;

import java.util.List;

import top.jplayer.baseprolibrary.ui.adapter.VLayoutAdapter;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;

/**
 * Created by Obl on 2018/7/24.
 * top.jplayer.quick_test.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class AdapterHomeFooter extends VLayoutAdapter<RecyclerView.ViewHolder> {
    public AdapterHomeFooter(Context context, LinearLayoutHelper linearLayoutHelper, int size, int bodySingle) {
        super(context, linearLayoutHelper, size, bodySingle);
    }

    private List<HomeBean.ResponseBean.FooterBean> mFooterBeans;

    public void setSingleBeans(List<HomeBean.ResponseBean.FooterBean> footerBeans) {
        mFooterBeans = footerBeans;
    }

    @Override
    protected int addHolderLayout(ViewGroup parent, int viewType) {
        return R.layout.adapter_home_footer;
    }


    @Override
    protected void onBindViewHolderWithOffset(RecyclerView.ViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
        ImageView ivSrc = holder.itemView.findViewById(R.id.ivSrc);
        Glide.with(ivSrc).load(mFooterBeans.get(position).typeUrl).into(ivSrc);
        TextView tvTip = holder.itemView.findViewById(R.id.tvTip);
        tvTip.setText(mFooterBeans.get(position).typeTitle);
    }

}
