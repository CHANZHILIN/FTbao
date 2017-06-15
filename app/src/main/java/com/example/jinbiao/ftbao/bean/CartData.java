package com.example.jinbiao.ftbao.bean;

import java.util.List;

/**
 * Created by CHEN_ on 2017/6/14.
 */

public class CartData {

    /**
     * data : [{"imgurl":"http://d9.yihaodianimg.com/V00/M0B/69/32/CgQDslUZ_XaABlt2AAPEP8yD9gE10400_230*322.jpg","proinfo":"HANRI'S瀚瑞 中老年男士夏季新款鸡心领短袖T恤透气棉套头衫印花运动休闲男装LML2096(深咖绿","price":"￥79","count":1,"storename":"瀚瑞服饰旗舰店","proact":"年中盛典，3件8折！加15元换购！","cid":1}]
     * message : success
     */

    private String message;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * imgurl : http://d9.yihaodianimg.com/V00/M0B/69/32/CgQDslUZ_XaABlt2AAPEP8yD9gE10400_230*322.jpg
         * proinfo : HANRI'S瀚瑞 中老年男士夏季新款鸡心领短袖T恤透气棉套头衫印花运动休闲男装LML2096(深咖绿
         * price : ￥79
         * count : 1
         * storename : 瀚瑞服饰旗舰店
         * proact : 年中盛典，3件8折！加15元换购！
         * cid : 1
         */

        private String imgurl;
        private String proinfo;
        private String price;
        private int count;
        private String storename;
        private String proact;
        private int cid;

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getProinfo() {
            return proinfo;
        }

        public void setProinfo(String proinfo) {
            this.proinfo = proinfo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public String getProact() {
            return proact;
        }

        public void setProact(String proact) {
            this.proact = proact;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }
    }
}
