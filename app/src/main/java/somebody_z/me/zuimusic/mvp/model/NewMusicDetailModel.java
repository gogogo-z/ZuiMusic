package somebody_z.me.zuimusic.mvp.model;

import android.content.Context;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.db.MySongSheetManager;
import somebody_z.me.zuimusic.db.SongSheetDetailManager;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.PlaySongInfoBean;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.http.ApiService;
import somebody_z.me.zuimusic.utils.http.ServiceFactory;


/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class NewMusicDetailModel implements IModel {

    private ApiService apiService;

    /**
     * 获取歌曲信息和下载地址
     *
     * @param subscription
     * @param songid
     * @param getSongInfoListener
     */
    public void getSongInfo(Subscription subscription, String songid, final GetSongInfoListener getSongInfoListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getSongInfo(Constants.SONG_INFO, songid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<PlaySongInfoBean>() {
                    @Override
                    public void onCompleted() {
                        // TODO Auto-generated method stub
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        // TODO Auto-generated method stub
                        //使用try catch 避免出现空指针异常
                        try {
                            LogUtil.e(arg0.toString());
                            getSongInfoListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }

                    }

                    @Override
                    public void onNext(PlaySongInfoBean playSongInfoBean) {
                        // TODO Auto-generated method stu
                        getSongInfoListener.getSuccess(playSongInfoBean);
                    }
                });
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

    public void delectSong(ContentBean songSheetDetail, Context context, String songSheetName) {
        SongSheetDetailManager.getInstance().deleteSongSheetDetail(songSheetDetail, context, songSheetName);
    }

    public interface GetSongInfoListener {
        void getSuccess(PlaySongInfoBean playSongInfoBean);

        void getFail(String msg);
    }


}
