package somebody_z.me.zuimusic.mvp.presenter;

import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.SearchContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.SearchModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.PlaySongInfoBean;
import somebody_z.me.zuimusic.mvp.model.bean.SearchSongBean;
import somebody_z.me.zuimusic.mvp.view.activity.SearchActivity;
import somebody_z.me.zuimusic.service.ServiceManager;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.downloadmanager.DownLoadManager;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/16.
 */
public class SearchPresenter extends BasePresenter<SearchModel, SearchActivity> implements SearchContract.SearchPresenter {

    private ServiceManager mServiceManager = null;

    private boolean isChecked = false;

    @Override
    public SearchModel loadModel() {
        return new SearchModel();
    }

    @Override
    public void isImmerse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setBarMarginTop();
        }
    }

    @Override
    public void init() {
        mServiceManager = MyApplication.mServiceManager;
        getIView().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    private boolean isSearching = false;

    @Override
    public void search(String content) {
        final List<ContentBean> searchList = new ArrayList<>();

        //如果内容为空，则清空searchList。
        if (content.equals("")) {
            searchList.clear();
            getIView().setSongAdapter(searchList);
            getIView().setAdapterListener();
            return;
        }

        if (!isSearching) {
            getIView().showLoading();
        }

        isSearching = true;

        //根据关键词搜索
        getiModel().searchSong(getIView().subscription, content, new SearchModel.GetSongListener() {
            @Override
            public void getSuccess(SearchSongBean searchSongBean) {
                for (int i = 0; i < searchSongBean.getSong().size(); i++) {

                    String title = searchSongBean.getSong().get(i).getSongname();
                    String author = searchSongBean.getSong().get(i).getArtistname();
                    String song_id = searchSongBean.getSong().get(i).getSongid();
                    int has_mv = Integer.valueOf(searchSongBean.getSong().get(i).getHas_mv());

                    ContentBean contentBean = new ContentBean(title, song_id, author, "", null, null, has_mv, null, null);

                    searchList.add(contentBean);
                }

                getIView().closeLoading();
                isSearching = false;

                getIView().setSongAdapter(searchList);
                getIView().setAdapterListener();

            }

            @Override
            public void getFail(String msg) {
                getIView().toast(msg);
                getIView().closeLoading();
                isSearching = false;
            }
        });

    }

    @Override
    public void play(ContentBean songSheetDetail) {

        getiModel().getSongInfo(getIView().subscription, songSheetDetail.getSong_id(), new SearchModel.GetSongInfoListener() {
            @Override
            public void getSuccess(PlaySongInfoBean playSongInfoBean) {
                String songSheetDetailTitle = playSongInfoBean.getSonginfo().getTitle();
                String songSheetDetailSongID = playSongInfoBean.getSonginfo().getSong_id();
                String songSheetDetailAuthor = playSongInfoBean.getSonginfo().getAuthor();
                String songSheetDetailAlbumTitle = playSongInfoBean.getSonginfo().getAlbum_title();
                String songSheetDetailAlbumID = playSongInfoBean.getSonginfo().getAlbum_id();
                String songSheetDetailLocalURL = playSongInfoBean.getBitrate().getFile_link();
                int songSheetDetailHasMV = playSongInfoBean.getSonginfo().getHas_mv();
                String songSheetDetailPicUrl = playSongInfoBean.getSonginfo().getPic_big();
                String songSheetDetailLrcUrl = playSongInfoBean.getSonginfo().getLrclink();

                ContentBean contentBean = new ContentBean(songSheetDetailTitle, songSheetDetailSongID, songSheetDetailAuthor
                        , songSheetDetailAlbumTitle, songSheetDetailAlbumID, songSheetDetailLocalURL, songSheetDetailHasMV,
                        songSheetDetailPicUrl, songSheetDetailLrcUrl);

                List<ContentBean> list = mServiceManager.getMusicList();
                list.add(contentBean);

                mServiceManager.refreshMusicList(list);

                mServiceManager.play(list.size() - 1);
            }

            @Override
            public void getFail(String msg) {
                getIView().toast(msg);
            }
        });

    }

    @Override
    public void showSongInfoDialog(ContentBean songSheetDetail) {
        getIView().initSongInfoDialog(songSheetDetail);
    }

    @Override
    public void showCollectToSongSheetDialog(ContentBean songInfo) {
        getIView().initCollectToSongSheetDialog(getiModel().getMySongSheet(getIView()), songInfo);
    }

    @Override
    public void collectToMySongSheet(final MySongSheet mySongSheet, final ContentBean songInfo) {

        if (getiModel().searchSongIsCollect(songInfo.getTitle(), getIView(), mySongSheet.getSongSheetName()) == null) {
            String songSheetDetailSongID = songInfo.getSong_id();

            getiModel().getSongInfo(getIView().subscription, songSheetDetailSongID, new SearchModel.GetSongInfoListener() {
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

                    getIView().showMsg("已收藏到歌单");

                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
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
        params.width = ScreenUtil.getScreenWidth(getIView()) - DisplayUtils.dp2px(getIView(), 16);

        if (height > ScreenUtil.getScreenHeight(getIView()) * 2 / 3) {
            params.height = ScreenUtil.getScreenHeight(getIView()) * 2 / 3;
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

        if (getiModel().searchMySongSheet(songSheet, getIView()) == null) {
            getiModel().addMySongSheet(mySongSheet, getIView());
            collectToMySongSheet(mySongSheet, songInfo);
        } else {
            getIView().toast("歌单已存在");
        }

    }

    @Override
    public void download(final ContentBean songInfo) {
        getIView().showMsg("已加入下载队列");

        getiModel().getSongInfo(getIView().subscription, songInfo.getSong_id(), new SearchModel.GetSongInfoListener() {
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

    /**
     * 分享
     */
    public void showShare(ContentBean songInfo) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(songInfo.getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(songInfo.getShare());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(songInfo.getAuthor() + " - " + songInfo.getAlbum_title());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(songInfo.getShare());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("最音乐，动听音乐。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("最音乐");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(songInfo.getShare());

        // 启动分享GUI
        oks.show(getIView());
    }
}
