package somebody_z.me.zuimusic.mvp.Contract;

import android.support.v7.app.AlertDialog;

import java.util.List;

import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;

/**
 * Created by Huanxing Zeng on 2017/2/27.
 * email : zenghuanxing123@163.com
 */
public class LocalSongContract {

    public interface LocalSongView {
        void setSongSheetAdapter(List<ContentBean> songSheetDetailList);

        void setAdapterListener();

        void setPlaying(String localUrl);

        void initRecyclerView(int count);

        void initSongInfoDialog(final ContentBean songInfo);

        void showCreateSongSheet(final ContentBean songInfo);

        void setPrivateSongSheetChecked(boolean isChecked);

        void initCollectToSongSheetDialog(List<MySongSheet> mySongSheetList, final ContentBean songInfo);

        void setCommitCanClick();

        void setCommitNotClick();

        void showMsg(String msg);

        void setNoneVisible(int visible);

        void setIndexBarMargin(int margin);
    }

    public interface LocalSongPresenter {
        void init();

        void showSongInfoDialog(ContentBean songInfo);

        void showCollectToSongSheetDialog(ContentBean songInfo);

        void setDialogMaxHeight(int height, AlertDialog dialog);

        void collectToMySongSheet(MySongSheet mySongSheet, ContentBean songInfo);

        void showCreateNewSongSheetDialog(ContentBean songInfo);

        void setCommitClickable(int count);

        void setPrivateSongSheetChecked();

        void setCheckedState(boolean isChecked);

        void createSongSheet(String songSheet, ContentBean songInfo);

        void jumpToSongSelect();

        void jumpToPlay(ContentBean songSheetDetail);

        void unRegisterReceiver();

        void registerReceiver();

    }

}
