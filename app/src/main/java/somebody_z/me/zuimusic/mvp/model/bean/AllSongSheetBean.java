package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2017/1/9.
 * email : zenghuanxing123@163.com
 */
public class AllSongSheetBean {

    /**
     * havemore : 1
     * content : [{"width":"300","tag":"欧美,经典,怀旧","pic_300":"http://musicugc.cdn.qianqian.com/ugcdiy/pic/c63ceda389579980e8964a6494668582.jpg","desc":"聆听环球旗下音乐巨星们的经典演绎，这些跨越了一个世纪的珍贵声音，将历久弥新的成为永恒的经典。","pic_w300":"http://musicugc.cdn.qianqian.com/ugcdiy/pic/c63ceda389579980e8964a6494668582.jpg","title":"【环球之音】世界最伟大的不朽名曲","collectnum":"207","listenum":"27678","height":"300","listid":"364160155"},{},{},{},{},{},{},{},{},{}]
     * error_code : 22000
     * total : 943
     */

    private int havemore;
    private int error_code;
    private int total;
    /**
     * width : 300
     * tag : 欧美,经典,怀旧
     * pic_300 : http://musicugc.cdn.qianqian.com/ugcdiy/pic/c63ceda389579980e8964a6494668582.jpg
     * desc : 聆听环球旗下音乐巨星们的经典演绎，这些跨越了一个世纪的珍贵声音，将历久弥新的成为永恒的经典。
     * pic_w300 : http://musicugc.cdn.qianqian.com/ugcdiy/pic/c63ceda389579980e8964a6494668582.jpg
     * title : 【环球之音】世界最伟大的不朽名曲
     * collectnum : 207
     * listenum : 27678
     * height : 300
     * listid : 364160155
     */

    private List<ContentBean> content;

    public static AllSongSheetBean objectFromData(String str) {

        return new Gson().fromJson(str, AllSongSheetBean.class);
    }

    public static List<AllSongSheetBean> arraySongSheetBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AllSongSheetBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getHavemore() {
        return havemore;
    }

    public void setHavemore(int havemore) {
        this.havemore = havemore;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String width;
        private String tag;
        private String pic_300;
        private String desc;
        private String pic_w300;
        private String title;
        private String collectnum;
        private String listenum;
        private String height;
        private String listid;

        public static ContentBean objectFromData(String str) {

            return new Gson().fromJson(str, ContentBean.class);
        }

        public static List<ContentBean> arrayContentBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ContentBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getPic_300() {
            return pic_300;
        }

        public void setPic_300(String pic_300) {
            this.pic_300 = pic_300;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPic_w300() {
            return pic_w300;
        }

        public void setPic_w300(String pic_w300) {
            this.pic_w300 = pic_w300;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCollectnum() {
            return collectnum;
        }

        public void setCollectnum(String collectnum) {
            this.collectnum = collectnum;
        }

        public String getListenum() {
            return listenum;
        }

        public void setListenum(String listenum) {
            this.listenum = listenum;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getListid() {
            return listid;
        }

        public void setListid(String listid) {
            this.listid = listid;
        }
    }
}
