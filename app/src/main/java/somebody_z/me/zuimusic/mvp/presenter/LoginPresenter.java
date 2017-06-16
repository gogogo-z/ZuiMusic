package somebody_z.me.zuimusic.mvp.presenter;

import android.content.Intent;
import android.media.AudioManager;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import somebody_z.me.zuimusic.mvp.Contract.LoginContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.LoginModel;
import somebody_z.me.zuimusic.mvp.model.bean.LoginEvent;
import somebody_z.me.zuimusic.mvp.view.activity.LoginActivity;
import somebody_z.me.zuimusic.mvp.view.activity.RegisterActivity;
import somebody_z.me.zuimusic.mvp.view.activity.TelLoginActivity;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/14.
 */
public class LoginPresenter extends BasePresenter<LoginModel, LoginActivity> implements LoginContract.LoginPresenter {
    @Override
    public LoginModel loadModel() {
        return new LoginModel();
    }

    @Override
    public void init() {
        //注册订阅者
        EventBus.getDefault().register(this);

        getIView().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void unRegister() {
        //注销订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void jumpToLogin() {
        Intent intent = new Intent(getIView(), TelLoginActivity.class);
        getIView().startActivity(intent);
        //设置跳转无动画
        getIView().overridePendingTransition(0, 0);
    }

    @Override
    public void jumpToRegister() {
        Intent intent = new Intent(getIView(), RegisterActivity.class);
        getIView().startActivity(intent);
        //设置跳转无动画
        getIView().overridePendingTransition(0, 0);
    }


    @Subscribe
    public void onEventMainThread(LoginEvent event) {

        getIView().finish();

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
