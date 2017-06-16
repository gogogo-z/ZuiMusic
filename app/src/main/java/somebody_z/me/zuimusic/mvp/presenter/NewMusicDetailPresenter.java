package somebody_z.me.zuimusic.mvp.presenter;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.NewMusicDetailContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.NewMusicDetailModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.mvp.model.bean.PlaySongInfoBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.mvp.view.activity.impl.playBarVisible;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.DiscoverFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.NewMusicDetailFragment;
import somebody_z.me.zuimusic.service.ServiceManager;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class NewMusicDetailPresenter extends BasePresenter<NewMusicDetailModel, NewMusicDetailFragment> implements NewMusicDetailContract.NewMusicDetailPresenter {
    private NewMusicBean.SongListBean songListBean;

    private String title, song_id, author, album_title, album_id, localUrl;

    private int has_mv;

    private String url_premiun, url_bg, recommend_reason;

    private List<String> songSheetName = new ArrayList<>();

    private boolean isChecked = false;

    private ServiceManager mServiceManager = null;

    @Override
    public NewMusicDetailModel loadModel() {
        return new NewMusicDetailModel();
    }

    @Override
    public void isImmerse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setMarginTop();
        }
    }

    @Override
    public void init() {
        Bundle bundle = getIView().getArguments();
        ArrayList list = bundle.getParcelableArrayList(Constants.NEWMUSIC);
        //强转成自定义的list，这样得到的list就是传过来的那个list了。
        songListBean = (NewMusicBean.SongListBean) list.get(0);

        title = songListBean.getTitle();
        song_id = songListBean.getSong_id();
        author = songListBean.getAuthor();
        album_title = songListBean.getAlbum_title();
        album_id = songListBean.getAlbum_id();
        localUrl = null;
        has_mv = songListBean.getHas_mv();

        url_premiun = songListBean.getPic_premium();

        url_bg = songListBean.getPic_small();

        recommend_reason = songListBean.getRecommend_reason();

        if (album_title != "") {
            album_title = " - " + album_title;
        }

        mServiceManager = MyApplication.mServiceManager;

        getIView().setTitle(title);
        getIView().setAuthorAndAlbum(author + album_title);
        getIView().setRecommendReason(recommend_reason);
        getIView().setCoverAndBg(url_premiun, url_bg);

        if (isCollect()) {
            getIView().setCollectVisible(R.drawable.selector_detail_collect_success);

            String songSheets = "";

            for (int i = 0; i < songSheetName.size(); i++) {
                if (i == songSheetName.size() - 1) {
                    songSheets = songSheets + songSheetName.get(i);
                } else {
                    songSheets = songSheets + songSheetName.get(i) + "、";
                }
            }

            getIView().setIsCollect("已收藏到 " + songSheets + " 歌单 ~");
        } else {
            getIView().setCollectVisible(R.drawable.selector_detail_collect);
            getIView().setIsCollect("还没有收藏该歌曲哦 ~");
        }

    }

    @Override
    public void onBackPressed() {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).showBar();
        }
        DiscoverFragment.getInstance().showTab();
        getIView().getFragmentManager().popBackStack();
    }

    @Override
    public void showCollectToSongSheetDialog() {

        if (isCollect()) {
            getIView().showConfirmCancelCollect();
        } else {
            getiModel().getSongInfo(getIView().subscription, song_id, new NewMusicDetailModel.GetSongInfoListener() {
                @Override
                public void getSuccess(PlaySongInfoBean playSongInfoBean) {
                    String songSheetDetailLocalURL = playSongInfoBean.getBitrate().getFile_link();
                    String songSheetDetailPicUrl = playSongInfoBean.getSonginfo().getPic_big();
                    String songSheetDetailLrcUrl = playSongInfoBean.getSonginfo().getLrclink();

                    ContentBean contentBean = new ContentBean(title, song_id, author, album_title, album_id, songSheetDetailLocalURL, has_mv,
                            songSheetDetailPicUrl, songSheetDetailLrcUrl);

                    getIView().initCollectToSongSheetDialog(getiModel().getMySongSheet(getIView().mContext), contentBean);
                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
        }
    }

    @Override
    public void collectToMySongSheet(MySongSheet mySongSheet, ContentBean songInfo) {

        if (getiModel().searchSongIsCollect(songInfo.getTitle(), getIView().mContext, mySongSheet.getSongSheetName()) == null) {

            getiModel().addCollectToMySongSheet(songInfo, getIView().mContext, mySongSheet.getSongSheetName());

            getIView().showMsg("已收藏到歌单");
            setCollectVibsible();

            songSheetName.clear();

            isCollect();

            String songSheets = "";

            for (int i = 0; i < songSheetName.size(); i++) {
                if (i == songSheetName.size() - 1) {
                    songSheets = songSheets + songSheetName.get(i);
                } else {
                    songSheets = songSheets + songSheetName.get(i) + "、";
                }
            }

            getIView().setIsCollect("已收藏到 " + songSheets + " 歌单 ~");
        } else {
            getIView().showMsg("歌曲已存在");
        }
    }

    @Override
    public void showCreateNewSongSheetDialog(ContentBean songInfo) {
        getIView().showCreateSongSheet(songInfo);
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
        params.width = ScreenUtil.getScreenWidth(getIView().mContext) - DisplayUtils.dp2px(getIView().mContext, 16);

        if (height > ScreenUtil.getScreenHeight(getIView().mContext) * 2 / 3) {
            params.height = ScreenUtil.getScreenHeight(getIView().mContext) * 2 / 3;
        }
        dialog.getWindow().setAttributes(params);
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
    public void createSongSheet(String songSheet, ContentBean songInfo) {
        MySongSheet mySongSheet = new MySongSheet(songSheet, null, null, null);

        if (getiModel().searchMySongSheet(songSheet, getIView().mContext) == null) {
            getiModel().addMySongSheet(mySongSheet, getIView().mContext);
            collectToMySongSheet(mySongSheet, songInfo);
        } else {
            getIView().toast("歌单已存在");
        }

    }

    @Override
    public boolean isCollect() {
        List<MySongSheet> mySongSheetList = getiModel().getMySongSheet(getIView().mContext);

        boolean isCollect = false;

        for (MySongSheet mySongSheet : mySongSheetList) {
            if (getiModel().searchSongIsCollect(title, getIView().mContext, mySongSheet.getSongSheetName()) != null) {
                songSheetName.add(mySongSheet.getSongSheetName());
                isCollect = true;
            }
        }

        return isCollect;
    }

    @Override
    public void setCollectVibsible() {
        if (isCollect()) {
            getIView().setCollectVisible(R.drawable.selector_detail_collect_success);
        } else {
            getIView().setCollectVisible(R.drawable.selector_detail_collect);
        }
    }

    @Override
    public void delectCollectSong() {
        ContentBean songInfo = new ContentBean(title, song_id, author, album_title, album_id, localUrl, has_mv, null, null);

        for (String songSheet : songSheetName) {
            getiModel().delectSong(songInfo, getIView().mContext, songSheet);
        }

        setCollectVibsible();

        getIView().showMsg("已取消收藏");
        getIView().setIsCollect("还没有收藏该歌曲哦 ~");
    }

    @Override
    public void playSong() {
        getiModel().getSongInfo(getIView().subscription, song_id, new NewMusicDetailModel.GetSongInfoListener() {
            @Override
            public void getSuccess(PlaySongInfoBean playSongInfoBean) {
                String file_link = playSongInfoBean.getBitrate().getFile_link();
                String pic_url = playSongInfoBean.getSonginfo().getPic_big();
                String lrc_url = playSongInfoBean.getSonginfo().getLrclink();

                ContentBean contentBean = new ContentBean(title, song_id, author, album_title, album_id, file_link, has_mv,
                        pic_url, lrc_url);

                List<ContentBean> songlist = mServiceManager.getMusicList();

                songlist.add(contentBean);

                mServiceManager.refreshMusicList(songlist);

                mServiceManager.play(songlist.size() - 1);

                if (getIView().getActivity() instanceof playBarVisible) {
                    ((playBarVisible) getIView().getActivity()).showPlayBar();
                }
            }

            @Override
            public void getFail(String msg) {
                getIView().showMsg(msg);
            }
        });
    }
}
