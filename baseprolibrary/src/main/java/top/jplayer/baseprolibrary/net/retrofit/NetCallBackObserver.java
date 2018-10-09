package top.jplayer.baseprolibrary.net.retrofit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.mvp.model.bean.BaseBean;
import top.jplayer.baseprolibrary.net.tip.INetTip;
import top.jplayer.baseprolibrary.net.tip.NullTip;

/**
 * Created by Obl on 2018/7/12.
 * top.jplayer.baseprolibrary.net.retrofit
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public abstract class NetCallBackObserver<T extends BaseBean> implements Observer<T> {
    private INetTip mProgress;
    private Disposable mDisposable;

    public Disposable getDisposable() {
        return mDisposable;
    }

    public NetCallBackObserver(INetTip progress) {
        this.mProgress = progress;
        if (mProgress == null) {
            mProgress = new NullTip();
        }
    }

    public NetCallBackObserver() {
        if (mProgress == null) {
            mProgress = new NullTip();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        if (!d.isDisposed()) {
            mProgress.tipStart();
        }
    }

    @Override
    public void onNext(T t) {
        onRequestEnd(t);
    }

    @Override
    public void onError(Throwable e) {
        mProgress.tipEnd();
        if (e.getMessage().contains("401")) {
            errorLogin();
        } else {
            mProgress.tipError(e);
        }
//        try {
//            if (e instanceof ConnectException
//                    || e instanceof TimeoutException
//                    || e instanceof NetworkErrorException
//                    || e instanceof UnknownHostException) {
//                //网络错误
//            } else {
//                //未知错误
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
    }

    @Override
    public void onComplete() {
        mProgress.tipComplete();
    }

    protected void onRequestEnd(T t) {
        mProgress.tipEnd();
        if (t.isSuccess()) {
            mProgress.tipSuccess(t.msg);
            responseSuccess(t);
        } else {
            mProgress.tipFail(t.code, t.msg);
            responseFail(t);
        }
    }

    public abstract void responseSuccess(T t);

    public abstract void responseFail(T t);


    /**
     * 无登录状况，默认实现
     */
    public void errorLogin() {
        mProgress.tipFail("401", "当前账号未登录，请前往登录");
    }
}
