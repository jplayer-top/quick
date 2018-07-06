package top.jplayer.quick_test.mvp.presenter;

import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.construct.HomeConstruct;
import top.jplayer.quick_test.mvp.model.HomeModel;

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
        Disposable subscribe = mHomeModel.requestHome().subscribe();
        addSubscription(subscribe);
    }
}
