package somebody_z.me.zuimusic.mvp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * 设置界面
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/15.
 */
public class SettingActivity extends BaseActivity {
    @Bind(R.id.rl_setting_about)
    RelativeLayout rlSettingAbout;
    @Bind(R.id.rl_setting_exit_account)
    RelativeLayout rlSettingExitAccount;
    @Bind(R.id.ll_setting_back)
    LinearLayout llSettingBack;

    private SharedPreferences sp;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

        sp = getSharedPreferences("Bottom_Bar", Activity.MODE_PRIVATE);

        boolean isLogin = sp.getBoolean("isLogin", false);

        if (!isLogin) {
            rlSettingExitAccount.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.rl_setting_about, R.id.rl_setting_exit_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_setting_about:
                toast("zuimusic是本人模仿网易云音乐的界面做的一款音乐播放器，采用的数据接口是百度音乐的接口。");
                break;
            case R.id.rl_setting_exit_account:
                showConfirmExitAccount();
                break;
        }
    }

    public void showConfirmExitAccount() {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_confirm_delete_song_sheet, null);

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);

        //创建对话框
        final AlertDialog dialog = builder.create();

        dialog.show();

        //设置宽高，需在show（）之后设置，否则没有效果。
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtil.getScreenWidth(this) * 8 / 9;
        dialog.getWindow().setAttributes(params);

        Button mbtnCancel, btnDelete;
        TextView tvContent;

        mbtnCancel = (Button) view.findViewById(R.id.btn_delete_song_sheet_cancel);
        btnDelete = (Button) view.findViewById(R.id.btn_delete_song_sheet_confirm);
        tvContent = (TextView) view.findViewById(R.id.tv_dialog_content);

        btnDelete.setText(R.string.exit);
        tvContent.setText(R.string.confirm_exit_account);

        //PS:初始化时设置button不可点击应设置android:enable=false,设置Android:clickable=false无效
        //因为button默认clickable=true。动态设置也是这样。

        mbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交
                dialog.cancel();
                //退出
                //发布一个事件
                EventBus.getDefault().post("exit_account");

                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    @OnClick(R.id.ll_setting_back)
    public void onClick() {
        finish();
    }
}
