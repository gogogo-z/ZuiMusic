package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2017/1/3.
 * email : zenghuanxing123@163.com
 */
public class NewMusicBean {

    /**
     * error_code : 22000
     * content : [{"title":"歌曲推荐","song_list":[{"artist_id":"130","pic_big":"http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg@s_0,w_150","pic_small":"http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg@s_0,w_90","pic_premium":"http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg@s_0,w_500","pic_huge":"http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg","pic_singer":"","all_artist_ting_uid":"1100","file_duration":"277","del_status":"0","resource_type":"0","all_rate":"24,64,128,192,256,320,flac","toneid":"600902000005286912","copy_type":"1","has_mv_mobile":1,"song_id":"7313983","title":"喜欢你","ting_uid":"1100","author":"Beyond","album_id":"7311104","album_title":"传奇再续","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"1100000000","desc":"","url":"http://music.baidu.com/song/7313983","recommend_reason":"以往片刻欢笑仍挂在脸上"},{},{},{},{},{}]}]
     */

    private int error_code;
    /**
     * title : 歌曲推荐
     * song_list : [{"artist_id":"130","pic_big":"http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg@s_0,w_150","pic_small":"http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg@s_0,w_90","pic_premium":"http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg@s_0,w_500","pic_huge":"http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg","pic_singer":"","all_artist_ting_uid":"1100","file_duration":"277","del_status":"0","resource_type":"0","all_rate":"24,64,128,192,256,320,flac","toneid":"600902000005286912","copy_type":"1","has_mv_mobile":1,"song_id":"7313983","title":"喜欢你","ting_uid":"1100","author":"Beyond","album_id":"7311104","album_title":"传奇再续","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"1100000000","desc":"","url":"http://music.baidu.com/song/7313983","recommend_reason":"以往片刻欢笑仍挂在脸上"},{},{},{},{},{}]
     */

    private List<ContentBean> content;

    public static NewMusicBean objectFromData(String str) {

        return new Gson().fromJson(str, NewMusicBean.class);
    }

    public static List<NewMusicBean> arrayHotMusicBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<NewMusicBean>>() {
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
        private String title;
        /**
         * artist_id : 130
         * pic_big : http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg@s_0,w_150
         * pic_small : http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg@s_0,w_90
         * pic_premium : http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg@s_0,w_500
         * pic_huge : http://musicdata.baidu.com/data2/music/1805DC21398BAF2A5723BCC7956D8D15/252492173/252492173.jpg
         * pic_singer :
         * all_artist_ting_uid : 1100
         * file_duration : 277
         * del_status : 0
         * resource_type : 0
         * all_rate : 24,64,128,192,256,320,flac
         * toneid : 600902000005286912
         * copy_type : 1
         * has_mv_mobile : 1
         * song_id : 7313983
         * title : 喜欢你
         * ting_uid : 1100
         * author : Beyond
         * album_id : 7311104
         * album_title : 传奇再续
         * is_first_publish : 0
         * havehigh : 2
         * charge : 0
         * has_mv : 0
         * learn : 0
         * song_source : web
         * piao_id : 0
         * korean_bb_song : 0
         * resource_type_ext : 0
         * mv_provider : 1100000000
         * desc :
         * url : http://music.baidu.com/song/7313983
         * recommend_reason : 以往片刻欢笑仍挂在脸上
         */

        private List<SongListBean> song_list;

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

        public List<SongListBean> getSong_list() {
            return song_list;
        }

        public void setSong_list(List<SongListBean> song_list) {
            this.song_list = song_list;
        }

    }

