package somebody_z.me.zuimusic.mvp.model;

import android.content.Context;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.CollectSongSheet;
import somebody_z.me.zuimusic.db.CollectSongSheetManager;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.db.MySongSheetManager;
import somebody_z.me.zuimusic.db.SongSheetDetailManager;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.PlaySongInfoBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.http.ApiService;
import somebody_z.me.zuimusic.utils.http.ServiceFactory;

/**
 * Created by Huanxing Zeng on 2017/2/5.
 * email : zenghuanxing123@163.com
 */
public class MySongSheetDetailModel implements IModel {

    public void addCollectSongSheet(CollectSongSheet collectSongSheet, Context context) {
        CollectSongSheetManager.getInstance().addCollectSongSheet(collectSongSheet, context);
    }

    public CollectSongSheet searchCollectSongSheet(String songSheetName, Context context) {
        CollectSongSheet collectSongSheet = CollectSongSheetManager.getInstance().searchCollectSongSheet(songSheetName, context);
        return collectSongSheet;
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

    public List<ContentBean> getSongList(String songSheetName, Context context) {
        List<ContentBean> songSheetDetails = SongSheetDetailManager.getInstance().getSongSheetDetailList(context,
                songSheetName);
        return songSheetDetails;
    }

}
