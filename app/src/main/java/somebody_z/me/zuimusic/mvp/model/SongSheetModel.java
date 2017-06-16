package somebody_z.me.zuimusic.mvp.model;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.AllSongSheetBean;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.model.bean.HotAlbumBean;
import somebody_z.me.zuimusic.mvp.model.bean.LoopPicBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.http.ApiService;
import somebody_z.me.zuimusic.utils.http.ServiceFactory;

/**
 * Created by Huanxing Zeng on 2016/12/30.
 * email : zenghuanxing123@163.com
 */
public class SongSheetModel implements IModel {

    private ApiService apiService;

    public void getAllSongSheet(Subscription subscription, int page_size, int page_no, final GetAllSongSheetListener getAllSongSheetListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getAllSongSheet(Constants.ALL_SONG_SHEET, page_size, page_no).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AllSongSheetBean>() {
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
                            getAllSongSheetListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }
                    }

                    @Override
                    public void onNext(AllSongSheetBean allSongSheetBean) {
                        // TODO Auto-generated method stu
                        getAllSongSheetListener.getSuccess(allSongSheetBean);
                    }
                });

    }

    public void getCategorySongSheet(Subscription subscription, int page_size, int page_no, String tagName, final GetCategorySongSheetListener getCategorySongSheetListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getCategorySongSheet(Constants.CATEGORY_SONG_SHEET, page_size, page_no, tagName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AllSongSheetBean>() {
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
                            getCategorySongSheetListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }
                    }

                    @Override
                    public void onNext(AllSongSheetBean allSongSheetBean) {
                        // TODO Auto-generated method stu
                        getCategorySongSheetListener.getSuccess(allSongSheetBean);
                    }
                });
    }

    public interface GetAllSongSheetListener {

        void getSuccess(AllSongSheetBean allSongSheetBean);

        void getFail(String msg);
    }

    public interface GetCategorySongSheetListener {
        void getSuccess(AllSongSheetBean allSongSheetBean);

        void getFail(String msg);
    }
}
