package somebody_z.me.zuimusic.mvp.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.CollectSongSheet;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.MusicContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.MusicModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.DiscoverFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetDetailFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.LocalMusicFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.MusicFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.MyAlbumFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.MyPlayerFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.MySongSheetDetailFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.RecentlyPlayFragment;

/**
 * Created by Huanxing Zeng on 2017/1/13.
 * email : zenghuanxing123@163.com
 */
public class MusicPresenter extends BasePresenter<MusicModel, MusicFragment> implements MusicContract.MusicPresenter {
    private boolean isCreateVisible = true;
    private boolean isCollectVisible = true;

    private boolean isChecked = false;

    private MusicPlayBroadcast mPlayBroadcast;

    @Override
    public MusicModel loadModel() {
        return new MusicModel();
    }

    @Override
    public void isFirstUse() {
        if (getiModel().getIsFirstUse(getIView().mContext)) {
            createSongSheet("我喜欢的音乐");
            getiModel().setFirstUse(getIView().mContext);
        }

        if (mPlayBroadcast == null) {
            mPlayBroadcast = new MusicPlayBroadcast();

            IntentFilter filter = new IntentFilter(Constants.BROADCAST_NAME);
            filter.addAction(Constants.BROADCAST_NAME);
            filter.addAction(Constants.BROADCAST_QUERY_COMPLETE_NAME);
            getIView().mContext.registerReceiver(mPlayBroadcast, filter);
        }
    }

    @Override
    public void init() {
        getIView().setLocalMusicCount("(" + getiModel().getLocalSong(getIView().mContext).size() + ")");
        getIView().setRecentPlayCount("(" + getiModel().getRecentlyPlay(getIView().mContext).size() + ")");
        getIView().setDownloadManagerCount("(" + 0 + ")");
        getIView().setMySingerCount("(" + 0 + ")");
        getIView().setMyMVCount("(" + getiModel().getMyAlbum(getIView().mContext).size() + ")");
    }

    @Override
    public void setCreateSongSheetVisible() {
        if (isCreateVisible) {
            isCreateVisible = false;
            getIView().setCreateRotationCW();
        } else {
            isCreateVisible = true;
            getIView().setCreateRotationCCW();
        }
    }

    @Override
    public void setCollectSongSheetVisible() {
        if (isCollectVisible) {
            isCollectVisible = false;
            getIView().setCollectRotationCW();
        } else {
            isCollectVisible = true;
            getIView().setCollectRotationCCW();
        }
    }

    @Override
    public void createSongSheet(String songSheet) {
        MySongSheet mySongSheet = new MySongSheet(songSheet, null, null, null);

        if (getiModel().searchMySongSheet(songSheet, getIView().mContext) == null) {
            getiModel().addMySongSheet(mySongSheet, getIView().mContext);
        } else {
            getIView().toast("歌单已存在");
        }

    }

    @Override
    public void showSongSheet() {
        getIView().setMySongSheetAdapter(getiModel().getMySongSheet(getIView().mContext));
        getIView().setCreateSongSheetCount("(" + getiModel().getMySongSheet(getIView().mContext).size() + ")");
    }

    @Override
    public void delectSongSheet(String songSheetName) {
        MySongSheet mySongSheet = getiModel().searchMySongSheet(songSheetName, getIView().mContext);

        getiModel().delectMySongSheet(mySongSheet, getIView().mContext);
    }

    @Override
    public void updateSongSheet(MySongSheet mySongSheet) {
        getiModel().updateMySongSheet(mySongSheet, getIView().mContext);
    }

    @Override
    public void showCollectSongSheet() {
        getIView().setCollectSongSheetAdapter(getiModel().getCollectSongSheet(getIView().mContext));
        getIView().setCollectSongSheetCount("(" + getiModel().getCollectSongSheet(getIView().mContext).size() + ")");
    }

    @Override
    public void delectCollectSongSheet(String title) {
        CollectSongSheet collectSongSheet1 = getiModel().searchCollectSongSheet(title, getIView().mContext);

        getiModel().delectCollectSongSheet(collectSongSheet1, getIView().mContext);
    }

    @Override
    public void updateCollectSongSheet(CollectSongSheet collectSongSheet) {
        getiModel().updateCollectSongSheet(collectSongSheet, getIView().mContext);
    }

