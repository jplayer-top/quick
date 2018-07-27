package top.jplayer.quick_test.mvp;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.model.bean.CartBean;
import top.jplayer.baseprolibrary.net.retrofit.DownLoadResponseBody;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;
import top.jplayer.quick_test.mvp.model.bean.VersionBean;

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

    String KEY_TEST = "test";
    String VALUE_TEST = "http://www.wanandroid.com/";
    String TEST = BaseInitApplication.urlHeardHost + ":" + KEY_TEST;

    String KEY_VERSION = "version";
    String VALUE_VERSION = "https://app.xiaoyi99.com/";
    String VERSION = BaseInitApplication.urlHeardHost + ":" + KEY_VERSION;

    @Headers(TEST)
    @GET("tools/mockapi/2247/home")
    Observable<HomeBean> home();

    @Headers(TEST)
    @GET("tools/mockapi/2247/func")
    Observable<FuncBean> func(@Query("test") String test);

    @Headers(TEST)
    @GET("tools/mockapi/2247/cart")
    Observable<CartBean> cart();

    @Headers(VERSION)
    @GET("v2/ver2")
    Observable<VersionBean> version();

    @Streaming //IO 大文件下载处理
    @GET
    Observable<DownLoadResponseBody> apk(@Url String url);
}
