package top.jplayer.baseprolibrary.alive.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2018/8/17.
 * top.jplayer.baseprolibrary.alive.service
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class CustomAccessibilityService extends AccessibilityService {


    /**
     * 当启动服务的时候就会被调用
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        settingAccessibilityInfo();
    }

    /**
     * 监听窗口变化的回调
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //根据事件回调类型进行处理
        int eventType = event.getEventType();

        switch (eventType) {

            //当通知栏发生改变时
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                ToastUtils.init().showQuickToast("通知栏的状态发生改变");
                break;
            //当窗口的状态发生改变时
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                ToastUtils.init().showQuickToast("窗口的状态发生改变");
                break;
                /*窗口变化*/
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    String id = "com.alibaba.android.rimet:id/home_bottom_tab_button_work";
                    clickById(findById(id));
                });
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    String id = "com.alibaba.android.rimet:id/oa_entry_title";
                    clickById(findById(id));
                });

                break;

        }
    }

    private void clickByWeb(String text) {
        AccessibilityNodeInfo mAccessibilityNodeInfo = this.getRootInActiveWindow();
        for (int i = 0; i < mAccessibilityNodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo child = mAccessibilityNodeInfo.getChild(i);
            CharSequence contentDescription = child.getContentDescription();
            LogUtil.str(contentDescription);
        }
        //todo 7.0  dispatchGesture
    }

    private AccessibilityNodeInfo findById(String id) {
        AccessibilityNodeInfo mAccessibilityNodeInfo = this.getRootInActiveWindow();
        AccessibilityNodeInfo ac = null;
        if (mAccessibilityNodeInfo == null)
            return null;
        List<AccessibilityNodeInfo> mNodeInfos = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (mNodeInfos == null || mNodeInfos.isEmpty())
            return null;
        for (AccessibilityNodeInfo info : mNodeInfos) {
            CharSequence contentDescription = info.getContentDescription();
            if (info.getContentDescription() != null) {
                boolean contains = contentDescription.toString().contains("工作");
                if (contains) {
                    ac = info;
                }
            }
            CharSequence text = info.getText();
            if (text != null && text.toString().contains("考勤打卡")) {
                ac = info;
            }
        }
        return ac;
    }

    private void clickById(AccessibilityNodeInfo ac0) {
        if (ac0 != null) {
            CharSequence contentDescription = ac0.getContentDescription();
            if (contentDescription != null) {
                boolean contains = contentDescription.toString().contains("工作");
                if (contains) {
                    ac0.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
            CharSequence text = ac0.getText();
            if (text != null && "考勤打卡".equals(text)) {
                LogUtil.str(  "----------what");
                ac0.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    private void clickById(String id) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();

        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            LogUtil.str(list + "----------what");
            if (list != null && !list.isEmpty()) {
                AccessibilityNodeInfo accessibilityNodeInfo = list.get(0);
                LogUtil.str(accessibilityNodeInfo + "-----");
            }

        }
    }
    //  dispatchGesture()

    private void clickByText(String text) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        AccessibilityNodeInfo ac0;
        for (AccessibilityNodeInfo info : list) {
            ac0 = info;
            if (text.equals(ac0.getContentDescription())) {
                ac0.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            LogUtil.str(ac0);
        }
    }

    /**
     * 中断服务的回调
     */
    @Override
    public void onInterrupt() {

    }

    private void settingAccessibilityInfo() {
        String[] packageNames = {"com.alibaba.android.rimet"};
        AccessibilityServiceInfo mAccessibilityServiceInfo = new AccessibilityServiceInfo();
        // 响应事件的类型，这里是全部的响应事件（长按，单击，滑动等）
        mAccessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        // 反馈给用户的类型，这里是语音提示
        mAccessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        // 过滤的包名
        mAccessibilityServiceInfo.packageNames = packageNames;
        setServiceInfo(mAccessibilityServiceInfo);
    }
}
