package somebody_z.me.zuimusic.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import rx.Subscription;
import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.Contract.SongSheetContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.SongSheetModel;
import somebody_z.me.zuimusic.mvp.model.bean.AllSongSheetBean;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.DiscoverFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.SongSheetFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetCategoryFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetDetailFragment;

/**
 * Created by Huanxing Zeng on 2016/12/30.
 * email : zenghuanxing123@163.com
 */
public class SongSheetPresenter extends BasePresenter<SongSheetModel, SongSheetFragment> implements SongSheetContract.SongSheetPresenter {

    private int page_no = 1;

    private boolean isFirst = true;

    private String tagName = "全部歌单";

    private AllSongSheetBean.ContentBean songSheet;

    @Override
    public SongSheetModel loadModel() {
        return new SongSheetModel();
    }

    /**
     * 获取分类的歌单
     *
     * @param page_no
     */
    public void getCategorySongSheet(final int page_no) {
        try {
            getiModel().getCategorySongSheet(getIView().subscription, 15, page_no, tagName, new SongSheetModel.GetCategorySongSheetListener() {
                @Override
                public void getSuccess(AllSongSheetBean allSongSheetBean) {
                    List<AllSongSheetBean.ContentBean> songSheetList = allSongSheetBean.getContent();
                    if (isFirst) {
                        getIView().closeLoading();

                        songSheet = songSheetList.get(0);
                        songSheetList.remove(0);
                        isFirst = false;
                        getIView().setHeader(songSheet);
                        getIView().setCategoryAdapter(songSheetList);
                    } else {
                        //只需添加新的即可，旧的数据有保留
                        getIView().setAdapter(songSheetList);
                    }
                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
        } catch (Exception e) {

        }

    }

    /**
     * 获取全部歌单
     *
     * @param page_no
     */
    public void getAllSongSheet(final int page_no) {
        try {
            getiModel().getAllSongSheet(getIView().subscription, 15, page_no, new SongSheetModel.GetAllSongSheetListener() {
                @Override
                public void getSuccess(AllSongSheetBean allSongSheetBean) {
                    List<AllSongSheetBean.ContentBean> songSheetList = allSongSheetBean.getContent();
                    if (isFirst) {
                        getIView().closeLoading();

                        songSheet = songSheetList.get(0);
                        songSheetList.remove(0);
                        isFirst = false;
                        getIView().setHeader(songSheet);
                        getIView().setCategoryAdapter(songSheetList);
                    } else {
                        //只需添加新的即可，旧的数据有保留
                        getIView().setAdapter(songSheetList);
                    }
                }

                @Override
                public void getFail(String msg) {
                    getIView().showMsg(msg);
                }
            });
        } catch (Exception e) {

        }

    }

    public void loadMore() {
        if (tagName.equals("全部歌单")) {
            getAllSongSheet(getPageNo());
        } else {
            getCategorySongSheet(getPageNo());
        }

    }

    @Override
    public int getPageNo() {
        return page_no++;
    }

    @Override
    public void jumpToDetail(AllSongSheetBean.ContentBean songSheet) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ID, songSheet.getListid());
        bundle.putString(Constants.TYPE, Constants.COLLECT_SONG_SHEET_ONLINE);

        jumpToDetail(bundle, SongSheetDetailFragment.getInstance());
    }

    @Override
    public void jumpToDetail() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ID, songSheet.getListid());
        bundle.putString(Constants.TYPE, Constants.COLLECT_SONG_SHEET_ONLINE);

        jumpToDetail(bundle, SongSheetDetailFragment.getInstance());
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

    @Override
    public void jumpToCategory() {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).hideBar();
        }
        DiscoverFragment.getInstance().hideTab();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.CATEGORY, tagName);

        FragmentTransaction ft = getIView().getFragmentManager().beginTransaction();

        Fragment fragment = SongSheetCategoryFragment.getInstance();

        fragment.setArguments(bundle);
        ft.add(R.id.ll_discover_root, fragment);
        ft.hide(getIView());
        //将当前的事务添加到了回退栈
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void getTag(boolean hidden) {
        if (!hidden) {
            String tag = ((MyApplication) (getIView().getActivity().getApplication())).getTag();
            if (tag != null) {
                if (!tag.equals(tagName)) {
                    tagName = tag;
                    //刷新界面
                    page_no = 1;
                    isFirst = true;
                    getIView().showLoading();
                    getIView().setCategoryTitle(tagName);

                    if (tagName.equals("全部歌单")) {
                        getAllSongSheet(page_no);
                    } else {
                        getCategorySongSheet(page_no);
                    }
                    getPageNo();
                }
            }
        }

    }

    public void showLoading() {
        getIView().showLoading();
    }

}
