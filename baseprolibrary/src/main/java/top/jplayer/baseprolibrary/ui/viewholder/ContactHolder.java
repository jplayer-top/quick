package top.jplayer.baseprolibrary.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.ContactUtils;

/**
 * Created by PEO on 2017/2/27.
 * 邀请人Holder
 */

public class ContactHolder extends RecyclerView.ViewHolder {

    public CheckBox mRbInv;
    public TextView mTvName;
    public TextView mTvQux;
    public LinearLayout mLlQux;
    public boolean isChecked;
    private ContactUtils.ContactsInfoBean mBean;

    public ContactHolder(View itemView) {
        super(itemView);
        mRbInv = itemView.findViewById(R.id.rbInv);
        mTvName = itemView.findViewById(R.id.tvFamilyName);
        mTvQux = itemView.findViewById(R.id.tvQux);
        mLlQux = itemView.findViewById(R.id.llQux);
    }

    public static ContactHolder getHolder(Context context) {
        View itemView = View.inflate(context, R.layout.item_inv, null);
        return new ContactHolder(itemView);
    }

    public void bindDate(List<ContactUtils.ContactsInfoBean> list, final int position, final Map<Integer, Integer>
            map, CommonToolBarActivity activity) {
        mBean = list.get(position);
        mBean.isCheck = false;
        //解决复用问题CheckBox
        mRbInv.setChecked(false);
        mRbInv.setOnClickListener(v -> {
            if (mRbInv.isChecked()) {
                mRbInv.setChecked(true);
                map.put(position, position);
                mBean.isCheck = true;
            } else {
                mRbInv.setChecked(false);
                map.remove(position);
                mBean.isCheck = false;
            }
        });
        if (map.containsKey(position)) {
            mRbInv.setChecked(true);
        } else {
            mRbInv.setChecked(false);
        }
        mLlQux.setVisibility(View.VISIBLE);
        if (position != 0) {
            if (list.get(position - 1).sortKey.equals(mBean.sortKey))
                mLlQux.setVisibility(View.GONE);
            else {
                mTvQux.setText(mBean.sortKey);
                mLlQux.setVisibility(View.VISIBLE);
            }
        } else mTvQux.setText(mBean.sortKey);
        mTvName.setText(mBean.name);
    }
}
