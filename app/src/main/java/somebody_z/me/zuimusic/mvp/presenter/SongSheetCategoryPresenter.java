package somebody_z.me.zuimusic.mvp.presenter;

import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.Contract.SongSheetCategoryContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.SongSheetCategoryModel;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetCategoryBean;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.DiscoverFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetCategoryFragment;

/**
 * Created by Huanxing Zeng on 2017/2/15.
 * email : zenghuanxing123@163.com
 */
public class SongSheetCategoryPresenter extends BasePresenter<SongSheetCategoryModel, SongSheetCategoryFragment> implements
        SongSheetCategoryContract.SongSheetCategoryPresenter {
    private String tagName;

    @Override
    public SongSheetCategoryModel loadModel() {
        return new SongSheetCategoryModel();
    }

    @Override
    public void onBackPressed() {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).showBar();
        }
        DiscoverFragment.getInstance().showTab();
        getIView().getFragmentManager().popBackStack();
    }

    @Override
    public void isImmerse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setBarPaddingTop();
        }
    }

    @Override
    public void init() {

        getIView().showLoading();

        Bundle bundle = getIView().getArguments();

        tagName = bundle.getString(Constants.CATEGORY);

        getiModel().getSongSheetCategory(getIView().subscription, new SongSheetCategoryModel.GetCategoryListener() {
            @Override
            public void getSuccess(SongSheetCategoryBean songSheetCategoryBean) {
                getIView().closeLoading();

                List<SongSheetCategoryBean.ContentBean> contentBeenList = songSheetCategoryBean.getContent();

                for (int i = 1; i < contentBeenList.size(); i++) {
                    List<SongSheetCategoryBean.TagsBean> tagsBeanList = new ArrayList<SongSheetCategoryBean.TagsBean>();
                    SongSheetCategoryBean.TagsBean tagsBean = new SongSheetCategoryBean.TagsBean("null", "null");
                    tagsBeanList.add(tagsBean);
                    tagsBeanList.addAll(contentBeenList.get(i).getTags());

                    getIView().addRecyclerView(tagsBeanList, tagName, contentBeenList.get(i).getTitle(), i - 1);
                }

                if (tagName.equals("全部歌单")){
                    getIView().setAllSelected(R.drawable.selector_category_checked);
                }else{
                    getIView().setAllSelected(R.drawable.selector_category);
                }

            }

            @Override
            public void getErrol(String error) {
                getIView().toast(error);
            }
        });

    }

    @Override
    public void setTag(String tag) {

        ((MyApplication) (getIView().getActivity().getApplication())).setTag(tag);

        onBackPressed();
    }

}
