package somebody_z.me.zuimusic.mvp.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.Contract.RecommendContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.RecommendModel;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.model.bean.HotAlbumBean;
import somebody_z.me.zuimusic.mvp.model.bean.LoopPicBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.DiscoverFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.RecommendFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.AlbumDetailFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.NewMusicDetailFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetDetailFragment;
import somebody_z.me.zuimusic.widget.anchorradio.AnchorRadioPanel;
import somebody_z.me.zuimusic.widget.hotalbum.HotAlbumPanel;
import somebody_z.me.zuimusic.widget.newmusic.NewMusicPanel;
import somebody_z.me.zuimusic.widget.recommendsongsheet.RecommendSongSheetPanel;
import somebody_z.me.zuimusic.widget.ThreeMenuPanel;

/**
 * Created by Huanxing Zeng on 2016/12/30.
 * email : zenghuanxing123@163.com
 */
public class RecommendPresenter extends BasePresenter<RecommendModel, RecommendFragment> implements RecommendContract.RecommendPresenter {
    @Override
    public RecommendModel loadModel() {
        return new RecommendModel();
    }

    @Override
    public void initViews() {
        //添加轮播图以及三个按钮菜单
        getIView().showLoading();
        getLoopPic();
    }

    @Override
    public void getLoopPic() {
        try {
            getiModel().getLoopPic(getIView().subscription, 8, new RecommendModel.GetLoopPicListener() {
                @Override
                public void getSuccess(LoopPicBean loopPicBean) {
                    List<LoopPicBean.PicBean> picBeanList = loopPicBean.getPic();

                    List<String> urls = new ArrayList<>();
                    List<String> webUrls = new ArrayList<>();

                    for (int i = 0; i < picBeanList.size(); i++) {
                        String url = picBeanList.get(i).getRandpic();
                        String webUrl = picBeanList.get(i).getCode();
                        urls.add(url);
                        webUrls.add(webUrl);
                    }
                    getIView().closeLoading();

                    //添加轮播图
                    getIView().addLoopPic(urls, webUrls);
                    //添加三个按钮
                    getIView().addMenu();
                    getRecommendSongSheet();
                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
        } catch (Exception e) {

        }

    }

    @Override
    public void setMenuEvent(ThreeMenuPanel threeMenuPanel) {
        threeMenuPanel.setJumpListener(new ThreeMenuPanel.JumpListener() {
            @Override
            public void setPersonalFMListener() {
                getIView().Log("jump to personalFm");
            }

            @Override
            public void setDailyRecommendListener() {
                //固定写死，^-^
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ID, "7107");
                bundle.putString(Constants.TYPE, Constants.COLLECT_SONG_SHEET_ONLINE);

                jumpToDetail(bundle, SongSheetDetailFragment.getInstance());
            }

            @Override
            public void setCloundRecommendListener() {
                DiscoverFragment.getInstance().setCurrIndex(3);
            }
        });
    }

    @Override
    public void getRecommendSongSheet() {
        try {
            getiModel().getRecommendSongSheet(getIView().subscription, 6, new RecommendModel.GetSongSheetListener() {
                @Override
                public void getSuccess(RecommendSongSheetBean recommendSongSheetBean) {
                    List<RecommendSongSheetBean.SongSheetListBean> songSheetListBeen =
                            recommendSongSheetBean.getContent().getList();
                    //添加推荐歌单
                    getIView().addRecommendSongSheet(songSheetListBeen);

                    //添加主播电台
                    getAnchorRadio();

                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
        } catch (Exception e) {

        }

    }

    @Override
    public void setRecommendSongSheetListener(RecommendSongSheetPanel recommendSongSheetPanels) {
        recommendSongSheetPanels.setSongSheetPanelListener(new RecommendSongSheetPanel.SongSheetPanelListener() {
            @Override
            public void setPanel(RecommendSongSheetBean.SongSheetListBean songSheetListBean) {
                //跳转歌单详情
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ID, songSheetListBean.getListid());
                bundle.putString(Constants.TYPE, Constants.COLLECT_SONG_SHEET_ONLINE);

                jumpToDetail(bundle, SongSheetDetailFragment.getInstance());
            }

            @Override
            public void loadMore() {
                //获取更多歌单
                DiscoverFragment.getInstance().setCurrIndex(1);
            }
        });
    }

    @Override
    public void getHotAlbum() {
        try {
            getiModel().getHotAlbum(getIView().subscription, 10, 6, new RecommendModel.GetHotAlbumListener() {
                @Override
                public void getSuccess(HotAlbumBean hotAlbumBean) {
                    List<HotAlbumBean.ListBean> list = hotAlbumBean.getPlaze_album_list().getRM().getAlbum_list().getList();

                    getIView().addHotAlbum(list);

                    //添加最新音乐
                    getNewMusic();
                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void setHotAlbumListener(HotAlbumPanel hotAlbumPanel) {
        hotAlbumPanel.setHotAlbumListener(new HotAlbumPanel.HotAlbumListener() {
            @Override
            public void getDetail(HotAlbumBean.ListBean list) {
                //跳转唱片详情
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ID, list.getAlbum_id());
                bundle.putString(Constants.TYPE, Constants.ONLINE_ALBUM);

                jumpToDetail(bundle, AlbumDetailFragment.getInstance());
            }

            @Override
            public void loadMore() {
                //获取更多唱片
            }
        });
    }

    @Override
    public void getNewMusic() {
        try {
            getiModel().getNewMusic(getIView().subscription, 6, new RecommendModel.GetNewMusicListener() {
                @Override
                public void getSuccess(NewMusicBean newMusicBean) {
                    List<NewMusicBean.SongListBean> list = newMusicBean.getContent().get(0).getSong_list();

                    getIView().addNewMusic(list);

                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void setNewMusicListener(NewMusicPanel newMusicPanel) {
        newMusicPanel.setNewMusicPanelListener(new NewMusicPanel.NewMusicPanelListener() {
            @Override
            public void setPanel(NewMusicBean.SongListBean songListBean) {
                //跳转音乐详情页
                Bundle bundle = new Bundle();

                ArrayList list = new ArrayList(); //这个list用于在bundle中传递 需要传递的songSheetDetailList
                list.add(songListBean);
                bundle.putParcelableArrayList(Constants.NEWMUSIC, list);

                jumpToDetail(bundle, NewMusicDetailFragment.getInstance());
            }

            @Override
            public void loadMore() {
                //获取更多音乐
            }
        });
    }

    @Override
    public void getAnchorRadio() {
        try {
            getiModel().getAnchorRadio(getIView().subscription, 3, new RecommendModel.GetAnchorRadioListener() {
                @Override
                public void getSuccess(AnchorRadioBean anchorRadioBean) {
                    List<AnchorRadioBean.RadioList> list = anchorRadioBean.getList();
                    getIView().addAnchorRadio(list);

                    //添加热门唱片
                    getHotAlbum();
                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void setAnchorRadioListener(AnchorRadioPanel anchorRadioPanel) {
        anchorRadioPanel.setAnchorRadioPanelListener(new AnchorRadioPanel.AnchorRadioPanelListener() {
            @Override
            public void setPanel(AnchorRadioBean.RadioList radioList) {
                //播放电台
                getIView().Log(radioList.getTitle());
                getIView().showMsg("获取数据失败，请稍后重试。");
            }

            @Override
            public void loadMore() {
                DiscoverFragment.getInstance().setCurrIndex(2);
            }
        });
    }

    @Override
    public void jumpToDetail(Bundle bundle, Fragment fragment) {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).hideBar();
        }
        DiscoverFragment.getInstance().hideTab();

        FragmentTransaction ft = getIView().getFragmentManager().beginTransaction();

        fragment.setArguments(bundle);
        ft.add(R.id.ll_discover_root, fragment);
        ft.hide(getIView());
        //将当前的事务添加到了回退栈
        ft.addToBackStack(null);
        ft.commit();
    }

}
