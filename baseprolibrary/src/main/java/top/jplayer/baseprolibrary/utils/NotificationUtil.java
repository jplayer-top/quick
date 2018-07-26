package top.jplayer.baseprolibrary.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.lang.ref.WeakReference;

import top.jplayer.baseprolibrary.BaseInitApplication;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Obl on 2018/7/25.
 * top.jplayer.baseprolibrary.utils
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class NotificationUtil {
    private NotificationManager manager;
    public static final String id1 = "1";
    public static final String id2 = "2";
    public static final String name1 = "重要";
    public static final String name2 = "普通";
    private static NotificationUtil mNotificationUtil;
    private WeakReference<Context> mWeakReference;

    public NotificationUtil(Context context) {
        mWeakReference = new WeakReference<>(context);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) mWeakReference.get().getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public static NotificationUtil init(Context context) {
        if (mNotificationUtil == null) {
            mNotificationUtil = new NotificationUtil(context);
        }
        return mNotificationUtil;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel1 = new NotificationChannel(id1, name1, NotificationManager.IMPORTANCE_HIGH);
        NotificationChannel channel2 = new NotificationChannel(id2, name2, NotificationManager.IMPORTANCE_DEFAULT);
        getManager().createNotificationChannel(channel1);
        getManager().createNotificationChannel(channel2);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotification(String id, String title, String content) {
        return new Notification.Builder(mWeakReference.get().getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
    }


    public NotificationCompat.Builder getNotification(String title, String content) {
        return new NotificationCompat.Builder(mWeakReference.get().getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
    }

    public Notification pendingIntent(Intent intent, String title, String content) {
        return pendingIntent(intent, title, content, id1);
    }

    public Notification pendingIntent(Intent intent, String title, String content, String id) {
        PendingIntent pendingIntent = PendingIntent.getActivity(mWeakReference.get(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= 26) {
            Notification.Builder builder = getNotification(id, title, content);
            builder.setContentIntent(pendingIntent);
            return builder.build();
        } else {
            NotificationCompat.Builder builder = getNotification(title, content);
            builder.setContentIntent(pendingIntent);
            return builder.build();
        }
    }

    public void sendNotification(String title, String content, String id) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            Notification notification = getNotification(id, title, content).build();
            getManager().notify(1, notification);
        } else {
            Notification notification = getNotification(title, content).build();
            getManager().notify(1, notification);
        }
    }

    /**
     * 打开通知管理
     */
    public static void intentBackGround() {
        Context context = BaseInitApplication.mWeakReference.get();
        Intent intent = new Intent();
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        //android 8.0引导
        if (Build.VERSION.SDK_INT >= 26) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        }
        //android 5.0-7.0
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 26) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        }
        if (Build.VERSION.SDK_INT < 21) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        context.startActivity(intent);
    }

    /**
     * 打开后台管理
     */
    public static void intentNotice() {
        Context context = BaseInitApplication.mWeakReference.get();
        String mtype = android.os.Build.MODEL; // 手机型号
        Intent intent = new Intent();
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = null;
        if (mtype.startsWith("Redmi") || mtype.startsWith("MI")) {
            componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
        } else if (mtype.startsWith("HUAWEI")) {
            componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } else if (mtype.startsWith("vivo")) {
            LogUtil.e("selfStartManagerSettingIntent: vivo");
            componentName = new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity");
        } else if (mtype.startsWith("ZTE")) {
//            /.autorun.AppAutoRunManager
            componentName = new ComponentName("com.zte.heartyservice", "com.zte.heartyservice.autorun.AppAutoRunManager");
        } else if (mtype.startsWith("F")) {
            LogUtil.e("selfStartManagerSettingIntent: F");
            componentName = new ComponentName("com.gionee.softmanager", "com.gionee.softmanager.oneclean.AutoStartMrgActivity");
        } else if (mtype.startsWith("oppo")) {
            componentName = new ComponentName("oppo com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity");
        } else if (mtype.contains("Note")) {
            componentName = new ComponentName("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity");
        }
        intent.setComponent(componentName);
        try {
            context.startActivity(intent);
        } catch (Exception e) {//抛出异常就直接打开设置页面
            intent = new Intent(Settings.ACTION_SETTINGS);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
