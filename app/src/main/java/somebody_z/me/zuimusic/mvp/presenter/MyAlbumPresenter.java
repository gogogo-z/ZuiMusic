package somebody_z.me.zuimusic.mvp.presenter;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.Contract.MyAlbumContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.MyAlbumModel;
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.AlbumDetailFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.MyAlbumFragment;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class MyAlbumPresenter extends BasePresenter<MyAlbumModel, MyAlbumFragment> implements MyAlbumContract.MyAlbumPresenter {
    @Override
    public MyAlbumModel loadModel() {
        return new MyAlbumModel();
    }

    @Override
    public void isImmerse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setBarMarginTop();
        }
    }

    @Override
    public void init() {
        getIView().setMyAlbumAdapter(getiModel().getMyAlbum(getIView().mContext));
    }

    //跳转详情
    @Override
    public void goToDetail(AlbumDetailBean.AlbumInfoBean albumInfoBean) {
        //跳转唱片详情
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ID, albumInfoBean.getAlbum_id());
        bundle.putString(Constants.TYPE, Constants.MY_ALBUM);

        jumpToDetail(bundle, AlbumDetailFragment.getInstance());
    }

    @Override
    public void jumpToDetail(Bundle bundle, Fragment fragment) {
        FragmentTransaction ft = getIView().getFragmentManager().beginTransaction();

        fragment.setArguments(bundle);
        ft.add(R.id.fl_home_content, fragment);
        ft.hide(getIView());
        //将当前的事务添加到了回退栈
        ft.addToBackStack(null);
        ft.commit();
    }

    //弹出设置框
    @Override
    public void goToOption(AlbumDetailBean.AlbumInfoBean albumInfoBean) {
        getIView().showSongSheetDetailPopupWindow(albumInfoBean);
    }

    @Override
    public void showSongSheetDetailEdit(String listID) {
        if (listID != null) {
            getIView().setSongSheetDetailEditVisible();
        }
    }

    @Override
    public void showDeleteConfirm(AlbumDetailBean.AlbumInfoBean albumInfoBean) {
        getIView().showComfirmDelete(albumInfoBean);
    }

    @Override
    public void jumpToSongSheetInfoManager() {

    }

    @Override
    public void deleteSongSheet(AlbumDetailBean.AlbumInfoBean albumInfoBean) {
        getiModel().deleteMyAlbum(albumInfoBean, getIView().mContext);

        init();
        getIView().toast("删除成功");
    }

    @Override
    public void onBackPressed() {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).showBar();
        }
        getIView().getFragmentManager().popBackStack();
    }
}
