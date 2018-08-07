package top.jplayer.quick_test.mvp.presenter;

import java.io.File;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.retrofit.NetCallBackObserver;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.baseprolibrary.utils.FileUtil;
import top.jplayer.baseprolibrary.utils.LogUtil;
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
        url = "http://www.jplayer.top/app-release.apk";
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        mModel.requestApk(url).subscribe(responseBody -> {
            File file = FileUtil.saveFile(responseBody.byteStream(), fileName);
            mIView.downloadSuccess(file);
        }, throwable -> LogUtil.net(throwable.getMessage()));
    }
}
