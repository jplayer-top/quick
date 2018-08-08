package top.jplayer.quick_test;

import android.support.multidex.MultiDexApplication;

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
    }
}
