package somebody_z.me.zuimusic.mvp.Contract;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class MyPlayerContract {

    public interface MyPlayerView {
        void setBarMarginTop();
    }

    public interface MyPlayerPresenter {
        void isImmerse();

        void onBackPressed();
    }

}
