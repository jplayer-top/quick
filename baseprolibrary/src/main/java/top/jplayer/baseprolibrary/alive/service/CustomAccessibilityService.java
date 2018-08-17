package top.jplayer.baseprolibrary.alive.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2018/8/17.
 * top.jplayer.baseprolibrary.alive.service
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class CustomAccessibilityService extends AccessibilityService {
    /*
     disableSelf()：禁用当前服务，也就是在服务可以通过该方法停止运行
     findFoucs(int falg)：查找拥有特定焦点类型的控件
     getRootInActiveWindow()：如果配置能够获取窗口内容,则会返回当前活动窗口的根结点
     getSeviceInfo()：获取当前服务的配置信息
     onAccessibilityEvent(AccessibilityEvent event)：有关AccessibilityEvent事件的回调函数，系统通过sendAccessibiliyEvent()不断的发送AccessibilityEvent到此处
     performGlobalAction(int action)：执行全局操作，比如返回，回到主页，打开最近等操作
     setServiceInfo(AccessibilityServiceInfo info)：设置当前服务的配置信息
     getSystemService(String name)：获取系统服务
     onKeyEvent(KeyEvent event)：如果允许服务监听按键操作，该方法是按键事件的回调，需要注意，这个过程发生了系统处理按键事件之前
     onServiceConnected()：系统成功绑定该服务时被触发，也就是当你在设置中开启相应的服务，系统成功的绑定了该服务时会触发，通常我们可以在这里做一些初始化操作
     onInterrupt()：服务中断时的回调
     */

    /**
     * 当启动服务的时候就会被调用
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    /**
     * 监听窗口变化的回调
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //根据事件回调类型进行处理
        int eventType = event.getEventType();
        //根据事件回调类型进行处理
        switch (eventType) {
            //当通知栏发生改变时
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                ToastUtils.init().showQuickToast("通知栏的状态发生改变");
                break;
            //当窗口的状态发生改变时
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Observable.timer(3, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    clickById("com.alibaba.android.rimet:id/home_bottom_tab_button_work");
                    clickById("com.alibaba.android.rimet:id/oa_entry_title");
                });
                Observable.timer(10, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    clickByText("下班打卡");
                });
                break;
        }
    }

    private void clickById(String id) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(id);
        AccessibilityNodeInfo ac0;
        AccessibilityNodeInfo ac1;
        for (AccessibilityNodeInfo info : list) {
            ac0 = info;
            ac1 = info;
            if ("工作".equals(ac0.getContentDescription())) {
                ac0.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            if ("考勤打卡".equals(ac1.getText())) {
                ac1.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    private void clickByText(String text) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        AccessibilityNodeInfo ac0;
        for (AccessibilityNodeInfo info : list) {
            ac0 = info;
            if (text.equals(ac0.getContentDescription())) {
                ac0.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }

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
