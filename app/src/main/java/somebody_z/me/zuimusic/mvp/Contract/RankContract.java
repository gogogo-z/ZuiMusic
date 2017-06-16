package somebody_z.me.zuimusic.mvp.Contract;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.RankBean;
import somebody_z.me.zuimusic.widget.rankglobal.RankGlobalPanel;
import somebody_z.me.zuimusic.widget.rankofficial.RankOfficialPanel;

/**
 * Created by Huanxing Zeng on 2017/1/13.
 * email : zenghuanxing123@163.com
 */
public class RankContract {

    public interface RankView {

        void addRankOfficial(List<RankBean.ContentBean> contentBeenList);

        void addRankGlobal(List<RankBean.ContentBean> contentBeenList);

    }

    public interface RankPresenter {
        void getRank();

        void setRankOfficialListener(RankOfficialPanel rankOfficialPanel);

        void setRankGlobalListener(RankGlobalPanel rankGlobalPanel);

        void jumpToDetail(Bundle bundle, Fragment fragment);
    }

}
