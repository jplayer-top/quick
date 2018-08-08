package top.jplayer.baseprolibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.ArrayMap;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import okhttp3.Interceptor;
import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;
import top.jplayer.baseprolibrary.listener.SampleApplicationLifecycleCallbacks;
import top.jplayer.baseprolibrary.listener.observer.CustomObserver;
import top.jplayer.baseprolibrary.net.retrofit.RetrofitManager;

/**
 * Created by Obl on 2018/1/9.
 * top.jplayer.baseprolibrary
 */

public class BaseInitApplication {


    private static BaseInitApplication mInit;
    public static WeakReference<Application> mWeakReference;
    public static Map<String, String> mUrlMap;
    public static final String urlHeardHost = "url_header_host";
    public static final Long TIME_OUT = 30L;
    public static List<Activity> sActivityList;
    public static Map<String, CustomObserver> mObserverMap;

    private BaseInitApplication(Application application) {
        mWeakReference = new WeakReference<>(application);
        mUrlMap = new ArrayMap<>();
        sActivityList = new LinkedList<>();
        mObserverMap = new ArrayMap<>();
    }

    public synchronized static BaseInitApplication with(Application application) {
        if (mInit == null) {

            synchronized (BaseInitApplication.class) {
                if (mInit == null) {
                    mInit = new BaseInitApplication(application);
                }
            }
        }
        return mInit;
    }

    /**
     * Activity管理器
     */
    public BaseInitApplication lifecycle() {
        getApplication().registerActivityLifecycleCallbacks(mCallbacks);
        return this;
    }

    public BaseInitApplication swipeBack() {
        BGASwipeBackHelper.init(mWeakReference.get(), null);
        return this;
    }

    public BaseInitApplication fixFileProvide() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        return this;
    }

    public static Context getContext() {
        return mWeakReference.get().getApplicationContext();
    }

    public Application getApplication() {
        return mWeakReference.get();
    }

    /**
     * 初始化retrofit
     */
    public BaseInitApplication retrofit() {
        retrofit(BuildConfig.HOST);
        return this;
    }

    public BaseInitApplication retrofit(String urlDefHost) {
        RetrofitManager.init().client(mWeakReference.get()).build(urlDefHost);
        return this;
    }

    public BaseInitApplication retrofit(Interceptor... interceptors) {
        retrofit(BuildConfig.HOST, interceptors);
        return this;
    }


    public BaseInitApplication retrofit(String urlDefHost, Interceptor... interceptors) {
        RetrofitManager.init().client(mWeakReference.get(), Arrays.asList(interceptors)).build(BuildConfig.HOST);
        return this;
    }


    /**
     * 添加单个url
     * key :设置的Header 值，value 设定的需要修改的 host
     * 使用：在注解上添加 @Header("url_heard_host:key")
     */
    public BaseInitApplication addUrl(String key, String value) {
        mUrlMap.put(key, value);
        return this;
    }

    /**
     * 添加集合url
     */
    public BaseInitApplication urlMap(Map<String, String> map) {
        mUrlMap.putAll(map);
        return this;
    }

    /**
     * 初始化zxing
     */
    public BaseInitApplication zxing() {
        ZXingLibrary.initDisplayOpinion(getContext());
        return this;
    }

    public BaseInitApplication skin() {
        SkinCompatManager.withoutActivity(this.getApplication())                         // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
        return this;
    }

    private Application.ActivityLifecycleCallbacks mCallbacks = new SampleApplicationLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            sActivityList.add(activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

}
