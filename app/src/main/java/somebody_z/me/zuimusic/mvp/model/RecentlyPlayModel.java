package somebody_z.me.zuimusic.mvp.model;

import android.content.Context;

import java.util.List;

import somebody_z.me.zuimusic.db.CollectSongSheet;
import somebody_z.me.zuimusic.db.CollectSongSheetManager;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.db.MySongSheetManager;
import somebody_z.me.zuimusic.db.RecentlyPlayManager;
import somebody_z.me.zuimusic.db.SongSheetDetailManager;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class RecentlyPlayModel implements IModel {

    public List<ContentBean> getRecentlyPlay(Context context) {
        List<ContentBean> recentlyPlayList = RecentlyPlayManager.getInstance().getRecentlyPlayList(context);
        return recentlyPlayList;
    }

    public void delectCollectSongSheet(CollectSongSheet collectSongSheet, Context context) {
        CollectSongSheetManager.getInstance().deleteCollectSongSheet(collectSongSheet, context);
    }

    public List<MySongSheet> getMySongSheet(Context context) {
        List<MySongSheet> mySongSheetList = MySongSheetManager.getInstance().getMySongSheetList(context);
        return mySongSheetList;
    }

    public void addCollectToMySongSheet(ContentBean contentBean, Context context, String songSheet) {
        SongSheetDetailManager.getInstance().addSongSheetDetail(contentBean, context, songSheet);
    }

    public ContentBean searchSongIsCollect(String songSheetDetailName, Context context, String songSheet) {
        ContentBean contentBean = SongSheetDetailManager.getInstance().searchSongSheetDetail(songSheet, songSheetDetailName, context);
        return contentBean;
    }

    public void addMySongSheet(MySongSheet mySongSheet, Context context) {
        MySongSheetManager.getInstance().addMySongSheet(mySongSheet, context);
    }

    public MySongSheet searchMySongSheet(String songSheetName, Context context) {
        MySongSheet mySongSheet = MySongSheetManager.getInstance().searchMySongSheet(songSheetName, context);
        return mySongSheet;
    }

    public void clearRecentlyPlay(Context context) {
        RecentlyPlayManager.getInstance().clearRecentlyPlay(context);
    }

}
