package top.jplayer.quick_test.mvp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.model.bean.CartBean;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.commonmodule.mvp
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public interface CommonServer {
    /**
     * 第三方链接需要配置 Headers,默认Host 不需要添加注解 Headers
     */
    String TEST_JSON = BaseInitApplication.urlHeardHost + ":test_json";

    @Headers(TEST_JSON)
    @GET("tools/mockapi/2247/home")
    Observable<HomeBean> home();

    @Headers(TEST_JSON)
    @GET("tools/mockapi/2247/func")
    Observable<FuncBean> func(@Query("test") String test);

    @Headers(TEST_JSON)
    @GET("tools/mockapi/2247/cart")
    Observable<CartBean> cart();
}
