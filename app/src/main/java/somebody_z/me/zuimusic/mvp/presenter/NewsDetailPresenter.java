package somebody_z.me.zuimusic.mvp.presenter;

import android.media.AudioManager;
import android.view.View;

import somebody_z.me.zuimusic.mvp.Contract.NewsDetailContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.NewsDetailModel;
import somebody_z.me.zuimusic.mvp.model.bean.NewsDetailBean;
import somebody_z.me.zuimusic.mvp.view.activity.NewsDetailActivity;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/17.
 */
public class NewsDetailPresenter extends BasePresenter<NewsDetailModel, NewsDetailActivity> implements NewsDetailContract.NewsDetailPresenter {

    private int id;

    private boolean isFirst = true;

    @Override
    public NewsDetailModel loadModel() {
        return new NewsDetailModel();
    }

    @Override
    public void init() {
        id = getIView().getIntent().getIntExtra("news_id", 101);

        getIView().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void getDetail() {
        getiModel().getNewsDetail(getIView().subscription, id, new NewsDetailModel.GetNewsDetailListener() {
            @Override
            public void getSuccess(NewsDetailBean newsDetailBean) {
                getIView().loadDetail(newsDetailBean.getBody());
                getIView().setTitle(newsDetailBean.getTitle());
            }

            @Override
            public void getFail(String msg) {
                getIView().toast(msg);
            }
        });
    }

    @Override
    public void setProgressBarVisible(int newProgress) {
        if (newProgress == 100) {
            getIView().setProgressBarVisible(View.GONE);
        } else {
            getIView().setProgressBarVisible(View.VISIBLE);
            getIView().setNewProgress(newProgress);
        }
    }

    @Override
    public void setTitle(String title) {
        if (!isFirst) {
            getIView().setTitle(title);
        } else {
            isFirst = false;
        }
    }
}
