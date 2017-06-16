package somebody_z.me.zuimusic.mvp.Contract;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.AllSongSheetBean;

/**
 * 歌单 契约类
 * Created by Huanxing Zeng on 2016/12/30.
 * email : zenghuanxing123@163.com
 */
public class SongSheetContract {

    public interface SongSheetView {
        void setAdapter(List<AllSongSheetBean.ContentBean> songSheetList);

        void setCategoryAdapter(List<AllSongSheetBean.ContentBean> songSheetList);

        void setHeader(AllSongSheetBean.ContentBean songSheet);

        void setCategoryTitle(String tagName);
    }

    public interface SongSheetPresenter {
        int getPageNo();

        void jumpToDetail(AllSongSheetBean.ContentBean songSheet);

        void jumpToDetail(Bundle bundle, Fragment fragment);

        void jumpToDetail();

        void jumpToCategory();

        void getTag(boolean hidden);

    }
}
