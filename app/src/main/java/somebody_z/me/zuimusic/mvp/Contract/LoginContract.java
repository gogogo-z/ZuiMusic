package somebody_z.me.zuimusic.mvp.Contract;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/14.
 */
public class LoginContract {

    public interface LoginView {

    }

    public interface LoginPresenter {
        void init();

        void unRegister();

        void jumpToLogin();

        void jumpToRegister();

        void loginByShareSDK(String platformName, String platformTag);
    }


}
