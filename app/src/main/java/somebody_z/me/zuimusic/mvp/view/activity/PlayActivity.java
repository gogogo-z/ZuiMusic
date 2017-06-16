package somebody_z.me.zuimusic.mvp.view.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.PlayContract;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.presenter.PlayPresenter;
import somebody_z.me.zuimusic.utils.downloadmanager.DownLoadManager;
import somebody_z.me.zuimusic.utils.transformation.BlurTransformation;
import somebody_z.me.zuimusic.widget.lrcview.LrcView;
import somebody_z.me.zuimusic.widget.play.PlayView;
import somebody_z.me.zuimusic.widget.songlistdialog.SongListDialog;

/**
 * Created by Huanxing Zeng on 2017/3/7.
 * email : zenghuanxing123@163.com
 */
public class PlayActivity extends BaseActivity<PlayPresenter> implements PlayContract.PlayView {
    @Bind(R.id.iv_play_bg)
    ImageView ivPlayBg;
    @Bind(R.id.ll_play_back)
    LinearLayout llPlayBack;
    @Bind(R.id.ll_play_share)
    LinearLayout llPlayShare;
    @Bind(R.id.tv_play_title)
    TextView tvPlayTitle;
    @Bind(R.id.tv_play_artist)
    TextView tvPlayArtist;
    @Bind(R.id.ll_detail_title)
    LinearLayout llDetailTitle;
    @Bind(R.id.rl_play_top_bar)
    RelativeLayout rlPlayTopBar;
    @Bind(R.id.pb_play_progress)
    SeekBar pbPlayProgress;
    @Bind(R.id.btn_play_mode)
    Button btnPlayMode;
    @Bind(R.id.btn_play_previous)
    Button btnPlayPrevious;
    @Bind(R.id.btn_play_play)
    Button btnPlayPlay;
    @Bind(R.id.btn_play_next)
    Button btnPlayNext;
    @Bind(R.id.btn_play_more)
    Button btnPlayMore;
    @Bind(R.id.ll_play_bottom)
    LinearLayout llPlayBottom;
    @Bind(R.id.rl_play_root)
    RelativeLayout rlPlayRoot;
    @Bind(R.id.ll_play_top_bar)
    LinearLayout llPlayTopBar;
    @Bind(R.id.btn_play_get_cover_and_lrc)
    Button btnPlayGetCoverAndLrc;
    @Bind(R.id.rl_play_get_cover_and_lrc)
    RelativeLayout rlPlayGetCoverAndLrc;
    @Bind(R.id.btn_play_love)
    Button btnPlayLove;
    @Bind(R.id.btn_play_download)
    Button btnPlayDownload;
    @Bind(R.id.btn_play_comment)
    Button btnPlayComment;
    @Bind(R.id.btn_play_more_option)
    Button btnPlayMoreOption;
    @Bind(R.id.ll_play_bottom_operation)
    LinearLayout llPlayBottomOperation;
    @Bind(R.id.tv_play_start_time)
    TextView tvPlayStartTime;
    @Bind(R.id.tv_play_end_time)
    TextView tvPlayEndTime;
    @Bind(R.id.sb_play_volume)
    SeekBar sbPlayVolume;
    @Bind(R.id.lv_play_lrc_content)
    LrcView lvPlayLrcContent;
    @Bind(R.id.tv_play_lrc_empty)
    TextView tvPlayLrcEmpty;
    @Bind(R.id.ll_play_content_lrc)
    LinearLayout llPlayContentLrc;

    private PlayView playView;

    @Override
    protected PlayPresenter loadPresenter() {
        return new PlayPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.init();
        mPresenter.refreshTotalTime();
    }


