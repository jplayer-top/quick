package top.jplayer.quick_test.ui.activity;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import top.jplayer.baseprolibrary.ui.activity.SuperBaseActivity;
import top.jplayer.baseprolibrary.ui.adapter.SampleAdapter;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/12/27.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class BehaviorActivity extends SuperBaseActivity {

    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private BottomSheetDialog mBottomSheetDialog;


    @Override
    protected int initRootLayout() {
        return R.layout.activity_behavior;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_layout));
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setSkipCollapsed(false);
        findViewById(R.id.btn_bottom_sheet_control).setOnClickListener(v -> {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        findViewById(R.id.btn_bottom_sheet_dialog).setOnClickListener(v -> {
            if (mBottomSheetDialog != null) {
                if (mBottomSheetDialog.isShowing()) {
                    mBottomSheetDialog.dismiss();
                } else {
                    mBottomSheetDialog.show();
                }
            }
        });
        createBottomSheetDialog();
    }

    private void createBottomSheetDialog() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_sample, null, false);
        mBottomSheetDialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> data = new ArrayList<>();
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        recyclerView.setAdapter(new SampleAdapter(data));
    }

}
