package somebody_z.me.zuimusic.utils.downloadmanager;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/17.
 */
public class LrcBean {

    public LrcBean(String songName) {
        this.songName = songName;
    }

    private String songName;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
