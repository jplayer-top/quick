package top.jplayer.baseprolibrary.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.ui.adapter.ContactAdapter;
import top.jplayer.baseprolibrary.utils.ContactUtils;
import top.jplayer.baseprolibrary.widgets.sidebar.QuickerIndexView;

/**
 * Created by Obl on 2018/8/14.
 * top.jplayer.baseprolibrary.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ContactActivity extends CommonToolBarActivity {

    private RecyclerView mRecycleView;
    QuickerIndexView mQivItems;
    private Map<String, String> mMap;
    TextView tvShow;
    private ContactAdapter mAdapter;

    @Override
    public int initAddLayout() {
        return R.layout.activity_contact;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mMap = new HashMap<>();
        mRecycleView = rootView.findViewById(R.id.recycleView);
        mQivItems = rootView.findViewById(R.id.qiv_items);
        tvShow = rootView.findViewById(R.id.tv_show);
        readContact();
    }

    private void readContact() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);

        ContactUtils.load(this);

        List<ContactUtils.ContactsInfoBean> list = ContactUtils.list;
        Collections.sort(list);
        mAdapter = new ContactAdapter(list, this);
        for (int i = 0; i < list.size(); i++) {
            String sortKey = list.get(i).sortKey;
            if (!mMap.containsKey(sortKey)) {
                mMap.put(sortKey, i + "");
            }
        }
        mRecycleView.setAdapter(mAdapter);
        mQivItems.setOnIndexChangeListener((cellIndex, showIndex) -> {
            tvShow.setVisibility(View.VISIBLE);
            tvShow.setText(showIndex);
            Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                if (tvShow != null) {
                    tvShow.setVisibility(View.GONE);
                }
            });
            if (mMap.containsKey(showIndex)) {
                int parseInt = Integer.parseInt(mMap.get(showIndex));
                System.out.println(parseInt);
                mRecycleView.scrollToPosition(parseInt);
            }
        });
    }

}
