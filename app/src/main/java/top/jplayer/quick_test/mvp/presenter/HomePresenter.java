package top.jplayer.quick_test.mvp.presenter;

import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.baseprolibrary.net.progress.PostProgressImpl;
import top.jplayer.baseprolibrary.net.retrofit.NetCallBackObserver;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.construct.HomeConstruct;
import top.jplayer.quick_test.mvp.model.HomeModel;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.quick_test.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class HomePresenter extends BasePresenter<top.jplayer.quick_test.ui.fragment.HomeFragment> implements HomeConstruct.HomeIPresenter {

    private final HomeModel mHomeModel;

    public HomePresenter(top.jplayer.quick_test.ui.fragment.HomeFragment iView) {
        super(iView);
        mHomeModel = new HomeModel(CommonServer.class);
    }

    @Override
    public void requestHome() {
        NetCallBackObserver<HomeBean> observer = new NetCallBackObserver<HomeBean>(new
                PostProgressImpl(mIView.getContext())) {
            @Override
            public void responseFail(HomeBean bean) {

            }

            @Override
            public void responseSuccess(HomeBean bean) {

            }
        };
        mHomeModel.requestHome().subscribe(observer);
        addSubscription(observer.getDisposable());
    }
}
