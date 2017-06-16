package somebody_z.me.zuimusic.mvp.Contract;

import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;

/**
 * Created by Huanxing Zeng on 2017/2/28.
 * email : zenghuanxing123@163.com
 */
public class LocalMusicSearchContract {

    public interface LocalMusicSearchView {
        void setBarMarginTop();

        void setEditTextHint(String hint);

        void setBackground();

        void setSongSheetAdapter(List<ContentBean> songSheetDetailList);

        void setAdapterListener();

    }

    public interface LocalMusicSearchPresenter {
        void isImmerse();

        void onBackPressed();

        void init();

        void search(String content);
    }

}
