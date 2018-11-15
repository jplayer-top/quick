package top.jplayer.baseprolibrary.alive.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;

import top.jplayer.baseprolibrary.BuildConfig;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.NotificationUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;


/**
 * 正常的系统前台进程，会在系统通知栏显示一个Notification通知图标
 *
 * @author clock
 * @since 2016-04-12
 */
public class WhiteService extends Service {

    private final static int FOREGROUND_ID = 1000;

    @Override
    public void onCreate() {
        LogUtil.e("WhiteService->onCreate");
        super.onCreate();
    }


    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("WhiteService->onStartCommand");
        Intent activityIntent = new Intent(BuildConfig.APPLICATION_ID + ".main.live");
        Notification notification = NotificationUtil.init(this).pendingIntent(activityIntent, "白色服务", "服务运行中...");
        startForeground(FOREGROUND_ID, notification);
        Intent intent1 = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        startOutActivity("com.alibaba.android.rimet", "com.alibaba.android.rimet.biz.SplashActivity");
        return START_STICKY;
    }

    public void startOutActivity(String sPkg, String tClass) {
        try {
            ComponentName cn = new ComponentName(sPkg, tClass);
            Intent i = new Intent();
            i.setComponent(cn);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.init().showQuickToast("无法查找Activity");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        LogUtil.e("WhiteService->onDestroy");
        super.onDestroy();
    }
}
