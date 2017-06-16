package somebody_z.me.zuimusic.mvp.presenter;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.Contract.SongSheetDetailSearchContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.SongSheetDetailSearchModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetDetailSearchFragment;

/**
 * Created by Huanxing Zeng on 2017/2/10.
 * email : zenghuanxing123@163.com
 */
public class SongSheetDetailSearchPresenter extends BasePresenter<SongSheetDetailSearchModel, SongSheetDetailSearchFragment> implements SongSheetDetailSearchContract.SearchPresenter {

    String url;
    List<ContentBean> songSheetDetailList;

    @Override
    public SongSheetDetailSearchModel loadModel() {
        return new SongSheetDetailSearchModel();
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
        getIView().setEditTextHint("搜索歌单内歌曲");

        Bundle bundle = getIView().getArguments();
        ArrayList list = bundle.getParcelableArrayList(Constants.SEARCH_LIST);
        url = bundle.getString(Constants.URL);
        //强转成自定义的list，这样得到的list就是传过来的那个list了。
        songSheetDetailList = (List<ContentBean>) list.get(0);

        getIView().setBackground(url);
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
