package top.jplayer.baseprolibrary.net.tip;

import android.content.Context;
import android.view.View;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
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
            long aftTime = mDate.getTime();
            long l = aftTime - mPreTime;
            Observable.timer(l < 1000 ? 1000 - l : 0, TimeUnit.MILLISECONDS)
                    .compose(new IoMainSchedule<>())
                    .subscribe(aLong -> {
                        if (isShowing()) {
                            super.dismiss();
                        }
                    });
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
