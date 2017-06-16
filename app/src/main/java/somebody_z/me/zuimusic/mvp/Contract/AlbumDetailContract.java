package somebody_z.me.zuimusic.mvp.Contract;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.List;

import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;

/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class AlbumDetailContract {

    public interface AlbumDetailView {
        void setToolBarMarginTop();

        void setToolBarBgAlpha(int alpha);

        void setToolBarTitle(String title);

        void setToolBarSubTitle(String subTitle);

        void setBgAndSongSheetCover(String url);

        void setListenNum(String listenNum);

        void setSongSheetTitle(String title);

        void setSongSheetTag(String tag);

        void setCollectNum(String collectNum);

        void setCommentNum(String commentNum);

        void setShareNum(String shareNum);

        void setInfoAlpha(float alpha);

        void setOperationAlpha(float alpha);

        void setCollapsingToolbarLayoutScroll();

        void setCollapsingToolbarLayoutNotScroll();

        void showLoading();

        void closeLoading();

        void setSongSheetAdapter(List<AlbumDetailBean.SonglistBean> songSheetDetailList);

        void setAdapterListener();

        void initRecyclerView(int count, String collectNum);

        void showMsg(String msg);

        void initMoreWindow(int height);

        void showSortMode();

        void hideSortMode();

        void showEmptyFile();

        void hideEmptyFile();

        void setCollectSuccess();

        void setCollectCancel();

        void setCollectCount(String count);

        void setBottomOperationClickable(boolean clickable);

        void setSearchClick(boolean clickable);

        void showConfirmCancelCollect();

        void initSongInfoDialog(AlbumDetailBean.SonglistBean songInfo);

        void initCollectToSongSheetDialog(List<MySongSheet> mySongSheetList, AlbumDetailBean.SonglistBean songInfo);

        void showCreateSongSheet(AlbumDetailBean.SonglistBean songInfo);

        void setPrivateSongSheetChecked(boolean isChecked);

        void setCommitCanClick();

        void setCommitNotClick();

        void initCoverDialog(String url, String title, String desc, String tag);

        void setDismissMarginTop();
    }

    public interface AlbumDetailPresenter {
        void isImmerse();

        void onBackPressed();

        void setToolBarStatus(int offset, int maxScroll);

        void init();

        void showMoreWindow();

        void showSongInfoDialog(AlbumDetailBean.SonglistBean songInfo);

        void showCollectToSongSheetDialog(AlbumDetailBean.SonglistBean songInfo);

        void collectAlbum();

        void addCollectSongSheet();

        void delectCollectAlbum();

        boolean isCollect();

        void setDialogMaxHeight(int height, AlertDialog dialog);

        void collectToMySongSheet(MySongSheet mySongSheet, AlbumDetailBean.SonglistBean songInfo);

        void showCreateNewSongSheetDialog(AlbumDetailBean.SonglistBean songInfo);

        void setCommitClickable(int count);

        void setPrivateSongSheetChecked();

        void setCheckedState(boolean isChecked);

        void createSongSheet(String songSheet, AlbumDetailBean.SonglistBean songInfo);

        void searchSong();

        void jumpToCollectorInfo();

        void jumpToDetail(Bundle bundle);

        void showCoverDialog();

        void setDismissMarginTop();

        void jumpToSongSelect();

        void jumpToPlay(AlbumDetailBean.SonglistBean songSheetDetail);

        void download();

        void download(AlbumDetailBean.SonglistBean songInfo);
    }

}
