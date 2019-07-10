package top.jplayer.baseprolibrary.mvp.model;

import android.annotation.SuppressLint;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.BaseInitApplication;
import top.jplayer.baseprolibrary.mvp.model.bean.LocalBean;

/**
 * Created by Obl on 2018/10/25.
 * top.jplayer.baseprolibrary.mvp.model
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class LocalModel extends BaseModel<LocalServer> {
    public LocalModel(Class<LocalServer> t) {
        super(t);
    }

    public Observable<LocalBean> requestLocalBean() {
        return mServer.getLocalBean("https://jplayer.top/area.json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    public Observable<LocalBean> requestLocalBeanByJson() {

        return Observable.just("area.json").subscribeOn(Schedulers.io())
                .map(s -> new Gson().fromJson(getFromAssets(s), LocalBean.class))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader =
                    new InputStreamReader(BaseInitApplication.getContext().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder Result = new StringBuilder();
            while ((line = bufReader.readLine()) != null)
                Result.append(line);
            return Result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
