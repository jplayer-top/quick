package top.jplayer.baseprolibrary.mvp.model.bean;

/**
 * Created by Obl on 2018/8/23.
 * top.jplayer.baseprolibrary.mvp.model.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class LocationBean extends BaseBean {
    /**
     * response : {"address":"CN|山东|青岛|None|CHINANET|0|0","content":{"address":"山东省青岛市","address_detail":{"city":"青岛市","city_code":236,"district":"","province":"山东省","street":"","street_number":""},"point":{"x":"120.38442818","y":"36.10521490"}},"status":0}
     * ip : 182.40.40.218
     */

    public ResponseBean response;
    public String ip;

    public static class ResponseBean {
        /**
         * address : CN|山东|青岛|None|CHINANET|0|0
         * content : {"address":"山东省青岛市","address_detail":{"city":"青岛市","city_code":236,"district":"","province":"山东省","street":"","street_number":""},"point":{"x":"120.38442818","y":"36.10521490"}}
         * status : 0
         */

        public String address;
        public ContentBean content;
        public int status;

        public static class ContentBean {
            /**
             * address : 山东省青岛市
             * address_detail : {"city":"青岛市","city_code":236,"district":"","province":"山东省","street":"","street_number":""}
             * point : {"x":"120.38442818","y":"36.10521490"}
             */

            public String address;
            public AddressDetailBean address_detail;
            public PointBean point;

            public static class AddressDetailBean {
                /**
                 * city : 青岛市
                 * city_code : 236
                 * district :
                 * province : 山东省
                 * street :
                 * street_number :
                 */

                public String city;
                public int city_code;
                public String district;
                public String province;
                public String street;
                public String street_number;
            }

            public static class PointBean {
                /**
                 * x : 120.38442818
                 * y : 36.10521490
                 */

                public String x;
                public String y;
            }
        }
    }
}
