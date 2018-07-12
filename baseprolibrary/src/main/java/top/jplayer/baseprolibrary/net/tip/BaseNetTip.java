package top.jplayer.baseprolibrary.net.tip;

import android.content.Context;

import java.lang.ref.WeakReference;

import top.jplayer.baseprolibrary.R;

/**
 * Created by Obl on 2018/7/12.
 * top.jplayer.baseprolibrary.net.progress
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public abstract class BaseNetTip implements INetTip {
    private Context mContext;
    public DialogLoading mLoading;

    public BaseNetTip(Context cxt) {
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
        new DialogShortNetTip(mContext)
                .color(mContext.getResources().getColor(R.color.colorPrimary))
                .text(msg)
                .res(R.drawable.dialog_success)
                .show();
    }

    @Override
    public void tipFail(String code, String msg) {
        new DialogShortNetTip(mContext)
                .color(mContext.getResources().getColor(R.color.tomato))
                .text(msg)
                .res(R.drawable.dialog_warn)
                .show();
    }

    @Override
    public void tipError(Throwable t) {
        new DialogShortNetTip(mContext).show();
    }
}
