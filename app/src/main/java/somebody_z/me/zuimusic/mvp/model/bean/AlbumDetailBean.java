package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class AlbumDetailBean {


    /**
     * album_id : 533282400
     * author : 洛天依
     * title : 元宵吃货节
     * publishcompany : 海蝶音乐
     * prodcompany : null
     * country : 内地
     * language : 国语
     * songs_total : 1
     * info : 洛天依完美演绎新曲《元宵吃货节》首
     * styles :
     * style_id : null
     * publishtime : 2017-02-10
     * artist_ting_uid : 90655321
     * all_artist_ting_uid : null
     * gender : 3
     * area : 4
     * pic_small : http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_90
     * pic_big : http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_150
     * hot :
     * favorites_num : 0
     * recommend_num : 0
     * collect_num : 78
     * share_num : 10
     * comment_num : 1
     * artist_id : 38945624
     * all_artist_id : 38945624
     * pic_radio : http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_300
     * pic_s500 : http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_500
     * pic_s1000 : http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_1000
     * ai_presale_flag : 0
     * resource_type_ext : 0
     * listen_num : 1066
     * buy_url :
     */

    private AlbumInfoBean albumInfo;
    /**
     * albumInfo : {"album_id":"533282400","author":"洛天依","title":"元宵吃货节","publishcompany":"海蝶音乐","prodcompany":null,"country":"内地","language":"国语","songs_total":"1","info":"洛天依完美演绎新曲《元宵吃货节》首","styles":"","style_id":null,"publishtime":"2017-02-10","artist_ting_uid":"90655321","all_artist_ting_uid":null,"gender":"3","area":"4","pic_small":"http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_90","pic_big":"http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_150","hot":"","favorites_num":0,"recommend_num":0,"collect_num":78,"share_num":10,"comment_num":1,"artist_id":"38945624","all_artist_id":"38945624","pic_radio":"http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_300","pic_s500":"http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_500","pic_s1000":"http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_1000","ai_presale_flag":"0","resource_type_ext":"0","listen_num":"1066","buy_url":""}
     * songlist : [{"artist_id":"38945624","all_artist_id":"38945624","all_artist_ting_uid":"90655321","language":"国语","publishtime":"2017-02-10","album_no":"0","versions":"","pic_big":"http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_150","pic_small":"http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_90","hot":"1066","file_duration":"120","del_status":"0","resource_type":"0","copy_type":"1","has_mv_mobile":0,"all_rate":"64,128,256,320,flac","toneid":"0","country":"内地","area":"0","lrclink":"http://musicdata.baidu.com/data2/lrc/cd3cd2038dd311f4ce3c22835a400c65/533283725/533283725.lrc","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","si_presale_flag":"0","song_id":"533283728","title":"元宵吃货节","ting_uid":"90655321","author":"洛天依","album_id":"533282400","album_title":"元宵吃货节","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000"}]
     * is_collect : 0
     */

    private int is_collect;
    /**
     * artist_id : 38945624
     * all_artist_id : 38945624
     * all_artist_ting_uid : 90655321
     * language : 国语
     * publishtime : 2017-02-10
     * album_no : 0
     * versions :
     * pic_big : http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_150
     * pic_small : http://musicdata.baidu.com/data2/pic/43622f6b01eb610ffae726b854375f6a/533282397/533282397.jpg@s_0,w_90
     * hot : 1066
     * file_duration : 120
     * del_status : 0
     * resource_type : 0
     * copy_type : 1
     * has_mv_mobile : 0
     * all_rate : 64,128,256,320,flac
     * toneid : 0
     * country : 内地
     * area : 0
     * lrclink : http://musicdata.baidu.com/data2/lrc/cd3cd2038dd311f4ce3c22835a400c65/533283725/533283725.lrc
     * bitrate_fee : {"0":"0|0","1":"0|0"}
     * si_presale_flag : 0
     * song_id : 533283728
     * title : 元宵吃货节
     * ting_uid : 90655321
     * author : 洛天依
     * album_id : 533282400
     * album_title : 元宵吃货节
     * is_first_publish : 0
     * havehigh : 2
     * charge : 0
     * has_mv : 0
     * learn : 0
     * song_source : web
     * piao_id : 0
     * korean_bb_song : 0
     * resource_type_ext : 0
     * mv_provider : 0000000000
     */

    private List<SonglistBean> songlist;

    public static AlbumDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, AlbumDetailBean.class);
    }

    public static List<AlbumDetailBean> arrayAlbumDetailBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AlbumDetailBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public AlbumInfoBean getAlbumInfo() {
        return albumInfo;
    }

    public void setAlbumInfo(AlbumInfoBean albumInfo) {
        this.albumInfo = albumInfo;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public List<SonglistBean> getSonglist() {
        return songlist;
    }

    public void setSonglist(List<SonglistBean> songlist) {
        this.songlist = songlist;
    }

    public static class AlbumInfoBean {
        private String album_id;
        private String author;
        private String title;
        private String publishcompany;
        private Object prodcompany;
        private String country;
        private String language;
        private String songs_total;
        private String info;
        private String styles;
        private Object style_id;
        private String publishtime;
        private String artist_ting_uid;
        private Object all_artist_ting_uid;
        private String gender;
        private String area;
        private String pic_small;
        private String pic_big;
        private String hot;
        private int favorites_num;
        private int recommend_num;
        private int collect_num;
        private int share_num;
        private int comment_num;
        private String artist_id;
        private String all_artist_id;
        private String pic_radio;
        private String pic_s500;
        private String pic_s1000;
        private String ai_presale_flag;
        private String resource_type_ext;
        private String listen_num;
        private String buy_url;

        public AlbumInfoBean(String album_id, String author, String title, String pic_big) {
            this.album_id = album_id;
            this.author = author;
            this.title = title;
            this.pic_big = pic_big;
        }

        public static AlbumInfoBean objectFromData(String str) {

            return new Gson().fromJson(str, AlbumInfoBean.class);
        }

        public static List<AlbumInfoBean> arrayAlbumInfoBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<AlbumInfoBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublishcompany() {
            return publishcompany;
        }

        public void setPublishcompany(String publishcompany) {
            this.publishcompany = publishcompany;
        }

        public Object getProdcompany() {
            return prodcompany;
        }

        public void setProdcompany(Object prodcompany) {
            this.prodcompany = prodcompany;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getSongs_total() {
            return songs_total;
        }

        public void setSongs_total(String songs_total) {
            this.songs_total = songs_total;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getStyles() {
            return styles;
        }

        public void setStyles(String styles) {
            this.styles = styles;
        }

        public Object getStyle_id() {
            return style_id;
        }

        public void setStyle_id(Object style_id) {
            this.style_id = style_id;
        }

        public String getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(String publishtime) {
            this.publishtime = publishtime;
        }

        public String getArtist_ting_uid() {
            return artist_ting_uid;
        }

        public void setArtist_ting_uid(String artist_ting_uid) {
            this.artist_ting_uid = artist_ting_uid;
        }

        public Object getAll_artist_ting_uid() {
            return all_artist_ting_uid;
        }

        public void setAll_artist_ting_uid(Object all_artist_ting_uid) {
            this.all_artist_ting_uid = all_artist_ting_uid;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public int getFavorites_num() {
            return favorites_num;
        }

        public void setFavorites_num(int favorites_num) {
            this.favorites_num = favorites_num;
        }

        public int getRecommend_num() {
            return recommend_num;
        }

        public void setRecommend_num(int recommend_num) {
            this.recommend_num = recommend_num;
        }

        public int getCollect_num() {
            return collect_num;
        }

        public void setCollect_num(int collect_num) {
            this.collect_num = collect_num;
        }

        public int getShare_num() {
            return share_num;
        }

        public void setShare_num(int share_num) {
            this.share_num = share_num;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public String getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getAll_artist_id() {
            return all_artist_id;
        }

        public void setAll_artist_id(String all_artist_id) {
            this.all_artist_id = all_artist_id;
        }

        public String getPic_radio() {
            return pic_radio;
        }

        public void setPic_radio(String pic_radio) {
            this.pic_radio = pic_radio;
        }

        public String getPic_s500() {
            return pic_s500;
        }

        public void setPic_s500(String pic_s500) {
            this.pic_s500 = pic_s500;
        }

        public String getPic_s1000() {
            return pic_s1000;
        }

        public void setPic_s1000(String pic_s1000) {
            this.pic_s1000 = pic_s1000;
        }

        public String getAi_presale_flag() {
            return ai_presale_flag;
        }

        public void setAi_presale_flag(String ai_presale_flag) {
            this.ai_presale_flag = ai_presale_flag;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getListen_num() {
            return listen_num;
        }

        public void setListen_num(String listen_num) {
            this.listen_num = listen_num;
        }

        public String getBuy_url() {
            return buy_url;
        }

        public void setBuy_url(String buy_url) {
            this.buy_url = buy_url;
        }
    }

    public static class SonglistBean implements Serializable {
        private String artist_id;
        private String all_artist_id;
        private String all_artist_ting_uid;
        private String language;
        private String publishtime;
        private String album_no;
        private String versions;
        private String pic_big;
        private String pic_small;
        private String hot;
        private String file_duration;
        private String del_status;
        private String resource_type;
        private String copy_type;
        private int has_mv_mobile;
        private String all_rate;
        private String toneid;
        private String country;
        private String area;
        private String lrclink;
        private String bitrate_fee;
        private String si_presale_flag;
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

        public static SonglistBean objectFromData(String str) {

            return new Gson().fromJson(str, SonglistBean.class);
        }

        public static List<SonglistBean> arraySonglistBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<SonglistBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getAll_artist_id() {
            return all_artist_id;
        }

        public void setAll_artist_id(String all_artist_id) {
            this.all_artist_id = all_artist_id;
        }

        public String getAll_artist_ting_uid() {
            return all_artist_ting_uid;
        }

        public void setAll_artist_ting_uid(String all_artist_ting_uid) {
            this.all_artist_ting_uid = all_artist_ting_uid;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(String publishtime) {
            this.publishtime = publishtime;
        }

        public String getAlbum_no() {
            return album_no;
        }

        public void setAlbum_no(String album_no) {
            this.album_no = album_no;
        }

        public String getVersions() {
            return versions;
        }

        public void setVersions(String versions) {
            this.versions = versions;
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

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getLrclink() {
            return lrclink;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public String getSi_presale_flag() {
            return si_presale_flag;
        }

        public void setSi_presale_flag(String si_presale_flag) {
            this.si_presale_flag = si_presale_flag;
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
    }
}
