package somebody_z.me.zuimusic.mvp.view.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.SearchContract;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.presenter.SearchPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.CollectToSongSheetAdapter;
import somebody_z.me.zuimusic.mvp.view.adapter.DetailAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.widget.ListViewForScrollView;
import somebody_z.me.zuimusic.widget.MyScrollView;
import somebody_z.me.zuimusic.widget.ZEditText;
import somebody_z.me.zuimusic.widget.crefresh.ZRecyclerView;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/16.
 */
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.SearchView {

    @Bind(R.id.ll_serach_back)
    LinearLayout llSerachBack;
    @Bind(R.id.zt_search)
    ZEditText ztSearch;
    @Bind(R.id.zr_search)
    ZRecyclerView zrSearch;
    @Bind(R.id.ll_search_bar)
    LinearLayout llSearchBar;
    @Bind(R.id.iv_search_loading)
    ImageView ivSearchLoading;
    @Bind(R.id.ll_search_loading)
    LinearLayout llSearchLoading;

    private DetailAdapter mAdapter;

    private Button btnCancel, btnCommit;
    private CheckBox cbPrivate;

    @Override
    protected SearchPresenter loadPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ztSearch.setOnChangeTextListener(new ZEditText.OnChangeTextListener() {
            @Override
            public void setTextContent(String content) {
                mPresenter.search(content);
            }
        });
    }

    @Override
    protected void initView() {
        mPresenter.isImmerse();

        ztSearch.setHint("搜索音乐");

        mPresenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onPause() {
        //设置跳转无动画
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @OnClick(R.id.ll_serach_back)
    public void onClick() {
        finish();
    }

    @Override
    public void setBarMarginTop() {
        llSearchBar.setPadding(0, ScreenUtil.getStatusBarHeight(this), 0, 0);
    }

    @Override
    public void setSongAdapter(List<ContentBean> songList) {
        mAdapter = new DetailAdapter(this, songList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        zrSearch.setLayoutManager(layoutManager);

        zrSearch.setAdapter(mAdapter);

        zrSearch.setPullRefreshEnabled(false);

        zrSearch.setNoMore(true);

        View footView = View.inflate(this, R.layout.widget_local_song_foot, null);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(this, 6));
        footView.setLayoutParams(params);

        //添加底部
        zrSearch.setFootView(footView);
    }

    @Override
    public void setAdapterListener() {
        mAdapter.setOnItemClickListener(new DetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ContentBean songSheetDetail) {
                mPresenter.play(songSheetDetail);

                HideSoftInput();
            }

            @Override
            public void onItemMoreClick(ContentBean songSheetDetail) {
                mPresenter.showSongInfoDialog(songSheetDetail);
                HideSoftInput();
            }
        });
    }

    public void showLoading() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llSearchLoading.getLayoutParams();
        params.setMargins(0, DisplayUtils.dp2px(this, 135), 0, 0);
        llSearchLoading.setLayoutParams(params);
        llSearchLoading.setVisibility(View.VISIBLE);
        zrSearch.setVisibility(View.GONE);
        ivSearchLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivSearchLoading.getDrawable();
        animationDrawable.start();
    }

    public void closeLoading() {
        llSearchLoading.setVisibility(View.GONE);
        zrSearch.setVisibility(View.VISIBLE);
        ivSearchLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivSearchLoading.getDrawable();
        animationDrawable.stop();
    }

    @Override
    public void initSongInfoDialog(final ContentBean songInfo) {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_detail_song_info, null);

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog_Fullscreen);

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
        layoutParams.width = ScreenUtil.getScreenWidth(this);
        layoutParams.height = ScreenUtil.getScreenHeight(this) * 31 / 50;
        window.setAttributes(layoutParams);
    }

    @Override
    public void initCollectToSongSheetDialog(List<MySongSheet> mySongSheetList, final ContentBean songInfo) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_collect_to_song_sheet, null);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_collect_to_song_sheet_new_song_sheet);

        ListViewForScrollView listViewForScrollView = (ListViewForScrollView) view.findViewById(R.id.lv_collect_to_song_sheet);

        CollectToSongSheetAdapter collectToSongSheetAdapter = new CollectToSongSheetAdapter(this, mySongSheetList, null);
        listViewForScrollView.setAdapter(collectToSongSheetAdapter);

        //获取listview item高度
        View item = collectToSongSheetAdapter.getView(0, null, listViewForScrollView);
        item.measure(0, 0);
        int itemHeight = item.getMeasuredHeight();

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog_FullWidth);

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

    public void showMsg(String msg) {
        toast(msg);
    }

    /**
     * 创建新歌单
     *
     * @param songInfo
     */
    @Override
    public void showCreateSongSheet(final ContentBean songInfo) {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_create_song_sheet, null);

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);

        //创建对话框
        final AlertDialog dialog = builder.create();

        dialog.show();

        //设置宽高，需在show（）之后设置，否则没有效果。
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtil.getScreenWidth(this) * 8 / 9;
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

}
