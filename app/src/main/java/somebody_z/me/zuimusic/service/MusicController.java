package somebody_z.me.zuimusic.service;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.RecentlyPlayManager;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.utils.MusicUtils;

/**
 * Created by Huanxing Zeng on 2017/3/14.
 * email : zenghuanxing123@163.com
 */
public class MusicController implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mMediaPlayer; // 安卓提供的用于媒体播放控制的类
    private int mPlayMode;
    private List<ContentBean> mMusicList = new ArrayList<ContentBean>();
    private int mPlayState;

    //可以设置一个获取方法
    private int mCurPlayIndex;
    private Context mContext;
    private Random mRandom;
    private int mCurMusicId;
    private ContentBean mCurMusic;

    private int bufferProgress = 0;

    public MusicController(Context context) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnCompletionListener(this); // 监听播放过程

        mMediaPlayer.setOnBufferingUpdateListener(this); //监听缓冲

        mPlayMode = Constants.MPM_LIST_LOOP_PLAY; // 默认为列表循环
        mPlayState = Constants.MPS_NOFILE;
        mCurPlayIndex = -1;
        mCurMusicId = -1;
        this.mContext = context;
        mRandom = new Random();
        mRandom.setSeed(System.currentTimeMillis());
    }

    public boolean play(int pos) {
        if (mCurPlayIndex == pos) {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
                mPlayState = Constants.MPS_PLAYING;
                sendBroadCast();
            } else {
                pause();
            }
            return true;
        }
        if (!prepare(pos)) {
            return false;
        }
        return replay();
    }

    /**
     * 根据歌曲的id来播放
     *
     * @param id
     * @return
     */
    public boolean playById(int id) {
        int position = MusicUtils.seekPosInListById(mMusicList, id);
        mCurPlayIndex = position;
        if (mCurMusicId == id) {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
                mPlayState = Constants.MPS_PLAYING;
                sendBroadCast();
            } else {
                pause();
            }
            return true;
        }

        if (!prepare(position)) {
            return false;
        }
        return replay();
    }

    public boolean replay() {
        if (mPlayState == Constants.MPS_INVALID || mPlayState == Constants.MPS_NOFILE) {
            return false;
        }

        mMediaPlayer.start();
        mPlayState = Constants.MPS_PLAYING;
        sendBroadCast();
        return true;
    }

    public boolean pause() {
        if (mPlayState != Constants.MPS_PLAYING) {
            return false;
        }
        mMediaPlayer.pause();
        mPlayState = Constants.MPS_PAUSE;
        sendBroadCast();
        return true;
    }

    public boolean prev() {
        if (mPlayState == Constants.MPS_NOFILE) {
            return false;
        }
        mCurPlayIndex--;
        mCurPlayIndex = reviseIndex(mCurPlayIndex);
        if (!prepare(mCurPlayIndex)) {
            return false;
        }
        return replay();
    }

    public boolean next() {
        if (mPlayState == Constants.MPS_NOFILE) {
            return false;
        }
        mCurPlayIndex++;
        mCurPlayIndex = reviseIndex(mCurPlayIndex);
        if (!prepare(mCurPlayIndex)) {
            return false;
        }
        return replay();
    }

    private int reviseIndex(int index) {
        if (index < 0) {
            index = mMusicList.size() - 1;
        }
        if (index >= mMusicList.size()) {
            index = 0;
        }
        return index;
    }

    public int position() {
        if (mPlayState == Constants.MPS_PLAYING || mPlayState == Constants.MPS_PAUSE) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 毫秒
     *
     * @return
     */
    public int duration() {
        if (mPlayState == Constants.MPS_INVALID || mPlayState == Constants.MPS_NOFILE) {
            return 0;
        }
        return mMediaPlayer.getDuration();
    }

    public boolean seekTo(int progress) {
        if (mPlayState == Constants.MPS_INVALID || mPlayState == Constants.MPS_NOFILE) {
            return false;
        }
        int pro = reviseSeekValue(progress);
        int time = mMediaPlayer.getDuration();
        int curTime = (int) ((float) pro / 100 * time);
        mMediaPlayer.seekTo(curTime);
        return true;
    }

    private int reviseSeekValue(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }
        return progress;
    }

    public void refreshMusicList(List<ContentBean> musicList) {
        mMusicList.clear();
        mMusicList.addAll(musicList);
        if (mMusicList.size() == 0) {
            mPlayState = Constants.MPS_NOFILE;
            mCurPlayIndex = -1;
            return;
        }

    }

    private boolean prepare(final int pos) {
        mCurPlayIndex = pos;
        mMediaPlayer.reset();
        String path = mMusicList.get(pos).getLocalUrl();

        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mPlayState = Constants.MPS_PREPARE;

            if (searchRecentlyPlay(mMusicList.get(pos))) {
                deleteRecentlyPlay(mMusicList.get(pos));
            }
            addRecentlyPlay(mMusicList.get(pos));

        } catch (Exception e) {
            mPlayState = Constants.MPS_INVALID;
            if (pos < mMusicList.size()) {

                playById(Integer.valueOf(mMusicList.get(pos + 1).getSong_id()));
            }
            // sendBroadCast();
            return false;
        }
        sendBroadCast();
        return true;
    }

    public void sendBroadCast() {
        Intent intent = new Intent(Constants.BROADCAST_NAME);
        intent.putExtra(Constants.PLAY_STATE_NAME, mPlayState);
        intent.putExtra(Constants.PLAY_MUSIC_INDEX, mCurPlayIndex);
        intent.putExtra("music_num", mMusicList.size());
        if (mPlayState != Constants.MPS_NOFILE && mMusicList.size() > 0) {
            Bundle bundle = new Bundle();
            mCurMusic = mMusicList.get(mCurPlayIndex);
            mCurMusicId = Integer.valueOf(mCurMusic.getSong_id());
            bundle.putParcelable(ContentBean.KEY_MUSIC, mCurMusic);
            intent.putExtra(ContentBean.KEY_MUSIC, bundle);
        }
        mContext.sendBroadcast(intent);
    }

    public int getCurMusicId() {
        return mCurMusicId;
    }

    public ContentBean getCurMusic() {
        return mCurMusic;
    }

    public int getPlayState() {
        return mPlayState;
    }

    public int getPlayMode() {
        return mPlayMode;
    }

    public void setPlayMode(int mode) {
        switch (mode) {
            case Constants.MPM_LIST_LOOP_PLAY: // 列表循环
            case Constants.MPM_ORDER_PLAY:
            case Constants.MPM_RANDOM_PLAY: // 随机播放
            case Constants.MPM_SINGLE_LOOP_PLAY: // 单曲循环
                mPlayMode = mode;
                break;
        }
    }

    public List<ContentBean> getMusicList() {
        return mMusicList;
    }

    /**
     * 播放模式下的歌曲切换监听
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (mPlayMode) {
            case Constants.MPM_LIST_LOOP_PLAY:
                next();
                break;
            case Constants.MPM_ORDER_PLAY:
                if (mCurPlayIndex != mMusicList.size() - 1) {
                    next();
                } else {
                    prepare(mCurPlayIndex);
                }
                break;
            case Constants.MPM_RANDOM_PLAY:
                int index = getRandomIndex();
                if (index != -1) {
                    mCurPlayIndex = index;
                } else {
                    mCurPlayIndex = 0;
                }
                if (prepare(mCurPlayIndex)) {
                    replay();
                }
                break;
            case Constants.MPM_SINGLE_LOOP_PLAY:
                play(mCurPlayIndex);
                break;
        }
    }

    private int getRandomIndex() {
        int size = mMusicList.size();
        if (size == 0) {
            return -1;
        }
        return Math.abs(mRandom.nextInt() % size);
    }

    public void exit() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mCurPlayIndex = -1;
        mMusicList.clear();
    }

    public void addRecentlyPlay(ContentBean contentBean) {
        RecentlyPlayManager.getInstance().addToRecentlyPlay(contentBean, mContext);
    }

    public boolean searchRecentlyPlay(ContentBean contentBean) {
        if (RecentlyPlayManager.getInstance().searchRecentlyPlay(contentBean.getTitle(), mContext) == null) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteRecentlyPlay(ContentBean contentBean) {
        RecentlyPlayManager.getInstance().deleteRecentlyPlay(contentBean, mContext);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        bufferProgress = percent;
    }

    public int getBufferProgress() {
        return bufferProgress;
    }
}
