package somebody_z.me.zuimusic.mvp.presenter;

import de.greenrobot.event.EventBus;
import somebody_z.me.zuimusic.db.UserInfo;
import somebody_z.me.zuimusic.mvp.Contract.TelLoginContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.TelLoginModel;
import somebody_z.me.zuimusic.mvp.model.bean.LoginEvent;
import somebody_z.me.zuimusic.mvp.view.activity.TelLoginActivity;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/15.
 */
public class TelLoginPresenter extends BasePresenter<TelLoginModel, TelLoginActivity> implements TelLoginContract.TelLoginPresenter {

    private UserInfo userInfo;

    @Override
    public TelLoginModel loadModel() {
        return new TelLoginModel();
    }

    @Override
    public void login() {

        if (getIView().getUserName().equals("") || getIView().getUserName() == null) {
            getIView().toast("请输入手机号");
            return;
        }

        if (getIView().getUserPwd().equals("") || getIView().getUserPwd() == null) {
            getIView().toast("请输入密码");
            return;
        }

        userInfo = getiModel().getUserInfo(getIView(), getIView().getUserName());

        if (userInfo == null) {
            getIView().toast("账户不存在");
            return;
        }

        if (userInfo.getUserPwd().equals(getIView().getUserPwd())) {
            //登录成功
            //发布一个事件
            EventBus.getDefault().post(new LoginEvent("", userInfo.getUserName(), "local", null));

            getIView().finish();
        } else {
            getIView().toast("密码错误");
        }
    }
}
