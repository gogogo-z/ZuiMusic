package somebody_z.me.zuimusic.mvp.model.bean;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.widget.indexbar.IndexableEntity;

/**
 * Created by Huanxing Zeng on 2017/3/13.
 * email : zenghuanxing123@163.com
 */
public class ContentBean implements Serializable, IndexableEntity, Parcelable {
    public final static String KEY_MUSIC = "music";

    private String title;
    public final static String KEY_TITLE = "title";
    private String song_id;
    public final static String KEY_SONG_ID = "song_id";
    private String author;
    public final static String KEY_AUTHOR = "author";
    private String album_id;
    public final static String KEY_ALBUM_ID = "album_id";
    private String album_title;
    public final static String KEY_ALBUM_TITLE = "album_title";
    private String relate_status;
    public final static String KEY_RELATE_STATUS = "relate_status";
    private String is_charge;
    public final static String KEY_IS_CHARGE = "is_charge";
    private String all_rate;
    public final static String KEY_ALL_RATE = "all_rate";
    private String high_rate;
    public final static String KEY_HIGH_RATE = "high_rate";
    private String all_artist_id;
    public final static String KEY_ALL_ARTIST_ID = "all_artist_id";
    private String copy_type;
    public final static String KEY_COPY_TYPE = "copy_type";
    private int has_mv;
    public final static String KEY_HAS_MV = "has_mv";
    private String toneid;
    public final static String KEY_TONEID = "toneid";
    private String resource_type;
    public final static String KEY_RESOURCE_TYPE = "resource_type";
    private String is_ksong;
    public final static String KEY_IS_KSONG = "is_ksong";
    private String resource_type_ext;
    public final static String KEY_RESOURCE_TYPE_EXT = "resource_type_ext";
    private String versions;
    public final static String KEY_VERSIONS = "versions";
    private String bitrate_fee;
    public final static String KEY_BITRATE_FEE = "bitrate_fee";
    private int has_mv_mobile;
    public final static String KEY_HAS_MV_MOBILE = "has_mv_mobile";
    private String ting_uid;
    public final static String KEY_TING_UID = "ting_uid";
    private int is_first_publish;
    public final static String KEY_IS_FIRST_PUBLISH = "is_first_publish";
    private int havehigh;
    public final static String KEY_HAVEHIGH = "havehigh";
    private int charge;
    public final static String KEY_CHARGE = "charge";
    private int learn;
    public final static String KEY_LEARN = "learn";
    private String song_source;
    public final static String KEY_SONG_SOURCE = "song_source";
    private String piao_id;
    public final static String KEY_PIAO_ID = "piao_id";
    private String korean_bb_song;
    public final static String KEY_KOREAN_BB_SONG = "korean_bb_song";
    private String mv_provider;
    public final static String KEY_MV_PROVIDER = "mv_provider";
    private String share;
    public final static String KEY_SHARE = "share";
    private String localUrl;
    public final static String KEY_LOCALURL = "localUrl";
    private int duration;
    public final static String KEY_DURATION = "duration";
    private long size;
    public final static String KEY_SIZE = "size";
    private String pic_url;
    public final static String KEY_PIC_URL = "pic_url";
    private String lrc_url;
    public final static String KEY_LRC_URL = "lrc_url";

    private String pinyin;

    public ContentBean(String title, String song_id, String author, String album_title, String album_id,
                       String localUrl, int has_mv, String pic_url, String lrc_url) {
        this.title = title;
        this.song_id = song_id;
        this.author = author;
        this.album_title = album_title;
        this.album_id = album_id;
        this.localUrl = localUrl;
        this.has_mv = has_mv;
        this.pic_url = pic_url;
        this.lrc_url = lrc_url;
    }

    public ContentBean(String title, String song_id, String author, String album_title, String album_id,
                       String localUrl, int has_mv, int duration, long size) {
        this.title = title;
        this.song_id = song_id;
        this.author = author;
        this.album_title = album_title;
        this.album_id = album_id;
        this.localUrl = localUrl;
        this.has_mv = has_mv;
        this.duration = duration;
        this.size = size;
    }

