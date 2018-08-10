package top.jplayer.baseprolibrary.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;

/**
 * Created by Obl on 2018/1/18.
 * top.jplayer.baseprolibrary.utils
 */

public class ActivityUtils {
    private static ActivityUtils activityUtils;

    public static synchronized ActivityUtils init() {
        if (activityUtils == null) {
            synchronized (ActivityUtils.class) {
                if (activityUtils == null) {
                    activityUtils = new ActivityUtils();
                }
            }
        }
        return activityUtils;
    }

    public void start(Activity activity, Class tClass, MotionEvent event) {
        Intent i = new Intent(activity, tClass);
        i.putExtra("x", (int) event.getX());
        i.putExtra("y", (int) event.getY());
        activity.startActivity(i);
    }

    public void start(Activity activity, String tClass, String title) {
        start(activity, tClass, title, null);
    }

    public void start(Activity activity, String tClass, String title, String bundle) {
        try {
            ComponentName cn = new ComponentName(activity, tClass);
            Intent i = new Intent();
            i.setComponent(cn);
            if (title != null) i.putExtra("title", title);
            if (bundle != null) i.putExtra("bundle", bundle);
            activity.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.init().showQuickToast("无法查找Activity");
        }
    }

    public void start(Activity activity, Class tClass, String title, Bundle bundle) {
        Intent i = new Intent(activity, tClass);
        if (title != null) i.putExtra("title", title);
        if (bundle != null) i.putExtra("bundle", bundle);
        activity.startActivity(i);
    }

    /**
     * 融云 聊天开启
     *
     * @param clazz
     * @param title
     * @param type
     * @param mFid
     */
    public void startConversion(Activity activity, Class clazz, String title, String type, String mFid) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtra("title", title);
        intent.putExtra("fid", mFid.substring(2));
        intent.setAction("android.groupIntent.action.VIEW");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("rong://com.ilanchuang.xiaoi/conversation/" + type + "/?targetId=" + mFid));
        activity.startActivity(intent);
    }

    public void start(Activity activity, Class tClass) {
        start(activity, tClass, null, null);
    }

    public void start(Activity activity, Class tClass, String title) {
        start(activity, tClass, title, null);
    }

    public void startForResult(Activity activity, Class tClass, String title, Bundle bundle, int requestCode) {
        Intent i = new Intent(activity, tClass);
        if (title != null) i.putExtra("title", title);
        if (bundle != null) i.putExtra("bundle", bundle);
        activity.startActivityForResult(i, requestCode);
    }

    public void startForResult(Activity activity, Class tClass, String title, int requestCode) {
        startForResult(activity, tClass, title, null, requestCode);
    }

    public void startForResult(Activity activity, Class tClass, int requestCode) {
        startForResult(activity, tClass, null, null, requestCode);
    }

    /**
     * 默认为 requestCode = 1
     */
    public void startForResult(Activity activity, Class tClass, String title) {
        startForResult(activity, tClass, title, null, 1);
    }

    public void startFragmentForResult(SuperBaseFragment fragment, Class tClass, String title, int requestCode) {
        Intent intent = new Intent();
        if (title != null) intent.putExtra("title", title);
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            intent.setClass(activity, tClass);
            fragment.startActivityForResult(intent, requestCode);
        }
    }


    /**
     * 默认为 requestCode = 1
     */
    public void startForResult(Activity activity, Class tClass) {
        startForResult(activity, tClass, null, null, 1);
    }
}
