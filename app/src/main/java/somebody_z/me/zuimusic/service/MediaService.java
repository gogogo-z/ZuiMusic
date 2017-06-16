package somebody_z.me.zuimusic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.RemoteViews;

import java.util.List;

import somebody_z.me.zuimusic.IMediaService;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.view.activity.PlayActivity;

/**
 * Created by Huanxing Zeng on 2017/3/14.
 * email : zenghuanxing123@163.com
 */
public class MediaService extends Service implements ShakeDetector.OnShakeListener {

    private static final String PLAY_BROADCAST_NAME = "somebody_z.me.zuimusic.play.broadcast";
    private static final String PAUSE_BROADCAST_NAME = "somebody_z.me.zuimusic.pause.broadcast";
    private static final String NEXT_BROADCAST_NAME = "somebody_z.me.zuimusic.next.broadcast";
    private static final String PRE_BROADCAST_NAME = "somebody_z.me.zuimusic.pre.broadcast";
    private static final int PAUSE_FLAG = 0x1;
    private static final int NEXT_FLAG = 0x2;
    private static final int PRE_FLAG = 0x3;
    private static final int PLAY_FLAG = 0x4;

    private MusicController mMc;
    private NotificationManager mNotificationManager;
    private int NOTIFICATION_ID = 0x1;
    private RemoteViews rv; // 跨进程显示的view，比如notification中自定义view
    private ShakeDetector mShakeDetector;
    /**
     * 当前是否正在播放
     */
    private boolean mIsPlaying;
    /**
     * 在设置界面是否开启了摇一摇的监听
     */
    public boolean mShake;
    private SPStorage mSp;
    private ControlBroadcast mConrolBroadcast;
    private MusicPlayBroadcast mPlayBroadcast;

    @Override
    public void onCreate() {
        super.onCreate();

        mMc = new MusicController(this);
        mSp = new SPStorage(this);
        mShakeDetector = new ShakeDetector(this);
        mShakeDetector.setOnShakeListener(this); // 摇一摇监听
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mConrolBroadcast = new ControlBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PLAY_BROADCAST_NAME);
        filter.addAction(PAUSE_BROADCAST_NAME);
        filter.addAction(NEXT_BROADCAST_NAME);
        filter.addAction(PRE_BROADCAST_NAME);
        registerReceiver(mConrolBroadcast, filter);

