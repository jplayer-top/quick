package top.jplayer.baseprolibrary.mvp.model;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.mvp.model.bean.LocalBean;

/**
 * Created by Obl on 2018/10/25.
 * top.jplayer.baseprolibrary.mvp.model
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class LocalModel extends BaseModel<LocalServer> {
    public LocalModel(Class<LocalServer> t) {
        super(t);
    }
    public Observable<LocalBean> requestLocalBean() {
        return mServer.getLocalBean("https://jplayer.top/area.json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
