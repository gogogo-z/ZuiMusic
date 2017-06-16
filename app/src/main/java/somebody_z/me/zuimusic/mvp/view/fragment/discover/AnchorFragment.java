package somebody_z.me.zuimusic.mvp.view.fragment.discover;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.AnchorContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.presenter.AnchorPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.AnchorHotAdapter;
import somebody_z.me.zuimusic.mvp.view.adapter.HorizontalPageAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.widget.BottomLine;
import somebody_z.me.zuimusic.widget.HorizontalPage.HorizontalPageLayoutManager;
import somebody_z.me.zuimusic.widget.HorizontalPage.PageScrollHelper;
import somebody_z.me.zuimusic.widget.IndexView;
import somebody_z.me.zuimusic.widget.anchordayradio.AnchorDayRadioPanel;
import somebody_z.me.zuimusic.widget.anchorradio.AnchorRadioPanel;
import somebody_z.me.zuimusic.widget.anchorradiorank.AnchorRadioRankPanel;
import somebody_z.me.zuimusic.widget.crefresh.ItemDecoration.SpacesItemDecoration;

/**
 * 主播电台
 * scrollerView 与 recyclerview 嵌套冲突，待解决。
 * 由于数据有限，故没有选择加载更多，直接全部显示出来
 * <p/>
 * 由于接口问题，无法播放
 * Created by Huanxing Zeng on 2016/12/29.
 * email : zenghuanxing123@163.com
 */
public class AnchorFragment extends BaseFragment<AnchorPresenter> implements AnchorContract.AnchorView, PageScrollHelper.onPageChangeListener {

    public AnchorFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final AnchorFragment INSTANCE = new AnchorFragment();
    }

    public static AnchorFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Bind(R.id.rv_anchor)
    RecyclerView rvAnchor;
    @Bind(R.id.iv_anchor_index)
    IndexView ivAnchorIndex;
    @Bind(R.id.ll_anchor_root)
    LinearLayout llAnchorRoot;
    @Bind(R.id.msv_anchor_root)
    ScrollView mScrollView;
    @Bind(R.id.iv_anchor_loading)
    ImageView ivAnchorLoading;
    @Bind(R.id.ll_anchor_loading)
    LinearLayout llAnchorLoading;

    private AnchorHotAdapter anchorHotAdapter;

    private RecyclerView mRecyclerView;

    private PageScrollHelper scrollHelper = new PageScrollHelper();

    private HorizontalPageAdapter mAdapter;

    @Override
    protected AnchorPresenter loadPresenter() {
        return new AnchorPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mPresenter.settHorizontalAdapterListener(mAdapter);
    }

    @Override
    protected void initView() {
        mPresenter.showLoading();

        mPresenter.setHorizontalAdapter();
        rvAnchor.setAdapter(mAdapter);

        scrollHelper.setUpRecycleView(rvAnchor);
        scrollHelper.setOnPageChangeListener(this);
        rvAnchor.setLayoutManager(new HorizontalPageLayoutManager(2, 4));
        scrollHelper.updateLayoutManger();

        ivAnchorIndex.setCount(3);

        mPresenter.getAnchorRadio();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor;
    }

    @Override
    public void onPageChange(int index) {
        ivAnchorIndex.setCurrIndex(index);
    }

    @Override
    public void setHorizontalAdapter(int[] imageList, String[] nameList) {
        mAdapter = new HorizontalPageAdapter(mContext, imageList, nameList);
    }

    @Override
    public void showMsg(String msg) {
        toast(msg);
    }

    @Override
    public void addAnchorRadioRank(List<AnchorRadioBean.RadioList> radioLists) {
        AnchorRadioRankPanel anchorRadioRankPanel = new AnchorRadioRankPanel(mContext, radioLists);
        llAnchorRoot.addView(anchorRadioRankPanel);
        mPresenter.setAnchorRadioRankListener(anchorRadioRankPanel);
    }

    @Override
    public void addExcellen(List<AnchorRadioBean.RadioList> radioLists) {
        AnchorRadioPanel anchorRadioPanel = new AnchorRadioPanel(mContext, radioLists, "精彩节目推荐", R.drawable.icon_left_songsheet);
        llAnchorRoot.addView(anchorRadioPanel);
        mPresenter.setExcellentListener(anchorRadioPanel);
    }

    @Override
    public void addDay(List<AnchorRadioBean.RadioList> radioLists) {
        AnchorDayRadioPanel anchorDayRadioPanel = new AnchorDayRadioPanel(mContext, radioLists);
        llAnchorRoot.addView(anchorDayRadioPanel);
        mPresenter.setDayListener(anchorDayRadioPanel);
        addLine();
    }

    @Override
    public void addHot(List<AnchorRadioBean.RadioList> radioLists) {

        View view = View.inflate(mContext, R.layout.item_anchor_hot_radio, null);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DisplayUtils.dp2px(mContext, 30));

        layoutParams.setMargins(0, DisplayUtils.dp2px(mContext, 10), 0, 0);

        view.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(DisplayUtils.dp2px(mContext, 5), 0, DisplayUtils.dp2px(mContext, 5), 0);

        mRecyclerView = new RecyclerView(mContext);

        mRecyclerView.setLayoutParams(params);

        anchorHotAdapter = new AnchorHotAdapter(mContext);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);

        //设置间距，item上下左右margin，由于中间间距为最边界的两倍，
        //所以需给recyclerview设置margin
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(DisplayUtils.dp2px(mContext, 5)));

        anchorHotAdapter.addItems(radioLists);

        mRecyclerView.setAdapter(anchorHotAdapter);

        mPresenter.setAnchorHotAdapterListener(anchorHotAdapter);

        llAnchorRoot.addView(view);
        llAnchorRoot.addView(mRecyclerView);
    }

    @Override
    public void addBottomLine() {
        BottomLine bottomLine = new BottomLine(mContext, "已经到底啦~");
        bottomLine.setPadding(0, 0, 0, DisplayUtils.dp2px(mContext, 75));
        llAnchorRoot.addView(bottomLine);
    }

    public void setLoadAdapter(List<AnchorRadioBean.RadioList> radioLists) {
        anchorHotAdapter.addItems(radioLists);
        //加载完成后记得调用此方法，不然下一次加载无法调用
        //mRecyclerView.loadMoreComplete();
    }

    public void addLine() {
        TextView tvLine = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(mContext, 0.5f));
        tvLine.setLayoutParams(params);
        tvLine.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        llAnchorRoot.addView(tvLine);
    }

    public void showLoading() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llAnchorLoading.getLayoutParams();
        params.setMargins(0, DisplayUtils.dp2px(mContext, 90), 0, 0);
        llAnchorLoading.setLayoutParams(params);
        llAnchorLoading.setVisibility(View.VISIBLE);
        llAnchorRoot.setVisibility(View.GONE);
        ivAnchorLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivAnchorLoading.getDrawable();
        animationDrawable.start();
    }

    public void closeLoading() {
        llAnchorLoading.setVisibility(View.GONE);
        llAnchorRoot.setVisibility(View.VISIBLE);
        ivAnchorLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivAnchorLoading.getDrawable();
        animationDrawable.stop();
    }

}
