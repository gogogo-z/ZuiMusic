package somebody_z.me.zuimusic.mvp.view.fragment.discover.second;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.SongSheetDetailContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.mvp.presenter.SongSheetDetailPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.CollectToSongSheetAdapter;
import somebody_z.me.zuimusic.mvp.view.adapter.DetailAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.utils.WindowAnimUtil;
import somebody_z.me.zuimusic.utils.transformation.BlurTransformation;
import somebody_z.me.zuimusic.utils.transformation.CircleTransformation;
import somebody_z.me.zuimusic.widget.ListViewForScrollView;
import somebody_z.me.zuimusic.widget.crefresh.ZRecyclerView;
import somebody_z.me.zuimusic.widget.MyScrollView;

/**
 * toolbar 标题文字过长设置横向滚动
 * 两段相同的文字中间间隔距离，设置位移，停止时间
 * <p/>
 * show 和 hide 两个方法可以合成一个，传进来一个参数View.GONE/View.Visible即可，
 * 不需要分成两个再去设置状态。
 * <p/>
 * 使dialog宽度占满的方法：
 * 1、使用自定义的style不继承dialog的任何样式
 * 2、window.getDecorView().setPadding(0, 0, 0, 0);
 * <p/>
 * Created by Huanxing Zeng on 2017/2/3.
 * email : zenghuanxing123@163.com
 */
