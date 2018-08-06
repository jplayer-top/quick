package top.jplayer.baseprolibrary.net.interceptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

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
        Request requestHeader = chain.request();
        okhttp3.Response requestBody = chain.proceed(chain.request());
        List<String> headerValues = requestHeader.headers("download");
        return requestBody.newBuilder()
                .body(new DownLoadResponseBody(requestBody.body(), (progress, total, done) -> {
                    LogUtil.e("onProgress: " + "total ---->" + total + "--- done ---->" + progress);
                    if (headerValues != null && headerValues.contains("download")) {
                        Context context = BaseInitApplication.getContext();
                        Intent intent = new Intent();
                        intent.setAction(context.getPackageName() + ".download");
                        intent.putExtra("total", total / 1024 / 1024);
                        intent.putExtra("progress", progress / 1024 / 1024);
                        context.sendBroadcast(intent);
                    }
                }))
                .build();
    }
}
