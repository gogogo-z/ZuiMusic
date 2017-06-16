package somebody_z.me.zuimusic.mvp.view.fragment.discover;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.CommFragmentPagerAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * 发现页面容器类
 * Created by Huanxing Zeng on 2016/12/29.
 * email : zenghuanxing123@163.com
 */
public class DiscoverFragment extends BaseFragment {

    public DiscoverFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final DiscoverFragment INSTANCE = new DiscoverFragment();
    }

    public static DiscoverFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Bind(R.id.tl_discover)
    TabLayout tlDiscover;
    @Bind(R.id.tv_discover_tab_indicator)
    TextView tvDiscoverTabIndicator;
    @Bind(R.id.vp_discover_content)
    ViewPager vpDiscoverContent;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(RecommendFragment.getInstance());
        fragmentList.add(SongSheetFragment.getInstance());
        fragmentList.add(AnchorFragment.getInstance());
        fragmentList.add(RankFragment.getInstance());

        CommFragmentPagerAdapter fragmentPagerAdapter = new CommFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        vpDiscoverContent.setAdapter(fragmentPagerAdapter);
    }

    @Override
    protected void initListener() {
        //viewpager的滑动事件监听
        vpDiscoverContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //tablayout横条根据viewpager的位置进行变换
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvDiscoverTabIndicator.getLayoutParams();
                lp.leftMargin = (int) ((position % 4 + positionOffset) * ScreenUtil.getScreenWidth(mContext) / 4);
                tvDiscoverTabIndicator.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                //与tablayout同步
                tlDiscover.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //tablayout的监听事件
        tlDiscover.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //与viewpager同步
                vpDiscoverContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initView() {
        //添加tab项
        tlDiscover.addTab(tlDiscover.newTab().setText("个性推荐"));
        tlDiscover.addTab(tlDiscover.newTab().setText("歌单"));
        tlDiscover.addTab(tlDiscover.newTab().setText("主播电台"));
        tlDiscover.addTab(tlDiscover.newTab().setText("排行榜"));

        vpDiscoverContent.setOffscreenPageLimit(6);// 到少缓存两页

        //设置tablayout底部条
        tvDiscoverTabIndicator.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(mContext) / 4, DisplayUtils.dp2px(mContext, 2)));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    public void setCurrIndex(int index) {
        vpDiscoverContent.setCurrentItem(index);
    }

    public void hideTab() {
        tlDiscover.setVisibility(View.GONE);
        tvDiscoverTabIndicator.setVisibility(View.GONE);
        vpDiscoverContent.setVisibility(View.GONE);
    }

    public void showTab() {
        tlDiscover.setVisibility(View.VISIBLE);
        tvDiscoverTabIndicator.setVisibility(View.VISIBLE);
        vpDiscoverContent.setVisibility(View.VISIBLE);
    }
}
