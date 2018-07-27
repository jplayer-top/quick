package top.jplayer.quick_test.mvp.model;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.mvp.model.BaseModel;
import top.jplayer.baseprolibrary.mvp.model.bean.CartBean;
import top.jplayer.baseprolibrary.net.retrofit.DownLoadResponseBody;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;
import top.jplayer.quick_test.mvp.model.bean.VersionBean;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.quick_test.mvp.model
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class FuncModel extends BaseModel<CommonServer> {
    public FuncModel(Class<CommonServer> t) {
        super(t);
    }

    public Observable<FuncBean> requestFunc(String test) {
        return mServer.func(test)
                .compose(new IoMainSchedule<>());
    }

    public Observable<CartBean> requestCart() {
        return mServer.cart()
                .compose(new IoMainSchedule<>());
    }

    public Observable<VersionBean> requestVersion() {
        return mServer.version()
                .compose(new IoMainSchedule<>());
    }

    public Observable<DownLoadResponseBody> requestApk(String url) {
        return mServer.apk(url)
                .compose(new IoMainSchedule<>());
    }
}
