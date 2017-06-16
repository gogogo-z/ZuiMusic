package somebody_z.me.zuimusic.mvp.Contract;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import somebody_z.me.zuimusic.db.CollectSongSheet;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.widget.ListViewForScrollView;

/**
 * Created by Huanxing Zeng on 2017/1/13.
 * email : zenghuanxing123@163.com
 */
public class MusicContract {

    public interface MusicView {
        void show(String msg);

        void setLocalMusicCount(String count);

        void setRecentPlayCount(String count);

        void setDownloadManagerCount(String count);

        void setMySingerCount(String count);

        void setMyMVCount(String count);

        void setCreateSongSheetCount(String count);

        void setCollectSongSheetCount(String count);

        void setCreateRotationCW();

        void setCreateRotationCCW();

        void setCollectRotationCW();

        void setCollectRotationCCW();

        void setMySongSheetAdapter(List<MySongSheet> mySongSheetList);

        void setCollectSongSheetAdapter(List<CollectSongSheet> collectSongSheetList);

        void showSongSheetPopupWindow(int type);

        void showSongSheetDetailPopupWindow(String songSheetName, String listID);

        void showCreateSongSheet();

        void setCreateSongSheetTitle();

        void setCollectSongSheetTitle();

        void setSongSheetDetailEditVisible();

        void setPrivateSongSheetChecked(boolean isChecked);

        void setCommitCanClick();

        void setCommitNotClick();

        void showComfirmDelete(String listID, String songSheetName);

        void hideCollectSongSheet();

        void showCollectSongSheet();

        void refresh();
    }

    public interface MusicPresenter {
        void isFirstUse();

        void init();

        void setCreateSongSheetVisible();

        void setCollectSongSheetVisible();

        void createSongSheet(String songSheet);

        void showSongSheet();

        void delectSongSheet(String songSheetName);

        void updateSongSheet(MySongSheet mySongSheet);

        void showCollectSongSheet();

        void delectCollectSongSheet(String title);

        void updateCollectSongSheet(CollectSongSheet collectSongSheet);

        void goToDetail(MySongSheet mySongSheet);

        void goToOption(MySongSheet mySongSheet);

        void goToCollectDetail(CollectSongSheet collectSongSheet);

        void jumpToDetail(Bundle bundle, Fragment fragment);

        void goToCollectOption(CollectSongSheet collectSongSheet);

        void showCreatSongSheetOption();

        void showCollectSongSheetOption();

        void createSongSheet();

        void setPopupTitle(int type);

        void showSongSheetDetailEdit(String listID);

        void deleteSongSheet(String listID, String songSheetName);

        void setPrivateSongSheetChecked();

        void setCheckedState(boolean isChecked);

        void setCommitClickable(int count);

        void showDeleteConfirm(String listID, String songSheetName);

        void setCollectSongSheetIsVisible(int size);

        void refresh();

        void jumpManager(Fragment fragment);

        void jumpToLocalMusic();

        void jumpToRecentPlay();

        void jumpToDownloadManeger();

        void jumpToMyPlayer();

        void jumpToMyMV();

        void jumpToSongSheetInfoManager();

        void jumpToSongSheetManager(int type);

        void unRegisterReceiver();

        void registerReceiver();

    }
}
