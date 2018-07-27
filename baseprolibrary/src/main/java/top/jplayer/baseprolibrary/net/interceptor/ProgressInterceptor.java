package top.jplayer.baseprolibrary.net.interceptor;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.net.retrofit.DownLoadResponseBody;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Administrator on 2018/1/26.
 * 下载进度
 */

public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        okhttp3.Response request = chain.proceed(chain.request());
        List<String> headerValues = request.headers("download");
        return request.newBuilder()
                .body(new DownLoadResponseBody(request.body(), (progress, total, done) -> {
                    LogUtil.e("onProgress: " + "total ---->" + total + "done ---->" + progress);
                    if (headerValues != null && headerValues.contains("download")) {
                        Context context = BaseInitApplication.getContext();
                        Intent intent = new Intent();
                        intent.setAction(context.getPackageName() + ".download");
                        intent.putExtra("total", total);
                        intent.putExtra("progress", progress);
                        Observable.interval(500, TimeUnit.MILLISECONDS)
                                .subscribe(aLong -> context.sendBroadcast(intent));
                    }
                }))
                .build();
    }
}
