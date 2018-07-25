package top.jplayer.quick_test.ui.adapter;

import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;

/**
 * Created by Obl on 2018/7/25.
 * top.jplayer.quick_test.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class AdapterFunc extends BaseQuickAdapter<FuncBean.ResponseBean.TypeBean, BaseViewHolder> {
    public AdapterFunc(List<FuncBean.ResponseBean.TypeBean> data) {
        super(R.layout.adapter_func, data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        CardView cardView = holder.itemView.findViewById(R.id.cardView);
        cardView.getLayoutParams().height = ScreenUtils.getWidthOfScreen(mContext, 12, 2) + ScreenUtils.dp2px(10);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, FuncBean.ResponseBean.TypeBean item) {
        ImageView ivSrc = helper.itemView.findViewById(R.id.ivSrc);
        Glide.with(mContext).load(item.typeUrl).into(ivSrc);
        helper.setText(R.id.tvTip, item.typeTitle);
    }
}
