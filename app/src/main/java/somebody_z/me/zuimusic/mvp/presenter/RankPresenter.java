package somebody_z.me.zuimusic.mvp.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.Contract.RankContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.RankModel;
import somebody_z.me.zuimusic.mvp.model.bean.RankBean;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.DiscoverFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.RankFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.RankDetailFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetDetailFragment;
import somebody_z.me.zuimusic.widget.rankglobal.RankGlobalPanel;
import somebody_z.me.zuimusic.widget.rankofficial.RankOfficialPanel;

/**
 * Created by Huanxing Zeng on 2017/1/13.
 * email : zenghuanxing123@163.com
 */
public class RankPresenter extends BasePresenter<RankModel, RankFragment> implements RankContract.RankPresenter {
    @Override
    public RankModel loadModel() {
        return new RankModel();
    }

    @Override
    public void getRank() {
        try {
            getiModel().getAllRank(getIView().subscription, 1, new RankModel.GetAllRankListener() {
                @Override
                public void getSuccess(RankBean rankBean) {
                    getIView().closeLoading();
                    List<RankBean.ContentBean> contentBeenList = rankBean.getContent();

                    List<RankBean.ContentBean> rankOfficialList = new ArrayList<RankBean.ContentBean>();
                    List<RankBean.ContentBean> rankGlobalList = new ArrayList<RankBean.ContentBean>();

                    for (int i = 0; i < contentBeenList.size(); i++) {
                        int type = contentBeenList.get(i).getType();
                        if (type < 20) {
                            rankOfficialList.add(contentBeenList.get(i));
                        } else {
                            rankGlobalList.add(contentBeenList.get(i));
                        }
                    }

                    getIView().addRankOfficial(rankOfficialList);
                    getIView().addRankGlobal(rankGlobalList);

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
    public void setRankOfficialListener(RankOfficialPanel rankOfficialPanel) {
        rankOfficialPanel.setRankOfficialPanelListener(new RankOfficialPanel.RankOfficialPanelListener() {
            @Override
            public void setPanel(RankBean.ContentBean contentBean) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TYPE, String.valueOf(contentBean.getType()));

                jumpToDetail(bundle, RankDetailFragment.getInstance());
            }
        });
    }

    @Override
    public void setRankGlobalListener(RankGlobalPanel rankGlobalPanel) {
        rankGlobalPanel.setRankGlobalPanelListener(new RankGlobalPanel.RankGlobalPanelListener() {
            @Override
            public void setPanel(RankBean.ContentBean contentBean) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TYPE, String.valueOf(contentBean.getType()));

                jumpToDetail(bundle, RankDetailFragment.getInstance());
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

    public void showLoading() {
        getIView().showLoading();
    }
}
