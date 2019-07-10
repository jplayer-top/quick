package top.jplayer.quick_test;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.quick_test.mvp.CommonServer;

/**
 * Created by Obl on 2018/6/29.
 * top.jplayer.quick_test
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class QuickApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        BaseInitApplication.with(this)
                .addUrl(CommonServer.KEY_TEST, CommonServer.VALUE_TEST)
                .addUrl(CommonServer.KEY_TOP, CommonServer.VALUE_TOP)
                .addUrl(CommonServer.KEY_VERSION, CommonServer.VALUE_VERSION)
                .retrofit()
                .swipeBack()
                .zxing()
                .skin()
                .fixFileProvide();
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

    }
}
