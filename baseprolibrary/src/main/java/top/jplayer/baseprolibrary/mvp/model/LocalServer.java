package top.jplayer.baseprolibrary.mvp.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;
import top.jplayer.baseprolibrary.mvp.model.bean.LocalBean;

/**
 * Created by Obl on 2018/10/25.
 * top.jplayer.baseprolibrary.mvp.model
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public interface LocalServer {
    @GET()
    Observable<LocalBean> getLocalBean(@Url String url);
}
