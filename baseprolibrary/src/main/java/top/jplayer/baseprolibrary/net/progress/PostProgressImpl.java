package top.jplayer.baseprolibrary.net.progress;

import android.content.Context;

import java.lang.ref.WeakReference;

import top.jplayer.baseprolibrary.R;

/**
 * Created by Obl on 2018/7/12.
 * top.jplayer.baseprolibrary.net.progress
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class PostProgressImpl implements IProgress {
    private Context mContext;
    public DialogLoading mLoading;


    public PostProgressImpl(Context cxt) {
        WeakReference<Context> weakReference = new WeakReference<>(cxt);
        this.mContext = weakReference.get();
    }

    @Override
    public void tipStart() {
        mLoading = new DialogLoading(mContext);
        mLoading.show();
    }

    @Override
    public void tipEnd() {
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }

    @Override
    public void tipComplete() {

    }

    @Override
    public void tipSuccess(String msg) {
        new DialogNetTipShort(mContext)
                .color(mContext.getResources().getColor(R.color.colorPrimary))
                .text(msg)
                .res(R.drawable.dialog_success)
                .show();
    }

    @Override
    public void tipFail(String code, String msg) {
        new DialogNetTipShort(mContext)
                .color(mContext.getResources().getColor(R.color.tomato))
                .text(msg)
                .res(R.drawable.dialog_warn)
                .show();
    }

    @Override
    public void tipError(Throwable t) {
        new DialogNetTipShort(mContext).show();
    }
}
