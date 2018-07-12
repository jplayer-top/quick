package top.jplayer.quick_test;

import android.support.multidex.MultiDexApplication;

import top.jplayer.baseprolibrary.BaseInitApplication;

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
                .addUrl("test_json", "http://www.wanandroid.com/")
                .retrofit()
                .swipeBack()
                .zxing()
                .fixFileProvide();
    }
}
