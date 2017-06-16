package somebody_z.me.zuimusic.mvp.view.fragment.music.second;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.LocalMusicSearchContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.mvp.presenter.LocalMusicSearchPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.LocalMusicSearchAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.widget.ZEditText;
import somebody_z.me.zuimusic.widget.crefresh.ZRecyclerView;

/**
 * Created by Huanxing Zeng on 2017/2/10.
 * email : zenghuanxing123@163.com
 */
public class LocalMusicSearchFragment extends BaseFragment<LocalMusicSearchPresenter> implements LocalMusicSearchContract.LocalMusicSearchView {

    @Bind(R.id.iv_search_bg)
    ImageView ivSearchBg;
    @Bind(R.id.ll_search_back)
    LinearLayout llSearchBack;
    @Bind(R.id.rl_search_bar)
    RelativeLayout rlSearchBar;
    @Bind(R.id.zr_search)
    ZRecyclerView zrSearch;
    @Bind(R.id.zet_search)
    ZEditText zetSearch;

    private LocalMusicSearchAdapter mAdapter;

    public LocalMusicSearchFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final LocalMusicSearchFragment INSTANCE = new LocalMusicSearchFragment();
    }

    public static LocalMusicSearchFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected LocalMusicSearchPresenter loadPresenter() {
        return new LocalMusicSearchPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        zetSearch.setOnChangeTextListener(new ZEditText.OnChangeTextListener() {
            @Override
            public void setTextContent(String content) {
                mPresenter.search(content);
            }
        });
    }

    @Override
    protected void initView() {
        mPresenter.isImmerse();
        mPresenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @OnClick(R.id.ll_search_back)
    public void onClick() {
        mPresenter.onBackPressed();
    }

    @Override
    public void setBarMarginTop() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rlSearchBar.getLayoutParams();
        params.height = ScreenUtil.getStatusBarHeight(mContext) + DisplayUtils.dp2px(mContext, 58);
        rlSearchBar.setLayoutParams(params);
    }

    @Override
    public void setEditTextHint(String hint) {
        zetSearch.setHint(hint);
        zetSearch.setHintColor(R.color.colorAlphaWhite);
        zetSearch.setDefaultColor(R.color.colorAlphaWhite);
    }

    @Override
    public void setBackground() {
        ivSearchBg.setBackgroundColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }

    @Override
    public void setSongSheetAdapter(List<ContentBean> songSheetDetailList) {
        mAdapter = new LocalMusicSearchAdapter(mContext, songSheetDetailList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        zrSearch.setLayoutManager(layoutManager);

        zrSearch.setAdapter(mAdapter);

        zrSearch.setPullRefreshEnabled(false);

        zrSearch.setNoMore(true);

        View footView = View.inflate(mContext, R.layout.widget_local_song_foot, null);

        //添加底部
        zrSearch.setFootView(footView);
    }

    @Override
    public void setAdapterListener() {
        mAdapter.setOnItemClickListener(new LocalMusicSearchAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ContentBean songSheetDetail) {
                //播放
                LocalSongFragment.getInstance().playSong(songSheetDetail);
            }

            @Override
            public void onItemMoreClick(ContentBean songSheetDetail) {
                LocalSongFragment.getInstance().initSongInfoDialog(songSheetDetail);
            }
        });
    }

    public void showMsg(String msg) {
        toast(msg);
    }

}
