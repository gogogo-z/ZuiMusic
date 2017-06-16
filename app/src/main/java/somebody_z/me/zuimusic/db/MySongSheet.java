package somebody_z.me.zuimusic.db;

/**
 * Created by Huanxing Zeng on 2017/1/17.
 * email : zenghuanxing123@163.com
 */
public class MySongSheet {

    private String songSheetName;
    private String songSheetTag;
    private String songSheetDesc;
    private String songSheetPic;

    public MySongSheet(String songSheetName, String songSheetTag, String songSheetDesc, String songSheetPic) {
        this.songSheetName = songSheetName;
        this.songSheetTag = songSheetTag;
        this.songSheetDesc = songSheetDesc;
        this.songSheetPic = songSheetPic;
    }

    public String getSongSheetName() {
        return songSheetName;
    }

    public void setSongSheetName(String songSheetName) {
        this.songSheetName = songSheetName;
    }

    public String getSongSheetTag() {
        return songSheetTag;
    }

    public void setSongSheetTag(String songSheetTag) {
        this.songSheetTag = songSheetTag;
    }

    public String getSongSheetDesc() {
        return songSheetDesc;
    }

    public void setSongSheetDesc(String songSheetDesc) {
        this.songSheetDesc = songSheetDesc;
    }

    public String getSongSheetPic() {
        return songSheetPic;
    }

    public void setSongSheetPic(String songSheetPic) {
        this.songSheetPic = songSheetPic;
    }
}
