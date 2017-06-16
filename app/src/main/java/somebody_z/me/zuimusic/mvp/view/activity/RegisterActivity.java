package somebody_z.me.zuimusic.mvp.view.activity;

import android.media.AudioManager;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.RegisterContract;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.presenter.RegisterPresenter;
import somebody_z.me.zuimusic.widget.ZEditText;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/15.
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.RegisterView {
    @Bind(R.id.ll_tel_register_back)
    LinearLayout llTelRegisterBack;
    @Bind(R.id.zt_tel_register_tel)
    ZEditText ztTelRegisterTel;
    @Bind(R.id.zt_tel_register_pwd)
    ZEditText ztTelRegisterPwd;
    @Bind(R.id.tv_tel_register_next)
    TextView tvTelRegisterNext;
    @Bind(R.id.tv_bottom_line_desc)
    TextView tvBottomLineDesc;
    @Bind(R.id.iv_register_weixin)
    ImageView ivRegisterWeixin;
    @Bind(R.id.iv_register_qq)
    ImageView ivRegisterQq;
    @Bind(R.id.iv_register_weibo)
    ImageView ivRegisterWeibo;
    @Bind(R.id.iv_register_neteasy)
    ImageView ivRegisterNeteasy;

    @Override
    protected RegisterPresenter loadPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ztTelRegisterTel.setOnChangeTextListener(new ZEditText.OnChangeTextListener() {
            @Override
            public void setTextContent(String content) {

            }
        });

        ztTelRegisterPwd.setOnChangeTextListener(new ZEditText.OnChangeTextListener() {
            @Override
            public void setTextContent(String content) {

            }
        });

        ztTelRegisterTel.setInputType(InputType.TYPE_CLASS_PHONE);
        ztTelRegisterPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    @Override
    protected void initView() {
        ztTelRegisterTel.setHint("请输入手机号");
        ztTelRegisterPwd.setHint("设置登录密码，不少于6位");
        ztTelRegisterPwd.setDefaultColor(R.color.colorGray);
        ztTelRegisterPwd.setFocusColor(R.color.colorBlack);
        ztTelRegisterTel.setDefaultColor(R.color.colorGray);
        ztTelRegisterTel.setFocusColor(R.color.colorBlack);

        ztTelRegisterTel.setClearRes(R.drawable.et_login_clear);
        ztTelRegisterPwd.setClearRes(R.drawable.et_login_clear);

        ztTelRegisterTel.setHintColor(R.color.colorMiddleGray);
        ztTelRegisterPwd.setHintColor(R.color.colorMiddleGray);

        ztTelRegisterTel.setEditTextColor(R.color.colorBlack);
        ztTelRegisterPwd.setEditTextColor(R.color.colorBlack);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onPause() {
        //设置跳转无动画
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public String getUserName() {
        return ztTelRegisterTel.getText();
    }

    @Override
    public String getUserPwd() {
        return ztTelRegisterPwd.getText();
    }

    @OnClick({R.id.ll_tel_register_back, R.id.tv_tel_register_next, R.id.iv_register_weixin, R.id.iv_register_qq, R.id.iv_register_weibo, R.id.iv_register_neteasy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tel_register_back:
                finish();
                break;
            case R.id.tv_tel_register_next:
                mPresenter.register();
                break;
            case R.id.iv_register_weixin:
                mPresenter.loginByShareSDK(Wechat.NAME, "weixin");
                break;
            case R.id.iv_register_qq:
                mPresenter.loginByShareSDK(QQ.NAME, "qq");
                break;
            case R.id.iv_register_weibo:
                mPresenter.loginByShareSDK(SinaWeibo.NAME, "weibo");
                break;
            case R.id.iv_register_neteasy:
                mPresenter.loginByShareSDK(QZone.NAME, "qzone");
                break;
        }
    }
}
