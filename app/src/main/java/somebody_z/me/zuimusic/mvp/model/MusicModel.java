package somebody_z.me.zuimusic.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.AlbumDetailManager;
import somebody_z.me.zuimusic.db.CollectSongSheet;
import somebody_z.me.zuimusic.db.CollectSongSheetManager;
import somebody_z.me.zuimusic.db.LocalMusicManager;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.db.MySongSheetManager;
import somebody_z.me.zuimusic.db.RecentlyPlayManager;
import somebody_z.me.zuimusic.db.SongSheetDetailManager;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;

/**
 * Created by Huanxing Zeng on 2017/1/13.
 * email : zenghuanxing123@163.com
 */
public class MusicModel implements IModel {

    public List<ContentBean> getLocalSong(Context context) {
        return LocalMusicManager.getInstance().getLocalMusicList(context);
    }

    public void addMySongSheet(MySongSheet mySongSheet, Context context) {
        MySongSheetManager.getInstance().addMySongSheet(mySongSheet, context);
    }

    public List<MySongSheet> getMySongSheet(Context context) {
        List<MySongSheet> mySongSheetList = MySongSheetManager.getInstance().getMySongSheetList(context);
        return mySongSheetList;
    }

    public void delectMySongSheet(MySongSheet mySongSheet, Context context) {
        MySongSheetManager.getInstance().deleteMySongSheet(mySongSheet, context);
    }

    public void updateMySongSheet(MySongSheet mySongSheet, Context context) {
        MySongSheetManager.getInstance().updateMySongSheet(mySongSheet, context);
    }

    public MySongSheet searchMySongSheet(String songSheetName, Context context) {
        MySongSheet mySongSheet = MySongSheetManager.getInstance().searchMySongSheet(songSheetName, context);
        return mySongSheet;
    }

    public List<CollectSongSheet> getCollectSongSheet(Context context) {
        List<CollectSongSheet> collectSongSheetList = CollectSongSheetManager.getInstance().getCollectSongSheetList(context);
        return collectSongSheetList;
    }

    public void delectCollectSongSheet(CollectSongSheet collectSongSheet, Context context) {
        CollectSongSheetManager.getInstance().deleteCollectSongSheet(collectSongSheet, context);
    }

    public void updateCollectSongSheet(CollectSongSheet collectSongSheet, Context context) {
        CollectSongSheetManager.getInstance().updateColloctSongSheet(collectSongSheet, context);
    }

    public CollectSongSheet searchCollectSongSheet(String songSheetName, Context context) {
        CollectSongSheet collectSongSheet = CollectSongSheetManager.getInstance().searchCollectSongSheet(songSheetName, context);
        return collectSongSheet;
    }

    public List<ContentBean> getSongSheetDetailList(String songSheetName, Context context) {
        List<ContentBean> songSheetDetailList = SongSheetDetailManager.getInstance().getSongSheetDetailList(context,
                songSheetName);
        return songSheetDetailList;
    }

    public List<ContentBean> getRecentlyPlay(Context context) {
        List<ContentBean> recentlyPlayList = RecentlyPlayManager.getInstance().getRecentlyPlayList(context);
        return recentlyPlayList;
    }

    public List<AlbumDetailBean.AlbumInfoBean> getMyAlbum(Context context) {
        List<AlbumDetailBean.AlbumInfoBean> myAlbumList = AlbumDetailManager.getInstance().getAlbumList(context);
        return myAlbumList;
    }

    public boolean getIsFirstUse(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.FLAG_FIRST_USED, Context.MODE_PRIVATE);
        boolean isFirst = sp.getBoolean(Constants.FLAG_FIRST_USED_VALUE, true);
        return isFirst;
    }

    public void setFirstUse(Context context) {
        //修改是否第一次使用的状态
        SharedPreferences preferences = context.getSharedPreferences(Constants.FLAG_FIRST_USED, Context.MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.FLAG_FIRST_USED_VALUE, false);
        editor.commit();//最后别忘记提交了
    }
}
