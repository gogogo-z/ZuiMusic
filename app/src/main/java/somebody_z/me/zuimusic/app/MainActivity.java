package somebody_z.me.zuimusic.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.view.activity.BannerActivity;
import somebody_z.me.zuimusic.mvp.view.activity.GuideActivity;
import somebody_z.me.zuimusic.mvp.view.activity.HomeActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        //获取保存第一次使用的配置文件
        SharedPreferences preferences = getSharedPreferences(Constants.FLAG_FIRST_USED, Context.MODE_PRIVATE);

        boolean firstUsed = preferences.getBoolean(Constants.FLAG_FIRST_USED_VALUE, true);

        //如果是第一次使用，那么跳转到引导页
        if (firstUsed) {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, BannerActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
