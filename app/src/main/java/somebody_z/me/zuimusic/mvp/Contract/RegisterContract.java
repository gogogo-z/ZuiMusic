package somebody_z.me.zuimusic.mvp.Contract;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/15.
 */
public class RegisterContract {

    public interface RegisterView {

        String getUserName();

        String getUserPwd();

    }

    public interface RegisterPresenter {
        void register();

        void loginByShareSDK(String platformName, final String platformTag);
    }

}
