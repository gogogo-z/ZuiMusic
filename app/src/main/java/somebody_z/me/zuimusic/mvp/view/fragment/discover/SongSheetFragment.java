package somebody_z.me.zuimusic.mvp.view.fragment.discover;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.SongSheetContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.AllSongSheetBean;
import somebody_z.me.zuimusic.mvp.presenter.SongSheetPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.SongSheetAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.widget.crefresh.ItemDecoration.SpacesItemDecoration;
import somebody_z.me.zuimusic.widget.crefresh.ZRecyclerView;

/**
 * 歌单页面
 * Created by Huanxing Zeng on 2016/12/29.
 * email : zenghuanxing123@163.com
 */
public class SongSheetFragment extends BaseFragment<SongSheetPresenter> implements SongSheetContract.SongSheetView {

    public SongSheetFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final SongSheetFragment INSTANCE = new SongSheetFragment();
    }

    public static SongSheetFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Bind(R.id.zr_song_sheet)
    ZRecyclerView mRecyclerView;
    @Bind(R.id.tv_song_sheet_category)
    TextView tvSongSheetCategory;
    @Bind(R.id.btn_song_sheet_category)
    Button btnSongSheetCategory;
    @Bind(R.id.iv_song_sheet_loading)
    ImageView ivSongSheetLoading;
    @Bind(R.id.ll_song_sheet_loading)
    LinearLayout llSongSheetLoading;

    private ImageView ivSongSheetHeader;

    private TextView tvSongSheetHeaderTitle;

    private SongSheetAdapter mAdapter;

    private View header;

    @Override
    protected SongSheetPresenter loadPresenter() {
        return new SongSheetPresenter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mPresenter != null) {
            mPresenter.getTag(hidden);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mRecyclerView.setLoadingListener(new ZRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });

        mAdapter.setOnItemClickListener(new SongSheetAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, AllSongSheetBean.ContentBean songSheet) {
                mPresenter.jumpToDetail(songSheet);
            }
        });

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.jumpToDetail();
            }
        });

        btnSongSheetCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.jumpToCategory();
            }
        });
    }

    @Override
    public void initView() {
        mPresenter.showLoading();

        mAdapter = new SongSheetAdapter(mContext);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        header = View.inflate(mContext, R.layout.widget_song_sheet_header, null);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DisplayUtils.dp2px(mContext, 65));

        header.setLayoutParams(params);

        LinearLayout.LayoutParams ivparams = new LinearLayout.LayoutParams(DisplayUtils.dp2px(mContext, 65),
                DisplayUtils.dp2px(mContext, 65));

        ivSongSheetHeader = (ImageView) header.findViewById(R.id.iv_song_sheet_header);

        ivSongSheetHeader.setLayoutParams(ivparams);

        tvSongSheetHeaderTitle = (TextView) header.findViewById(R.id.tv_song_sheet_header_title);

        mRecyclerView.addHeaderView(header);

        mRecyclerView.setLayoutManager(layoutManager);

        //设置间距，item上下左右margin，由于中间间距为最边界的两倍，
        //所以需给recyclerview设置margin
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(DisplayUtils.dp2px(mContext, 5)));

        mRecyclerView.setPullRefreshEnabled(false);

        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getAllSongSheet(mPresenter.getPageNo());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_songsheet;
    }

    @Override
    public void setAdapter(List<AllSongSheetBean.ContentBean> songSheetList) {
        mAdapter.addItems(songSheetList);
        //加载完成后记得调用此方法，不然下一次加载无法调用
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void setCategoryAdapter(List<AllSongSheetBean.ContentBean> songSheetList) {
        mAdapter.addCategoryItem(songSheetList);
        //加载完成后记得调用此方法，不然下一次加载无法调用
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void setHeader(AllSongSheetBean.ContentBean songSheet) {
        //使用Glide第三方框架加载图片
        Glide.with(mContext)
                .load(songSheet.getPic_300())
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivSongSheetHeader);

        tvSongSheetHeaderTitle.setText(songSheet.getTitle());
    }

    @Override
    public void setCategoryTitle(String tagName) {
        tvSongSheetCategory.setText(tagName);
    }

    public void showMsg(String msg) {
        toast(msg);
    }

    public void showLoading() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llSongSheetLoading.getLayoutParams();
        params.setMargins(0, DisplayUtils.dp2px(mContext, 90), 0, 0);
        llSongSheetLoading.setLayoutParams(params);
        llSongSheetLoading.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        ivSongSheetLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivSongSheetLoading.getDrawable();
        animationDrawable.start();
    }

    public void closeLoading() {
        llSongSheetLoading.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        ivSongSheetLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivSongSheetLoading.getDrawable();
        animationDrawable.stop();
    }

}
