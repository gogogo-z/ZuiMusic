package somebody_z.me.zuimusic.mvp.presenter;

import android.content.Intent;

import java.util.Calendar;
import java.util.List;

import somebody_z.me.zuimusic.mvp.Contract.FriendsContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.FriendsModel;
import somebody_z.me.zuimusic.mvp.model.bean.NewsBean;
import somebody_z.me.zuimusic.mvp.view.activity.NewsDetailActivity;
import somebody_z.me.zuimusic.mvp.view.fragment.friend.FriendsFragment;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/17.
 */
public class FriendsPresenter extends BasePresenter<FriendsModel, FriendsFragment> implements FriendsContract.FriendsPresenter {

    private int year, month, day;
    private String month_str, day_str;

    @Override
    public FriendsModel loadModel() {
        return new FriendsModel();
    }

    @Override
    public void init() {
        //获取系统日期
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        //month从0开始,所以+1
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void refreshNews(String date) {
        getiModel().getNews(getIView().subscription, date, new FriendsModel.GetNewsListener() {
            @Override
            public void getSuccess(NewsBean newsBean) {
                getIView().closeLoading();

                List<NewsBean.StoriesBean> list = newsBean.getStories();

                getIView().refresh(list);
            }

            @Override
            public void getFail(String msg) {
                getIView().toast(msg);
            }
        });
    }

    @Override
    public void loadMoreNews(String date) {
        getiModel().getNews(getIView().subscription, date, new FriendsModel.GetNewsListener() {
            @Override
            public void getSuccess(NewsBean newsBean) {
                getIView().closeLoading();

                List<NewsBean.StoriesBean> list = newsBean.getStories();

                getIView().setAdapter(list);
            }

            @Override
            public void getFail(String msg) {
                getIView().toast(msg);
            }
        });
    }

    @Override
    public String getDate() {

        if (day == 0) {
            month--;
            day = 29;
        }
        if (month == 0) {
            year--;
            month = 12;
        }

        if (day < 10) {
            day_str = "0" + day;
        } else {
            day_str = String.valueOf(day);
        }

        if (month < 10) {
            month_str = "0" + month;
        } else {
            month_str = String.valueOf(month);
        }

        String date = year + month_str + day_str;

        day--;

        return date;
    }

    @Override
    public void jumpToDetail(NewsBean.StoriesBean news) {

        Intent intent = new Intent(getIView().mContext, NewsDetailActivity.class);

        intent.putExtra("news_id", news.getId());

        getIView().startActivity(intent);
        //设置跳转无动画
        getIView().getActivity().overridePendingTransition(0, 0);
    }
}
