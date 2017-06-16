package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2017/1/3.
 * email : zenghuanxing123@163.com
 */
public class AnchorRadioBean {


    /**
     * error_code : 22000
     * list : [{"channelid":"11373553","itemid":"13399216","album_id":"8107509","title":"祝你幸福","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_250f3cfa9589fe9b60348668b8c22c3f.jpg","type":"lebo","desc":"都市情感"},{},{},{},{},{}]
     */

    private int error_code;
    /**
     * channelid : 11373553
     * itemid : 13399216
     * album_id : 8107509
     * title : 祝你幸福
     * pic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_250f3cfa9589fe9b60348668b8c22c3f.jpg
     * type : lebo
     * desc : 都市情感
     */

    private List<RadioList> list;

    public static AnchorRadioBean objectFromData(String str) {

        return new Gson().fromJson(str, AnchorRadioBean.class);
    }

    public static List<AnchorRadioBean> arrayAnchorRadioBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AnchorRadioBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<RadioList> getList() {
        return list;
    }

    public void setList(List<RadioList> list) {
        this.list = list;
    }

    public static class RadioList {
        private String channelid;
        private String itemid;
        private String album_id;
        private String title;
        private String pic;
        private String type;
        private String desc;

        public static RadioList objectFromData(String str) {

            return new Gson().fromJson(str, RadioList.class);
        }

        public static List<RadioList> arrayRadioListFromData(String str) {

            Type listType = new TypeToken<ArrayList<RadioList>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getChannelid() {
            return channelid;
        }

        public void setChannelid(String channelid) {
            this.channelid = channelid;
        }

        public String getItemid() {
            return itemid;
        }

        public void setItemid(String itemid) {
            this.itemid = itemid;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
