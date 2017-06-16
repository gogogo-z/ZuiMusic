package somebody_z.me.zuimusic.mvp.Contract;

import android.support.v7.app.AlertDialog;

import java.util.List;

import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/16.
 */
public class SearchContract {

    public interface SearchView {
        void setBarMarginTop();

        void setSongAdapter(List<ContentBean> songList);

        void setAdapterListener();

        void initSongInfoDialog(ContentBean songInfo);

        void initCollectToSongSheetDialog(List<MySongSheet> mySongSheetList, ContentBean songInfo);

        void showCreateSongSheet(ContentBean songInfo);

        void setPrivateSongSheetChecked(boolean isChecked);

        void setCommitCanClick();

        void setCommitNotClick();

    }

    public interface SearchPresenter {
        void isImmerse();

        void init();

        void search(String content);

        void play(ContentBean songSheetDetail);

        void showSongInfoDialog(ContentBean songSheetDetail);

        void showCollectToSongSheetDialog(ContentBean songInfo);

        void collectToMySongSheet(MySongSheet mySongSheet, ContentBean songInfo);

        void showCreateNewSongSheetDialog(ContentBean songInfo);

        void setDialogMaxHeight(int height, AlertDialog dialog);

        void setCommitClickable(int count);

        void setPrivateSongSheetChecked();

        void setCheckedState(boolean isChecked);

        void createSongSheet(String songSheet, ContentBean songInfo);

        void download(ContentBean songInfo);
    }

}
