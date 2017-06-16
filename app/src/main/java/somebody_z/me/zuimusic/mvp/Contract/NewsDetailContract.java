package somebody_z.me.zuimusic.mvp.Contract;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/17.
 */
public class NewsDetailContract {

    public interface NewsDetailView {

        void loadDetail(String content);

        void setNewProgress(int newProgress);

        void setProgressBarVisible(int visible);

        void setTitle(String title);

    }

    public interface NewsDetailPresenter {

        void init();

        void getDetail();

        void setProgressBarVisible(int newProgress);

        void setTitle(String title);

    }

}
