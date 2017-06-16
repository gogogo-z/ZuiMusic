package somebody_z.me.zuimusic.mvp.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.Contract.PlayContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.PlayModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.view.activity.PlayActivity;
import somebody_z.me.zuimusic.service.SPStorage;
import somebody_z.me.zuimusic.service.ServiceManager;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.downloadmanager.DownLoadManager;
import somebody_z.me.zuimusic.utils.MusicTimer;
import somebody_z.me.zuimusic.utils.MusicUtils;
import somebody_z.me.zuimusic.utils.downloadmanager.LrcBean;

/**
 * Created by Huanxing Zeng on 2017/3/7.
 * email : zenghuanxing123@163.com
 */
public class PlayPresenter extends BasePresenter<PlayModel, PlayActivity> implements PlayContract.PlayPresenter {

    private List<ContentBean> songSheetDetailList;

    private int index;

    private ServiceManager mServiceManager;

    private AudioManager mAudioManager;
    private int mMaxVolume;
    private int mCurVolume;

    private SPStorage mSp;

    private MusicTimer mMusicTimer;
    private int mProgress;
    public Handler mHandler;

    private boolean isCover = true;

    private Uri coverUri;

    private boolean mPlayAuto = true;

    private Bitmap mDefaultAlbumIcon;

    private int playState;

    private MusicPlayBroadcast mPlayBroadcast;

    private int animalStatus = 0;

    private boolean isFirst = true;

    private boolean isGetBuffer = true;

    private Handler handler = new Handler();

    private MyVolumeReceiver mVolumeReceiver;

    @Override
    public PlayModel loadModel() {
        return new PlayModel();
    }

    @Override
    public void init() {

        mServiceManager = MyApplication.mServiceManager;

        mPlayBroadcast = new MusicPlayBroadcast();
        IntentFilter filter = new IntentFilter(Constants.BROADCAST_NAME);
        filter.addAction(Constants.BROADCAST_NAME);
        getIView().registerReceiver(mPlayBroadcast, filter);

        mVolumeReceiver = new MyVolumeReceiver();
        IntentFilter filter_1 = new IntentFilter();
        filter_1.addAction("android.media.VOLUME_CHANGED_ACTION");
        getIView().registerReceiver(mVolumeReceiver, filter_1);

        mSp = new SPStorage(getIView());

        mAudioManager = (AudioManager) getIView().getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mCurVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        getIView().setMaxVolume(mMaxVolume);

        getIView().setCurrVolume(mCurVolume);

        getIView().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        refresh();

        mDefaultAlbumIcon = BitmapFactory.decodeResource(getIView().getResources(), R.drawable.note_default_cover);

        //初始化界面布局，隐藏歌词，显示封面
        getIView().setLrcContentVisible(View.GONE);
        getIView().setCoverAlbumVisible(View.VISIBLE);
        getIView().setGetCoverAndLrcVisible(View.GONE);
        getIView().setOperationVisible(View.VISIBLE);
        getIView().setLrcEmpty(View.GONE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                refreshSeekProgress(mServiceManager.position(), mServiceManager.duration());
            }
        };

        mMusicTimer = new MusicTimer(mHandler);

        //如果进来是播放状态，需要计时，更新播放按钮
        if (mServiceManager.getPlayState() == Constants.MPS_PLAYING) {
            mMusicTimer.startTimer();
            getIView().showPlay(false);
            // showAnimal(Constants.UP_ANIMAL);
            //  showAnimal(Constants.DOWN_ANIMAL);
        } else {
            //  showAnimal(Constants.UP_ANIMAL);
        }

        // 列表循环
        if (mServiceManager.getPlayMode() == Constants.MPM_LIST_LOOP_PLAY) {
            getIView().setModeBg(R.drawable.selector_play_loop);
        }

        // 随机播放
        if (mServiceManager.getPlayMode() == Constants.MPM_RANDOM_PLAY) {
            getIView().setModeBg(R.drawable.selector_play_random);
        }

