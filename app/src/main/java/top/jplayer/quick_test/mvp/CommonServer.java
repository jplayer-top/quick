package top.jplayer.quick_test.mvp;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.model.bean.BaseBean;
import top.jplayer.baseprolibrary.mvp.model.bean.CartBean;
import top.jplayer.baseprolibrary.mvp.model.bean.LocationBean;
import top.jplayer.quick_test.mvp.model.bean.FuncBean;
import top.jplayer.quick_test.mvp.model.bean.HomeBean;
import top.jplayer.quick_test.mvp.model.bean.LoginBean;
import top.jplayer.quick_test.mvp.model.bean.RetrofitPostBean;
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

    String KEY_TOP = "top";
    String VALUE_TOP = "http://www.jplayer.top/";
    String TOP = BaseInitApplication.urlHeardHost + ":" + KEY_TOP;

    @Headers(TEST)
    @GET("tools/mockapi/2247/home")
    Observable<HomeBean> home();

    @Headers(TOP)
    @GET("func.json")
    Observable<FuncBean> func(@Query("test") String test);

    @Headers(TEST)
    @GET("tools/mockapi/2247/cart")
    Observable<CartBean> cart();

    @Headers(VERSION)
    @GET("v2/ver2")
    Observable<VersionBean> version();

    @Headers(TOP)
    @FormUrlEncoded
    @POST("request/{type}.php")
    Observable<RetrofitPostBean> retrofitPost(@Path("type") String type,
                                              @Field("tel") String tel,
                                              @Field("pwd") String pwd);

    @Headers(TOP)
    @GET("request/get.php")
    Observable<BaseBean> retrofitGet(@Query("test") String tel);

    @Headers(TOP)
    @Multipart
    @POST("request/file.php")
    Observable<BaseBean> retrofitFile(@Part("uid") RequestBody uid, @Part() MultipartBody.Part file);


    @Headers("download:download")
    @Streaming //IO 大文件下载处理
    @GET
    Observable<ResponseBody> apk(@Url String url);

    @Headers(TOP)
    @GET("location.php")
    Observable<LocationBean> getLocation();

    @POST("user/login?")
    Observable<LoginBean> login(@Query("phone") String phone, @Query("password") String password);

    @POST("user/smcode?")
    Observable<LoginBean> sendSms(@QueryMap() Map<String, String> map);

    @POST("user/verfiysmcode?")
    Observable<LoginBean> verfiySms(@Query("phone") String phone, @Query("smCode") String password);

    @POST("user/register?")
    Observable<BaseBean> register(@QueryMap() Map<String, String> map);

    @POST("user/resetpw?")
    Observable<BaseBean> forget(@QueryMap() Map<String, String> map);
}
