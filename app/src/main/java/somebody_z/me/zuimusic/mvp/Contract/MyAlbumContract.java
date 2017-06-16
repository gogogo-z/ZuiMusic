package somebody_z.me.zuimusic.mvp.Contract;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class MyAlbumContract {

    public interface MyAlbumView {

        void setBarMarginTop();

        void setMyAlbumAdapter(List<AlbumDetailBean.AlbumInfoBean> mySongSheetList);

        void showSongSheetDetailPopupWindow(AlbumDetailBean.AlbumInfoBean albumInfoBean);

        void setSongSheetDetailEditVisible();

        void showComfirmDelete(AlbumDetailBean.AlbumInfoBean albumInfoBean);

    }

    public interface MyAlbumPresenter {
        void isImmerse();

        void init();

        void goToDetail(AlbumDetailBean.AlbumInfoBean albumInfoBean);

        void jumpToDetail(Bundle bundle, Fragment fragment);

        void goToOption(AlbumDetailBean.AlbumInfoBean albumInfoBean);

        void showSongSheetDetailEdit(String listID);

        void showDeleteConfirm(AlbumDetailBean.AlbumInfoBean albumInfoBean);

        void jumpToSongSheetInfoManager();

        void deleteSongSheet(AlbumDetailBean.AlbumInfoBean albumInfoBean);

        void onBackPressed();
    }

}
