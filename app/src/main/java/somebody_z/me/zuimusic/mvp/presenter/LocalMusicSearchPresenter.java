package somebody_z.me.zuimusic.mvp.presenter;

import android.os.Build;
import android.view.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.mvp.Contract.LocalMusicSearchContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.LocalMusicSearchModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.LocalMusicSearchFragment;

/**
 * Created by Huanxing Zeng on 2017/2/28.
 * email : zenghuanxing123@163.com
 */
public class LocalMusicSearchPresenter extends BasePresenter<LocalMusicSearchModel, LocalMusicSearchFragment> implements
        LocalMusicSearchContract.LocalMusicSearchPresenter {
    String url;
    List<ContentBean> songSheetDetailList;

    @Override
    public LocalMusicSearchModel loadModel() {
        return new LocalMusicSearchModel();
    }

    @Override
    public void isImmerse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setBarMarginTop();
        }
    }

    /**
     * 模拟返回键
     */
    @Override
    public void onBackPressed() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        getIView().setEditTextHint("搜索本地歌曲");

        songSheetDetailList = getiModel().getLocalSong(getIView().mContext);

        getIView().setBackground();
    }

    @Override
    public void search(String content) {
        List<ContentBean> searchList = new ArrayList<>();

        //匹配输入内容---歌名，歌手，唱片
        for (int i = 0; i < songSheetDetailList.size(); i++) {
            if (songSheetDetailList.get(i).getTitle().contains(content)) {
                searchList.add(songSheetDetailList.get(i));
            } else if (songSheetDetailList.get(i).getAuthor().contains(content)) {
                searchList.add(songSheetDetailList.get(i));
            } else if (songSheetDetailList.get(i).getAlbum_title().contains(content)) {
                searchList.add(songSheetDetailList.get(i));
            }

        }

        //如果内容为空，则清空searchList。
        if (content.equals("")) {
            searchList.clear();
        }

        getIView().setSongSheetAdapter(searchList);
        getIView().setAdapterListener();
    }

}
