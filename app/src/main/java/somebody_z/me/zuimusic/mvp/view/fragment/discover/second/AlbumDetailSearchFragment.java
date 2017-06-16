package somebody_z.me.zuimusic.mvp.view.fragment.discover.second;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.AlbumDetailSearchContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;
import somebody_z.me.zuimusic.mvp.presenter.AlbumDetailSearchPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.AlbumDetailAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.utils.transformation.BlurTransformation;
import somebody_z.me.zuimusic.widget.ZEditText;
import somebody_z.me.zuimusic.widget.crefresh.ZRecyclerView;

/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class AlbumDetailSearchFragment extends BaseFragment<AlbumDetailSearchPresenter> implements AlbumDetailSearchContract.SearchView {

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

    private AlbumDetailAdapter mAdapter;

    public AlbumDetailSearchFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final AlbumDetailSearchFragment INSTANCE = new AlbumDetailSearchFragment();
    }

    public static AlbumDetailSearchFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected AlbumDetailSearchPresenter loadPresenter() {
        return new AlbumDetailSearchPresenter();
    }

    @Override
    protected void initData() {
        zetSearch.getFocus();
        //弹出键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
    }

    @Override
    public void setBackground(String url) {
        //使用Glide第三方框架加载图片
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .bitmapTransform(new BlurTransformation(mContext))
                .into(ivSearchBg);
        //设置滤镜
        ivSearchBg.setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }

    @Override
    public void setSongSheetAdapter(List<AlbumDetailBean.SonglistBean> songSheetDetailList) {
        mAdapter = new AlbumDetailAdapter(mContext, songSheetDetailList);

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
        mAdapter.setOnItemClickListener(new AlbumDetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, AlbumDetailBean.SonglistBean songSheetDetail) {
                AlbumDetailFragment.getInstance().playSong(songSheetDetail);
            }

            @Override
            public void onItemMoreClick(AlbumDetailBean.SonglistBean songSheetDetail) {
                AlbumDetailFragment.getInstance().initSongInfoDialog(songSheetDetail);
            }
        });
    }

    public void showMsg(String msg) {
        toast(msg);
    }

}
