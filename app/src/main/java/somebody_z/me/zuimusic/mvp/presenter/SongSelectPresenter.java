package somebody_z.me.zuimusic.mvp.presenter;

import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.LocalMusicManager;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.SongSelectContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.SongSelectModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.PlaySongInfoBean;
import somebody_z.me.zuimusic.mvp.view.activity.SongSelectActivity;
import somebody_z.me.zuimusic.service.ServiceManager;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.downloadmanager.DownLoadManager;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class SongSelectPresenter extends BasePresenter<SongSelectModel, SongSelectActivity> implements SongSelectContract.SongSelectPresenter {

    private boolean isSelectAll = true;

    private boolean isChecked = false;

    private List<ContentBean> songSheetDetailList;

    private List<ContentBean> selectedList = new ArrayList<>();

    private ServiceManager mServiceManager;

    @Override
    public SongSelectModel loadModel() {
        return new SongSelectModel();
    }

    @Override
    public void init() {
        Intent intent = getIView().getIntent();

        //强转成自定义的list，这样得到的list就是传过来的那个list了。
        songSheetDetailList = (List<ContentBean>) intent.getSerializableExtra(Constants.SEARCH_LIST);

        if (songSheetDetailList == null) {
            songSheetDetailList = LocalMusicManager.getInstance().getLocalMusicList(getIView());
        }

        getIView().setSongSheetAdapter(songSheetDetailList);
        getIView().setAdapterListener();

        mServiceManager = MyApplication.mServiceManager;

        getIView().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void setChecked() {
        getIView().setChecked(isSelectAll);
        if (isSelectAll) {
            getIView().setSelect(R.string.cancel_selected_all);
            getIView().setSelectCount("已选择" + songSheetDetailList.size() + "项");
        } else {
            getIView().setSelect(R.string.selected_all);
            getIView().setSelectCount("已选择0项");
        }
        isSelectAll = !isSelectAll;
    }

    @Override
    public void addTiList(ContentBean songSheetDetail, boolean isChecked) {
        if (isChecked) {
            if (!match(songSheetDetail)) {
                selectedList.add(songSheetDetail);
            }
        } else {
            if (match(songSheetDetail)) {
                selectedList.remove(songSheetDetail);
            }
        }

        getIView().setSelectCount("已选择" + selectedList.size() + "项");
    }

    @Override
    public boolean match(ContentBean songSheetDetail) {

        if (selectedList.size() == 0) {
            return false;
        }

        for (ContentBean contentBean : selectedList) {
            if (contentBean.getSong_id().equals(songSheetDetail.getSong_id())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showCollectToSongSheetDialog() {
        if (selectedList.size() == 0) {
            getIView().showMsg("请选择要添加到歌单的歌曲");
            return;
        }
        getIView().initCollectToSongSheetDialog(getiModel().getMySongSheet(getIView()), selectedList);
    }

    /**
     * 动态设置dialog的高度，设置最大高度为ScreenUtil.getScreenHeight(mContext) * 2 / 3；
     *
     * @param height
     * @param dialog
     */
    @Override
    public void setDialogMaxHeight(int height, AlertDialog dialog) {
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtil.getScreenWidth(getIView()) - DisplayUtils.dp2px(getIView(), 16);

        if (height > ScreenUtil.getScreenHeight(getIView()) * 2 / 3) {
            params.height = ScreenUtil.getScreenHeight(getIView()) * 2 / 3;
        }
        dialog.getWindow().setAttributes(params);
    }

    @Override
    public void collectToMySongSheet(final MySongSheet mySongSheet, List<ContentBean> songInfoList) {
        for (final ContentBean songInfo : songInfoList) {
            if (getiModel().searchSongIsCollect(songInfo.getTitle(), getIView(), mySongSheet.getSongSheetName()) == null) {
                if (songInfo.getLocalUrl() == null) {
                    String songSheetDetailSongID = songInfo.getSong_id();

                    getiModel().getSongInfo(getIView().subscription, songSheetDetailSongID, new SongSelectModel.GetSongInfoListener() {
                        @Override
                        public void getSuccess(PlaySongInfoBean playSongInfoBean) {
                            String songSheetDetailTitle = songInfo.getTitle();
                            String songSheetDetailSongID = songInfo.getSong_id();
                            String songSheetDetailAuthor = songInfo.getAuthor();
                            String songSheetDetailAlbumTitle = songInfo.getAlbum_title();
                            String songSheetDetailAlbumID = songInfo.getAlbum_id();
                            String songSheetDetailLocalURL = playSongInfoBean.getBitrate().getFile_link();
                            int songSheetDetailHasMV = songInfo.getHas_mv();
                            String songSheetDetailPicUrl = playSongInfoBean.getSonginfo().getPic_big();
                            String songSheetDetailLrcUrl = playSongInfoBean.getSonginfo().getLrclink();

                            ContentBean contentBean = new ContentBean(songSheetDetailTitle, songSheetDetailSongID, songSheetDetailAuthor
                                    , songSheetDetailAlbumTitle, songSheetDetailAlbumID, songSheetDetailLocalURL, songSheetDetailHasMV,
                                    songSheetDetailPicUrl, songSheetDetailLrcUrl);
                            getiModel().addCollectToMySongSheet(contentBean, getIView(), mySongSheet.getSongSheetName());

                            getIView().Log(songSheetDetailLocalURL);

                            getIView().showMsg("已收藏到歌单");

                        }

                        @Override
                        public void getFail(String msg) {
                            getIView().showMsg(msg);
                        }
                    });
                } else {
                    String songSheetDetailTitle = songInfo.getTitle();
                    String songSheetDetailSongID = songInfo.getSong_id();
                    String songSheetDetailAuthor = songInfo.getAuthor();
                    String songSheetDetailAlbumTitle = songInfo.getAlbum_title();
                    String songSheetDetailAlbumID = songInfo.getAlbum_id();
                    String songSheetDetailLocalURL = songInfo.getLocalUrl();
                    int songSheetDetailHasMV = songInfo.getHas_mv();

                    ContentBean contentBean = new ContentBean(songSheetDetailTitle, songSheetDetailSongID, songSheetDetailAuthor
                            , songSheetDetailAlbumTitle, songSheetDetailAlbumID, songSheetDetailLocalURL, songSheetDetailHasMV, null, null);
                    getiModel().addCollectToMySongSheet(contentBean, getIView(), mySongSheet.getSongSheetName());

                    getIView().Log(songSheetDetailLocalURL);

                    getIView().showMsg("已收藏到歌单");
                }

            } else {
                getIView().showMsg("歌曲已存在");
            }
        }

    }

    @Override
    public void showCreateNewSongSheetDialog(List<ContentBean> songInfoList) {
        getIView().showCreateSongSheet(songInfoList);
    }

    @Override
    public void setCommitClickable(int count) {
        if (count == 0 || count > 40) {
            getIView().setCommitNotClick();
        } else {
            getIView().setCommitCanClick();
        }
    }

    @Override
    public void setPrivateSongSheetChecked() {
        getIView().setPrivateSongSheetChecked(!isChecked);
    }

    @Override
    public void setCheckedState(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public void createSongSheet(String songSheet, List<ContentBean> songInfoList) {
        MySongSheet mySongSheet = new MySongSheet(songSheet, null, null, null);

        if (getiModel().searchMySongSheet(songSheet, getIView()) == null) {
            getiModel().addMySongSheet(mySongSheet, getIView());
            collectToMySongSheet(mySongSheet, songInfoList);
        } else {
            getIView().toast("歌单已存在");
        }

    }

    @Override
    public void download() {
        if (selectedList.size() != 0) {
            getIView().showMsg("已加入下载队列");

            for (final ContentBean songInfo : selectedList) {
                if (songInfo.getLocalUrl() == null) {
                    getiModel().getSongInfo(getIView().subscription, songInfo.getSong_id(), new SongSelectModel.GetSongInfoListener() {
                        @Override
                        public void getSuccess(PlaySongInfoBean playSongInfoBean) {

                            DownLoadManager.getInstance().downLoad(getIView(), playSongInfoBean.getBitrate().getFile_link(), songInfo.getTitle() + " - " +
                                    songInfo.getAuthor() + ".mp3");
                        }

                        @Override
                        public void getFail(String msg) {
                            getIView().showMsg(msg);
                        }
                    });
                }
            }
        } else {
            getIView().showMsg("请选择要下载的歌曲");
        }
    }

    @Override
    public void nextPlay() {

        if (selectedList.size() != 0) {
            getIView().showMsg("已加入播放队列");

            for (final ContentBean songInfo : selectedList) {
                if (songInfo.getLocalUrl() == null) {
                    getiModel().getSongInfo(getIView().subscription, songInfo.getSong_id(), new SongSelectModel.GetSongInfoListener() {
                        @Override
                        public void getSuccess(PlaySongInfoBean playSongInfoBean) {

                            String songSheetDetailTitle = songInfo.getTitle();
                            String songSheetDetailSongID = songInfo.getSong_id();
                            String songSheetDetailAuthor = songInfo.getAuthor();
                            String songSheetDetailAlbumTitle = songInfo.getAlbum_title();
                            String songSheetDetailAlbumID = songInfo.getAlbum_id();
                            String songSheetDetailLocalURL = playSongInfoBean.getBitrate().getFile_link();
                            int songSheetDetailHasMV = songInfo.getHas_mv();
                            String songSheetDetailPicUrl = playSongInfoBean.getSonginfo().getPic_big();
                            String songSheetDetailLrcUrl = playSongInfoBean.getSonginfo().getLrclink();

                            ContentBean contentBean = new ContentBean(songSheetDetailTitle, songSheetDetailSongID, songSheetDetailAuthor
                                    , songSheetDetailAlbumTitle, songSheetDetailAlbumID, songSheetDetailLocalURL, songSheetDetailHasMV,
                                    songSheetDetailPicUrl, songSheetDetailLrcUrl);

                            List<ContentBean> insertList = mServiceManager.getMusicList();

                            int index = 0;

                            for (int i = 0; i < insertList.size(); i++) {
                                if (insertList.get(i) == mServiceManager.getCurMusic()) {
                                    index = i;
                                }
                            }

                            insertList.add(index + 1, contentBean);

                            mServiceManager.refreshMusicList(insertList);

                        }

                        @Override
                        public void getFail(String msg) {
                            getIView().showMsg(msg);
                        }
                    });
                } else {
                    //添加本地音乐
                    List<ContentBean> insertList = mServiceManager.getMusicList();

                    int index = 0;

                    for (int i = 0; i < insertList.size(); i++) {
                        if (insertList.get(i) == mServiceManager.getCurMusic()) {
                            index = i;
                        }
                    }

                    insertList.add(index + 1, songInfo);

                    mServiceManager.refreshMusicList(insertList);
                }
            }
        } else {
            getIView().showMsg("请选择要播放的歌曲");
        }
    }

}
