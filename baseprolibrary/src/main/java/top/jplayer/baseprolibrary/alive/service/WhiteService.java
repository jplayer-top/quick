package top.jplayer.baseprolibrary.alive.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import top.jplayer.baseprolibrary.BuildConfig;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.NotificationUtil;


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
        return START_STICKY;
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
