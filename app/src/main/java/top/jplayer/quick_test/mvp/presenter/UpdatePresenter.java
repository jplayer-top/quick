package top.jplayer.quick_test.mvp.presenter;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.retrofit.DownLoadResponseBody;
import top.jplayer.baseprolibrary.net.retrofit.NetCallBackObserver;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.model.FuncModel;
import top.jplayer.quick_test.mvp.model.bean.VersionBean;
import top.jplayer.quick_test.ui.activity.UpdateActivity;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.quick_test.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class UpdatePresenter extends BasePresenter<UpdateActivity> {

    private final FuncModel mModel;
    private DialogLoading mLoading;

    public UpdatePresenter(UpdateActivity iView) {
        super(iView);
        mModel = new FuncModel(CommonServer.class);
    }

    public void requestUpdate() {
        NetCallBackObserver<VersionBean> observer = new NetCallBackObserver<VersionBean>(new
                GetImplTip(mIView)) {
            @Override
            public void responseFail(VersionBean versionBean) {

            }

            @Override
            public void responseSuccess(VersionBean bean) {
                mIView.responseSuccess();
                mIView.responseVersion(bean);
            }
        };
        mModel.requestVersion().subscribe(observer);
        addSubscription(observer.getDisposable());
    }

    public void requestApk(String url) {
        mLoading = new DialogLoading(mIView);
        mModel.requestApk(url).subscribe(new Observer<DownLoadResponseBody>() {

            @Override
            public void onSubscribe(Disposable d) {
                if (!d.isDisposed() && !mLoading.isShowing()) {
                    mLoading.show();
                }
            }

            @Override
            public void onNext(DownLoadResponseBody downLoadResponseBody) {
                try {
                    mLoading.dismiss();
                    InputStream is = downLoadResponseBody.byteStream();
                    File mFile = new File(Environment.getExternalStorageDirectory(), "aaa.apk");
                    FileOutputStream fos = new FileOutputStream(mFile);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                    mIView.downloadSuccess(mFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                mLoading.dismiss();
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
