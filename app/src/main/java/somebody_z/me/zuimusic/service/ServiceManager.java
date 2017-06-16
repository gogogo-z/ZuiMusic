package somebody_z.me.zuimusic.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.IMediaService;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.utils.ServiceUtil;

/**
 * setvice控制类
 * <p/>
 * Created by Huanxing Zeng on 2017/3/14.
 * email : zenghuanxing123@163.com
 */
public class ServiceManager {

    public IMediaService mService;

    private Context mContext;
    private ServiceConnection mConn;
    private IOnServiceConnectComplete mIOnServiceConnectComplete;

    public ServiceManager(Context mContext) {
        this.mContext = mContext;
        initConn();
    }

    private void initConn() {
        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mService = IMediaService.Stub.asInterface(iBinder);
                if (mService != null) {
                    mIOnServiceConnectComplete.onServiceConnectComplete(mService);
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
    }

    public void connectService() {
        Intent intent = new Intent("somebody_z.me.zuimusic.service.MediaService");
        Intent serviceIntent = new Intent(ServiceUtil.createExplicitFromImplicitIntent(mContext, intent));
        mContext.bindService(serviceIntent, mConn, Context.BIND_AUTO_CREATE);
    }

    public void disConnectService() {
        mContext.unbindService(mConn);
        mContext.stopService(new Intent("somebody_z.me.zuimusic.service.MediaService"));
    }

    public void refreshMusicList(List<ContentBean> musicList) {
        if (musicList != null && mService != null) {
            try {
                mService.refreshMusicList(musicList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ContentBean> getMusicList() {
        List<ContentBean> musicList = new ArrayList<ContentBean>();
        try {
            if (mService != null) {
                mService.getMusicList(musicList);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return musicList;
    }

    public boolean play(int pos) {
        if (mService != null) {
            try {
                return mService.play(pos);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean playById(int id) {
        if (mService != null) {
            try {
                return mService.playById(id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean rePlay() {
        if (mService != null) {
            try {
                return mService.rePlay();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean pause() {
        if (mService != null) {
            try {
                return mService.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean prev() {
        if (mService != null) {
            try {
                return mService.prev();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean next() {
        if (mService != null) {
            try {
                return mService.next();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean seekTo(int progress) {
        if (mService != null) {
            try {
                return mService.seekTo(progress);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public int position() {
        if (mService != null) {
            try {
                return mService.position();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int duration() {
        if (mService != null) {
            try {
                return mService.duration();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int getPlayState() {
        if (mService != null) {
            try {
                int mode = mService.getPlayState();
                return mService.getPlayState();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void setPlayMode(int mode) {
        if (mService != null) {
            try {
                mService.setPlayMode(mode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPlayMode() {
        if (mService != null) {
            try {
                return mService.getPlayMode();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int getCurMusicId() {
        if (mService != null) {
            try {
                return mService.getCurMusicId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public ContentBean getCurMusic() {
        if (mService != null) {
            try {
                return mService.getCurMusic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getBufferProgress() {
        try {
            return mService.getBufferProgress();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void sendBroadcast() {
        if (mService != null) {
            try {
                mService.sendPlayStateBrocast();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void exit() {
        if (mService != null) {
            try {
                mService.exit();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mContext.unbindService(mConn);
        mContext.stopService(new Intent(Constants.SERVICE_NAME));
    }

    public void updateNotification(Bitmap bitmap, String title, String name) {
        try {
            mService.updateNotification(bitmap, title, name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void cancelNotification() {
        try {
            mService.cancelNotification();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setOnServiceConnectComplete(IOnServiceConnectComplete IServiceConnect) {
        mIOnServiceConnectComplete = IServiceConnect;
    }

}
