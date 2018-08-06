package top.jplayer.quick_test.mvp.model;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.jplayer.baseprolibrary.mvp.model.BaseModel;
import top.jplayer.baseprolibrary.mvp.model.bean.BaseBean;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.quick_test.mvp.CommonServer;
import top.jplayer.quick_test.mvp.model.bean.RetrofitPostBean;

/**
 * Created by Obl on 2018/8/6.
 * top.jplayer.quick_test.mvp.model.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class RetrofitModel extends BaseModel<CommonServer> {

    public RetrofitModel(Class<CommonServer> t) {
        super(t);
    }

    public Observable<RetrofitPostBean> post(String tel, String pwd) {
        return mServer.retrofitPost("post", tel, pwd).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> get(String test) {
        return mServer.retrofitGet(test).compose(new IoMainSchedule<>());
    }

    public Observable<BaseBean> file(String uid, File file, String key) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData(key, file.getName(), requestFile);

        // 添加字段
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), uid);

        return mServer.retrofitFile(description, body).compose(new IoMainSchedule<>());
    }
}