        mPlayBroadcast = new MusicPlayBroadcast();
        IntentFilter filter1 = new IntentFilter(Constants.BROADCAST_NAME);
        filter1.addAction(Constants.BROADCAST_SHAKE);
        registerReceiver(mPlayBroadcast, filter1);
    }

    /**
     * 更新notification，作为前台服务
     *
     * @param title
     * @param name
     */
    private void updateNotification(Bitmap bitmap, String title, String name) {
        Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rv = new RemoteViews(this.getPackageName(), R.layout.notification);
        Notification notification = new Notification();
        notification.icon = R.drawable.logo;
        notification.tickerText = title;
        notification.contentIntent = pi;
        notification.contentView = rv;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;

        if (bitmap != null) {
            rv.setImageViewBitmap(R.id.image, bitmap);
        } else {
            rv.setImageViewResource(R.id.image, R.drawable.note_default_cover);
        }

        rv.setTextViewText(R.id.title, title);
        rv.setTextViewText(R.id.text, name);

        // 此处action不能是一样的 如果一样的 接受的flag参数只是第一个设置的值
        if (mMc.getPlayState() == Constants.MPS_PAUSE) {
            rv.setImageViewResource(R.id.iv_pause, R.drawable.note_btn_play);
            Intent playIntent = new Intent(PLAY_BROADCAST_NAME);
            playIntent.putExtra("FLAG", PLAY_FLAG);
            PendingIntent playPIntent = PendingIntent.getBroadcast(this, 0, playIntent, 0);
            rv.setOnClickPendingIntent(R.id.iv_pause, playPIntent);
        } else if (mMc.getPlayState() == Constants.MPS_PLAYING) {
            rv.setImageViewResource(R.id.iv_pause, R.drawable.note_btn_pause);
            Intent pauseIntent = new Intent(PAUSE_BROADCAST_NAME);
            pauseIntent.putExtra("FLAG", PAUSE_FLAG);
            PendingIntent pausePIntent = PendingIntent.getBroadcast(this, 0, pauseIntent, 0);
            rv.setOnClickPendingIntent(R.id.iv_pause, pausePIntent);
        }

        Intent nextIntent = new Intent(NEXT_BROADCAST_NAME);
        nextIntent.putExtra("FLAG", NEXT_FLAG);
        PendingIntent nextPIntent = PendingIntent.getBroadcast(this, 0, nextIntent, 0);
        rv.setOnClickPendingIntent(R.id.iv_next, nextPIntent);

        startForeground(NOTIFICATION_ID, notification); // 开启前台服务
    }

    private class MusicPlayBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.BROADCAST_NAME)) {
                int playState = intent.getIntExtra(Constants.PLAY_STATE_NAME, Constants.MPS_NOFILE);
                switch (playState) {
                    case Constants.MPS_PLAYING:
                        mIsPlaying = true;
                        if (mSp.getShake()) {
                            mShakeDetector.start();
                        }
                        break;
                    default:
                        mIsPlaying = false;
                        mShakeDetector.stop();
                }
            } else if (intent.getAction().equals(Constants.BROADCAST_SHAKE)) {
                mShake = intent.getBooleanExtra(Constants.SHAKE_ON_OFF, false);
                if (mShake && mIsPlaying) {// 如果开启了监听并且歌曲正在播放
                    mShakeDetector.start();
                } else if (!mShake) {
                    mShakeDetector.stop();
                }
            }
        }
    }

    private class ControlBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int flag = intent.getIntExtra("FLAG", -1);
            switch (flag) {
                case PAUSE_FLAG:
                    mMc.pause();
                    break;
                case NEXT_FLAG:
                    mMc.next();
                    break;
                case PRE_FLAG:
                    mMc.prev();
                    break;
                case PLAY_FLAG:
                    mMc.replay();
                    break;
            }
        }
    }

    private void cancelNotification() {
        stopForeground(true);
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private class ServerStub extends IMediaService.Stub {

        @Override
        public boolean pause() throws RemoteException {
            // MediaService.this.stopForeground(true);
            return mMc.pause();
        }

        @Override
        public boolean prev() throws RemoteException {
            return mMc.prev();
        }

        @Override
        public boolean next() throws RemoteException {
            return mMc.next();
        }

        @Override
        public boolean play(int pos) throws RemoteException {
            return mMc.play(pos);
        }

        @Override
        public int duration() throws RemoteException {
            return mMc.duration();
        }

        @Override
        public int position() throws RemoteException {
            return mMc.position();
        }

        @Override
        public boolean seekTo(int progress) throws RemoteException {
            return mMc.seekTo(progress);
        }

        @Override
        public void refreshMusicList(List<ContentBean> musicList) throws RemoteException {
            mMc.refreshMusicList(musicList);
        }

        @Override
        public void getMusicList(List<ContentBean> musicList) throws RemoteException {
            List<ContentBean> music = mMc.getMusicList();
            for (ContentBean m : music) {
                musicList.add(m);
            }
        }

        @Override
        public int getPlayState() throws RemoteException {
            return mMc.getPlayState();
        }

        @Override
        public int getPlayMode() throws RemoteException {
            return mMc.getPlayMode();
        }

        @Override
        public void setPlayMode(int mode) throws RemoteException {
            mMc.setPlayMode(mode);
        }

        @Override
        public void sendPlayStateBrocast() throws RemoteException {
            mMc.sendBroadCast();
        }

        @Override
        public void exit() throws RemoteException {
            cancelNotification();
            stopSelf();
            mMc.exit();
        }

        @Override
        public boolean rePlay() throws RemoteException {
            return mMc.replay();
        }

        @Override
        public int getCurMusicId() throws RemoteException {
            return mMc.getCurMusicId();
        }

        @Override
        public int getBufferProgress() throws RemoteException {
            return mMc.getBufferProgress();
        }

        @Override
        public void updateNotification(Bitmap bitmap, String title, String name) throws RemoteException {
            MediaService.this.updateNotification(bitmap, title, name);
        }

        @Override
        public void cancelNotification() throws RemoteException {
            MediaService.this.cancelNotification();
        }

        @Override
        public boolean playById(int id) throws RemoteException {
            return mMc.playById(id);
        }

        @Override
        public ContentBean getCurMusic() throws RemoteException {
            return mMc.getCurMusic();
        }

    }

    private final IBinder mBinder = new ServerStub();

    @Override
    public void onShake() {
        mMc.next();
    }

    @Override
    public void onDestroy() {
        if (mConrolBroadcast != null) {
            unregisterReceiver(mConrolBroadcast);
        }
        if (mPlayBroadcast != null) {
            unregisterReceiver(mPlayBroadcast);
        }
        super.onDestroy();
    }

}
