package somebody_z.me.zuimusic.service;

import android.content.Context;
import android.content.SharedPreferences;

import somebody_z.me.zuimusic.common.Constants;

/**
 * Created by Huanxing Zeng on 2017/3/16.
 * email : zenghuanxing123@163.com
 */
public class SPStorage {

    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;

    @SuppressWarnings("deprecation")
    public SPStorage(Context context) {
        mSp = context.getSharedPreferences(Constants.SP_NAME,
                Context.MODE_WORLD_WRITEABLE);
        mEditor = mSp.edit();
    }

    /**
     * 保存背景图片的地址
     */
    public void savePath(String path) {
        mEditor.putString(Constants.SP_BG_PATH, path);
        mEditor.commit();
    }

    /**
     * 获取背景图片的地址
     *
     * @return
     */
    public String getPath() {
        return mSp.getString(Constants.SP_BG_PATH, null);
    }

    public void saveShake(boolean shake) {
        mEditor.putBoolean(Constants.SP_SHAKE_CHANGE_SONG, shake);
        mEditor.commit();
    }

    public boolean getShake() {
        return mSp.getBoolean(Constants.SP_SHAKE_CHANGE_SONG, false);
    }

    public void saveAutoLyric(boolean auto) {
        mEditor.putBoolean(Constants.SP_AUTO_DOWNLOAD_LYRIC, auto);
        mEditor.commit();
    }

    public boolean getAutoLyric() {
        return mSp.getBoolean(Constants.SP_AUTO_DOWNLOAD_LYRIC, false);
    }

    public void saveFilterSize(boolean size) {
        mEditor.putBoolean(Constants.SP_FILTER_SIZE, size);
        mEditor.commit();
    }

    public boolean getFilterSize() {
        return mSp.getBoolean(Constants.SP_FILTER_SIZE, false);
    }

    public void saveFilterTime(boolean time) {
        mEditor.putBoolean(Constants.SP_FILTER_TIME, time);
        mEditor.commit();
    }

    public boolean getFilterTime() {
        return mSp.getBoolean(Constants.SP_FILTER_TIME, false);
    }

}
