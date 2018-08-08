package top.jplayer.quick_test.mvp.presenter;

import java.io.File;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.mvp.model.bean.BaseBean;
import top.jplayer.baseprolibrary.net.retrofit.NetCallBackObserver;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.baseprolibrary.net.tip.PostImplTip;
import top.jplayer.baseprolibrary.utils.FileUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.model.RetrofitModel;
import top.jplayer.quick_test.mvp.model.bean.RetrofitPostBean;
import top.jplayer.quick_test.ui.activity.RetrofitActivity;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.quick_test.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class RetrofitPresenter extends BasePresenter<RetrofitActivity> {

    private final RetrofitModel mModel;

    public RetrofitPresenter(RetrofitActivity iView) {
        super(iView);
        mModel = new RetrofitModel(CommonServer.class);
    }

    public void requestPost(String tel, String pwd) {
        NetCallBackObserver<RetrofitPostBean> observer = new NetCallBackObserver<RetrofitPostBean>(new
                PostImplTip(mIView)) {
            @Override
            public void responseFail(RetrofitPostBean bean) {
                mIView.showError();
            }

            @Override
            public void responseSuccess(RetrofitPostBean bean) {
                mIView.responseSuccess();
            }
        };
        mModel.post(tel, pwd).subscribe(observer);
        addSubscription(observer.getDisposable());
    }

    public void requestGet(String test) {
        NetCallBackObserver<BaseBean> observer = new NetCallBackObserver<BaseBean>(new
                GetImplTip(mIView)) {
            @Override
            public void responseFail(BaseBean bean) {
                mIView.showError();
            }

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.responseSuccess();
            }
        };
        mModel.get(test).subscribe(observer);
        addSubscription(observer.getDisposable());
    }

    public void requestFile(String uid, File file, String key) {
        NetCallBackObserver<BaseBean> observer = new NetCallBackObserver<BaseBean>(new
                PostImplTip(mIView)) {
            @Override
            public void responseFail(BaseBean bean) {
                mIView.showError();
            }

            @Override
            public void responseSuccess(BaseBean bean) {
                mIView.responseSuccess();
            }
        };
        mModel.file(uid, file, key).subscribe(observer);
        addSubscription(observer.getDisposable());
    }

    public void requestFileDown(String url) {
        url = "http://www.jplayer.top/app-release.apk";
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        mModel.requestApk(url).subscribe(responseBody -> {
            File file = FileUtil.saveFile(responseBody.byteStream(), fileName);
            mIView.downloadSuccess(file);
        }, throwable -> LogUtil.net(throwable.getMessage()));
    }

}
