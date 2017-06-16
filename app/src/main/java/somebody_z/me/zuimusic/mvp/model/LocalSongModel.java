package somebody_z.me.zuimusic.mvp.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import somebody_z.me.zuimusic.db.LocalMusicManager;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.db.MySongSheetManager;
import somebody_z.me.zuimusic.db.SongSheetDetailManager;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;

/**
 * Created by Huanxing Zeng on 2017/2/27.
 * email : zenghuanxing123@163.com
 */
public class LocalSongModel implements IModel {

    private SharedPreferences sp;

    public boolean getIsPlayed(Context context) {
        sp = context.getSharedPreferences("Bottom_Bar", Activity.MODE_PRIVATE);
        return sp.getBoolean("played", false);
    }

    public List<ContentBean> getLocalSong(Context context) {
        return LocalMusicManager.getInstance().getLocalMusicList(context);
    }

    public List<MySongSheet> getMySongSheet(Context context) {
        List<MySongSheet> mySongSheetList = MySongSheetManager.getInstance().getMySongSheetList(context);
        return mySongSheetList;
    }

    public void addMySongSheet(MySongSheet mySongSheet, Context context) {
        MySongSheetManager.getInstance().addMySongSheet(mySongSheet, context);
    }

    public MySongSheet searchMySongSheet(String songSheetName, Context context) {
        MySongSheet mySongSheet = MySongSheetManager.getInstance().searchMySongSheet(songSheetName, context);
        return mySongSheet;
    }

    public void addCollectToMySongSheet(ContentBean contentBean, Context context, String songSheet) {
        SongSheetDetailManager.getInstance().addSongSheetDetail(contentBean, context, songSheet);
    }

    public ContentBean searchSongIsCollect(String songSheetDetailName, Context context, String songSheet) {
        ContentBean contentBean = SongSheetDetailManager.getInstance().searchSongSheetDetail(songSheet, songSheetDetailName, context);
        return contentBean;
    }

}
