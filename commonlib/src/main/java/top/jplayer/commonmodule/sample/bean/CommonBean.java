package top.jplayer.commonmodule.sample.bean;

import android.text.TextUtils;

/**
 * Created by Obl on 2018/7/6.
 * top.jplayer.commonmodule.mvpb.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class CommonBean {
    private static final String SUCCESS_CODE = "000";
    private static final String NCOOKIE_CODE = "401";
    public String code;
    public String msg;
    public String xxx;

    public String getCode() {
        return code;
    }

    public boolean isSuccess() {
        return TextUtils.equals(getCode(), SUCCESS_CODE);
    }
}
