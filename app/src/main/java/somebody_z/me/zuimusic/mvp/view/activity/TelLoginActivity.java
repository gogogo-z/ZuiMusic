package somebody_z.me.zuimusic.mvp.view.activity;

import android.media.AudioManager;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.TelLoginContract;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.presenter.TelLoginPresenter;
import somebody_z.me.zuimusic.widget.ZEditText;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/15.
 */
public class TelLoginActivity extends BaseActivity<TelLoginPresenter> implements TelLoginContract.TelLoginView {
    @Bind(R.id.ll_tel_login_back)
    LinearLayout llTelLoginBack;
    @Bind(R.id.zt_tel_login_tel)
    ZEditText ztTelLoginTel;
    @Bind(R.id.zt_tel_login_pwd)
    ZEditText ztTelLoginPwd;
    @Bind(R.id.tv_tel_login_login)
    TextView tvTelLoginLogin;

    @Override
    protected TelLoginPresenter loadPresenter() {
        return new TelLoginPresenter();
    }

    @Override
    protected void initData() {
        ztTelLoginTel.getFocus();

        //对于刚跳到一个新的界面就要弹出软键盘的情况上述代码可能由于界面为加载完全而无法弹出软键盘。
        //此时应该适当的延迟弹出软键盘保证界面的数据加载完成
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               ztTelLoginTel.showSoftInput();
                           }

                       },
                100);

    }

    @Override
    protected void initListener() {

        ztTelLoginTel.setOnChangeTextListener(new ZEditText.OnChangeTextListener() {
            @Override
            public void setTextContent(String content) {

            }
        });

        ztTelLoginPwd.setOnChangeTextListener(new ZEditText.OnChangeTextListener() {
            @Override
            public void setTextContent(String content) {

            }
        });

        ztTelLoginTel.setInputType(InputType.TYPE_CLASS_PHONE);
        ztTelLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    @Override
    protected void initView() {
        ztTelLoginTel.setHint("请输入手机号");
        ztTelLoginPwd.setHint("请输入密码");
        ztTelLoginPwd.setDefaultColor(R.color.colorGray);
        ztTelLoginPwd.setFocusColor(R.color.colorBlack);
        ztTelLoginTel.setDefaultColor(R.color.colorGray);
        ztTelLoginTel.setFocusColor(R.color.colorBlack);

        ztTelLoginTel.setClearRes(R.drawable.et_login_clear);
        ztTelLoginPwd.setClearRes(R.drawable.et_login_clear);

        ztTelLoginTel.setHintColor(R.color.colorMiddleGray);
        ztTelLoginPwd.setHintColor(R.color.colorMiddleGray);

        ztTelLoginTel.setEditTextColor(R.color.colorBlack);
        ztTelLoginPwd.setEditTextColor(R.color.colorBlack);

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
        return R.layout.activity_tel_login;
    }

    @OnClick({R.id.ll_tel_login_back, R.id.tv_tel_login_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tel_login_back:
                finish();
                break;
            case R.id.tv_tel_login_login:
                mPresenter.login();
                break;
        }
    }

    @Override
    public String getUserName() {
        return ztTelLoginTel.getText();
    }

    @Override
    public String getUserPwd() {
        return ztTelLoginPwd.getText();
    }
}
