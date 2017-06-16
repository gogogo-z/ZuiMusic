package somebody_z.me.zuimusic.mvp.Contract;

import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.NewsBean;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/17.
 */
public class FriendsContract {

    public interface FriendsView {
        void setAdapter(List<NewsBean.StoriesBean> newsList);

        void refresh(List<NewsBean.StoriesBean> newsList);

    }

    public interface FriendsPresenter {

        void init();

        void refreshNews(String date);

        void loadMoreNews(String date);

        String getDate();

        void jumpToDetail(NewsBean.StoriesBean news);
    }

}
