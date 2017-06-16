package somebody_z.me.zuimusic.mvp.view.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import butterknife.Bind;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;

/**
 * Created by Huanxing Zeng on 2017/1/25.
 * email : zenghuanxing123@163.com
 */
public class BannerActivity extends BaseActivity {
    @Bind(R.id.banner_content_iv)
    ImageView bannerContentIv;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BannerActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

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
        return R.layout.activity_banner;
    }

}