    public static class SongListBean implements Serializable{
        private String artist_id;
        private String pic_big;
        private String pic_small;
        private String pic_premium;
        private String pic_huge;
        private String pic_singer;
        private String all_artist_ting_uid;
        private String file_duration;
        private String del_status;
        private String resource_type;
        private String all_rate;
        private String toneid;
        private String copy_type;
        private int has_mv_mobile;
        private String song_id;
        private String title;
        private String ting_uid;
        private String author;
        private String album_id;
        private String album_title;
        private int is_first_publish;
        private int havehigh;
        private int charge;
        private int has_mv;
        private int learn;
        private String song_source;
        private String piao_id;
        private String korean_bb_song;
        private String resource_type_ext;
        private String mv_provider;
        private String desc;
        private String url;
        private String recommend_reason;

        public static SongListBean objectFromData(String str) {

            return new Gson().fromJson(str, SongListBean.class);
        }

        public static List<SongListBean> arraySongListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<SongListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getPic_premium() {
            return pic_premium;
        }

        public void setPic_premium(String pic_premium) {
            this.pic_premium = pic_premium;
        }

        public String getPic_huge() {
            return pic_huge;
        }

        public void setPic_huge(String pic_huge) {
            this.pic_huge = pic_huge;
        }

        public String getPic_singer() {
            return pic_singer;
        }

        public void setPic_singer(String pic_singer) {
            this.pic_singer = pic_singer;
        }

        public String getAll_artist_ting_uid() {
            return all_artist_ting_uid;
        }

        public void setAll_artist_ting_uid(String all_artist_ting_uid) {
            this.all_artist_ting_uid = all_artist_ting_uid;
        }

        public String getFile_duration() {
            return file_duration;
        }

        public void setFile_duration(String file_duration) {
            this.file_duration = file_duration;
        }

        public String getDel_status() {
            return del_status;
        }

        public void setDel_status(String del_status) {
            this.del_status = del_status;
        }

        public String getResource_type() {
            return resource_type;
        }

        public void setResource_type(String resource_type) {
            this.resource_type = resource_type;
        }

        public String getAll_rate() {
            return all_rate;
        }

        public void setAll_rate(String all_rate) {
            this.all_rate = all_rate;
        }

        public String getToneid() {
            return toneid;
        }

        public void setToneid(String toneid) {
            this.toneid = toneid;
        }

        public String getCopy_type() {
            return copy_type;
        }

        public void setCopy_type(String copy_type) {
            this.copy_type = copy_type;
        }

        public int getHas_mv_mobile() {
            return has_mv_mobile;
        }

        public void setHas_mv_mobile(int has_mv_mobile) {
            this.has_mv_mobile = has_mv_mobile;
        }

        public String getSong_id() {
            return song_id;
        }

        public void setSong_id(String song_id) {
            this.song_id = song_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTing_uid() {
            return ting_uid;
        }

        public void setTing_uid(String ting_uid) {
            this.ting_uid = ting_uid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public int getIs_first_publish() {
            return is_first_publish;
        }

        public void setIs_first_publish(int is_first_publish) {
            this.is_first_publish = is_first_publish;
        }

        public int getHavehigh() {
            return havehigh;
        }

        public void setHavehigh(int havehigh) {
            this.havehigh = havehigh;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public int getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(int has_mv) {
            this.has_mv = has_mv;
        }

        public int getLearn() {
            return learn;
        }

        public void setLearn(int learn) {
            this.learn = learn;
        }

        public String getSong_source() {
            return song_source;
        }

        public void setSong_source(String song_source) {
            this.song_source = song_source;
        }

        public String getPiao_id() {
            return piao_id;
        }

        public void setPiao_id(String piao_id) {
            this.piao_id = piao_id;
        }

        public String getKorean_bb_song() {
            return korean_bb_song;
        }

        public void setKorean_bb_song(String korean_bb_song) {
            this.korean_bb_song = korean_bb_song;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getMv_provider() {
            return mv_provider;
        }

        public void setMv_provider(String mv_provider) {
            this.mv_provider = mv_provider;
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

        public String getRecommend_reason() {
            return recommend_reason;
        }

        public void setRecommend_reason(String recommend_reason) {
            this.recommend_reason = recommend_reason;
        }
    }
}
