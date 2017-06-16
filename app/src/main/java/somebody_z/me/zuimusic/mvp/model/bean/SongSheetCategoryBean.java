package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2017/2/15.
 * email : zenghuanxing123@163.com
 */
public class SongSheetCategoryBean {

    /**
     * error_code : 22000
     * content : [{},{},{},{},{},{}]
     */

    private int error_code;
    private List<ContentBean> content;

    public static SongSheetCategoryBean objectFromData(String str) {

        return new Gson().fromJson(str, SongSheetCategoryBean.class);
    }

    public static List<SongSheetCategoryBean> arraySongSheetCategoryBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<SongSheetCategoryBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * tags : [{"type":"gedan","tag":"华语"},{},{},{},{},{},{}]
         * title : 语种
         * num : 7
         */

        private String title;
        private int num;
        /**
         * type : gedan
         * tag : 华语
         */

        private List<TagsBean> tags;

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

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

    }

    public static class TagsBean {
        private String type;
        private String tag;

        public TagsBean(String type, String tag) {
            this.type = type;
            this.tag = tag;
        }

        public static TagsBean objectFromData(String str) {

            return new Gson().fromJson(str, TagsBean.class);
        }

        public static List<TagsBean> arrayTagsBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<TagsBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
