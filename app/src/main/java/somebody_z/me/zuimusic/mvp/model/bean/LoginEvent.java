package somebody_z.me.zuimusic.mvp.model.bean;

import cn.sharesdk.framework.PlatformDb;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/15.
 */
public class LoginEvent {

    private String pic;

    private String user_name;

    private String platform;

    private PlatformDb db;

    public LoginEvent(String pic, String user_name, String platform, PlatformDb db) {
        this.pic = pic;
        this.user_name = user_name;
        this.platform = platform;
        this.db = db;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public PlatformDb getDb() {
        return db;
    }

    public void setDb(PlatformDb db) {
        this.db = db;
    }

}
