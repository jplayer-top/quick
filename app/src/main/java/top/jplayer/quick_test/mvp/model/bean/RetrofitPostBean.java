package top.jplayer.quick_test.mvp.model.bean;

import top.jplayer.baseprolibrary.mvp.model.bean.BaseBean;

/**
 * Created by Obl on 2018/8/6.
 * top.jplayer.quick_test.mvp.model.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class RetrofitPostBean extends BaseBean {
    /**
     * response : {"tel":null,"pwd":null}
     */

    public ResponseBean response;

    public static class ResponseBean {
        /**
         * tel : null
         * pwd : null
         */

        public String tel;
        public String pwd;
    }
}
