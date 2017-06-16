package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2017/2/5.
 * email : zenghuanxing123@163.com
 */
public class SongSheetDetail {

    /**
     * error_code : 22000
     * listid : 7096
     * title : 自然的旋律，麦田的香气
     * pic_300 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_8f8c12f97252f5e69f1aa0a090e5da3d.jpg
     * pic_500 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_8f8c12f97252f5e69f1aa0a090e5da3d.jpg
     * pic_w700 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_8f8c12f97252f5e69f1aa0a090e5da3d.jpg
     * width : 300
     * height : 300
     * listenum : 16804
     * collectnum : 458
     * tag : 乡村,清晨,咖啡厅
     * desc : 音乐是一份礼物，赐予你美好的体验
     * url : http://music.baidu.com/songlist/7096
     * content : [{"title":"One Hell Of An Amen","song_id":"238467936","author":"Brantley Gilbert","album_id":"238467859","album_title":"Read Me My Rights","relate_status":"0","is_charge":"0","all_rate":"24,64,128,192,256,320","high_rate":"320","all_artist_id":"17297416","copy_type":"1","has_mv":0,"toneid":"0","resource_type":"0","is_ksong":"0","resource_type_ext":"0","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","has_mv_mobile":0,"ting_uid":"87953886","is_first_publish":0,"havehigh":2,"charge":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","mv_provider":"0000000000","share":"http://music.baidu.com/song/238467936"},{},{},{},{},{},{},{},{},{},{},{},{},{}]
     */

    private int error_code;
    private String listid;
    private String title;
    private String pic_300;
    private String pic_500;
    private String pic_w700;
    private String width;
    private String height;
    private String listenum;
    private String collectnum;
    private String tag;
    private String desc;
    private String url;
    /**
     * title : One Hell Of An Amen
     * song_id : 238467936
     * author : Brantley Gilbert
     * album_id : 238467859
     * album_title : Read Me My Rights
     * relate_status : 0
     * is_charge : 0
     * all_rate : 24,64,128,192,256,320
     * high_rate : 320
     * all_artist_id : 17297416
     * copy_type : 1
     * has_mv : 0
     * toneid : 0
     * resource_type : 0
     * is_ksong : 0
     * resource_type_ext : 0
     * versions :
     * bitrate_fee : {"0":"0|0","1":"0|0"}
     * has_mv_mobile : 0
     * ting_uid : 87953886
     * is_first_publish : 0
     * havehigh : 2
     * charge : 0
     * learn : 0
     * song_source : web
     * piao_id : 0
     * korean_bb_song : 0
     * mv_provider : 0000000000
     * share : http://music.baidu.com/song/238467936
     */

    private List<ContentBean> content;

    public static SongSheetDetail objectFromData(String str) {

        return new Gson().fromJson(str, SongSheetDetail.class);
    }

    public static List<SongSheetDetail> arraySongSheetDetailFromData(String str) {

        Type listType = new TypeToken<ArrayList<SongSheetDetail>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_300() {
        return pic_300;
    }

    public void setPic_300(String pic_300) {
        this.pic_300 = pic_300;
    }

    public String getPic_500() {
        return pic_500;
    }

    public void setPic_500(String pic_500) {
        this.pic_500 = pic_500;
    }

    public String getPic_w700() {
        return pic_w700;
    }

    public void setPic_w700(String pic_w700) {
        this.pic_w700 = pic_w700;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

}
