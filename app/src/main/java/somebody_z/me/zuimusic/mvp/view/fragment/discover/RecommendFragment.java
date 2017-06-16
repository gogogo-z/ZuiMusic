package somebody_z.me.zuimusic.mvp.view.fragment.discover;

import android.graphics.drawable.AnimationDrawable;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.RecommendContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.model.bean.HotAlbumBean;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.mvp.presenter.RecommendPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.CommImagePageAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.handler.ScrollHandler;
import somebody_z.me.zuimusic.widget.BottomLine;
import somebody_z.me.zuimusic.widget.IndexView;
import somebody_z.me.zuimusic.widget.ThreeMenuPanel;
import somebody_z.me.zuimusic.widget.anchorradio.AnchorRadioPanel;
import somebody_z.me.zuimusic.widget.hotalbum.HotAlbumPanel;
import somebody_z.me.zuimusic.widget.newmusic.NewMusicPanel;
import somebody_z.me.zuimusic.widget.recommendsongsheet.RecommendSongSheetPanel;

/**
 * 个性推荐
 * Created by Huanxing Zeng on 2016/12/29.
 * email : zenghuanxing123@163.com
 */
public class RecommendFragment extends BaseFragment<RecommendPresenter> implements RecommendContract.RecommendView {

    public RecommendFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final RecommendFragment INSTANCE = new RecommendFragment();
    }

    public static RecommendFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Bind(R.id.ll_recommend_root)
    LinearLayout llroot;
    @Bind(R.id.iv_recommend_loading)
    ImageView ivRecommendLoading;
    @Bind(R.id.ll_recommend_loading)
    LinearLayout llRecommendLoading;

    @Override
    protected RecommendPresenter loadPresenter() {
        return new RecommendPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mPresenter.initViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    //添加轮播图
    @Override
    public void addLoopPic(final List<String> urls, final List<String> webUrls) {
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = DisplayUtils.dp2px(getActivity(), 135);
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(lparams);

        final ViewPager viewPager = new ViewPager(getActivity());
        //广告栏底部小圆点
        final IndexView indexView = new IndexView(getActivity());

        indexView.setCount(urls.size());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DisplayUtils.dp2px(getActivity(), 180), DisplayUtils.dp2px(getActivity(), 30));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0, 0, 0, DisplayUtils.dp2px(getActivity(), 10));
        indexView.setLayoutParams(params);

        //设置ViewPager的宽高
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width, height);
        viewPager.setLayoutParams(params1);

        List<ImageView> imageViews = new ArrayList<>();

        final ScrollHandler handler = new ScrollHandler(viewPager);

        for (int i = 0; i < urls.size(); i++) {
            //创建并添加imageView
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置ImageView的宽高
            ViewPager.LayoutParams iParams = new ViewPager.LayoutParams();
            iParams.width = width;
            iParams.height = height;
            imageView.setLayoutParams(iParams);

            imageViews.add(imageView);
        }

        //设置viewpager的滑动监听事件
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indexView.setCurrIndex(position % urls.size());
                //通过handler机制进行viewpager的轮播
                handler.sendMessage(Message.obtain(handler, ScrollHandler.MSG_PAGE_CHANGED, position, 0));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    //暂停轮播
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(ScrollHandler.MSG_KEEP_SILENT);
                        break;
                    //恢复轮播
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(ScrollHandler.MSG_UPDATE_IMAGE, ScrollHandler.MSG_DELAY);
                        break;
                }
            }
        });

        CommImagePageAdapter pagerAdapter = new CommImagePageAdapter(getActivity(), imageViews, urls, webUrls);
        viewPager.setAdapter(pagerAdapter);

        //设当前项为中间项即可向左滑动
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % urls.size());
        relativeLayout.addView(viewPager);
        relativeLayout.addView(indexView);
        llroot.addView(relativeLayout);
        //发送轮播消息，参数为当前位置和轮播间
        handler.sendEmptyMessageDelayed(ScrollHandler.MSG_UPDATE_IMAGE, ScrollHandler.MSG_DELAY);
    }

    //添加轮播图下方三个按钮
    @Override
    public void addMenu() {
        ThreeMenuPanel threeMenuPanel = new ThreeMenuPanel(mContext);
        llroot.addView(threeMenuPanel);
        mPresenter.setMenuEvent(threeMenuPanel);
        addLine();
    }

    @Override
    public void addRecommendSongSheet(List<RecommendSongSheetBean.SongSheetListBean> songSheetListBeen) {
        RecommendSongSheetPanel recommendSongSheetPanel = new RecommendSongSheetPanel(mContext, songSheetListBeen);
        llroot.addView(recommendSongSheetPanel);
        mPresenter.setRecommendSongSheetListener(recommendSongSheetPanel);
        addLine();
    }

    @Override
    public void addHotAlbum(List<HotAlbumBean.ListBean> list) {
        HotAlbumPanel hotAlbumPanel = new HotAlbumPanel(mContext, list);
        llroot.addView(hotAlbumPanel);
        mPresenter.setHotAlbumListener(hotAlbumPanel);
        addLine();
    }

    @Override
    public void addNewMusic(List<NewMusicBean.SongListBean> songListBeen) {
        NewMusicPanel newMusicPanel = new NewMusicPanel(mContext, songListBeen);
        llroot.addView(newMusicPanel);
        mPresenter.setNewMusicListener(newMusicPanel);
        addBottomLine();
    }

    @Override
    public void addAnchorRadio(List<AnchorRadioBean.RadioList> radioLists) {
        AnchorRadioPanel anchorRadioPanel = new AnchorRadioPanel(mContext, radioLists, "主播电台", R.drawable.icon_anchor_radiio);
        llroot.addView(anchorRadioPanel);
        mPresenter.setAnchorRadioListener(anchorRadioPanel);
    }

    @Override
    public void addBottomLine() {
        BottomLine bottomLine = new BottomLine(mContext, "我是有底线的");
        llroot.addView(bottomLine);
    }

    public void addLine() {
        TextView tvLine = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(mContext, 0.5f));
        tvLine.setLayoutParams(params);
        tvLine.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        llroot.addView(tvLine);
    }

    public void showMsg(String msg) {
        toast(msg);
    }

    public void showLoading() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llRecommendLoading.getLayoutParams();
        params.setMargins(0, DisplayUtils.dp2px(mContext, 135), 0, 0);
        llRecommendLoading.setLayoutParams(params);
        llRecommendLoading.setVisibility(View.VISIBLE);
        ivRecommendLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivRecommendLoading.getDrawable();
        animationDrawable.start();
    }

    public void closeLoading() {
        llRecommendLoading.setVisibility(View.GONE);
        ivRecommendLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivRecommendLoading.getDrawable();
        animationDrawable.stop();
    }

}
