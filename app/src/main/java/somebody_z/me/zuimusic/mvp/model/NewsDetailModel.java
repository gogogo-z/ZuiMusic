package somebody_z.me.zuimusic.mvp.model;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.NewsDetailBean;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.http.ApiService;
import somebody_z.me.zuimusic.utils.http.ServiceFactory;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/17.
 */
public class NewsDetailModel implements IModel {

    private ApiService apiService;

    /**
     * 获取新闻详情
     *
     * @param subscription
     * @param getNewsDetailListener
     */
    public void getNewsDetail(Subscription subscription, int id, final GetNewsDetailListener getNewsDetailListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getNewsDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<NewsDetailBean>() {
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
                            getNewsDetailListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }

                    }

                    @Override
                    public void onNext(NewsDetailBean newsBean) {
                        // TODO Auto-generated method stu
                        getNewsDetailListener.getSuccess(newsBean);
                    }
                });
    }

    public interface GetNewsDetailListener {
        void getSuccess(NewsDetailBean newsDetailBean);

        void getFail(String msg);
    }

}
