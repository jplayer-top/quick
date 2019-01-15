package top.jplayer.baseprolibrary.net.tip;

import android.content.Context;
import android.view.View;

import java.util.Date;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Administrator on 2017/3/16.
 * 加载中
 */

public class DialogLoading extends BaseCustomDialog {
    private Date mDate;
    private long mPreTime;

    public DialogLoading(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
            mDate = new Date();
            mPreTime = mDate.getTime();
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            try {
                super.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_loading_spinkit;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(5);
    }

}
