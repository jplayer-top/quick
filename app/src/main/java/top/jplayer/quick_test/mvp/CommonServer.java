package top.jplayer.quick_test.mvp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.commonmodule.mvp
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public interface CommonServer {

    @Headers("url_header_host:test_json")
    @GET("tools/mockapi/2247/home")
    Observable<HomeBean> home();
}
