package somebody_z.me.zuimusic.mvp.Contract;

import android.support.v7.app.AlertDialog;

import java.util.List;

import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;

/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class SongSelectContract {
    public interface SongSelectView {
        void setSongSheetAdapter(List<ContentBean> songSheetDetailList);

        void setAdapterListener();

        void showMsg(String msg);

        void setChecked(boolean isChecked);

        void setSelectCount(String str);

        void setSelect(int str);

        void initCollectToSongSheetDialog(List<MySongSheet> mySongSheetList, final List<ContentBean> songInfoList);

        void showCreateSongSheet(final List<ContentBean> songInfoList);

        void setPrivateSongSheetChecked(boolean isChecked);

        void setCommitCanClick();

        void setCommitNotClick();
    }

    public interface SongSelectPresenter {
        void init();

        void setChecked();

        void addTiList(ContentBean songSheetDetail, boolean isChecked);

        boolean match(ContentBean songSheetDetail);

        void showCollectToSongSheetDialog();

        void setDialogMaxHeight(int height, AlertDialog dialog);

        void collectToMySongSheet(MySongSheet mySongSheet, List<ContentBean> songInfoList);

        void showCreateNewSongSheetDialog(List<ContentBean> songInfoList);

        void setCommitClickable(int count);

        void setPrivateSongSheetChecked();

        void setCheckedState(boolean isChecked);

        void createSongSheet(String songSheet, List<ContentBean> songInfoList);

        void download();

        void nextPlay();
    }
}