    /**
     * 跳转歌单详情列表
     *
     * @param mySongSheet
     */
    @Override
    public void goToDetail(MySongSheet mySongSheet) {

        if (getiModel().getSongSheetDetailList(mySongSheet.getSongSheetName(), getIView().mContext).size()==0) {
            getIView().toast("该歌单暂时没有收藏歌曲~");
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SONG_SHEET_NAME, mySongSheet.getSongSheetName());

            jumpToDetail(bundle, MySongSheetDetailFragment.getInstance());
        }

    }

    //弹出设置窗口
    @Override
    public void goToOption(MySongSheet mySongSheet) {
        getIView().showSongSheetDetailPopupWindow(mySongSheet.getSongSheetName(), null);
    }

    /**
     * 跳转收藏歌单详情列表
     *
     * @param collectSongSheet
     */
    @Override
    public void goToCollectDetail(CollectSongSheet collectSongSheet) {
        //跳转歌单详情
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ID, collectSongSheet.getListid());
        bundle.putString(Constants.TYPE, Constants.COLLECT_SONG_SHEET_LOCAL);

        jumpToDetail(bundle, SongSheetDetailFragment.getInstance());
    }

    @Override
    public void jumpToDetail(Bundle bundle, Fragment fragment) {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).hideBar();
        }

        FragmentTransaction ft = getIView().getFragmentManager().beginTransaction();

        fragment.setArguments(bundle);

        ft.add(R.id.fl_home_content, fragment);
        ft.hide(getIView());
        //将当前的事务添加到了回退栈
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void goToCollectOption(CollectSongSheet collectSongSheet) {
        getIView().showSongSheetDetailPopupWindow(collectSongSheet.getTitle(), collectSongSheet.getListid());
    }

    @Override
    public void showCreatSongSheetOption() {
        getIView().showSongSheetPopupWindow(0);
    }

    @Override
    public void showCollectSongSheetOption() {
        getIView().showSongSheetPopupWindow(1);
    }

    @Override
    public void createSongSheet() {
        getIView().showCreateSongSheet();
    }

    @Override
    public void setPopupTitle(int type) {
        if (type == 0) {
            getIView().setCreateSongSheetTitle();
        } else if (type == 1) {
            getIView().setCollectSongSheetTitle();
        }
    }

    @Override
    public void showSongSheetDetailEdit(String listID) {
        if (listID != null) {
            getIView().setSongSheetDetailEditVisible();
        }
    }

    @Override
    public void deleteSongSheet(String listID, String songSheetName) {
        if (listID == null) {
            delectSongSheet(songSheetName);
            showSongSheet();
        } else {
            delectCollectSongSheet(songSheetName);
            showCollectSongSheet();
            getIView().show("删除成功");
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
    public void setCommitClickable(int count) {
        if (count == 0 || count > 40) {
            getIView().setCommitNotClick();
        } else {
            getIView().setCommitCanClick();
        }
    }

    @Override
    public void showDeleteConfirm(String listID, String songSheetName) {
        getIView().showComfirmDelete(listID, songSheetName);
    }

    @Override
    public void setCollectSongSheetIsVisible(int size) {
        if (size == 0) {
            getIView().hideCollectSongSheet();
        } else {
            getIView().showCollectSongSheet();
            getIView().showCollectSongSheetDetail();
        }
    }

    /**
     * 模拟刷新，2s
     */
    @Override
    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getIView().refresh();
            }
        }, 1000);
    }

    @Override
    public void jumpManager(Fragment fragment) {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).hideBar();
        }

        FragmentTransaction ft = getIView().getFragmentManager().beginTransaction();
        ft.add(R.id.fl_home_content, fragment);
        ft.hide(getIView());
        //将当前的事务添加到了回退栈
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void jumpToLocalMusic() {
        jumpManager(LocalMusicFragment.getInstance());
    }

    @Override
    public void jumpToRecentPlay() {
        jumpManager(new RecentlyPlayFragment());
    }

    @Override
    public void jumpToDownloadManeger() {
        getIView().toast("正在开发...");
    }

    @Override
    public void jumpToMyPlayer() {
        jumpManager(MyPlayerFragment.getInstance());
    }

    @Override
    public void jumpToMyMV() {
        jumpManager(new MyAlbumFragment());
    }

    @Override
    public void jumpToSongSheetInfoManager() {

    }

    /**
     * type==0 创建的歌单
     * type==1 收藏的歌单
     *
     * @param type
     */
    @Override
    public void jumpToSongSheetManager(int type) {

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

                init();

            }

        }
    }

}
