package somebody_z.me.zuimusic.mvp.presenter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.db.CollectSongSheet;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.SongSheetDetailContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.SongSheetDetailModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.PlaySongInfoBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.mvp.view.activity.SongSelectActivity;
import somebody_z.me.zuimusic.mvp.view.activity.impl.playBarVisible;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.DiscoverFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetDetailFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetDetailSearchFragment;
import somebody_z.me.zuimusic.service.ServiceManager;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.downloadmanager.DownLoadManager;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/2/5.
 * email : zenghuanxing123@163.com
 */
public class SongSheetDetailPresenter extends BasePresenter<SongSheetDetailModel, SongSheetDetailFragment> implements SongSheetDetailContract.DetailPresenter {
    private String title, listID, url, tag, desc, titleUrl;

    String collectNum, commentNum, shareNum;

    List<ContentBean> songSheetDetailList;

    private boolean isChecked = false;

    private ServiceManager mServiceManager = null;

    private String type;

    @Override
    public SongSheetDetailModel loadModel() {
        return new SongSheetDetailModel();
    }

    @Override
    public void isImmerse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setToolBarMarginTop();
        }
    }

    @Override
    public void onBackPressed() {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).showBar();
        }

        if (type == Constants.COLLECT_SONG_SHEET_ONLINE) {
            DiscoverFragment.getInstance().showTab();
        }

        getIView().getFragmentManager().popBackStack();
    }

    /**
     * 根据位移距离进行透明度、标题的动态改变
     *
     * @param offset
     * @param maxScroll
     */
    @Override
    public void setToolBarStatus(int offset, int maxScroll) {
        float percent = (float) Math.abs(offset) / (float) maxScroll;
        if (percent < 0.4) {
            getIView().setToolBarBgAlpha((int) (percent * 2.5 * 255));
            getIView().setInfoAlpha((float) (1 - percent / 0.7));
            getIView().setToolBarTitle("歌单");
            getIView().setOperationAlpha(1);
            getIView().setBottomOperationClickable(true);
        } else if (percent < 0.7 && percent > 0.4) {
            getIView().setToolBarBgAlpha(255);
            getIView().setToolBarTitle(title);
            getIView().setInfoAlpha((float) (1 - percent / 0.7));
            getIView().setOperationAlpha(1);
            getIView().setBottomOperationClickable(true);
        } else {
            getIView().setToolBarBgAlpha(255);
            getIView().setInfoAlpha((float) (0));
            getIView().setToolBarTitle(title);
            getIView().setOperationAlpha((float) ((0.9 - percent) / 0.2));

            if (percent > 0.9) {
                getIView().setBottomOperationClickable(false);
            } else {
                getIView().setBottomOperationClickable(true);
            }
        }
    }

    @Override
    public void init() {
        getIView().showLoading();

        mServiceManager = MyApplication.mServiceManager;

        //底部四个按钮设置和搜索无法点击
        getIView().setBottomOperationClickable(false);
        getIView().setSearchClick(false);

        Bundle bundle = getIView().getArguments();
        String id = bundle.getString(Constants.ID);

        type = bundle.getString(Constants.TYPE);

        getiModel().getSongSheetDetail(getIView().subscription, id, new SongSheetDetailModel.GetSongSheetDetailListener() {
            @Override
            public void getSuccess(SongSheetDetail songSheetDetail) {
                titleUrl = songSheetDetail.getUrl();

                title = songSheetDetail.getTitle();
                collectNum = songSheetDetail.getCollectnum();
                commentNum = String.valueOf(Integer.valueOf(collectNum) / 6);
                shareNum = String.valueOf(Integer.valueOf(collectNum) / 3);

                listID = songSheetDetail.getListid();
                url = songSheetDetail.getPic_300();
                tag = songSheetDetail.getTag();
                desc = songSheetDetail.getDesc();

                //底部四个按钮设置和搜索可点击
                getIView().setBottomOperationClickable(true);
                getIView().setSearchClick(true);

                if (isCollect()) {
                    getIView().setCollectSuccess();
                    //count +1;
                    int countNum = Integer.valueOf(collectNum) + 1;
                    collectNum = String.valueOf(countNum);
                } else {
                    getIView().setCollectCancel();
                }

                getIView().setToolBarSubTitle(desc);
                getIView().setListenNum(songSheetDetail.getListenum());
                getIView().setSongSheetTitle(title);
                getIView().setSongSheetTag(tag);
                getIView().setCollectNum(collectNum);
                getIView().setCommentNum(commentNum);
                getIView().setShareNum(shareNum);
                getIView().setBgAndSongSheetCover(url);

                //Glide图片缓存获取
                   /* new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FutureTarget<File> future = Glide.with(getIView().mContext)
                                    .load(url)
                                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                            try {
                                File cacheFile = future.get();
                                path = cacheFile.getAbsolutePath();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            getIView().Log(path);

                            getIView().getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getIView().setBgAndSongSheetCover(path);
                                }
                            });

                        }
                    }).start();*/

                songSheetDetailList = songSheetDetail.getContent();

                getIView().setSongSheetAdapter(songSheetDetailList);
                getIView().setAdapterListener();
                getIView().initRecyclerView(songSheetDetailList.size(), collectNum);

                getIView().closeLoading();
            }

            @Override
            public void getFail(String msg) {
                getIView().showMsg(msg);
            }
        });

    }

    @Override
    public void showMoreWindow() {
        getIView().initMoreWindow(ScreenUtil.getStatusBarHeight(getIView().mContext));
        getIView().showSortMode();
        getIView().showEmptyFile();
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
    public void collectSongSheet() {
        if (isCollect()) {
            getIView().showConfirmCancelCollect();
        } else {
            getIView().showMsg("收藏成功");

            addCollectSongSheet();
            getIView().setCollectSuccess();
            //count +1;
            int countNum = Integer.valueOf(collectNum) + 1;

            collectNum = String.valueOf(countNum);

            getIView().setCollectCount(collectNum);
        }

    }

    @Override
    public void addCollectSongSheet() {
        CollectSongSheet collectSongSheet = new CollectSongSheet(listID, url, title, tag);

        getiModel().addCollectSongSheet(collectSongSheet, getIView().mContext);
    }

    @Override
    public void delectCollectSongSheet() {
        CollectSongSheet collectSongSheet = getiModel().searchCollectSongSheet(title, getIView().mContext);

        getiModel().delectCollectSongSheet(collectSongSheet, getIView().mContext);

        getIView().setCollectCancel();
        //count -1;
        int countNum = Integer.valueOf(collectNum) - 1;

        collectNum = String.valueOf(countNum);

        getIView().setCollectCount(collectNum);

        getIView().showMsg("已取消收藏");
    }

    @Override
    public boolean isCollect() {
        if (getiModel().searchCollectSongSheet(title, getIView().mContext) == null) {
            return false;
        } else {
            return true;
        }

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
    public void collectToMySongSheet(final MySongSheet mySongSheet, final ContentBean songInfo) {

        if (getiModel().searchSongIsCollect(songInfo.getTitle(), getIView().mContext, mySongSheet.getSongSheetName()) == null) {
            String songSheetDetailSongID = songInfo.getSong_id();

            getiModel().getSongInfo(getIView().subscription, songSheetDetailSongID, new SongSheetDetailModel.GetSongInfoListener() {
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
                    getiModel().addCollectToMySongSheet(contentBean, getIView().mContext, mySongSheet.getSongSheetName());

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
    public void searchSong() {
        Bundle bundle = new Bundle();

        ArrayList list = new ArrayList(); //这个list用于在bundle中传递 需要传递的songSheetDetailList
        list.add(songSheetDetailList);
        bundle.putParcelableArrayList(Constants.SEARCH_LIST, list);
        bundle.putString(Constants.URL, url);

        jumpToDetail(bundle);
    }

    @Override
    public void jumpToCollectorInfo() {
        getIView().showMsg("正在开发中...");
    }

    @Override
    public void jumpToDetail(Bundle bundle) {
        FragmentTransaction ft = getIView().getFragmentManager().beginTransaction();
        SongSheetDetailSearchFragment searchFragment = SongSheetDetailSearchFragment.getInstance();
        searchFragment.setArguments(bundle);

        if (type == Constants.COLLECT_SONG_SHEET_ONLINE) {
            ft.add(R.id.ll_discover_root, searchFragment);
        } else if (type == Constants.COLLECT_SONG_SHEET_LOCAL) {
            ft.add(R.id.fl_home_content, searchFragment);
        }

        ft.hide(getIView());
        //将当前的事务添加到了回退栈
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showCoverDialog() {
        getIView().initCoverDialog(url, title, desc, tag);
    }

    @Override
    public void setDismissMarginTop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setDismissMarginTop();
        }
    }

    @Override
    public void jumpToSongSelect() {
        Intent intent = new Intent(getIView().mContext, SongSelectActivity.class);
        intent.putExtra(Constants.SEARCH_LIST, (Serializable) songSheetDetailList);
        getIView().getActivity().startActivity(intent);
        //设置activity跳转动画
        //getIView().getActivity().overridePendingTransition(R.anim.activity_transition_enter, R.anim.activity_transition_exit);
        //去除activity跳转动画
        getIView().getActivity().overridePendingTransition(0, 0);
    }

    int index = 0;

    @Override
    public void jumpToPlay(ContentBean songSheetDetail) {
        for (int i = 0; i < songSheetDetailList.size(); i++) {

            if (songSheetDetail == null) {
                index = 0;
            } else {
                if (songSheetDetail.getSong_id().equals(songSheetDetailList.get(i).getSong_id())) {
                    index = i;
                }
            }

            final int t = i;

            String songid = songSheetDetailList.get(i).getSong_id();

            getiModel().getSongInfo(getIView().subscription, songid, new SongSheetDetailModel.GetSongInfoListener() {
                @Override
                public void getSuccess(PlaySongInfoBean playSongInfoBean) {

                    String file_link = playSongInfoBean.getBitrate().getFile_link();

                    String pic_url = playSongInfoBean.getSonginfo().getPic_big();
                    String lrc_url = playSongInfoBean.getSonginfo().getLrclink();

                    songSheetDetailList.get(t).setLocalUrl(file_link);
                    songSheetDetailList.get(t).setPic_url(pic_url);
                    songSheetDetailList.get(t).setLrc_url(lrc_url);

                    if (t == songSheetDetailList.size() - 1) {
                        mServiceManager.refreshMusicList(songSheetDetailList);

                        mServiceManager.play(index);

                        if (getIView().getActivity() instanceof playBarVisible) {
                            ((playBarVisible) getIView().getActivity()).showPlayBar();
                        }
                    }

                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
        }

    }

    @Override
    public void download() {
        if (songSheetDetailList != null) {
            getIView().showMsg("已加入下载队列");

            for (final ContentBean songInfo : songSheetDetailList) {
                getiModel().getSongInfo(getIView().subscription, songInfo.getSong_id(), new SongSheetDetailModel.GetSongInfoListener() {
                    @Override
                    public void getSuccess(PlaySongInfoBean playSongInfoBean) {

                        DownLoadManager.getInstance().downLoad(getIView().mContext, playSongInfoBean.getBitrate().getFile_link(), songInfo.getTitle() + " - " +
                                songInfo.getAuthor() + ".mp3");
                    }

                    @Override
                    public void getFail(String msg) {
                        getIView().showMsg(msg);
                    }
                });
            }
        }
    }

    @Override
    public void download(final ContentBean songInfo) {

        getIView().showMsg("已加入下载队列");

        getiModel().getSongInfo(getIView().subscription, songInfo.getSong_id(), new SongSheetDetailModel.GetSongInfoListener() {
            @Override
            public void getSuccess(PlaySongInfoBean playSongInfoBean) {

                DownLoadManager.getInstance().downLoad(getIView().mContext, playSongInfoBean.getBitrate().getFile_link(), songInfo.getTitle() + " - " +
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
    public void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(desc);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(url);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(titleUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("最音乐，动听音乐。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("最音乐");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(titleUrl);

        // 启动分享GUI
        oks.show(getIView().mContext);
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
        oks.setImageUrl(url);
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