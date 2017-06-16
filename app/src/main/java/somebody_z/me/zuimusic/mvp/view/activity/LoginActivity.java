package somebody_z.me.zuimusic.mvp.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.LoginContract;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.presenter.LoginPresenter;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * 登录界面
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/14.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {
    @Bind(R.id.rl_login_icon)
    RelativeLayout rlLoginIcon;
    @Bind(R.id.iv_login_icon_logo)
    ImageView ivLoginIconLogo;
    @Bind(R.id.btn_login_tel_login)
    Button btnLoginTelLogin;
    @Bind(R.id.btn_login_register)
    Button btnLoginRegister;
    @Bind(R.id.tv_bottom_line_desc)
    TextView tvBottomLineDesc;
    @Bind(R.id.iv_login_weixin)
    ImageView ivLoginWeixin;
    @Bind(R.id.iv_login_qq)
    ImageView ivLoginQq;
    @Bind(R.id.iv_login_weibo)
    ImageView ivLoginWeibo;
    @Bind(R.id.iv_login_neteasy)
    ImageView ivLoginNeteasy;

    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.init();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlLoginIcon.getLayoutParams();
        layoutParams.height = ScreenUtil.getScreenHeight(this) * 3 / 5;
        rlLoginIcon.setLayoutParams(layoutParams);


        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivLoginIconLogo.getLayoutParams();
        params.setMargins(0, ScreenUtil.getScreenHeight(this) / 6, 0, 0);
        ivLoginIconLogo.setLayoutParams(params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.btn_login_tel_login, R.id.btn_login_register, R.id.iv_login_weixin, R.id.iv_login_qq, R.id.iv_login_weibo, R.id.iv_login_neteasy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_tel_login:
                mPresenter.jumpToLogin();
                break;
            case R.id.btn_login_register:
                mPresenter.jumpToRegister();
                break;
            case R.id.iv_login_weixin:
                mPresenter.loginByShareSDK(Wechat.NAME, "weixin");
                break;
            case R.id.iv_login_qq:
                mPresenter.loginByShareSDK(QQ.NAME, "qq");
                break;
            case R.id.iv_login_weibo:
                mPresenter.loginByShareSDK(SinaWeibo.NAME, "weibo");
                break;
            case R.id.iv_login_neteasy:
                mPresenter.loginByShareSDK(QZone.NAME, "qzone");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.unRegister();
        super.onDestroy();
    }
}
