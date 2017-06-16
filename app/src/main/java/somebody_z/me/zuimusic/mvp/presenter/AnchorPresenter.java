package somebody_z.me.zuimusic.mvp.presenter;

import android.view.View;

import java.util.List;

import somebody_z.me.zuimusic.mvp.Contract.AnchorContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.AnchorModel;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.view.adapter.AnchorHotAdapter;
import somebody_z.me.zuimusic.mvp.view.adapter.HorizontalPageAdapter;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.AnchorFragment;
import somebody_z.me.zuimusic.widget.anchordayradio.AnchorDayRadioPanel;
import somebody_z.me.zuimusic.widget.anchorradio.AnchorRadioPanel;
import somebody_z.me.zuimusic.widget.anchorradiorank.AnchorRadioRankPanel;

/**
 * Created by Huanxing Zeng on 2017/1/11.
 * email : zenghuanxing123@163.com
 */
public class AnchorPresenter extends BasePresenter<AnchorModel, AnchorFragment> implements AnchorContract.AnchorPresenter {
    private boolean isFirst = true;

    private int num = 51;
    private int lastNum;

    @Override
    public AnchorModel loadModel() {
        return new AnchorModel();
    }

    @Override
    public void setHorizontalAdapter() {
        getIView().setHorizontalAdapter(getiModel().getImageData(), getiModel().getNameData());
    }

    @Override
    public void settHorizontalAdapterListener(HorizontalPageAdapter mAdapter) {
        mAdapter.setItemClickListener(new HorizontalPageAdapter.ItemClickListener() {
            @Override
            public void click(String name) {
                horizontalListener(name);
            }
        });
    }

    /**
     * 主播电台排行榜
     *
     * @param anchorRadioRankPanel
     */
    @Override
    public void setAnchorRadioRankListener(AnchorRadioRankPanel anchorRadioRankPanel) {
        anchorRadioRankPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIView().Log("anchor radio rank");
                getIView().showMsg("获取数据失败，请稍后重试。");
            }
        });
    }

    @Override
    public void horizontalListener(String name) {
        getIView().showMsg(name);
    }

    @Override
    public void getAnchorRadio() {
        // 第一次获取51条，第二次获取51+12条，删除前51条，
        // 第三次获取51+12+12条，删除前51+12条，以此类推。
        try {
            getiModel().getAnchorRadio(getIView().subscription, num, new AnchorModel.GetAnchorRadioListener() {
                @Override
                public void getSuccess(AnchorRadioBean anchorRadioBean) {
                    List<AnchorRadioBean.RadioList> allList = anchorRadioBean.getList();
                    if (isFirst) {
                        getIView().closeLoading();
                        List<AnchorRadioBean.RadioList> rankList = allList.subList(0, 3);

                        List<AnchorRadioBean.RadioList> excellentList = allList.subList(3, 7);

                        List<AnchorRadioBean.RadioList> dayList = allList.subList(7, 13);

                        List<AnchorRadioBean.RadioList> hotRadioList = allList.subList(13, num);

                        //分别加载
                        getIView().addAnchorRadioRank(rankList);
                        getIView().addExcellen(excellentList);
                        getIView().addDay(dayList);
                        getIView().addHot(hotRadioList);
                        getIView().addBottomLine();

                        isFirst = false;
                    } else {
                        List<AnchorRadioBean.RadioList> hotRadioList = allList.subList(lastNum, num);
                        //加载
                        getIView().setLoadAdapter(hotRadioList);
                    }
                    lastNum = num;
                    num = num + 12;
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
    public void setExcellentListener(AnchorRadioPanel anchorRadioPanel) {
        anchorRadioPanel.setAnchorRadioPanelListener(new AnchorRadioPanel.AnchorRadioPanelListener() {
            @Override
            public void setPanel(AnchorRadioBean.RadioList radioList) {
                getIView().Log(radioList.getTitle());
            }

            @Override
            public void loadMore() {
            }
        });
    }

    /**
     * 每天精选电台
     *
     * @param anchorDayRadioPanel
     */
    @Override
    public void setDayListener(AnchorDayRadioPanel anchorDayRadioPanel) {
        anchorDayRadioPanel.setSongSheetPanelListener(new AnchorDayRadioPanel.SongSheetPanelListener() {
            @Override
            public void setPanel(AnchorRadioBean.RadioList radioList) {
                getIView().Log(radioList.getTitle());
                getIView().showMsg("获取数据失败，请稍后重试。");
            }

            @Override
            public void loadMore() {

            }
        });
    }

    /**
     * 精彩节目推荐
     *
     * @param anchorHotAdapter
     */
    @Override
    public void setAnchorHotAdapterListener(AnchorHotAdapter anchorHotAdapter) {
        anchorHotAdapter.setOnItemClickListener(new AnchorHotAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, AnchorRadioBean.RadioList radioList) {
                getIView().Log(radioList.getTitle());
                getIView().showMsg("获取数据失败，请稍后重试。");
            }
        });
    }

    public void showLoading() {
        getIView().showLoading();
    }

}
