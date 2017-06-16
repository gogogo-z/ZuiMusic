package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2017/2/14.
 * email : zenghuanxing123@163.com
 */
public class RankDetailBean {


    /**
     * billboard_type : 1
     * billboard_no : 2105
     * update_date : 2017-02-14
     * billboard_songnum : 188
     * havemore : 1
     * name : 新歌榜
     * comment : 该榜单是根据百度音乐平台歌曲每日播放量自动生成的数据榜单，统计范围为近期发行的歌曲，每日更新一次
     * pic_s640 : http://c.hiphotos.baidu.com/ting/pic/item/f7246b600c33874495c4d089530fd9f9d62aa0c6.jpg
     * pic_s444 : http://d.hiphotos.baidu.com/ting/pic/item/78310a55b319ebc4845c84eb8026cffc1e17169f.jpg
     * pic_s260 : http://b.hiphotos.baidu.com/ting/pic/item/e850352ac65c1038cb0f3cb0b0119313b07e894b.jpg
     * pic_s210 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_c49310115801d43d42a98fdc357f6057.jpg
     * web_url : http://music.baidu.com/top/new
     */

    private BillboardBean billboard;
    /**
     * song_list : [{"artist_id":"57297","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/522767550/f35e3b11b1a8b14afe8c02688e48502c/522767550.jpg@s_0,w_150","pic_small":"http://musicdata.baidu.com/data2/pic/522767550/f35e3b11b1a8b14afe8c02688e48502c/522767550.jpg@s_0,w_90","country":"内地","area":"0","publishtime":"2017-01-11","album_no":"5","lrclink":"http://musicdata.baidu.com/data2/lrc/84d58dd3489dbb1c21ed445ef3edd6cb/277389542/277389542.lrc","copy_type":"1","hot":"527776","all_artist_ting_uid":"245815","resource_type":"0","is_new":"0","rank_change":"0","rank":"2","all_artist_id":"57297","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320,flac","file_duration":205,"has_mv_mobile":0,"versions":"录音室版","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_id":"277389316","title":"一生为你感动","author":"祁隆","album_title":"老父亲","havehigh":2,"charge":0,"learn":0,"song_source":"web","korean_bb_song":"0","ting_uid":"245815","album_id":"256028619","is_first_publish":0,"has_mv":0,"piao_id":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"祁隆"},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]
     * billboard : {"billboard_type":"1","billboard_no":"2105","update_date":"2017-02-14","billboard_songnum":"188","havemore":1,"name":"新歌榜","comment":"该榜单是根据百度音乐平台歌曲每日播放量自动生成的数据榜单，统计范围为近期发行的歌曲，每日更新一次","pic_s640":"http://c.hiphotos.baidu.com/ting/pic/item/f7246b600c33874495c4d089530fd9f9d62aa0c6.jpg","pic_s444":"http://d.hiphotos.baidu.com/ting/pic/item/78310a55b319ebc4845c84eb8026cffc1e17169f.jpg","pic_s260":"http://b.hiphotos.baidu.com/ting/pic/item/e850352ac65c1038cb0f3cb0b0119313b07e894b.jpg","pic_s210":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_c49310115801d43d42a98fdc357f6057.jpg","web_url":"http://music.baidu.com/top/new"}
     * error_code : 22000
     */

    private int error_code;
    /**
     * artist_id : 57297
     * language : 国语
     * pic_big : http://musicdata.baidu.com/data2/pic/522767550/f35e3b11b1a8b14afe8c02688e48502c/522767550.jpg@s_0,w_150
     * pic_small : http://musicdata.baidu.com/data2/pic/522767550/f35e3b11b1a8b14afe8c02688e48502c/522767550.jpg@s_0,w_90
     * country : 内地
     * area : 0
     * publishtime : 2017-01-11
     * album_no : 5
     * lrclink : http://musicdata.baidu.com/data2/lrc/84d58dd3489dbb1c21ed445ef3edd6cb/277389542/277389542.lrc
     * copy_type : 1
     * hot : 527776
     * all_artist_ting_uid : 245815
     * resource_type : 0
     * is_new : 0
     * rank_change : 0
     * rank : 2
     * all_artist_id : 57297
     * style : 流行
     * del_status : 0
     * relate_status : 0
     * toneid : 0
     * all_rate : 64,128,256,320,flac
     * file_duration : 205
     * has_mv_mobile : 0
     * versions : 录音室版
     * bitrate_fee : {"0":"0|0","1":"0|0"}
     * song_id : 277389316
     * title : 一生为你感动
     * author : 祁隆
     * album_title : 老父亲
     * havehigh : 2
     * charge : 0
     * learn : 0
     * song_source : web
     * korean_bb_song : 0
     * ting_uid : 245815
     * album_id : 256028619
     * is_first_publish : 0
     * has_mv : 0
     * piao_id : 0
     * resource_type_ext : 0
     * mv_provider : 0000000000
     * artist_name : 祁隆
     */

    private List<ContentBean> song_list;

    public static RankDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, RankDetailBean.class);
    }

    public static List<RankDetailBean> arrayRankDetailBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RankDetailBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public BillboardBean getBillboard() {
        return billboard;
    }

    public void setBillboard(BillboardBean billboard) {
        this.billboard = billboard;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ContentBean> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<ContentBean> song_list) {
        this.song_list = song_list;
    }

    public static class BillboardBean {
        private String billboard_type;
        private String billboard_no;
        private String update_date;
        private String billboard_songnum;
        private int havemore;
        private String name;
        private String comment;
        private String pic_s640;
        private String pic_s444;
        private String pic_s260;
        private String pic_s210;
        private String web_url;

        public static BillboardBean objectFromData(String str) {

            return new Gson().fromJson(str, BillboardBean.class);
        }

        public static List<BillboardBean> arrayBillboardBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<BillboardBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getBillboard_type() {
            return billboard_type;
        }

        public void setBillboard_type(String billboard_type) {
            this.billboard_type = billboard_type;
        }

        public String getBillboard_no() {
            return billboard_no;
        }

        public void setBillboard_no(String billboard_no) {
            this.billboard_no = billboard_no;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getBillboard_songnum() {
            return billboard_songnum;
        }

        public void setBillboard_songnum(String billboard_songnum) {
            this.billboard_songnum = billboard_songnum;
        }

        public int getHavemore() {
            return havemore;
        }

        public void setHavemore(int havemore) {
            this.havemore = havemore;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPic_s640() {
            return pic_s640;
        }

        public void setPic_s640(String pic_s640) {
            this.pic_s640 = pic_s640;
        }

        public String getPic_s444() {
            return pic_s444;
        }

        public void setPic_s444(String pic_s444) {
            this.pic_s444 = pic_s444;
        }

        public String getPic_s260() {
            return pic_s260;
        }

        public void setPic_s260(String pic_s260) {
            this.pic_s260 = pic_s260;
        }

        public String getPic_s210() {
            return pic_s210;
        }

        public void setPic_s210(String pic_s210) {
            this.pic_s210 = pic_s210;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }

}
