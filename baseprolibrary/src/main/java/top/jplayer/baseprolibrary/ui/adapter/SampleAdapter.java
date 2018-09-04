package top.jplayer.baseprolibrary.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.jplayer.baseprolibrary.R;

/**
 * Created by Obl on 2018/9/4.
 * top.jplayer.baseprolibrary.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class SampleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SampleAdapter(List<String> data) {
        super(R.layout.adapter_sample_00, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
