package somebody_z.me.zuimusic.mvp.Contract;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.model.bean.HotAlbumBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.widget.anchorradio.AnchorRadioPanel;
import somebody_z.me.zuimusic.widget.hotalbum.HotAlbumPanel;
import somebody_z.me.zuimusic.widget.newmusic.NewMusicPanel;
import somebody_z.me.zuimusic.widget.recommendsongsheet.RecommendSongSheetPanel;
import somebody_z.me.zuimusic.widget.ThreeMenuPanel;

/**
 * 个性推荐 契约类
 * Created by Huanxing Zeng on 2016/12/30.
 * email : zenghuanxing123@163.com
 */
public class RecommendContract {

    public interface RecommendView {

        void addLoopPic(List<String> urls, List<String> webUrls);

        void addMenu();

        void addRecommendSongSheet(List<RecommendSongSheetBean.SongSheetListBean> songSheetListBeen);

        void addHotAlbum(List<HotAlbumBean.ListBean> list);

        void addNewMusic(List<NewMusicBean.SongListBean> songListBeen);

        void addAnchorRadio(List<AnchorRadioBean.RadioList> radioLists);

        void addBottomLine();

    }

    public interface RecommendPresenter {

        void initViews();

        void getLoopPic();

        void setMenuEvent(ThreeMenuPanel threeMenuPanel);

        void getRecommendSongSheet();

        void setRecommendSongSheetListener(RecommendSongSheetPanel recommendSongSheetPanels);

        void getHotAlbum();

        void setHotAlbumListener(HotAlbumPanel hotAlbumPanel);

        void getNewMusic();

        void setNewMusicListener(NewMusicPanel newMusicPanel);

        void getAnchorRadio();

        void setAnchorRadioListener(AnchorRadioPanel anchorRadioPanel);

        void jumpToDetail(Bundle bundle, Fragment fragment);

    }
}
