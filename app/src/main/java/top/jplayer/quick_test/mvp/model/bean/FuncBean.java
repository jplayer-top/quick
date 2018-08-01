package top.jplayer.quick_test.mvp.model.bean;

import java.util.List;

import top.jplayer.baseprolibrary.mvp.model.bean.BaseBean;

/**
 * Created by Obl on 2018/7/25.
 * top.jplayer.quick_test.mvp.model.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class FuncBean extends BaseBean {

    /**
     * response : {"type":[{"typeTitle":"Dialog","typeUrl":"http://file.jplayer.top/image/icon01.png"},{"typeTitle":"弹窗","typeUrl":"http://file.jplayer.top/image/icon02.png"}]}
     */

    public ResponseBean response;

    public static class ResponseBean {
        public List<TypeBean> type;

        public static class TypeBean {
            /**
             * typeTitle : Dialog
             * typeUrl : http://file.jplayer.top/image/icon01.png
             */

            public String typeTitle;
            public String typeUrl;
            public String typeClass;
        }
    }
}
