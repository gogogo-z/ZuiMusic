package somebody_z.me.zuimusic.mvp.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.RecentlyPlayContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.RecentlyPlayModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.view.activity.SongSelectActivity;
import somebody_z.me.zuimusic.mvp.view.activity.impl.playBarVisible;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.RecentlyPlayFragment;
import somebody_z.me.zuimusic.service.ServiceManager;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.downloadmanager.DownLoadManager;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class RecentlyPlayPresenter extends BasePresenter<RecentlyPlayModel, RecentlyPlayFragment> implements
        RecentlyPlayContract.RecentlyPlayPresenter {

    private ServiceManager mServiceManager = null;

    private List<ContentBean> recentlyPlayList;

    private boolean isChecked = false;

    private MusicPlayBroadcast mPlayBroadcast;

    @Override
    public RecentlyPlayModel loadModel() {
        return new RecentlyPlayModel();
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

        recentlyPlayList = getiModel().getRecentlyPlay(getIView().mContext);

        Collections.reverse(recentlyPlayList); //倒序排列

        getIView().setRecentlyPlayAdapter(recentlyPlayList);
        getIView().setAdapterListener();
        getIView().initRecyclerView(recentlyPlayList.size());

        if (mPlayBroadcast == null) {
            mPlayBroadcast = new MusicPlayBroadcast();

            IntentFilter filter = new IntentFilter(Constants.BROADCAST_NAME);
            filter.addAction(Constants.BROADCAST_NAME);
            filter.addAction(Constants.BROADCAST_QUERY_COMPLETE_NAME);
            getIView().mContext.registerReceiver(mPlayBroadcast, filter);
        }

    }

    @Override
    public void showSongInfoDialog(ContentBean songInfo) {
        getIView().initSongInfoDialog(songInfo);
    }

    @Override
    public void showCollectToSongSheetDialog(ContentBean songInfo) {
        getIView().initCollectToSongSheetDialog(getiModel().getMySongSheet(getIView().mContext), songInfo);
    }

    @Override
    public void collectToMySongSheet(final MySongSheet mySongSheet, final ContentBean songInfo) {
        if (getiModel().searchSongIsCollect(songInfo.getTitle(), getIView().mContext, mySongSheet.getSongSheetName()) == null) {
            String songSheetDetailTitle = songInfo.getTitle();
            String songSheetDetailSongID = songInfo.getSong_id();
            String songSheetDetailAuthor = songInfo.getAuthor();
            String songSheetDetailAlbumTitle = songInfo.getAlbum_title();
            String songSheetDetailAlbumID = songInfo.getAlbum_id();
            String songSheetDetailLocalURL = songInfo.getLocalUrl();
            int songSheetDetailHasMV = songInfo.getHas_mv();
            String songSheetDetailPicUrl = songInfo.getPic_url();
            String songSheetDetailLrcUrl = songInfo.getLrc_url();

            ContentBean contentBean = new ContentBean(songSheetDetailTitle, songSheetDetailSongID, songSheetDetailAuthor
                    , songSheetDetailAlbumTitle, songSheetDetailAlbumID, songSheetDetailLocalURL, songSheetDetailHasMV,
                    songSheetDetailPicUrl, songSheetDetailLrcUrl);
            getiModel().addCollectToMySongSheet(contentBean, getIView().mContext, mySongSheet.getSongSheetName());

            getIView().showMsg("已收藏到歌单");


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
    public void jumpToSongSelect() {
        Intent intent = new Intent(getIView().mContext, SongSelectActivity.class);
        intent.putExtra(Constants.SEARCH_LIST, (Serializable) recentlyPlayList);
        getIView().getActivity().startActivity(intent);
        //设置activity跳转动画
        //getIView().getActivity().overridePendingTransition(R.anim.activity_transition_enter, R.anim.activity_transition_exit);
        //去除activity跳转动画
        getIView().getActivity().overridePendingTransition(0, 0);
    }

    int index = 0;

    @Override
    public void jumpToPlay(ContentBean songSheetDetail) {
        for (int i = 0; i < recentlyPlayList.size(); i++) {

            if (songSheetDetail == null) {
                index = 0;
            } else {
                if (songSheetDetail.getSong_id().equals(recentlyPlayList.get(i).getSong_id())) {
                    index = i;
                }
            }

            mServiceManager.refreshMusicList(recentlyPlayList);

            mServiceManager.play(index);

            if (getIView().getActivity() instanceof playBarVisible) {
                ((playBarVisible) getIView().getActivity()).showPlayBar();
            }
        }

    }

    @Override
    public void clearAll() {
        getiModel().clearRecentlyPlay(getIView().mContext);

        recentlyPlayList.clear();

        getIView().refreshAdapter(recentlyPlayList);
        getIView().refreshCount(recentlyPlayList.size());
    }

    @Override
    public void unRegisterReceiver() {
        if (mPlayBroadcast != null) {
            getIView().mContext.unregisterReceiver(mPlayBroadcast);
        }
    }

    @Override
    public void registerReceiver() {
        IntentFilter filter = new IntentFilter(Constants.BROADCAST_NAME);
        filter.addAction(Constants.BROADCAST_NAME);
        filter.addAction(Constants.BROADCAST_QUERY_COMPLETE_NAME);
        getIView().mContext.registerReceiver(mPlayBroadcast, filter);
    }

    private class MusicPlayBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.BROADCAST_NAME)) {
                ContentBean music = new ContentBean();
                int playState = intent.getIntExtra(Constants.PLAY_STATE_NAME, Constants.MPS_NOFILE);
                int curPlayIndex = intent.getIntExtra(Constants.PLAY_MUSIC_INDEX, -1);
                Bundle bundle = intent.getBundleExtra(ContentBean.KEY_MUSIC);
                if (bundle != null) {
                    music = bundle.getParcelable(ContentBean.KEY_MUSIC);
                }

                recentlyPlayList.clear();

                recentlyPlayList = getiModel().getRecentlyPlay(getIView().mContext);

                Collections.reverse(recentlyPlayList); //Man 倒序排列

                getIView().refreshAdapter(recentlyPlayList);
                getIView().refreshCount(recentlyPlayList.size());

            }

        }
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
        oks.setImageUrl(songInfo.getPic_url());
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
        oks.show(getIView().mContext);
    }

    @Override
    public void onBackPressed() {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).showBar();
        }
        getIView().getFragmentManager().popBackStack();
    }

    @Override
    public void download(ContentBean songInfo) {
        getIView().showMsg("已加入下载队列");

        DownLoadManager.getInstance().downLoad(getIView().mContext, songInfo.getLocalUrl(), songInfo.getTitle() + " - " +
                songInfo.getAuthor() + ".mp3");


    }
}