    public ContentBean() {

    }


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

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
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

    public String getRelate_status() {
        return relate_status;
    }

    public void setRelate_status(String relate_status) {
        this.relate_status = relate_status;
    }

    public String getIs_charge() {
        return is_charge;
    }

    public void setIs_charge(String is_charge) {
        this.is_charge = is_charge;
    }

    public String getAll_rate() {
        return all_rate;
    }

    public void setAll_rate(String all_rate) {
        this.all_rate = all_rate;
    }

    public String getHigh_rate() {
        return high_rate;
    }

    public void setHigh_rate(String high_rate) {
        this.high_rate = high_rate;
    }

    public String getAll_artist_id() {
        return all_artist_id;
    }

    public void setAll_artist_id(String all_artist_id) {
        this.all_artist_id = all_artist_id;
    }

    public String getCopy_type() {
        return copy_type;
    }

    public void setCopy_type(String copy_type) {
        this.copy_type = copy_type;
    }

    public int getHas_mv() {
        return has_mv;
    }

    public void setHas_mv(int has_mv) {
        this.has_mv = has_mv;
    }

    public String getToneid() {
        return toneid;
    }

    public void setToneid(String toneid) {
        this.toneid = toneid;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getIs_ksong() {
        return is_ksong;
    }

    public void setIs_ksong(String is_ksong) {
        this.is_ksong = is_ksong;
    }

    public String getResource_type_ext() {
        return resource_type_ext;
    }

    public void setResource_type_ext(String resource_type_ext) {
        this.resource_type_ext = resource_type_ext;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getBitrate_fee() {
        return bitrate_fee;
    }

    public void setBitrate_fee(String bitrate_fee) {
        this.bitrate_fee = bitrate_fee;
    }

    public int getHas_mv_mobile() {
        return has_mv_mobile;
    }

    public void setHas_mv_mobile(int has_mv_mobile) {
        this.has_mv_mobile = has_mv_mobile;
    }

    public String getTing_uid() {
        return ting_uid;
    }

    public void setTing_uid(String ting_uid) {
        this.ting_uid = ting_uid;
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

    public String getMv_provider() {
        return mv_provider;
    }

    public void setMv_provider(String mv_provider) {
        this.mv_provider = mv_provider;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getLrc_url() {
        return lrc_url;
    }

    public void setLrc_url(String lrc_url) {
        this.lrc_url = lrc_url;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String getFieldIndexBy() {
        return title;
    }

    @Override
    public void setFieldIndexBy(String indexByField) {
        this.title = indexByField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_SONG_ID, song_id);
        bundle.putString(KEY_AUTHOR, author);
        bundle.putString(KEY_ALBUM_ID, album_id);
        bundle.putString(KEY_ALBUM_TITLE, album_title);
        bundle.putString(KEY_RELATE_STATUS, relate_status);
        bundle.putString(KEY_IS_CHARGE, is_charge);
        bundle.putString(KEY_ALL_RATE, all_rate);
        bundle.putString(KEY_HIGH_RATE, high_rate);
        bundle.putString(KEY_ALL_ARTIST_ID, all_artist_id);
        bundle.putString(KEY_COPY_TYPE, copy_type);
        bundle.putInt(KEY_HAS_MV, has_mv);
        bundle.putString(KEY_TONEID, toneid);
        bundle.putString(KEY_RESOURCE_TYPE, resource_type);
        bundle.putString(KEY_IS_KSONG, is_ksong);
        bundle.putString(KEY_RESOURCE_TYPE_EXT, resource_type_ext);
        bundle.putString(KEY_VERSIONS, versions);
        bundle.putString(KEY_BITRATE_FEE, bitrate_fee);
        bundle.putInt(KEY_HAS_MV_MOBILE, has_mv_mobile);
        bundle.putString(KEY_TING_UID, ting_uid);
        bundle.putInt(KEY_IS_FIRST_PUBLISH, is_first_publish);
        bundle.putInt(KEY_HAVEHIGH, havehigh);
        bundle.putInt(KEY_CHARGE, charge);
        bundle.putInt(KEY_LEARN, learn);
        bundle.putString(KEY_SONG_SOURCE, song_source);
        bundle.putString(KEY_PIAO_ID, piao_id);
        bundle.putString(KEY_KOREAN_BB_SONG, korean_bb_song);
        bundle.putString(KEY_MV_PROVIDER, mv_provider);
        bundle.putString(KEY_SHARE, share);
        bundle.putString(KEY_LOCALURL, localUrl);
        bundle.putInt(KEY_DURATION, duration);
        bundle.putLong(KEY_SIZE, size);
        bundle.putString(KEY_PIC_URL, pic_url);
        bundle.putString(KEY_LRC_URL, lrc_url);
        parcel.writeBundle(bundle);

    }

    public static final Parcelable.Creator<ContentBean> CREATOR = new Parcelable.Creator<ContentBean>() {

        @Override
        public ContentBean createFromParcel(Parcel source) {
            ContentBean music = new ContentBean();
            Bundle bundle = source.readBundle();
            music.title = bundle.getString(KEY_TITLE);
            music.song_id = bundle.getString(KEY_SONG_ID);
            music.author = bundle.getString(KEY_AUTHOR);
            music.album_id = bundle.getString(KEY_ALBUM_ID);
            music.album_title = bundle.getString(KEY_ALBUM_TITLE);
            music.relate_status = bundle.getString(KEY_RELATE_STATUS);
            music.is_charge = bundle.getString(KEY_IS_CHARGE);
            music.all_rate = bundle.getString(KEY_ALL_RATE);
            music.high_rate = bundle.getString(KEY_HIGH_RATE);
            music.all_artist_id = bundle.getString(KEY_ALL_ARTIST_ID);
            music.copy_type = bundle.getString(KEY_COPY_TYPE);
            music.has_mv = bundle.getInt(KEY_HAS_MV);
            music.toneid = bundle.getString(KEY_TONEID);
            music.resource_type = bundle.getString(KEY_RESOURCE_TYPE);
            music.is_ksong = bundle.getString(KEY_IS_KSONG);
            music.resource_type_ext = bundle.getString(KEY_RESOURCE_TYPE_EXT);
            music.versions = bundle.getString(KEY_VERSIONS);
            music.bitrate_fee = bundle.getString(KEY_BITRATE_FEE);
            music.has_mv_mobile = bundle.getInt(KEY_HAS_MV_MOBILE);
            music.ting_uid = bundle.getString(KEY_TING_UID);
            music.is_first_publish = bundle.getInt(KEY_IS_FIRST_PUBLISH);
            music.havehigh = bundle.getInt(KEY_HAVEHIGH);
            music.charge = bundle.getInt(KEY_CHARGE);
            music.learn = bundle.getInt(KEY_LEARN);
            music.song_source = bundle.getString(KEY_SONG_SOURCE);
            music.piao_id = bundle.getString(KEY_PIAO_ID);
            music.korean_bb_song = bundle.getString(KEY_KOREAN_BB_SONG);
            music.mv_provider = bundle.getString(KEY_MV_PROVIDER);
            music.share = bundle.getString(KEY_SHARE);
            music.localUrl = bundle.getString(KEY_LOCALURL);
            music.duration = bundle.getInt(KEY_DURATION);
            music.size = bundle.getLong(KEY_SIZE);
            music.pic_url = bundle.getString(KEY_PIC_URL);
            music.lrc_url = bundle.getString(KEY_LRC_URL);
            return music;
        }

        @Override
        public ContentBean[] newArray(int size) {
            return new ContentBean[size];
        }
    };
}