public class SongSheetDetailFragment extends BaseFragment<SongSheetDetailPresenter> implements SongSheetDetailContract.DetailView,
        AppBarLayout.OnOffsetChangedListener {

    @Bind(R.id.iv_detail_bg)
    ImageView ivDetailBg;
    @Bind(R.id.ll_detail_back)
    LinearLayout llDetailBack;
    @Bind(R.id.ll_detail_more)
    LinearLayout llDetailMore;
    @Bind(R.id.ll_detail_search)
    LinearLayout llDetailSearch;
    @Bind(R.id.tv_detail_songsheet)
    TextView tvDetailSongsheet;
    @Bind(R.id.tv_detail_songsheet_desc)
    TextView tvDetailSongsheetDesc;
    @Bind(R.id.ll_detail_title)
    LinearLayout llDetailTitle;
    @Bind(R.id.tl_detail)
    Toolbar tlDetail;
    @Bind(R.id.iv_detail_songsheet)
    ImageView ivDetailSongsheet;
    @Bind(R.id.tv_detail_listen_num)
    TextView tvDetailListenNum;
    @Bind(R.id.ll_detail_listen_num)
    LinearLayout llDetailListenNum;
    @Bind(R.id.tv_detail_songsheet_title)
    TextView tvDetailSongsheetTitle;
    @Bind(R.id.tv_detail_songsheet_tag)
    TextView tvDetailSongsheetTag;
    @Bind(R.id.tv_detail_collect_num)
    TextView tvDetailCollectNum;
    @Bind(R.id.ll_detail_collect)
    LinearLayout llDetailCollect;
    @Bind(R.id.tv_detail_comment_num)
    TextView tvDetailCommentNum;
    @Bind(R.id.ll_detail_comment)
    LinearLayout llDetailComment;
    @Bind(R.id.tv_detail_share_num)
    TextView tvDetailShareNum;
    @Bind(R.id.ll_detail_share)
    LinearLayout llDetailShare;
    @Bind(R.id.tv_detail_download_num)
    TextView tvDetailDownloadNum;
    @Bind(R.id.ll_detail_download)
    LinearLayout llDetailDownload;
    @Bind(R.id.ctl_detail)
    CollapsingToolbarLayout ctlDetail;
    @Bind(R.id.abl_detail)
    AppBarLayout ablDetail;
    @Bind(R.id.zr_detail_content)
    ZRecyclerView zrDetailContent;
    @Bind(R.id.rl_detail_songsheet)
    RelativeLayout rlDetailSongsheet;
    @Bind(R.id.ll_detail_bottom)
    LinearLayout llDetailBottom;
    @Bind(R.id.ll_detail_info)
    LinearLayout llDetailInfo;
    @Bind(R.id.ll_detail_operation)
    LinearLayout llDetailOperation;
    @Bind(R.id.iv_detail_loading)
    ImageView ivDetailLoading;
    @Bind(R.id.ll_detail_loading)
    LinearLayout llDetailLoading;
    @Bind(R.id.iv_detail_collect)
    ImageView ivDetailCollect;

    private DetailAdapter mAdapter;

    private WindowAnimUtil windowAnimUtil;

    private LinearLayout llSortMode, llEmptyFile, llTip, llDismiss;

    private TextView tvCount, tvCollectNum;

    private Button btnCancel, btnCommit;
    private CheckBox cbPrivate;

    public SongSheetDetailFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final SongSheetDetailFragment INSTANCE = new SongSheetDetailFragment();
    }

    public static SongSheetDetailFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected SongSheetDetailPresenter loadPresenter() {
        return new SongSheetDetailPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ablDetail.addOnOffsetChangedListener(this);

        llDetailMore.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showMsg("更多");
                return true;
            }
        });

        llDetailSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showMsg("搜索歌单内歌曲");
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        mPresenter.isImmerse();
        mPresenter.init();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ablDetail.getLayoutParams();
        params.height = ScreenUtil.getScreenHeight(mContext) * 15 / 36;
        ablDetail.setLayoutParams(params);

        windowAnimUtil = new WindowAnimUtil();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @OnClick({R.id.ll_detail_back, R.id.ll_detail_more, R.id.ll_detail_search, R.id.ll_detail_collect, R.id.ll_detail_comment,
            R.id.ll_detail_share, R.id.ll_detail_download, R.id.rl_detail_songsheet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_detail_back:
                mPresenter.onBackPressed();
                break;
            case R.id.ll_detail_more:
                mPresenter.showMoreWindow();
                break;
            case R.id.ll_detail_search:
                mPresenter.searchSong();
                break;
            case R.id.ll_detail_collect:
                mPresenter.collectSongSheet();
                break;
            case R.id.ll_detail_comment:
                break;
            case R.id.ll_detail_share:
                mPresenter.showShare();
                break;
            case R.id.ll_detail_download:
                mPresenter.download();
                break;
            case R.id.rl_detail_songsheet:
                mPresenter.showCoverDialog();
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        mPresenter.onBackPressed();
        return super.onBackPressed();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        mPresenter.setToolBarStatus(offset, maxScroll);
    }

    @Override
    public void setToolBarMarginTop() {
        CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) tlDetail.getLayoutParams();
        params.setMargins(0, ScreenUtil.getStatusBarHeight(mContext), 0, 0);
    }

    @Override
    public void setToolBarBgAlpha(int alpha) {
        tlDetail.getBackground().mutate().setAlpha(alpha);
    }

    @Override
    public void setToolBarTitle(String title) {
        tvDetailSongsheet.setText(title);
    }

    @Override
    public void setToolBarSubTitle(String subTitle) {
        tvDetailSongsheetDesc.setText(subTitle);
    }

    @Override
    public void setBgAndSongSheetCover(String url) {
        //加载歌单封面和背景
        //设置滤镜
        ivDetailBg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        //使用Glide第三方框架加载图片
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivDetailSongsheet);

        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .bitmapTransform(new BlurTransformation(mContext))
                .into(ivDetailBg);
    }

    @Override
    public void setListenNum(String listenNum) {
        tvDetailListenNum.setText(listenNum);
    }

    @Override
    public void setSongSheetTitle(String title) {
        tvDetailSongsheetTitle.setText(title);
    }

    @Override
    public void setSongSheetTag(String tag) {
        tvDetailSongsheetTag.setText(tag);
    }

    @Override
    public void setCollectNum(String collectNum) {
        tvDetailCollectNum.setText(collectNum);
    }

    @Override
    public void setCommentNum(String commentNum) {
        tvDetailCommentNum.setText(commentNum);
    }

    @Override
    public void setShareNum(String shareNum) {
        tvDetailShareNum.setText(shareNum);
    }

    @Override
    public void setInfoAlpha(float alpha) {
        llDetailInfo.setAlpha(alpha);
    }

    @Override
    public void setOperationAlpha(float alpha) {
        llDetailOperation.setAlpha(alpha);
    }

    @Override
    public void setCollapsingToolbarLayoutScroll() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ctlDetail.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        ctlDetail.setLayoutParams(params);
    }

    @Override
    public void setCollapsingToolbarLayoutNotScroll() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ctlDetail.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        ctlDetail.setLayoutParams(params);
    }

    @Override
    public void showLoading() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llDetailLoading.getLayoutParams();
        params.setMargins(0, DisplayUtils.dp2px(mContext, 60), 0, 0);
        llDetailLoading.setLayoutParams(params);
        llDetailLoading.setVisibility(View.VISIBLE);
        zrDetailContent.setVisibility(View.GONE);
        ivDetailLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivDetailLoading.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void closeLoading() {
        llDetailLoading.setVisibility(View.GONE);
        zrDetailContent.setVisibility(View.VISIBLE);
        ivDetailLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivDetailLoading.getDrawable();
        animationDrawable.stop();
    }

    @Override
    public void setSongSheetAdapter(List<ContentBean> songSheetDetailList) {
        mAdapter = new DetailAdapter(mContext, songSheetDetailList);
    }

    @Override
    public void setAdapterListener() {
        mAdapter.setOnItemClickListener(new DetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ContentBean songSheetDetail) {
                //播放
                mPresenter.jumpToPlay(songSheetDetail);
            }

            @Override
            public void onItemMoreClick(ContentBean songSheetDetail) {
                mPresenter.showSongInfoDialog(songSheetDetail);
            }
        });
    }

    @Override
    public void initRecyclerView(int count, String collectNum) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        View header = View.inflate(mContext, R.layout.widget_detail_header, null);

        tvCount = (TextView) header.findViewById(R.id.tv_detail_header_count);

        //点击跳转到选择页面
        header.findViewById(R.id.ll_detail_header_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.jumpToSongSelect();
            }
        });

        //点击播放全部
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.jumpToPlay(null);
            }
        });

        tvCount.setText("(" + count + "首)");

        zrDetailContent.addHeaderView(header);

        zrDetailContent.setLayoutManager(layoutManager);

        zrDetailContent.setAdapter(mAdapter);

        zrDetailContent.setPullRefreshEnabled(false);

        zrDetailContent.setNoMore(true);

        View footView = View.inflate(mContext, R.layout.widget_detail_foot, null);

        ImageView ivUser1 = (ImageView) footView.findViewById(R.id.iv_detail_foot_user_1);
        ImageView ivUser2 = (ImageView) footView.findViewById(R.id.iv_detail_foot_user_2);
        ImageView ivUser3 = (ImageView) footView.findViewById(R.id.iv_detail_foot_user_3);
        ImageView ivUser4 = (ImageView) footView.findViewById(R.id.iv_detail_foot_user_4);
        ImageView ivUser5 = (ImageView) footView.findViewById(R.id.iv_detail_foot_user_5);

        tvCollectNum = (TextView) footView.findViewById(R.id.tv_detail_foot_collect);

        tvCollectNum.setText(collectNum + "人收藏");

        //使用Glide第三方框架加载图片
        Glide.with(mContext)
                .load(R.drawable.icon_1)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .bitmapTransform(new CircleTransformation(mContext))
                .into(ivUser1);

        Glide.with(mContext)
                .load(R.drawable.icon_2)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .bitmapTransform(new CircleTransformation(mContext))
                .into(ivUser2);

        Glide.with(mContext)
                .load(R.drawable.icon_3)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .bitmapTransform(new CircleTransformation(mContext))
                .into(ivUser3);

        Glide.with(mContext)
                .load(R.drawable.icon_4)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .bitmapTransform(new CircleTransformation(mContext))
                .into(ivUser4);

        Glide.with(mContext)
                .load(R.drawable.icon_5)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .bitmapTransform(new CircleTransformation(mContext))
                .into(ivUser5);

        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转所有收藏用户的界面
                mPresenter.jumpToCollectorInfo();
            }
        });

        //添加底部
        zrDetailContent.setFootView(footView);
    }

    @Override
    public void showMsg(String msg) {
        toast(msg);
    }

    /**
     * 使用.9图片设置圆角和阴影
     */
    @Override
    public void initMoreWindow(int height) {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_detail_more, null);

        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        llSortMode = (LinearLayout) view.findViewById(R.id.ll_detail_more_sort_mode);
        llEmptyFile = (LinearLayout) view.findViewById(R.id.ll_detail_more_empty_file);
        llTip = (LinearLayout) view.findViewById(R.id.ll_detail_more_tip);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llSortMode.getLayoutParams();
        params.width = ScreenUtil.getScreenWidth(mContext) * 7 / 13;
        params.height = ScreenUtil.getScreenHeight(mContext) * 5 / 69;
        llSortMode.setLayoutParams(params);
        llEmptyFile.setLayoutParams(params);
        llTip.setLayoutParams(params);

        llSortMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });

        llEmptyFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });

        llTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        window.setTouchable(true);
        // 实例化一个ColorDrawable颜色为全透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        window.setBackgroundDrawable(dw);

        // 在右上方显示
        window.showAtLocation(ablDetail, Gravity.TOP | Gravity.RIGHT, 0, height);

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    @Override
    public void showSortMode() {
        llSortMode.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSortMode() {
        llSortMode.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyFile() {
        llEmptyFile.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyFile() {
        llEmptyFile.setVisibility(View.GONE);
    }

    @Override
    public void setCollectSuccess() {
        ivDetailCollect.setImageResource(R.drawable.selector_detail_collect_success);
    }

    @Override
    public void setCollectCancel() {
        ivDetailCollect.setImageResource(R.drawable.selector_detail_collect);
    }

    @Override
    public void setCollectCount(String count) {
        tvDetailCollectNum.setText(count);
        tvCollectNum.setText(count + "人收藏");
    }

    @Override
    public void setBottomOperationClickable(boolean clickable) {
        llDetailCollect.setClickable(clickable);
        llDetailComment.setClickable(clickable);
        llDetailShare.setClickable(clickable);
        llDetailDownload.setClickable(clickable);
    }

    @Override
    public void setSearchClick(boolean clickable) {
        llDetailSearch.setClickable(clickable);
    }

    @Override
    public void showConfirmCancelCollect() {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_confirm_delete_song_sheet, null);

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setView(view);

        //创建对话框
        final AlertDialog dialog = builder.create();

        dialog.show();

        //设置宽高，需在show（）之后设置，否则没有效果。
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtil.getScreenWidth(mContext) * 8 / 9;
        dialog.getWindow().setAttributes(params);

        Button mbtnCancel, btnDelete;
        TextView tvContent;

        mbtnCancel = (Button) view.findViewById(R.id.btn_delete_song_sheet_cancel);
        btnDelete = (Button) view.findViewById(R.id.btn_delete_song_sheet_confirm);
        tvContent = (TextView) view.findViewById(R.id.tv_dialog_content);

        btnDelete.setText(R.string.cancel_collect);
        tvContent.setText(R.string.confirm_cancel_collect);

        //PS:初始化时设置button不可点击应设置android:enable=false,设置Android:clickable=false无效
        //因为button默认clickable=true。动态设置也是这样。

        mbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交
                dialog.cancel();
                //删除歌单
                mPresenter.delectCollectSongSheet();
            }
        });
    }

    @Override
    public void initSongInfoDialog(final ContentBean songInfo) {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_detail_song_info, null);

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Dialog_Fullscreen);

        builder.setView(view);

        //创建对话框
        final AlertDialog dialog = builder.create();

        MyScrollView scrollView = (MyScrollView) view.findViewById(R.id.sl_detail_song_info_root);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_song_info_title);
        TextView tvComment = (TextView) view.findViewById(R.id.tv_detail_info_comment);
        TextView tvArtist = (TextView) view.findViewById(R.id.tv_detail_info_artist);
        TextView tvAlbum = (TextView) view.findViewById(R.id.tv_detail_info_album);
        LinearLayout llNextPlay = (LinearLayout) view.findViewById(R.id.ll_detail_info_next_play);
        LinearLayout llCollect = (LinearLayout) view.findViewById(R.id.ll_detail_info_collect);
        LinearLayout llDownload = (LinearLayout) view.findViewById(R.id.ll_detail_info_download);
        LinearLayout llComment = (LinearLayout) view.findViewById(R.id.ll_detail_info_comment);
        LinearLayout llShare = (LinearLayout) view.findViewById(R.id.ll_detail_info_share);
        LinearLayout llArtist = (LinearLayout) view.findViewById(R.id.ll_detail_info_artist);
        LinearLayout llAlbum = (LinearLayout) view.findViewById(R.id.ll_detail_info_album);
        LinearLayout llMV = (LinearLayout) view.findViewById(R.id.ll_detail_info_mv);
        LinearLayout llCL = (LinearLayout) view.findViewById(R.id.ll_detail_info_cailing);

        tvTitle.setText("歌曲 : " + songInfo.getTitle());
        tvComment.setText("评论(" + songInfo.getSong_id().substring(0, 3) + ")");
        tvArtist.setText("歌手 : " + songInfo.getAuthor());
        tvAlbum.setText("专辑 : " + songInfo.getAlbum_title());

        //下一首播放
        llNextPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        llCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mPresenter.showCollectToSongSheetDialog(songInfo);
            }
        });
        llDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.download(songInfo);

            }
        });
        llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showShare(songInfo);
            }
        });
        llArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        llAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        llMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        llCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.show();

        scrollView.setScrollChangedListener(new MyScrollView.IScrollChangedListener() {
            @Override
            public void scrollToBottom(int speed) {
                //dialog.getWindow().getDecorView().scrollBy(0, -speed);
                //dialog.dismiss();
            }

        });

        //设置宽高，需在show（）之后设置，否则没有效果。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ScreenUtil.getScreenWidth(mContext);
        layoutParams.height = ScreenUtil.getScreenHeight(mContext) * 31 / 50;
        window.setAttributes(layoutParams);
    }

    @Override
    public void initCollectToSongSheetDialog(List<MySongSheet> mySongSheetList, final ContentBean songInfo) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_collect_to_song_sheet, null);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_collect_to_song_sheet_new_song_sheet);

        ListViewForScrollView listViewForScrollView = (ListViewForScrollView) view.findViewById(R.id.lv_collect_to_song_sheet);

        CollectToSongSheetAdapter collectToSongSheetAdapter = new CollectToSongSheetAdapter(mContext, mySongSheetList, null);
        listViewForScrollView.setAdapter(collectToSongSheetAdapter);

        //获取listview item高度
        View item = collectToSongSheetAdapter.getView(0, null, listViewForScrollView);
        item.measure(0, 0);
        int itemHeight = item.getMeasuredHeight();

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Dialog_FullWidth);

        builder.setView(view);

        //创建对话框
        final AlertDialog dialog = builder.create();

        collectToSongSheetAdapter.setMySongSheetListener(new CollectToSongSheetAdapter.MySongSheetListener() {
            @Override
            public void goToDetail(MySongSheet mySongSheet) {
                // 收藏到歌单
                dialog.dismiss();
                mPresenter.collectToMySongSheet(mySongSheet, songInfo);
            }

        });

        //创建新歌单
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mPresenter.showCreateNewSongSheetDialog(songInfo);
            }
        });

        dialog.show();
        //设置宽高，需在show（）之后设置，否则没有效果,设置最大高度
        // 动态设置dialog的高度，设置最大高度为ScreenUtil.getScreenHeight(mContext) * 2 / 3；
        mPresenter.setDialogMaxHeight(itemHeight * (collectToSongSheetAdapter.getCount() + 2), dialog);
    }

    /**
     * 创建新歌单
     *
     * @param songInfo
     */
    @Override
    public void showCreateSongSheet(final ContentBean songInfo) {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_create_song_sheet, null);

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setView(view);

        //创建对话框
        final AlertDialog dialog = builder.create();

        dialog.show();

        //设置宽高，需在show（）之后设置，否则没有效果。
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtil.getScreenWidth(mContext) * 8 / 9;
        dialog.getWindow().setAttributes(params);

        final EditText etInput;
        final TextView tvCount, tvPrivate;

        etInput = (EditText) view.findViewById(R.id.et_popup_create_song_sheet_input);
        tvCount = (TextView) view.findViewById(R.id.tv_popup_create_song_sheet_count);
        tvPrivate = (TextView) view.findViewById(R.id.tv_popup_create_song_sheet_private);
        cbPrivate = (CheckBox) view.findViewById(R.id.cb_popup_create_song_sheet_private);
        btnCancel = (Button) view.findViewById(R.id.btn_create_song_sheet_cancel);
        btnCommit = (Button) view.findViewById(R.id.btn_create_song_sheet_commit);

        //PS:初始化时设置button不可点击应设置android:enable=false,设置Android:clickable=false无效
        //因为button默认clickable=true。动态设置也是这样。

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int count = charSequence.length();
                tvCount.setText(count + "/40");

                mPresenter.setCommitClickable(count);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cbPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mPresenter.setCheckedState(b);
            }
        });

        tvPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setPrivateSongSheetChecked();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交
                dialog.cancel();

                mPresenter.createSongSheet(etInput.getText().toString(), songInfo);
            }
        });
    }

    @Override
    public void setPrivateSongSheetChecked(boolean isChecked) {
        cbPrivate.setChecked(isChecked);
    }

    @Override
    public void setCommitCanClick() {
        btnCommit.setEnabled(true);
        btnCommit.setTextColor(getResources().getColor(R.color.colorBlue));
    }

    @Override
    public void setCommitNotClick() {
        btnCommit.setEnabled(false);
        btnCommit.setTextColor(getResources().getColor(R.color.alphaColorBlue));
    }

    @Override
    public void initCoverDialog(String url, String title, String desc, String tag) {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_song_sheet_cover, null);

        TextView tvTitle, tvDesc, tvTag;
        ImageView ivCoverBg, ivCover;

        tvTitle = (TextView) view.findViewById(R.id.tv_song_sheet_cover_title);
        tvDesc = (TextView) view.findViewById(R.id.tv_song_sheet_cover_desc);
        tvTag = (TextView) view.findViewById(R.id.tv_song_sheet_cover_tag);

        llDismiss = (LinearLayout) view.findViewById(R.id.ll_song_sheet_cover_dismiss);

        ivCover = (ImageView) view.findViewById(R.id.iv_song_sheet_cover);
        ivCoverBg = (ImageView) view.findViewById(R.id.iv_song_sheet_cover_bg);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivCover.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidth(mContext) * 17 / 28;
        layoutParams.height = ScreenUtil.getScreenHeight(mContext) * 11 / 30;

        mPresenter.setDismissMarginTop();

        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvTag.setText("标签: " + tag);

        //加载歌单封面和背景
        //设置滤镜
        ivCoverBg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        //使用Glide第三方框架加载图片
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivCover);

        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .bitmapTransform(new BlurTransformation(mContext))
                .into(ivCoverBg);

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Dialog_Full_All_Screen);

        builder.setView(view);

        //创建对话框
        final AlertDialog dialog = builder.create();

        dialog.show();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //设置宽高，需在show（）之后设置，否则没有效果。
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtil.getScreenWidth(mContext);
        params.height = ScreenUtil.getScreenHeight(mContext);
        dialog.getWindow().setAttributes(params);
    }

    @Override
    public void setDismissMarginTop() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llDismiss.getLayoutParams();
        params.setMargins(0, ScreenUtil.getStatusBarHeight(mContext), 0, 0);
    }

    public void playSong(ContentBean songSheetDetail) {
        mPresenter.jumpToPlay(songSheetDetail);
    }

}