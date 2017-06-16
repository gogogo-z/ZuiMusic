package somebody_z.me.zuimusic.mvp.model;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetCategoryBean;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.http.ApiService;
import somebody_z.me.zuimusic.utils.http.ServiceFactory;

/**
 * Created by Huanxing Zeng on 2017/2/15.
 * email : zenghuanxing123@163.com
 */
public class SongSheetCategoryModel implements IModel {

    private ApiService apiService;

    public void getSongSheetCategory(Subscription subscription, final GetCategoryListener getCategoryListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getSongSheetCategory(Constants.SONG_SHEET_CATEGORY).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<SongSheetCategoryBean>() {
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
                            getCategoryListener.getErrol("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }

                    }

                    @Override
                    public void onNext(SongSheetCategoryBean songSheetCategoryBean) {
                        // TODO Auto-generated method stu
                        getCategoryListener.getSuccess(songSheetCategoryBean);
                    }
                });
    }

    public interface GetCategoryListener {
        void getSuccess(SongSheetCategoryBean songSheetCategoryBean);

        void getErrol(String error);
    }

}
