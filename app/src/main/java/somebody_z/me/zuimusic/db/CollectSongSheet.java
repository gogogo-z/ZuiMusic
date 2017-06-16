package somebody_z.me.zuimusic.db;

/**
 * Created by Huanxing Zeng on 2017/1/18.
 * email : zenghuanxing123@163.com
 */
public class CollectSongSheet {

    /**
     * listid : 7259
     * pic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_5f2ecb9b02e287b82e12b84a6c93bbdf.jpg
     * title : 东海音乐节，去海边沙滩听音乐
     * tag : 流行,摇滚,民谣
     */

    private String listid;
    private String pic;
    private String title;
    private String tag;

    public CollectSongSheet(String listid, String pic, String title, String tag) {
        this.listid = listid;
        this.pic = pic;
        this.title = title;
        this.tag = tag;
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

}
