package top.jplayer.quick_test.mvp.presenter;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.mvp.model.bean.CartBean;
import top.jplayer.baseprolibrary.net.retrofit.NetCallBackObserver;
import top.jplayer.baseprolibrary.net.tip.GetImplTip;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.model.FuncModel;
import top.jplayer.quick_test.ui.activity.DialogActivity;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.quick_test.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class DialogPresenter extends BasePresenter<DialogActivity> {

    private final FuncModel mModel;

    public DialogPresenter(DialogActivity iView) {
        super(iView);
        mModel = new FuncModel(CommonServer.class);
    }

    public void requestCart() {
        NetCallBackObserver<CartBean> observer = new NetCallBackObserver<CartBean>(new
                GetImplTip(mIView)) {
            @Override
            public void responseFail(CartBean bean) {
                mIView.showError();
            }

            @Override
            public void responseSuccess(CartBean bean) {
                mIView.responseSuccess();
                mIView.responseShopOrder(bean);
            }
        };
        mModel.requestCart().subscribe(observer);
        addSubscription(observer.getDisposable());
    }
}
