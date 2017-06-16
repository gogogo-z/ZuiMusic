package somebody_z.me.zuimusic.mvp.presenter;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import de.greenrobot.event.EventBus;
import somebody_z.me.zuimusic.db.UserInfo;
import somebody_z.me.zuimusic.mvp.Contract.RegisterContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.RegisterModel;
import somebody_z.me.zuimusic.mvp.model.bean.LoginEvent;
import somebody_z.me.zuimusic.mvp.view.activity.RegisterActivity;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/15.
 */
public class RegisterPresenter extends BasePresenter<RegisterModel, RegisterActivity> implements RegisterContract.RegisterPresenter {

    private UserInfo userInfo;

    @Override
    public RegisterModel loadModel() {
        return new RegisterModel();
    }

    @Override
    public void register() {
        if (getIView().getUserName().equals("") || getIView().getUserName() == null) {
            getIView().toast("请输入手机号");
            return;
        }

        if (getIView().getUserPwd().equals("") || getIView().getUserPwd() == null) {
            getIView().toast("请设置密码");
            return;
        }

        if (getIView().getUserPwd().length() < 6) {
            getIView().toast("密码不少于6位");
            return;
        }

        userInfo = getiModel().getUserInfo(getIView(), getIView().getUserName());

        if (userInfo == null) {
            //注册
            UserInfo userInfo1 = new UserInfo(getIView().getUserName(), getIView().getUserPwd());

            getiModel().addUserInfo(userInfo1, getIView());

            //发布一个事件
            EventBus.getDefault().post(new LoginEvent("", getIView().getUserName(), "local", null));

            getIView().finish();

        } else {
            getIView().toast("该手机号已注册");
        }

    }

    @Override
    public void loginByShareSDK(String platformName, final String platformTag) {
        //创建一个平台
        final Platform platform = ShareSDK.getPlatform(platformName);

        //设置请求回调
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                PlatformDb db = platform.getDb();

                //发布一个事件
                EventBus.getDefault().post(new LoginEvent(db.getUserIcon(), db.getUserName(), platformTag, db));

                getIView().finish();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        });

        //授权,也就是发起登录请求
        platform.showUser(null);

    }
}
