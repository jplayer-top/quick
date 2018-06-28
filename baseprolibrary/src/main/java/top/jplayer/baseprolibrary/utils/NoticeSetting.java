package top.jplayer.baseprolibrary.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import top.jplayer.baseprolibrary.BaseInitApplication;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Obl on 2018/5/2.
 * top.jplayer.baseprolibrary.utils
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class NoticeSetting {

    public static void noticeOpen() {
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
     * 后台管理
     */
    public static void intentNotification() {
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
