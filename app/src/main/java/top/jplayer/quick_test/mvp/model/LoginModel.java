package top.jplayer.quick_test.mvp.model;

import java.util.Map;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.mvp.model.BaseModel;
import top.jplayer.baseprolibrary.mvp.model.bean.BaseBean;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.model.bean.LoginBean;

/**
 * Created by Obl on 2018/8/13.
 * top.jplayer.quick_test.mvp.model
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class LoginModel extends BaseModel<CommonServer> {
    public LoginModel(Class<CommonServer> t) {
        super(t);
    }


    public Observable<BaseBean> requestSendSms(Map<String, String> map) {
        return mServer
                .sendSms(map)
                .compose(new IoMainSchedule<>());
    }


    public Observable<BaseBean> requestVerifySms(String phone, String smCode) {
        return mServer
                .verfiySms(phone, smCode)
                .compose(new IoMainSchedule<>());
    }

    public Observable<LoginBean> requestLogin(String phone, String password) {
        return mServer
                .login(phone, password)
                .compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> requestRegister(Map<String, String> map) {
        return mServer
                .register(map)
                .compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> requestForget(Map<String, String> map) {
        return mServer
                .forget(map)
                .compose(new IoMainSchedule<>());
    }
}
