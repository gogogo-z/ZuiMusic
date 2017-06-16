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
public class HotAlbumBean {

    /**
     * error_code : 22000
     * plaze_album_list : {"RM":{"album_list":{"havemore":0,"list":[{"album_id":"300790312","artist_id":"25478635","all_artist_id":"25478635,300790311","author":"王弢,Michel Kiener","title":"舒伯特","publishcompany":"环球唱片","country":"港台","pic_small":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_90","pic_big":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_150","pic_radio":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_300","songs_total":"14","is_recommend_mis":"0","is_first_publish":"0","is_exclusive":"0"},{},{},{},{},{}]}}}
     */

    private int error_code;
    /**
     * RM : {"album_list":{"havemore":0,"list":[{"album_id":"300790312","artist_id":"25478635","all_artist_id":"25478635,300790311","author":"王弢,Michel Kiener","title":"舒伯特","publishcompany":"环球唱片","country":"港台","pic_small":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_90","pic_big":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_150","pic_radio":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_300","songs_total":"14","is_recommend_mis":"0","is_first_publish":"0","is_exclusive":"0"},{},{},{},{},{}]}}
     */

    private PlazeAlbumListBean plaze_album_list;

    public static HotAlbumBean objectFromData(String str) {

        return new Gson().fromJson(str, HotAlbumBean.class);
    }

    public static List<HotAlbumBean> arrayHotAlbumBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<HotAlbumBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public PlazeAlbumListBean getPlaze_album_list() {
        return plaze_album_list;
    }

    public void setPlaze_album_list(PlazeAlbumListBean plaze_album_list) {
        this.plaze_album_list = plaze_album_list;
    }

    public static class PlazeAlbumListBean {
        /**
         * album_list : {"havemore":0,"list":[{"album_id":"300790312","artist_id":"25478635","all_artist_id":"25478635,300790311","author":"王弢,Michel Kiener","title":"舒伯特","publishcompany":"环球唱片","country":"港台","pic_small":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_90","pic_big":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_150","pic_radio":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_300","songs_total":"14","is_recommend_mis":"0","is_first_publish":"0","is_exclusive":"0"},{},{},{},{},{}]}
         */

        private RMBean RM;

        public static PlazeAlbumListBean objectFromData(String str) {

            return new Gson().fromJson(str, PlazeAlbumListBean.class);
        }

        public static List<PlazeAlbumListBean> arrayPlazeAlbumListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<PlazeAlbumListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public RMBean getRM() {
            return RM;
        }

        public void setRM(RMBean RM) {
            this.RM = RM;
        }

        public static class RMBean {
            /**
             * havemore : 0
             * list : [{"album_id":"300790312","artist_id":"25478635","all_artist_id":"25478635,300790311","author":"王弢,Michel Kiener","title":"舒伯特","publishcompany":"环球唱片","country":"港台","pic_small":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_90","pic_big":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_150","pic_radio":"http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_300","songs_total":"14","is_recommend_mis":"0","is_first_publish":"0","is_exclusive":"0"},{},{},{},{},{}]
             */

            private AlbumListBean album_list;

            public static RMBean objectFromData(String str) {

                return new Gson().fromJson(str, RMBean.class);
            }

            public static List<RMBean> arrayRMBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<RMBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public AlbumListBean getAlbum_list() {
                return album_list;
            }

            public void setAlbum_list(AlbumListBean album_list) {
                this.album_list = album_list;
            }

            public static class AlbumListBean {
                private int havemore;
                /**
                 * album_id : 300790312
                 * artist_id : 25478635
                 * all_artist_id : 25478635,300790311
                 * author : 王弢,Michel Kiener
                 * title : 舒伯特
                 * publishcompany : 环球唱片
                 * country : 港台
                 * pic_small : http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_90
                 * pic_big : http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_150
                 * pic_radio : http://musicdata.baidu.com/data2/pic/e873a5ce4b6c094ffd25fd1c2e661861/303123443/303123443.jpeg@s_0,w_300
                 * songs_total : 14
                 * is_recommend_mis : 0
                 * is_first_publish : 0
                 * is_exclusive : 0
                 */

                private List<ListBean> list;

                public static AlbumListBean objectFromData(String str) {

                    return new Gson().fromJson(str, AlbumListBean.class);
                }

                public static List<AlbumListBean> arrayAlbumListBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<AlbumListBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public int getHavemore() {
                    return havemore;
                }

                public void setHavemore(int havemore) {
                    this.havemore = havemore;
                }

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }


            }
        }
    }

    public static class ListBean {
        private String album_id;
        private String artist_id;
        private String all_artist_id;
        private String author;
        private String title;
        private String publishcompany;
        private String country;
        private String pic_small;
        private String pic_big;
        private String pic_radio;
        private String songs_total;
        private String is_recommend_mis;
        private String is_first_publish;
        private String is_exclusive;

        public static ListBean objectFromData(String str) {

            return new Gson().fromJson(str, ListBean.class);
        }

        public static List<ListBean> arrayListBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ListBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
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

        public String getPic_radio() {
            return pic_radio;
        }

        public void setPic_radio(String pic_radio) {
            this.pic_radio = pic_radio;
        }

        public String getSongs_total() {
            return songs_total;
        }

        public void setSongs_total(String songs_total) {
            this.songs_total = songs_total;
        }

        public String getIs_recommend_mis() {
            return is_recommend_mis;
        }

        public void setIs_recommend_mis(String is_recommend_mis) {
            this.is_recommend_mis = is_recommend_mis;
        }

        public String getIs_first_publish() {
            return is_first_publish;
        }

        public void setIs_first_publish(String is_first_publish) {
            this.is_first_publish = is_first_publish;
        }

        public String getIs_exclusive() {
            return is_exclusive;
        }

        public void setIs_exclusive(String is_exclusive) {
            this.is_exclusive = is_exclusive;
        }
    }
}
