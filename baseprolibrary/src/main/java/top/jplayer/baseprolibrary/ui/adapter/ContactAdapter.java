package top.jplayer.baseprolibrary.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.ui.viewholder.ContactHolder;
import top.jplayer.baseprolibrary.utils.ContactUtils;

/**
 * Created by PEO on 2017/2/27.
 * inv
 */
public class ContactAdapter extends RecyclerView.Adapter {
   public List<ContactUtils.ContactsInfoBean> list;
   public Map<Integer, Integer> map = new HashMap<>();
   public CommonToolBarActivity activity;

    public ContactAdapter(List<ContactUtils.ContactsInfoBean> list, CommonToolBarActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ContactHolder.getHolder(parent.getContext());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactHolder invHolder = (ContactHolder) holder;
        invHolder.bindDate(list, position, map, activity);
        invHolder.mRbInv.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                activity.mTvToolRight.setEnabled(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        //后期需要做非空
        return list.size();
    }
}