        // 单曲循环
        if (mServiceManager.getPlayMode() == Constants.MPM_SINGLE_LOOP_PLAY) {
            getIView().setModeBg(R.drawable.selector_play_single);
        }

        //注册订阅者
        EventBus.getDefault().register(this);

    }

    @Override
    public void refresh() {
        songSheetDetailList = mServiceManager.getMusicList();

        index = MusicUtils.seekPosInListById(songSheetDetailList, mServiceManager.getCurMusicId());

        if (isFirst) {
            getIView().initPlayView(songSheetDetailList, index);
            isFirst = false;
        } else {
            getIView().setCurPlayCover(index);
        }

        if (mServiceManager.getCurMusic().getPic_url() == null) {
            coverUri = MusicUtils.getCoverUri(Long.valueOf(mServiceManager.getCurMusic().getAlbum_id()));

            getIView().setBg(coverUri);
        } else {
            getIView().setBg(mServiceManager.getCurMusic().getPic_url());
        }


        getIView().setMusicTitle(mServiceManager.getCurMusic().getTitle());

        String album = mServiceManager.getCurMusic().getAlbum_title();

        if (album != "") {
            album = " - " + album;
        }

        getIView().setMusicAuthor(mServiceManager.getCurMusic().getAuthor() + album);

        refreshTotalTime();

        refreshSeekProgress(mServiceManager.position(), mServiceManager.duration());

    }

    @Override
    public void play() {
        if (mServiceManager.getCurMusic() == null) {
            return;
        }

        if (mServiceManager.getPlayState() == Constants.MPS_PAUSE) {
            mServiceManager.rePlay();
            handler.post(runnable);
            if (animalStatus != Constants.DOWN_ANIMAL) {
                showAnimal(Constants.DOWN_ANIMAL);
            }
        } else if (mServiceManager.getPlayState() == Constants.MPS_PLAYING) {
            mServiceManager.pause();
            handler.removeCallbacks(runnable);
            if (animalStatus != Constants.UP_ANIMAL) {
                showAnimal(Constants.UP_ANIMAL);
            }
        }

        refresh();
    }

    @Override
    public void prev() {
        if (mServiceManager.getCurMusic() == null) {
            return;
        }
        mServiceManager.prev();
        isGetBuffer = true;

        handler.removeCallbacks(runnable);
        getIView().resetLrc();
        handler.post(runnable);

        if (animalStatus != Constants.DOWN_ANIMAL) {
            showAnimal(Constants.DOWN_ANIMAL);
        }

        refresh();

        downLoadLrc();
    }

    @Override
    public void next() {
        if (mServiceManager.getCurMusic() == null) {
            return;
        }
        mServiceManager.next();
        isGetBuffer = true;

        handler.removeCallbacks(runnable);
        getIView().resetLrc();
        handler.post(runnable);

        if (animalStatus != Constants.DOWN_ANIMAL) {
            showAnimal(Constants.DOWN_ANIMAL);
        }

        refresh();

        downLoadLrc();
    }

    @Override
    public void changeMode() {
        // 如果是列表循环，改为随机播放
        if (mServiceManager.getPlayMode() == Constants.MPM_LIST_LOOP_PLAY) {
            mServiceManager.setPlayMode(Constants.MPM_RANDOM_PLAY);
            getIView().setModeBg(R.drawable.selector_play_random);
            return;
        }

        // 如果是随机播放，改为单曲循环
        if (mServiceManager.getPlayMode() == Constants.MPM_RANDOM_PLAY) {
            mServiceManager.setPlayMode(Constants.MPM_SINGLE_LOOP_PLAY);
            getIView().setModeBg(R.drawable.selector_play_single);
            return;
        }

        // 如果是单曲循环，改为列表循环
        if (mServiceManager.getPlayMode() == Constants.MPM_SINGLE_LOOP_PLAY) {
            mServiceManager.setPlayMode(Constants.MPM_LIST_LOOP_PLAY);
            getIView().setModeBg(R.drawable.selector_play_loop);
            return;
        }

    }

    private boolean isfirst = true;

    @Override
    public void showChange() {

        if (isCover) {
            getIView().setLrcContentVisible(View.VISIBLE);
            getIView().setCoverAlbumVisible(View.GONE);
            getIView().setGetCoverAndLrcVisible(View.GONE);
            getIView().setOperationVisible(View.GONE);
            getIView().setLrcEmpty(View.GONE);

            if (isfirst) {
                downLoadLrc();

                isfirst = false;
            }

            isCover = false;
        } else {
            getIView().setLrcContentVisible(View.GONE);
            getIView().setCoverAlbumVisible(View.VISIBLE);
            getIView().setGetCoverAndLrcVisible(View.GONE);
            getIView().setOperationVisible(View.VISIBLE);
            getIView().setLrcEmpty(View.GONE);
            isCover = true;
        }

    }

    @Override
    public void refreshTotalTime() {
        int totalTime = mServiceManager.duration();

        totalTime /= 1000;
        int totalminute = totalTime / 60;
        int totalsecond = totalTime % 60;
        String totalTimeString = String.format("%02d:%02d", totalminute, totalsecond);

        getIView().setTotalTime(totalTimeString);
    }

    @Override
    public void playProgressChanged(int progress) {
        if (!mPlayAuto) {
            mProgress = progress;
            // mServiceManager.seekTo(progress);
            // refreshSeekProgress(mServiceManager.position(),
            // mServiceManager.duration());
        }
    }

    @Override
    public void playStartTrackingTouch() {
        mPlayAuto = false;
        mMusicTimer.stopTimer();
        mServiceManager.pause();
    }

    @Override
    public void playStopTrackingTouch() {
        mPlayAuto = true;
        mServiceManager.seekTo(mProgress);
        refreshSeekProgress(mServiceManager.position(), mServiceManager.duration());
        mServiceManager.rePlay();
        mMusicTimer.startTimer();
    }

    @Override
    public void volumeProgressChanged(int progress) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
    }

    @Override
    public void showAnimal(int status) {
        getIView().rotationNeedle(status);
        animalStatus = status;
    }

    @Override
    public void unRegisterReceiver() {
        if (mPlayBroadcast != null) {
            getIView().unregisterReceiver(mPlayBroadcast);
        }

        if (mVolumeReceiver != null) {
            getIView().unregisterReceiver(mVolumeReceiver);
        }

        handler.removeCallbacks(runnable);

        //注销订阅者
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void downLoad() {

        getIView().toast("已加入下载队列");

        ContentBean contentBean = mServiceManager.getCurMusic();

        DownLoadManager.getInstance().downLoad(getIView(), contentBean.getLocalUrl(), contentBean.getTitle() + " - " +
                contentBean.getAuthor() + ".mp3");

    }

    String lrc_path;

    public void downLoadLrc() {

        ContentBean contentBean = mServiceManager.getCurMusic();

        lrc_path = Environment.getExternalStorageDirectory().getPath() + "/zuimusic/" + contentBean.getTitle() + " - " +
                contentBean.getAuthor() + ".lrc";

        if (fileIsExists()) {
            getIView().loadLrc(lrc_path);

            handler.post(runnable);
        } else {
            if (contentBean.getLrc_url() != "") {
                DownLoadManager.getInstance().downLoad(getIView(), contentBean.getLrc_url(), contentBean.getTitle() + " - " +
                        contentBean.getAuthor() + ".lrc");
            }
        }

    }

    //判断文件是否存在
    public boolean fileIsExists() {
        try {
            File f = new File(lrc_path);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }


    @Subscribe
    public void onEventMainThread(LrcBean lrcBean) {
        if (lrcBean.getSongName().contains(".lrc")) {

            getIView().loadLrc(lrc_path);

            handler.post(runnable);

        }

    }

    public void refreshSeekProgress(int curTime, int totalTime) {
        curTime /= 1000;
        totalTime /= 1000;
        int curminute = curTime / 60;
        int cursecond = curTime % 60;

        String curTimeString = String.format("%02d:%02d", curminute, cursecond);
        try {
            getIView().setCurTime(curTimeString);
        } catch (Exception e) {

        }

        int rate = 0;
        if (totalTime != 0) {
            rate = (int) ((float) curTime / totalTime * 100);
        }

        try {
            getIView().setPlaySeekBarProgress(rate);
        } catch (Exception e) {

        }


        if (isGetBuffer) {
            //缓冲更新
            try {
                getIView().setPlaySeekBarSecondProgress(mServiceManager.getBufferProgress());
            } catch (Exception e) {

            }

            if (mServiceManager.getBufferProgress() == 100) {
                isGetBuffer = false;
            }
        }

        //mLyricLoadHelper.notifyTime(tempCurTime);
        //歌词更新
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mServiceManager.getPlayState() == Constants.MPS_PLAYING) {
                long time = mServiceManager.position();

                getIView().updateLrc(time);
            }

            handler.postDelayed(this, 100);
        }
    };

    private class MusicPlayBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.BROADCAST_NAME)) {
                ContentBean music = new ContentBean();
                playState = intent.getIntExtra(Constants.PLAY_STATE_NAME, Constants.MPS_NOFILE);
                int curPlayIndex = intent.getIntExtra(Constants.PLAY_MUSIC_INDEX, -1);
                Bundle bundle = intent.getBundleExtra(ContentBean.KEY_MUSIC);
                if (bundle != null) {
                    music = bundle.getParcelable(ContentBean.KEY_MUSIC);
                }

                switch (playState) {
                    case Constants.MPS_INVALID:// 考虑后面加上如果文件不可播放直接跳到下一首
                        mMusicTimer.stopTimer();
                        refresh();
                        getIView().showPlay(true);
                        break;
                    case Constants.MPS_PAUSE:
                        mMusicTimer.stopTimer();
                        refresh();
                        getIView().showPlay(true);
                        break;
                    case Constants.MPS_PLAYING:
                        mMusicTimer.startTimer();
                        refresh();
                        getIView().showPlay(false);
                        break;
                    case Constants.MPS_PREPARE:
                        mMusicTimer.stopTimer();
                        refresh();
                        getIView().showPlay(true);
                        downLoadLrc();
                        break;
                }

                if (music.getPic_url() == null) {

                    Bitmap bitmap = MusicUtils.getCachedArtwork(getIView(), Long.valueOf(music.getAlbum_id()),
                            mDefaultAlbumIcon);

                    mServiceManager.updateNotification(bitmap, music.getTitle(), music.getAuthor());

                } else {

                    final ContentBean music_back = music;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //使用Glide通过url获取bitmap
                                Bitmap bitmap = Glide.with(getIView())
                                        .load(music_back.getPic_url())
                                        .asBitmap() //必须
                                        .centerCrop()
                                        .into(200, 200)
                                        .get();
                                mServiceManager.updateNotification(bitmap, music_back.getTitle(), music_back.getAuthor());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }


            }
        }
    }

    /**
     * 处理音量变化时的界面显示
     *
     * @author long
     */
    private class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前的媒体音量
                getIView().setCurrVolume(currVolume);
            }
        }
    }

    /**
     * 分享
     */
    public void showShare() {
        ContentBean songInfo = mServiceManager.getCurMusic();

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(songInfo.getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(songInfo.getShare() + "");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(songInfo.getAuthor() + " - " + songInfo.getAlbum_title());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(songInfo.getPic_url());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(songInfo.getShare() + "");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("最音乐，动听音乐。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("最音乐");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(songInfo.getShare());

        // 启动分享GUI
        oks.show(getIView());
    }
}
