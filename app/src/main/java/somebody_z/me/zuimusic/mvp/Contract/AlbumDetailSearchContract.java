package somebody_z.me.zuimusic.mvp.Contract;

import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;

/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class AlbumDetailSearchContract {

    public interface SearchView {
        void setBarMarginTop();

        void setEditTextHint(String hint);

        void setBackground(String url);

        void setSongSheetAdapter(List<AlbumDetailBean.SonglistBean> songSheetDetailList);

        void setAdapterListener();

    }

    public interface SearchPresenter {
        void isImmerse();

        void onBackPressed();

        void init();

        void search(String content);

    }

}
