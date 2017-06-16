package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2017/1/1.
 * email : zenghuanxing123@163.com
 */
public class RecommendSongSheetBean {

    /**
     * error_code : 22000
     * content : {"title":"热门歌单","list":[{"listid":"7259","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_5f2ecb9b02e287b82e12b84a6c93bbdf.jpg","listenum":"22944","collectnum":"336","title":"东海音乐节，去海边沙滩听音乐","tag":"流行,摇滚,民谣","type":"gedan"},{},{},{},{},{}]}
     */

    private int error_code;
    /**
     * title : 热门歌单
     * list : [{"listid":"7259","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_5f2ecb9b02e287b82e12b84a6c93bbdf.jpg","listenum":"22944","collectnum":"336","title":"东海音乐节，去海边沙滩听音乐","tag":"流行,摇滚,民谣","type":"gedan"},{},{},{},{},{}]
     */

    private ContentBean content;

    public static RecommendSongSheetBean objectFromData(String str) {

        return new Gson().fromJson(str, RecommendSongSheetBean.class);
    }

    public static List<RecommendSongSheetBean> arrayRecommendSongSheetBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RecommendSongSheetBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private String title;
        /**
         * listid : 7259
         * pic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_5f2ecb9b02e287b82e12b84a6c93bbdf.jpg
         * listenum : 22944
         * collectnum : 336
         * title : 东海音乐节，去海边沙滩听音乐
         * tag : 流行,摇滚,民谣
         * type : gedan
         */

        private List<SongSheetListBean> list;

        public static ContentBean objectFromData(String str) {

            return new Gson().fromJson(str, ContentBean.class);
        }

        public static List<ContentBean> arrayContentBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ContentBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SongSheetListBean> getList() {
            return list;
        }

        public void setList(List<SongSheetListBean> list) {
            this.list = list;
        }

    }

    public static class SongSheetListBean {
        private String listid;
        private String pic;
        private String listenum;
        private String collectnum;
        private String title;
        private String tag;
        private String type;

        public static SongSheetListBean objectFromData(String str) {

            return new Gson().fromJson(str, SongSheetListBean.class);
        }

        public static List<SongSheetListBean> arrayListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<SongSheetListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getListid() {
            return listid;
        }

        public void setListid(String listid) {
            this.listid = listid;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getListenum() {
            return listenum;
        }

        public void setListenum(String listenum) {
            this.listenum = listenum;
        }

        public String getCollectnum() {
            return collectnum;
        }

        public void setCollectnum(String collectnum) {
            this.collectnum = collectnum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
