package somebody_z.me.zuimusic.mvp.model;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.model.bean.HotAlbumBean;
import somebody_z.me.zuimusic.mvp.model.bean.LoopPicBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.http.ApiService;
import somebody_z.me.zuimusic.utils.http.ServiceFactory;

/**
 * //使用try catch 避免出现空指针异常
 * Created by Huanxing Zeng on 2016/12/30.
 * email : zenghuanxing123@163.com
 */
public class RecommendModel implements IModel {

    private ApiService apiService;

    //获取轮播图片
    public void getLoopPic(Subscription subscription, int num, final GetLoopPicListener getLoopPicListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getLoopPic(Constants.LOOP_PIC, num).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<LoopPicBean>() {
                    @Override
                    public void onCompleted() {
                        // TODO Auto-generated method stub
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        // TODO Auto-generated method stub
                        try {
                            LogUtil.e(arg0.toString());
                            getLoopPicListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }
                    }

                    @Override
                    public void onNext(LoopPicBean loopPicBean) {
                        // TODO Auto-generated method stu
                        getLoopPicListener.getSuccess(loopPicBean);
                    }
                });
    }

    //获取推荐歌单
    public void getRecommendSongSheet(Subscription subscription, int num, final GetSongSheetListener getSongSheetListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getRecommendSongSheet(Constants.RECOMMEND_SONG_SHEET, num).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<RecommendSongSheetBean>() {
                    @Override
                    public void onCompleted() {
                        // TODO Auto-generated method stub
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        // TODO Auto-generated method stub
                        try {
                            LogUtil.e(arg0.toString());
                            getSongSheetListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }
                    }

                    @Override
                    public void onNext(RecommendSongSheetBean recommendSongSheetBean) {
                        // TODO Auto-generated method stu
                        getSongSheetListener.getSuccess(recommendSongSheetBean);
                    }
                });
    }

    //获取热门唱片
    public void getHotAlbum(Subscription subscription, int offset, int limit, final GetHotAlbumListener getHotAlbumListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getHotAlbum(Constants.HOT_ALBUM, offset, limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HotAlbumBean>() {
                    @Override
                    public void onCompleted() {
                        // TODO Auto-generated method stub
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        // TODO Auto-generated method stub
                        try {
                            LogUtil.e(arg0.toString());
                            getHotAlbumListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }
                    }

                    @Override
                    public void onNext(HotAlbumBean hotAlbumBean) {
                        // TODO Auto-generated method stu
                        getHotAlbumListener.getSuccess(hotAlbumBean);
                    }
                });
    }

    //获取最新音乐
    public void getNewMusic(Subscription subscription, int num, final GetNewMusicListener getNewMusicListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getNewMusic(Constants.NEW_MUSIC, num).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<NewMusicBean>() {
                    @Override
                    public void onCompleted() {
                        // TODO Auto-generated method stub
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        // TODO Auto-generated method stub
                        try {
                            LogUtil.e(arg0.toString());
                            getNewMusicListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }
                    }

                    @Override
                    public void onNext(NewMusicBean newMusicBean) {
                        // TODO Auto-generated method stu
                        getNewMusicListener.getSuccess(newMusicBean);
                    }
                });
    }

    //获取主播电台
    public void getAnchorRadio(Subscription subscription, int num, final GetAnchorRadioListener getAnchorRadioListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getAnchorRadio(Constants.ANCHOR_RADIO, num).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AnchorRadioBean>() {
                    @Override
                    public void onCompleted() {
                        // TODO Auto-generated method stub
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        // TODO Auto-generated method stub
                        try {
                            LogUtil.e(arg0.toString());
                            getAnchorRadioListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }
                    }

                    @Override
                    public void onNext(AnchorRadioBean anchorRadioBean) {
                        // TODO Auto-generated method stu
                        getAnchorRadioListener.getSuccess(anchorRadioBean);
                    }
                });
    }

    public interface GetAnchorRadioListener {
        void getSuccess(AnchorRadioBean anchorRadioBean);

        void getFail(String msg);
    }

    public interface GetNewMusicListener {
        void getSuccess(NewMusicBean newMusicBean);

        void getFail(String msg);
    }

    public interface GetHotAlbumListener {
        void getSuccess(HotAlbumBean hotAlbumBean);

        void getFail(String msg);
    }

    public interface GetSongSheetListener {
        void getSuccess(RecommendSongSheetBean recommendSongSheetBean);

        void getFail(String msg);
    }

    public interface GetLoopPicListener {
        void getSuccess(LoopPicBean loopPicBean);

        void getFail(String msg);
    }
}
