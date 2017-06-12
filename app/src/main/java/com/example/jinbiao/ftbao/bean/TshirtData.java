package com.example.jinbiao.ftbao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-6-12.
 */

public class TshirtData {

    /**
     * data : [{"imgurl":"http://d9.yihaodianimg.com/N07/M03/4B/2A/ChEbvFU2Cu2AHKWyAAGORN4XvAA00800_230*322.jpg","goodcomment":"95%","commentcount":"20","proinfo":"Semir/森马 2015夏装新款短袖t恤男休闲圆领字母星空t恤衫打底衫男装潮1y0214001301(中花灰0017)","price":"￥19.9","storename":"森马官方旗舰店","id":10,"proact":"6.11-6.19专区满199立减100"},{"imgurl":"http://d7.yihaodianimg.com/N10/M09/1D/13/ChEi2lkegAyAaJASAAbh5wy4OJI78200_230*322.jpg","goodcomment":"100%","commentcount":"101","proinfo":"广告 Happy time/幸福时光 T恤男 2017夏季新款男士T恤潮流韩版修身T恤男","price":"￥68.0","storename":"京帅服饰专营店","id":11,"proact":""},{"imgurl":"http://d8.yihaodianimg.com/N09/M02/7B/1E/ChEi2FjuSGyAa4ikAAdZrTjUJOk70700_230*322.jpg","goodcomment":"100%","commentcount":"70","proinfo":"圣晟 短袖T恤POLO衫2017夏季新品男士休闲吸汗舒适翻领大码t恤潮流拼色直筒打底衫(藏青色)","price":"￥89","storename":"圣晟旗舰店","id":12,"proact":""},{"imgurl":"http://d9.yihaodianimg.com/N09/M00/66/F5/ChEi11ZgAsOAcHI8AAFeQhEe01420600_230*322.jpg","goodcomment":"98%","commentcount":"50","proinfo":"Jeanswest/真维斯 夏装 男装立领条纹牛角短袖T恤JW-53-173507(2065 淡灰)","price":"￥90","storename":"真维斯官方旗舰店","id":13,"proact":""},{"imgurl":"http://d8.yihaodianimg.com/N10/M02/65/AF/ChEi3FcZuZyACBOBAAZIerfcdEI97000_230*322.jpg","goodcomment":"100%","commentcount":"5","proinfo":"美特斯邦威 美国队长系列夏装新款男MEE漫威系列前整片印花短袖恤(黑色组)","price":"￥59","storename":"美特斯邦威官方旗舰店","id":14,"proact":"\u201c威\u201d力引爆夏天 满199减50"},{"imgurl":"http://d6.yihaodianimg.com/N09/M04/BE/0E/ChEi2FkJ2WaAVRVgAAWwDRPMt4s90700_230*322.jpg","goodcomment":"100%","commentcount":"64","proinfo":"寓语 2017夏装新品男士短袖t恤时尚迷彩青少年纯色圆领t恤修身时尚大码简约t恤(藏青色)","price":"￥89","storename":"寓语旗舰店","id":15,"proact":""},{"imgurl":"http://d8.yihaodianimg.com/N09/M04/B2/C5/ChEi2FkHHIuAKnmvAAb0dba1DEU46600_230*322.jpg","goodcomment":"98%","commentcount":"99","proinfo":"广告 Happy time/幸福时光 T恤男2017春季新款男士纯色t恤修身时尚休闲T恤男","price":"￥68.0","storename":"京帅服饰专营店","id":16,"proact":""},{"imgurl":"http://d6.yihaodianimg.com/N10/M08/00/2E/ChEi2lkP8W6AHy6hAAN4HCTmDas79700_230*322.jpg","goodcomment":"100%","commentcount":"2","proinfo":"Lee 【618提前购】【情侣款】夏季新款 简洁版LOGO装饰 男士圆领短袖T恤 时尚潮流女士棉质T恤(L250232LQK11黑男)","price":"￥149","storename":"LEE专卖店","id":17,"proact":"领券 满300减100 满600减200"},{"imgurl":"http://d9.yihaodianimg.com/V00/M0B/69/32/CgQDslUZ_XaABlt2AAPEP8yD9gE10400_230*322.jpg","goodcomment":"100%","commentcount":"2","proinfo":"HANRI'S瀚瑞 中老年男士夏季新款鸡心领短袖T恤透气棉套头衫印花运动休闲男装LML2096(深咖绿)","price":"￥79","storename":"瀚瑞服饰旗舰店","id":18,"proact":"年中盛典，3件8折！加15元换购！"},{"imgurl":"http://d8.yihaodianimg.com/N10/M04/D8/9C/ChEi21juS0GAbzfXAAV8zTW8dWM13100_230*322.jpg","goodcomment":"100%","commentcount":"114","proinfo":"圣晟 2017夏季新款男装简约短袖t恤针织圆领纯棉潮流海魂衫条纹青少年加大码T恤(白色)","price":"￥98","storename":"圣晟旗舰店","id":19,"proact":""}]
     * message : success
     */

    private String message;
    private List<Tshirt> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Tshirt> getData() {
        return data;
    }

    public void setData(List<Tshirt> data) {
        this.data = data;
    }

    public static class Tshirt {
        /**
         * imgurl : http://d9.yihaodianimg.com/N07/M03/4B/2A/ChEbvFU2Cu2AHKWyAAGORN4XvAA00800_230*322.jpg
         * goodcomment : 95%
         * commentcount : 20
         * proinfo : Semir/森马 2015夏装新款短袖t恤男休闲圆领字母星空t恤衫打底衫男装潮1y0214001301(中花灰0017)
         * price : ￥19.9
         * storename : 森马官方旗舰店
         * id : 10
         * proact : 6.11-6.19专区满199立减100
         */

        private String imgurl;
        private String goodcomment;
        private String commentcount;
        private String proinfo;
        private String price;
        private String storename;
        private int id;
        private String proact;

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getGoodcomment() {
            return goodcomment;
        }

        public void setGoodcomment(String goodcomment) {
            this.goodcomment = goodcomment;
        }

        public String getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(String commentcount) {
            this.commentcount = commentcount;
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

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProact() {
            return proact;
        }

        public void setProact(String proact) {
            this.proact = proact;
        }

        @Override
        public String toString() {
            return "Tshirt{" +
                    "imgurl='" + imgurl + '\'' +
                    ", goodcomment='" + goodcomment + '\'' +
                    ", commentcount='" + commentcount + '\'' +
                    ", proinfo='" + proinfo + '\'' +
                    ", price='" + price + '\'' +
                    ", storename='" + storename + '\'' +
                    ", id=" + id +
                    ", proact='" + proact + '\'' +
                    '}';
        }
    }
}
