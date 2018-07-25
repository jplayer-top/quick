package top.jplayer.quick_test.mvp.model;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.mvp.model.BaseModel;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;

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
}
