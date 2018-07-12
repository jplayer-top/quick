package top.jplayer.baseprolibrary.mvp.model.bean;

import android.text.TextUtils;

/**
 * Created by Obl on 2018/1/25.
 * com.modiwu.mah.mvp.model.bean
 */

public class BaseBean {
    private static final String SUCCESS_CODE = "000";
    private static final String NO_LOGIN_CODE = "401";
    public String code;
    public String msg;

    public String getCode() {
        return code;
    }

    public boolean isSuccess() {
        return TextUtils.equals(getCode(), SUCCESS_CODE);
    }

    public boolean isNoLogin() {
        return TextUtils.equals(getCode(), NO_LOGIN_CODE);
    }
}
