package top.jplayer.quick_test.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;

/**
 * Created by Obl on 2018/7/23.
 * top.jplayer.quick_test.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class AdapterHomeTypeItem extends BaseQuickAdapter<HomeBean.ResponseBean.TypeBean, BaseViewHolder> {
    public AdapterHomeTypeItem(int layoutResId, List<HomeBean.ResponseBean.TypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateDefViewHolder(parent, viewType);
        View ivSrc = baseViewHolder.itemView.findViewById(R.id.ivSrc);
        ivSrc.getLayoutParams().width = ScreenUtils.getScreenWidth() / 5;
        ivSrc.getLayoutParams().height = ScreenUtils.getScreenWidth() / 5 - ScreenUtils.dp2px(5);
        return baseViewHolder;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.ResponseBean.TypeBean item) {
        ImageView ivSrc = helper.itemView.findViewById(R.id.ivSrc);
        Glide.with(ivSrc.getContext()).load(item.typeUrl).into(ivSrc);
        helper.setText(R.id.tvText, item.typeTitle + helper.getLayoutPosition());
    }
}
