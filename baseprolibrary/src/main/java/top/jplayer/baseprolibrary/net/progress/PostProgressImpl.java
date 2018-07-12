package top.jplayer.baseprolibrary.net.progress;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.widgets.dialog.DialogLoading;

/**
 * Created by Obl on 2018/7/12.
 * top.jplayer.baseprolibrary.net.progress
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class PostProgressImpl implements IProgress {
    private Context mContext;
    public DialogLoading mLoading;
    private Date mDate;
    private long mPreTime;

    public PostProgressImpl(Context cxt) {
        WeakReference<Context> weakReference = new WeakReference<>(cxt);
        this.mContext = weakReference.get();
    }

    @Override
    public void tipStart() {
        mLoading = new DialogLoading(mContext);
        if (!mLoading.isShowing()) {
            mDate = new Date();
            mLoading.show();
            mPreTime = mDate.getTime();
        }
    }

    @Override
    public void tipEnd() {
        if (mLoading != null && mLoading.isShowing()) {
            long aftTime = mDate.getTime();
            long l = aftTime - mPreTime;
            Observable.timer(l < 1000 ? 1000 - l : 0, TimeUnit.MILLISECONDS)
                    .compose(new IoMainSchedule<>())
                    .subscribe(aLong -> {
                        if (mLoading != null && mLoading.isShowing()) {
                            mLoading.dismiss();
                        }
                    });
        }
    }

    @Override
    public void tipComplete() {

    }

    @Override
    public void tipSuccess(String msg) {

    }

    @Override
    public void tipFail(String code, String msg) {

    }

    @Override
    public void tipError(Throwable t) {

    }
}
