package top.jplayer.quick_test.mvp.construct;

import top.jplayer.baseprolibrary.mvp.contract.IContract;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.quick_test.mvp.construct
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class HomeConstruct {
    public interface HomeIView extends IContract.IView {
        void responseHome(HomeBean bean);
    }

    public interface HomeIPresenter extends IContract.IPresenter {
        void requestHome();
    }
}
