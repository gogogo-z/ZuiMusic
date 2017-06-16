package somebody_z.me.zuimusic.mvp.view.fragment.friend;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.FriendsContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.NewsBean;
import somebody_z.me.zuimusic.mvp.presenter.FriendsPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.NewsAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.widget.crefresh.ZRecyclerView;

/**
 * 发现页面容器类
 * 内容为知乎日报内容
 * Created by Huanxing Zeng on 2016/12/29.
 * email : zenghuanxing123@163.com
 */
public class FriendsFragment extends BaseFragment<FriendsPresenter> implements FriendsContract.FriendsView {

    @Bind(R.id.iv_friend_loading)
    ImageView ivFriendLoading;
    @Bind(R.id.ll_friend_loading)
    LinearLayout llFriendLoading;
    @Bind(R.id.zr_friend)
    ZRecyclerView zrFriend;
    @Bind(R.id.sr_friend)
    SwipeRefreshLayout srFriend;

    private NewsAdapter mAdapter;

    public FriendsFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final FriendsFragment INSTANCE = new FriendsFragment();
    }

    public static FriendsFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected FriendsPresenter loadPresenter() {
        return new FriendsPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        zrFriend.setLoadingListener(new ZRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreNews(mPresenter.getDate());
            }
        });

        mAdapter.setOnItemClickListener(new NewsAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, NewsBean.StoriesBean news) {
                //点击查看详情
                mPresenter.jumpToDetail(news);
            }
        });

        //设置刷新事件监听器
        srFriend.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.init();
                mPresenter.refreshNews(mPresenter.getDate());
            }
        });
    }

    @Override
    protected void initView() {

        showLoading();

        mAdapter = new NewsAdapter(mContext);

        zrFriend.setLayoutManager(new LinearLayoutManager(mContext));

        zrFriend.setPullRefreshEnabled(false);

        zrFriend.setAdapter(mAdapter);

        //设置swipeRefreshLayout的旋转动画颜色（最多4种）
        srFriend.setColorSchemeResources(R.color.colorRed);

        mPresenter.init();

        mPresenter.loadMoreNews(mPresenter.getDate());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friend;
    }

    @Override
    public void setAdapter(List<NewsBean.StoriesBean> newsList) {
        mAdapter.addItems(newsList);
        //加载完成后记得调用此方法，不然下一次加载无法调用
        zrFriend.loadMoreComplete();
    }

    @Override
    public void refresh(List<NewsBean.StoriesBean> newsList) {
        mAdapter.refreshItem(newsList);
        //加载完成后记得调用此方法，不然下一次加载无法调用
        srFriend.setRefreshing(false);
    }

    public void showLoading() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llFriendLoading.getLayoutParams();
        params.setMargins(0, DisplayUtils.dp2px(mContext, 90), 0, 0);
        llFriendLoading.setLayoutParams(params);
        llFriendLoading.setVisibility(View.VISIBLE);
        zrFriend.setVisibility(View.GONE);
        ivFriendLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivFriendLoading.getDrawable();
        animationDrawable.start();
    }

    public void closeLoading() {
        llFriendLoading.setVisibility(View.GONE);
        zrFriend.setVisibility(View.VISIBLE);
        ivFriendLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivFriendLoading.getDrawable();
        animationDrawable.stop();
    }
}
