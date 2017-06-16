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
public class NewMusicDetailContract {

    public interface NewMusicDetailView {
        void setMarginTop();

        void setTitle(String title);

        void setAuthorAndAlbum(String str);

        void setRecommendReason(String recommendReason);

        void setCollectVisible(int resource);

        void setCoverAndBg(String url, String url_bg);

        boolean onBackPressed();

        void initCollectToSongSheetDialog(List<MySongSheet> mySongSheetList, final ContentBean songInfo);

        void showCreateSongSheet(final ContentBean songInfo);

        void setPrivateSongSheetChecked(boolean isChecked);

        void setCommitCanClick();

        void setCommitNotClick();

        void setIsCollect(String msg);

        void showConfirmCancelCollect();

        void showMsg(String msg);

    }

    public interface NewMusicDetailPresenter {
        void isImmerse();

        void init();

        void onBackPressed();

        void showCollectToSongSheetDialog();

        void collectToMySongSheet(MySongSheet mySongSheet, ContentBean songInfo);

        void showCreateNewSongSheetDialog(ContentBean songInfo);

        void setDialogMaxHeight(int height, AlertDialog dialog);

        void setCommitClickable(int count);

        void setPrivateSongSheetChecked();

        void setCheckedState(boolean isChecked);

        void createSongSheet(String songSheet, ContentBean songInfo);

        boolean isCollect();

        void setCollectVibsible();

        void delectCollectSong();

        void playSong();
    }

}