    @Override
    protected void initListener() {
        lvPlayLrcContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showChange();
            }
        });

        pbPlayProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPresenter.playProgressChanged(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mPresenter.playStartTrackingTouch();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPresenter.playStopTrackingTouch();
            }
        });

        sbPlayVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPresenter.volumeProgressChanged(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play;
    }

    @Override
    public void initPlayView(List<ContentBean> songSheetDetailList, int index) {
        playView = new PlayView(this, songSheetDetailList, index);
        rlPlayRoot.addView(playView);

        playView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showChange();
            }
        });

        playView.setOnClickViewPagerListener(new PlayView.OnClickViewPagerListener() {
            @Override
            public void onClick() {
                mPresenter.showChange();
            }
        });
    }

    @Override
    public void setCurPlayCover(int index) {
        playView.setCurIndex(index);
    }

    @Override
    public void rotationNeedle(int state) {
        playView.rotationNeedle(state);
    }

    @Override
    public void setBg(String url) {
        //设置滤镜
        ivPlayBg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        //使用Glide第三方框架加载图片
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.play_default_cover_bg)//显示默认图片
                .bitmapTransform(new BlurTransformation(this))
                .into(ivPlayBg);
    }

    @Override
    public void setBg(Uri uri) {
        //设置滤镜
        ivPlayBg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        //使用Glide第三方框架加载图片
        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.play_default_cover_bg)//显示默认图片
                .bitmapTransform(new BlurTransformation(this))
                .into(ivPlayBg);
    }

    @Override
    public void setLrcContentVisible(int visible) {
        llPlayContentLrc.setVisibility(visible);
    }

    @Override
    public void setCoverAlbumVisible(int visible) {
        playView.setVisibility(visible);
    }

    @Override
    public void setGetCoverAndLrcVisible(int visible) {
        rlPlayGetCoverAndLrc.setVisibility(visible);
    }

    @Override
    public void setOperationVisible(int visible) {
        llPlayBottomOperation.setVisibility(visible);
    }

    @Override
    public void setLrcEmpty(int visible) {
        tvPlayLrcEmpty.setVisibility(visible);
    }

    @Override
    public void setCurTime(String curTime) {
        tvPlayStartTime.setText(curTime);
    }

    @Override
    public void setTotalTime(String totalTime) {
        tvPlayEndTime.setText(totalTime);
    }

    @Override
    public void setPlaySeekBarProgress(int progress) {
        pbPlayProgress.setProgress(progress);
    }

    @Override
    public void setPlaySeekBarSecondProgress(int secondProgress) {
        pbPlayProgress.setSecondaryProgress(secondProgress);
    }

    @Override
    public void setMusicTitle(String title) {
        tvPlayTitle.setText(title);
    }

    @Override
    public void setMusicAuthor(String author) {
        tvPlayArtist.setText(author);
    }

    @Override
    public void loadLrc(String fileName) {
        lvPlayLrcContent.loadLrc(getLrcText(fileName));
    }

    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            File f = new File(fileName);
            InputStream is = new FileInputStream(f);
            //    InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }

    @Override
    public void resetLrc() {
        lvPlayLrcContent.onDrag(0);
    }

    @Override
    public void updateLrc(long time) {
        lvPlayLrcContent.updateTime(time);
    }

    @Override
    public void setMaxVolume(int maxVolume) {
        sbPlayVolume.setMax(maxVolume);
    }

    @Override
    public void setCurrVolume(int currVolume) {
        sbPlayVolume.setProgress(currVolume);
    }

    @Override
    public void setModeBg(int resId) {
        btnPlayMode.setBackgroundResource(resId);
    }

    public void showPlay(boolean flag) {
        if (flag) {
            btnPlayPlay.setBackgroundResource(R.drawable.selector_play_play);
        } else {
            btnPlayPlay.setBackgroundResource(R.drawable.selector_play_pause);
        }
    }

    @OnClick({R.id.ll_play_back, R.id.ll_play_share, R.id.btn_play_get_cover_and_lrc, R.id.btn_play_love, R.id.btn_play_download,
            R.id.btn_play_comment, R.id.btn_play_more_option, R.id.btn_play_mode, R.id.btn_play_previous,
            R.id.btn_play_play, R.id.btn_play_next, R.id.btn_play_more, R.id.rl_play_root})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_play_back:
                finish();
                break;
            case R.id.ll_play_share:
                mPresenter.showShare();
                break;
            case R.id.btn_play_get_cover_and_lrc:
                break;
            case R.id.btn_play_love:
                break;
            case R.id.btn_play_download:

                mPresenter.downLoad();

                break;
            case R.id.btn_play_comment:
                break;
            case R.id.btn_play_more_option:
                break;
            case R.id.btn_play_mode:
                mPresenter.changeMode();
                break;
            case R.id.btn_play_previous:
                mPresenter.prev();
                break;
            case R.id.btn_play_play:
                mPresenter.play();
                break;
            case R.id.btn_play_next:
                mPresenter.next();
                break;
            case R.id.btn_play_more:
                //播放列表
                new SongListDialog().showDialog(this);
                break;
            case R.id.rl_play_root:
                mPresenter.showChange();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.unRegisterReceiver();
        super.onDestroy();
    }
}
