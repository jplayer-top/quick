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
    /*登录出现异常按钮*/
    private static final String SURE_EXCEPTION_ID = "android:id/button1";
    /*退出登录按钮的ID*/
    private static final String CONFIRM_QUIT_ID = "android:id/button1";
    /*电话号码输入框*/
    private static final String PHONE_ID = "com.alibaba.android.rimet:id/et_phone_input";
    /*密码输入框*/
    private static final String PASSWORD_ID = "com.alibaba.android.rimet:id/et_pwd_login";
    /*登陆点击按钮*/
    private static final String BUTTON_ID = "com.alibaba.android.rimet:id/btn_next";
    /*Bottom 栏*/
    private static final String HOME_BOTTOM_ID = "com.alibaba.android.rimet:id/bottom_tab";
    /*工作页面*/
    private static final String HOME_BOTTOM_WORK_ID = "com.alibaba.android.rimet:id/home_bottom_tab_button_work";
    /*当前公司的指标*/
    private static final String TAB_COMPANY_INDEX_ID = "com.alibaba.android.rimet:id/menu_current_company";
    /*工作页面的RecyclerView id*/
    private static final String RECYCLERVIEW_WORK_ID = "com.alibaba.android.rimet:id/oa_fragment_gridview";
    /*webView*/
    private static final String CHECK_IN_PAGER_TAGET = "com.alibaba.lightapp.runtime.activity.CommonWebViewActivity";
    /*设置页面*/
    private static final String SETTINGWINDOW = "com.alibaba.android.user.settings.activity.NewSettingActivity";
    /*用于监控线程生命周期控制*/
    private volatile boolean runing_monitor = true;
    /*登陆窗口*/
    public static final String LOGINWINDOW = "com.alibaba.android.user.login.SignUpWithPwdActivity";
    /*主页面*/
    private static final String HOMEWINDOW = "com.alibaba.android.rimet.biz.home.activity.HomeActivity";
    /*当前窗口*/
    private static String CURRENT_WINDOW = "";
    /*表示当前钉钉用户是否打卡*/
    private static boolean isCheckIn = false;
    /*监控线程*/
//    private MonitorThread mMonitorThread;
    /**/
    private static int STATE = 0;
    /*当前状态是已经打卡状态*/
    private static final int STATE_CHECKED_IN = 1;
    /*当前状态是未打卡状态*/
    private static final int STATE_UNCHECKED_IN = 0;
    /*当前状态进入休息状态， 无需进入 打卡*/
    private static final int STATE_RELEASE = -1;
    /**
     * 通知的ID
     */
    private static final int NOTIFICATION_ID = 0x000088;
    /*钉钉的包名*/
    private static final String DING_DING_PAGKET_NAME = "com.alibaba.android.rimet";

    private static final String WEBVIEW_PARENT = "com.alibaba.android.rimet:id/common_webview";

    private static final String AFTER_WORK = "下班打卡";
    private static final String GO_TO_WORK = "上班打卡";
    private static final String ALERT_DIALOG_WINDOW = "android.app.AlertDialog";

    /**
     * 我的ID
     */
    private static final String HOME_MINE_ID = "com.alibaba.android.rimet:id/home_bottom_tab_button_mine";
    /*设置ID*/
    private static final String MINE_SETTING_ID = "com.alibaba.android.rimet:id/rl_setting";
    /*退出登录按钮ID*/
    private static final String SETTING_SIGN_OUT_ID = "com.alibaba.android.rimet:id/setting_sign_out";
    /*表现当前Fragment 二级页面*/
    private static String CURRENT_PAGER = "message";
    private static final String MESSAGE_PAGER = "message";
    private static final String DING_PAGER = "ding";
    private static final String TCN_PAGER = "tcn";
    private static final String CONTACT_PAGER = "contact";
    private static final String MINE_PAGER = "mine";

    private static final String HEADER_MINE_ID = "com.alibaba.android.rimet:id/header_mine";
    private static final String HEADER_CONTACT_ID = "com.alibaba.android.rimet:id/header_contact";
    private static final String HEADER_DING = "com.alibaba.android.rimet:id/header_ding";
    private static final String HEADER_MESSAGE = "com.alibaba.android.rimet:id/header_message";

    /*打卡页面的返回按钮*/
    private static final String CHECK_IN_PAGER_BACK = "com.alibaba.android.rimet:id/back_layout";
    /**
     * 中午十二点为上班或者下班打卡中间界限
     */
    private static final int TIME_LIMIT = 12;

    /*标示service是否已经开启*/
    public static volatile boolean IS_ENABLE_DINGDINGHELPERACCESSIBILITYSERVICE = false;

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
                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
                    clickByText("下班打卡");
                    clickById("30233468054");
                    clickById("mainPageWrap");
                });
//                Observable.timer(1, TimeUnit.SECONDS).compose(new IoMainSchedule<>()).subscribe(aLong -> {
//                    clickByText("考勤打卡");
//                });
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
                ac0.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    private void clickById(String id) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            if (list != null && !list.isEmpty()) {
                AccessibilityNodeInfo accessibilityNodeInfo = list.get(0);
                accessibilityNodeInfo.getChild(2).performAction(AccessibilityNodeInfo.ACTION_CLICK);
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
