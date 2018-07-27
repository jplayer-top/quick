package top.jplayer.baseprolibrary.net.interceptor;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.net.retrofit.DownLoadResponseBody;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Administrator on 2018/1/26.
 * 动态设置 Url
 */

public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        okhttp3.Response orginalResponse = chain.proceed(chain.request());

        return orginalResponse.newBuilder()
                .body(new DownLoadResponseBody(orginalResponse.body(), (progress, total, done) -> {
                    LogUtil.e("onProgress: " + "total ---->" + total + "done ---->" + progress);
                }))
                .build();
    }
}
