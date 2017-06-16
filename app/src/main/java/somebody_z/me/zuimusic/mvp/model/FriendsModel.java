package somebody_z.me.zuimusic.mvp.model;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewsBean;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.http.ApiService;
import somebody_z.me.zuimusic.utils.http.ServiceFactory;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/17.
 */
public class FriendsModel implements IModel {

    private ApiService apiService;

    /**
     * 获取新闻
     *
     * @param subscription
     * @param getNewsListener
     */
    public void getNews(Subscription subscription, String date, final GetNewsListener getNewsListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getNews(date).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<NewsBean>() {
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
                            getNewsListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }

                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        // TODO Auto-generated method stu
                        getNewsListener.getSuccess(newsBean);
                    }
                });
    }

    public interface GetNewsListener {
        void getSuccess(NewsBean newsBean);

        void getFail(String msg);
    }
}
