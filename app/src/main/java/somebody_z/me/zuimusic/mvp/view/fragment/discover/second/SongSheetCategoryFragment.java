package somebody_z.me.zuimusic.mvp.view.fragment.discover.second;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.SongSheetCategoryContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetCategoryBean;
import somebody_z.me.zuimusic.mvp.presenter.SongSheetCategoryPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.SongSheetCategoryAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/2/15.
 * email : zenghuanxing123@163.com
 */
public class SongSheetCategoryFragment extends BaseFragment<SongSheetCategoryPresenter> implements
        SongSheetCategoryContract.SongSheetCategoryView {

    @Bind(R.id.btn_song_sheet_category_all)
    Button btnSongSheetCategoryAll;
    @Bind(R.id.ll_song_sheet_category_root)
    LinearLayout llSongSheetCategoryRoot;
    @Bind(R.id.iv_song_sheet_category_loading)
    ImageView ivSongSheetCategoryLoading;
    @Bind(R.id.ll_song_sheet_category_loading)
    LinearLayout llSongSheetCategoryLoading;
    @Bind(R.id.ll_songsheet_category_back)
    LinearLayout llSongsheetCategoryBack;
    @Bind(R.id.ll_songsheet_category_bar)
    LinearLayout llSongsheetCategoryBar;

    public SongSheetCategoryFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final SongSheetCategoryFragment INSTANCE = new SongSheetCategoryFragment();
    }

    public static SongSheetCategoryFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected SongSheetCategoryPresenter loadPresenter() {
        return new SongSheetCategoryPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mPresenter.isImmerse();
        mPresenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_songsheet_category;
    }

    @OnClick({R.id.ll_songsheet_category_back, R.id.btn_song_sheet_category_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_songsheet_category_back:
                mPresenter.onBackPressed();
                break;
            case R.id.btn_song_sheet_category_all:
                mPresenter.setTag("全部歌单");
                break;
        }
    }

    @Override
    public void setBarPaddingTop() {
        llSongsheetCategoryBar.setPadding(0, ScreenUtil.getStatusBarHeight(mContext), 0, 0);
    }

    @Override
    public void addRecyclerView(List<SongSheetCategoryBean.TagsBean> tagsBeanList, String selectedTag, String type, int index) {

        RecyclerView recyclerView = new RecyclerView(mContext);

        recyclerView.setPadding(0, DisplayUtils.dp2px(mContext, 10), 0, 0);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });

        recyclerView.setLayoutManager(layoutManager);

        //设置间距，item上下左右margin，由于中间间距为最边界的两倍，
        //所以需给recyclerview设置margin

        SongSheetCategoryAdapter mAdapter = new SongSheetCategoryAdapter(mContext, tagsBeanList, selectedTag, type, index);

        mAdapter.setOnItemClickListener(new SongSheetCategoryAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, SongSheetCategoryBean.TagsBean tagsBean) {
                mPresenter.setTag(tagsBean.getTag());
            }
        });

        recyclerView.setAdapter(mAdapter);

        llSongSheetCategoryRoot.addView(recyclerView);
    }

    @Override
    public void setAllSelected(int rid) {
        btnSongSheetCategoryAll.setBackgroundResource(rid);
    }

    @Override
    public boolean onBackPressed() {
        mPresenter.onBackPressed();
        return super.onBackPressed();
    }

    public void showLoading() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llSongSheetCategoryLoading.getLayoutParams();
        params.setMargins(0, DisplayUtils.dp2px(mContext, 135), 0, 0);
        llSongSheetCategoryRoot.setVisibility(View.GONE);
        llSongSheetCategoryLoading.setLayoutParams(params);
        llSongSheetCategoryLoading.setVisibility(View.VISIBLE);
        ivSongSheetCategoryLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivSongSheetCategoryLoading.getDrawable();
        animationDrawable.start();
    }

    public void closeLoading() {
        llSongSheetCategoryLoading.setVisibility(View.GONE);
        llSongSheetCategoryRoot.setVisibility(View.VISIBLE);
        ivSongSheetCategoryLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivSongSheetCategoryLoading.getDrawable();
        animationDrawable.stop();
    }

}
