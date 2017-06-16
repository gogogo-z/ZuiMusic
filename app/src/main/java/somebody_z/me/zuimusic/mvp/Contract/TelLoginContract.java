package somebody_z.me.zuimusic.mvp.Contract;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/15.
 */
public class TelLoginContract {

    public interface TelLoginView {

        String getUserName();

        String getUserPwd();

    }

    public interface TelLoginPresenter {
        void login();
    }

}
