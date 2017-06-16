package somebody_z.me.zuimusic.mvp.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.LocalSongContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.LocalSongModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.view.activity.SongSelectActivity;
import somebody_z.me.zuimusic.mvp.view.activity.impl.playBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.LocalSongFragment;
import somebody_z.me.zuimusic.service.ServiceManager;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.MusicTimer;
import somebody_z.me.zuimusic.utils.PinyinComparatorUtil;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/2/27.
 * email : zenghuanxing123@163.com
 */
public class LocalSongPresenter extends BasePresenter<LocalSongModel, LocalSongFragment> implements
        LocalSongContract.LocalSongPresenter {
    private boolean isChecked = false;

    private ServiceManager mServiceManager = null;

    private MusicTimer mMusicTimer;

    private MusicPlayBroadcast mPlayBroadcast;

    private List<ContentBean> songList;

    @Override
    public LocalSongModel loadModel() {
        return new LocalSongModel();
    }

    @Override
    public void init() {

        //根据sp判断
        if (getiModel().getIsPlayed(getIView().mContext)) {
            getIView().setIndexBarMargin(48);
        } else {
            getIView().setIndexBarMargin(0);
        }

        songList = transform(getiModel().getLocalSong(getIView().mContext));

        if (songList.size() == 0) {
            getIView().setNoneVisible(View.VISIBLE);
        } else {
            getIView().setNoneVisible(View.GONE);

            getIView().initRecyclerView(songList.size());

            getIView().setSongSheetAdapter(songList);
            getIView().setAdapterListener();
        }

        mServiceManager = MyApplication.mServiceManager;

        if (mPlayBroadcast == null) {
            mPlayBroadcast = new MusicPlayBroadcast();

            IntentFilter filter = new IntentFilter(Constants.BROADCAST_NAME);
            filter.addAction(Constants.BROADCAST_NAME);
            filter.addAction(Constants.BROADCAST_QUERY_COMPLETE_NAME);
            getIView().mContext.registerReceiver(mPlayBroadcast, filter);
        }

        if (mServiceManager.getCurMusic() != null) {
            getIView().setPlaying(mServiceManager.getCurMusic().getLocalUrl());
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
    public void collectToMySongSheet(MySongSheet mySongSheet, ContentBean songInfo) {

        if (getiModel().searchSongIsCollect(songInfo.getTitle(), getIView().mContext, mySongSheet.getSongSheetName()) == null) {
            String songSheetDetailTitle = songInfo.getTitle();
            String songSheetDetailSongID = songInfo.getSong_id();
            String songSheetDetailAuthor = songInfo.getAuthor();
            String songSheetDetailAlbumTitle = songInfo.getAlbum_title();
            String songSheetDetailAlbumID = songInfo.getAlbum_id();
            String songSheetDetailLocalURL = null;
            int songSheetDetailHasMV = songInfo.getHas_mv();

            ContentBean contentBean = new ContentBean(songSheetDetailTitle, songSheetDetailSongID, songSheetDetailAuthor
                    , songSheetDetailAlbumTitle, songSheetDetailAlbumID, songSheetDetailLocalURL, songSheetDetailHasMV, null, null);
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
        // intent.putExtra(Constants.SEARCH_LIST, (Serializable) songList);
        getIView().getActivity().startActivity(intent);
        //设置activity跳转动画
        //getIView().getActivity().overridePendingTransition(R.anim.activity_transition_enter, R.anim.activity_transition_exit);
        //去除activity跳转动画
        getIView().getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void jumpToPlay(ContentBean songSheetDetail) {
        int index = 0;

        if (songSheetDetail != null) {
            for (int i = 0; i < songList.size(); i++) {

                if (songSheetDetail == songList.get(i)) {
                    index = i;
                }

            }
        }

        mServiceManager.refreshMusicList(songList);

        mServiceManager.play(index);

        if (getIView().getActivity() instanceof playBarVisible) {
            ((playBarVisible) getIView().getActivity()).showPlayBar();
            getIView().setIndexBarMargin(48);
        }

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

    /**
     * 对list进行排序
     *
     * @param datas
     * @return
     */
    @Nullable
    private List<ContentBean> transform(List<ContentBean> datas) {
        try {
            Comparator comparator;
            comparator = new PinyinComparatorUtil<ContentBean>();
            Collections.sort(datas, comparator);
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

                getIView().setPlaying(music.getLocalUrl());

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
        //oks.setImageUrl(url);
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
}
