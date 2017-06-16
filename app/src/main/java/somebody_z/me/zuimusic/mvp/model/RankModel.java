package somebody_z.me.zuimusic.mvp.model;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.AllSongSheetBean;
import somebody_z.me.zuimusic.mvp.model.bean.RankBean;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.http.ApiService;
import somebody_z.me.zuimusic.utils.http.ServiceFactory;

/**
 * Created by Huanxing Zeng on 2017/1/13.
 * email : zenghuanxing123@163.com
 */
public class RankModel implements IModel {

    private ApiService apiService;

    public void getAllRank(Subscription subscription, int kflag, final GetAllRankListener getAllRankListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getAllRank(Constants.ALL_RANK, kflag).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<RankBean>() {
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
                            getAllRankListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }
                    }

                    @Override
                    public void onNext(RankBean rankBean) {
                        // TODO Auto-generated method stu
                        getAllRankListener.getSuccess(rankBean);
                    }
                });
    }

    public interface GetAllRankListener {
        void getSuccess(RankBean rankBean);

        void getFail(String msg);
    }
}
