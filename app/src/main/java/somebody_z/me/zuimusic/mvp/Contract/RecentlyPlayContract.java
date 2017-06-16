package somebody_z.me.zuimusic.mvp.Contract;

import android.support.v7.app.AlertDialog;

import java.util.List;

import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class RecentlyPlayContract {

    public interface RecentlyPlayView {
        void setBarMarginTop();

        void setRecentlyPlayAdapter(List<ContentBean> songSheetDetailList);

        void setAdapterListener();

        void refreshAdapter(List<ContentBean> contentBeanList);

        void refreshCount(int count);

        void initRecyclerView(int count);

        void initSongInfoDialog(ContentBean songInfo);

        void initCollectToSongSheetDialog(List<MySongSheet> mySongSheetList, ContentBean songInfo);

        void showCreateSongSheet(ContentBean songInfo);

        void setPrivateSongSheetChecked(boolean isChecked);

        void setCommitCanClick();

        void setCommitNotClick();

        void showMsg(String msg);

    }

    public interface RecentlyPlayPresenter {
        void isImmerse();

        void init();

        void showSongInfoDialog(ContentBean songInfo);

        void showCollectToSongSheetDialog(ContentBean songInfo);

        void collectToMySongSheet(MySongSheet mySongSheet, ContentBean songInfo);

        void showCreateNewSongSheetDialog(ContentBean songInfo);

        void setDialogMaxHeight(int height, AlertDialog dialog);

        void setCommitClickable(int count);

        void setPrivateSongSheetChecked();

        void setCheckedState(boolean isChecked);

        void createSongSheet(String songSheet, ContentBean songInfo);

        void jumpToSongSelect();

        void jumpToPlay(ContentBean songSheetDetail);

        void clearAll();

        void unRegisterReceiver();

        void registerReceiver();

        void onBackPressed();

        void download(ContentBean songInfo);

    }

}
