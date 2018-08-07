package top.jplayer.quick_test.mvp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.BuildConfig;
import top.jplayer.baseprolibrary.mvp.model.BaseModel;
import top.jplayer.baseprolibrary.mvp.model.bean.CartBean;
import top.jplayer.baseprolibrary.net.cookie.CookieJarImpl;
import top.jplayer.baseprolibrary.net.cookie.OKhttpCookieChange;
import top.jplayer.baseprolibrary.net.interceptor.CommentHearderInterceptor;
import top.jplayer.baseprolibrary.net.interceptor.CommentQueryInterceptor;
import top.jplayer.baseprolibrary.net.interceptor.LoggerInterceptor;
import top.jplayer.baseprolibrary.net.interceptor.ProgressResponseInterceptor;
import top.jplayer.baseprolibrary.net.retrofit.FileConverterFactory;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.LogUtil;
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

    public Observable<ResponseBody> requestApk(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(BaseInitApplication.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(BaseInitApplication.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(BaseInitApplication.TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cookieJar(new CookieJarImpl(new OKhttpCookieChange(BaseInitApplication.getContext())));
        Observable.fromIterable(interceptors())
                .subscribe(builder::addInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addConverterFactory(FileConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
        return mServer.apk(url)
                .compose(new IoMainSchedule<>());
    }

    /**
     * 默认添加的拦截器
     *
     * @return
     */
    private List<Interceptor> interceptors() {
        List<Interceptor> list = new ArrayList<>();
        list.add(new CommentQueryInterceptor());
        list.add(new CommentHearderInterceptor());
        list.add(new LoggerInterceptor(LogUtil::net, BuildConfig.DEBUG));
        list.add(new ProgressResponseInterceptor());
        return list;
    }
}
