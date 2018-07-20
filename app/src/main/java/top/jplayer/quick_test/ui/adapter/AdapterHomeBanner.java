package top.jplayer.quick_test.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import top.jplayer.baseprolibrary.glide.GlideUtils;
import top.jplayer.baseprolibrary.ui.adapter.VLayoutAdapter;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/7/12.
 * top.jplayer.quick_test.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class AdapterHomeBanner extends VLayoutAdapter<RecyclerView.ViewHolder> {

    private BGABanner mBgaBanner;
    private List<String> mBanner;

    public AdapterHomeBanner(Context context, LayoutHelper helper, int count, int itemType) {
        super(context, helper, count, itemType);
    }

    @Override
    protected int addHolderLayout(ViewGroup parent, int viewType) {
        return R.layout.adapter_home_banner;
    }

    @Override
    protected void onBindViewHolderWithOffset(RecyclerView.ViewHolder holder, int position, int offsetTotal) {
        super.onBindViewHolderWithOffset(holder, position, offsetTotal);
        mBgaBanner = holder.itemView.findViewById(R.id.bgaBanner);
        mBgaBanner.setAdapter((banner, itemView, model, urlPosition) -> {
            Glide.with(context).load(model)
                    .apply(GlideUtils.init().options())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into((ImageView) itemView);
        });
        mBgaBanner.setData(mBanner, Arrays.asList("", "", "", "", ""));
    }

    public void setBanner(List<String> banner) {
        mBanner = banner;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (mBgaBanner != null) {
            mBgaBanner.setAdapter(null);
            mBgaBanner.setData(null);
        }
    }
}
