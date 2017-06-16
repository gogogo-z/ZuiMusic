package somebody_z.me.zuimusic.mvp.Contract;

import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.SongSheetCategoryBean;

/**
 * Created by Huanxing Zeng on 2017/2/15.
 * email : zenghuanxing123@163.com
 */
public class SongSheetCategoryContract {


    public interface SongSheetCategoryView {

        void setBarPaddingTop();

        void addRecyclerView(List<SongSheetCategoryBean.TagsBean> tagsBeanList, String selectedTag, String type, int index);

        void setAllSelected(int rid);
    }

    public interface SongSheetCategoryPresenter {
        void onBackPressed();

        void isImmerse();

        void init();

        void setTag(String tag);

    }
}
