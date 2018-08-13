package top.jplayer.quick_test.mvp.presenter;

import android.widget.Button;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.mvp.contract.BasePresenter;
import top.jplayer.quick_test.ui.activity.LoginActivity;

/**
 * Created by Obl on 2018/8/13.
 * top.jplayer.quick_test.mvp.presenter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class LoginPresenter extends BasePresenter<LoginActivity> {
    public LoginPresenter(LoginActivity iView) {
        super(iView);
    }

    public void verifySms(String phone, String smCode) {
        Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            mIView.verifySms();
        });
    }

    public void sendSms(Map<String, String> map, Button rtnCode) {
        Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            mIView.smsSend(rtnCode);
        });
    }

    public void forget(Map<String, String> map) {
        Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            mIView.forget();
        });
    }

    public void login(String phone, String password) {
        Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            mIView.login();
        });
    }

    public void register(Map<String, String> map) {
        Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            login(map.get("phone"), map.get("password"));
        });
    }
}
