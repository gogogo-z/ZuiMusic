package somebody_z.me.zuimusic.mvp.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/16.
 */
public class SearchSongBean {

    /**
     * song : [{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","weight":"9430","songname":"海阔天空","songid":"14880013","has_mv":"0","yyr_artist":"0","artistname":"林忆莲","resource_type_ext":"0","resource_provider":"1","control":"0000000000","encrypted_songid":"1606e30d0d095771062dL"},{},{},{},{},{},{},{},{},{}]
     * order : artist,song
     * error_code : 22000
     * artist : []
     */

    private String order;
    private int error_code;
    /**
     * bitrate_fee : {"0":"0|0","1":"0|0"}
     * weight : 9430
     * songname : 海阔天空
     * songid : 14880013
     * has_mv : 0
     * yyr_artist : 0
     * artistname : 林忆莲
     * resource_type_ext : 0
     * resource_provider : 1
     * control : 0000000000
     * encrypted_songid : 1606e30d0d095771062dL
     */

    private List<SongBean> song;
    private List<?> artist;

    public static SearchSongBean objectFromData(String str) {

        return new Gson().fromJson(str, SearchSongBean.class);
    }

    public static List<SearchSongBean> arraySearchSongBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<SearchSongBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<SongBean> getSong() {
        return song;
    }

    public void setSong(List<SongBean> song) {
        this.song = song;
    }

    public List<?> getArtist() {
        return artist;
    }

    public void setArtist(List<?> artist) {
        this.artist = artist;
    }

    public static class SongBean {
        private String bitrate_fee;
        private String weight;
        private String songname;
        private String songid;
        private String has_mv;
        private String yyr_artist;
        private String artistname;
        private String resource_type_ext;
        private String resource_provider;
        private String control;
        private String encrypted_songid;

        public static SongBean objectFromData(String str) {

            return new Gson().fromJson(str, SongBean.class);
        }

        public static List<SongBean> arraySongBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<SongBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getSongname() {
            return songname;
        }

        public void setSongname(String songname) {
            this.songname = songname;
        }

        public String getSongid() {
            return songid;
        }

        public void setSongid(String songid) {
            this.songid = songid;
        }

        public String getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(String has_mv) {
            this.has_mv = has_mv;
        }

        public String getYyr_artist() {
            return yyr_artist;
        }

        public void setYyr_artist(String yyr_artist) {
            this.yyr_artist = yyr_artist;
        }

        public String getArtistname() {
            return artistname;
        }

        public void setArtistname(String artistname) {
            this.artistname = artistname;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getResource_provider() {
            return resource_provider;
        }

        public void setResource_provider(String resource_provider) {
            this.resource_provider = resource_provider;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }

        public String getEncrypted_songid() {
            return encrypted_songid;
        }

        public void setEncrypted_songid(String encrypted_songid) {
            this.encrypted_songid = encrypted_songid;
        }
    }
}
