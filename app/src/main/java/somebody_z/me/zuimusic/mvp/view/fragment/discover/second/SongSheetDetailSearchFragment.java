package somebody_z.me.zuimusic.mvp.view.fragment.discover.second;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.SongSheetDetailSearchContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.mvp.presenter.SongSheetDetailSearchPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.DetailAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.utils.transformation.BlurTransformation;
import somebody_z.me.zuimusic.widget.ZEditText;
import somebody_z.me.zuimusic.widget.crefresh.ZRecyclerView;

/**
 * Created by Huanxing Zeng on 2017/2/10.
 * email : zenghuanxing123@163.com
 */
public class SongSheetDetailSearchFragment extends BaseFragment<SongSheetDetailSearchPresenter> implements SongSheetDetailSearchContract.SearchView {

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

    private DetailAdapter mAdapter;

    public SongSheetDetailSearchFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final SongSheetDetailSearchFragment INSTANCE = new SongSheetDetailSearchFragment();
    }

    public static SongSheetDetailSearchFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected SongSheetDetailSearchPresenter loadPresenter() {
        return new SongSheetDetailSearchPresenter();
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
        //使用Glide第三方框架加载图片,回调获取bitmap

        //取图片的主色调，Palette palette = Palette.generate(resource);
       /* Glide.with(mContext)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Palette palette = Palette.generate(resource);
                        ivSearchBg.setBackgroundColor(palette.getDarkMutedSwatch().getRgb());
                    }
                });*/
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
    public void setSongSheetAdapter(List<ContentBean> songSheetDetailList) {
        mAdapter = new DetailAdapter(mContext, songSheetDetailList);

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
        mAdapter.setOnItemClickListener(new DetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ContentBean songSheetDetail) {
                SongSheetDetailFragment.getInstance().playSong(songSheetDetail);
            }

            @Override
            public void onItemMoreClick(ContentBean songSheetDetail) {
                SongSheetDetailFragment.getInstance().initSongInfoDialog(songSheetDetail);
            }
        });
    }

    public void showMsg(String msg) {
        toast(msg);
    }

}
