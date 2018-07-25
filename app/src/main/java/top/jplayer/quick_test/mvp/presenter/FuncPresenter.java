package top.jplayer.quick_test.mvp.presenter;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.retrofit.NetCallBackObserver;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.model.FuncModel;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;
import top.jplayer.quick_test.ui.fragment.FuncFragment;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.quick_test.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class FuncPresenter extends BasePresenter<FuncFragment>  {

    private final FuncModel mModel;

    public FuncPresenter(FuncFragment iView) {
        super(iView);
        mModel = new FuncModel(CommonServer.class);
    }

    public void requestFunc(String test) {
        NetCallBackObserver<FuncBean> observer = new NetCallBackObserver<FuncBean>(new
                GetImplTip(mIView.getContext())) {
            @Override
            public void responseFail(FuncBean bean) {
                mIView.showError();
            }

            @Override
            public void responseSuccess(FuncBean bean) {
                if (bean.response == null) {
                    mIView.showEmpty();
                } else {
                    mIView.responseSuccess();
                    mIView.responseFunc(bean);
                }
            }
        };
        mModel.requestFunc(test).subscribe(observer);
        addSubscription(observer.getDisposable());
    }
}
