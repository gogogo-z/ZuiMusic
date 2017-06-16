package somebody_z.me.zuimusic.mvp.Contract;


import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.view.adapter.AnchorHotAdapter;
import somebody_z.me.zuimusic.mvp.view.adapter.HorizontalPageAdapter;
import somebody_z.me.zuimusic.widget.anchordayradio.AnchorDayRadioPanel;
import somebody_z.me.zuimusic.widget.anchorradio.AnchorRadioPanel;
import somebody_z.me.zuimusic.widget.anchorradiorank.AnchorRadioRankPanel;

/**
 * Created by Huanxing Zeng on 2017/1/11.
 * email : zenghuanxing123@163.com
 */
public class AnchorContract {

    public interface AnchorView {
        void setHorizontalAdapter(int[] imageList, String[] nameList);

        void showMsg(String msg);

        void addAnchorRadioRank(List<AnchorRadioBean.RadioList> radioLists);

        void addExcellen(List<AnchorRadioBean.RadioList> radioLists);

        void addDay(List<AnchorRadioBean.RadioList> radioLists);

        void addHot(List<AnchorRadioBean.RadioList> radioLists);

        void addBottomLine();
    }

    public interface AnchorPresenter {
        void setHorizontalAdapter();

        void settHorizontalAdapterListener(HorizontalPageAdapter mAdapter);

        void setAnchorRadioRankListener(AnchorRadioRankPanel anchorRadioRankPanel);

        void horizontalListener(String name);

        void getAnchorRadio();

        void setExcellentListener(AnchorRadioPanel anchorRadioPanel);

        void setDayListener(AnchorDayRadioPanel anchorDayRadioPanel);

        void setAnchorHotAdapterListener(AnchorHotAdapter anchorHotAdapter);
    }

}
