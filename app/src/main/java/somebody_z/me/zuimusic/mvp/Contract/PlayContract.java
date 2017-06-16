package somebody_z.me.zuimusic.mvp.Contract;

import android.net.Uri;

import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;

/**
 * Created by Huanxing Zeng on 2017/3/7.
 * email : zenghuanxing123@163.com
 */
public class PlayContract {


    public interface PlayView {
        void initPlayView(List<ContentBean> songSheetDetailList, int index);

        void setCurPlayCover(int index);

        void rotationNeedle(int state);

        void setBg(String url);

        void setBg(Uri uri);

        void setLrcContentVisible(int visible);

        void setCoverAlbumVisible(int visible);

        void setGetCoverAndLrcVisible(int visible);

        void setOperationVisible(int visible);

        void setLrcEmpty(int visible);

        void setCurTime(String curTime);

        void setTotalTime(String totalTime);

        void setPlaySeekBarProgress(int progress);

        void setPlaySeekBarSecondProgress(int secondProgress);

        void setMusicTitle(String title);

        void setMusicAuthor(String author);

        void loadLrc(String fileName);

        void resetLrc();

        void updateLrc(long time);

        void setMaxVolume(int maxVolume);

        void setCurrVolume(int currVolume);

        void setModeBg(int resId);
    }

    public interface PlayPresenter {

        void init();

        void refresh();

        void play();

        void prev();

        void next();

        void changeMode();

        void showChange();

        void refreshTotalTime();

        void playProgressChanged(int progress);

        void playStartTrackingTouch();

        void playStopTrackingTouch();

        void volumeProgressChanged(int progress);

        void showAnimal(int status);

        void unRegisterReceiver();

        void downLoad();
    }

}
