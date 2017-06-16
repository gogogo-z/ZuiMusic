package somebody_z.me.zuimusic.mvp.view.fragment.music.second;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.RecentlyPlayContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.presenter.RecentlyPlayPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.CollectToSongSheetAdapter;
import somebody_z.me.zuimusic.mvp.view.adapter.RecentlyPlayAdapter;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.widget.ListViewForScrollView;
import somebody_z.me.zuimusic.widget.MyScrollView;
import somebody_z.me.zuimusic.widget.crefresh.ZRecyclerView;

/**
 * 最近播放
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class RecentlyPlayFragment extends BaseFragment<RecentlyPlayPresenter> implements RecentlyPlayContract.RecentlyPlayView {
    @Bind(R.id.ll_recently_play_back)
    LinearLayout llRecentlyPlayBack;
    @Bind(R.id.ll_recently_play_clear)
    LinearLayout llRecentlyPlayClear;
    @Bind(R.id.ll_recently_play_bar)
    LinearLayout llRecentlyPlayBar;
    @Bind(R.id.zr_recently_play)
    ZRecyclerView zrRecentlyPlay;

    private TextView tvCount;

    private Button btnCancel, btnCommit;
    private CheckBox cbPrivate;

    private RecentlyPlayAdapter mAdapter;

    public RecentlyPlayFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final RecentlyPlayFragment INSTANCE = new RecentlyPlayFragment();
    }

    public static RecentlyPlayFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected RecentlyPlayPresenter loadPresenter() {
        return new RecentlyPlayPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mPresenter.isImmerse();
        mPresenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recently_play;
    }

    @OnClick({R.id.ll_recently_play_back, R.id.ll_recently_play_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_recently_play_back:
                mPresenter.onBackPressed();
                break;
            case R.id.ll_recently_play_clear:
                //清空
                mPresenter.clearAll();
                break;
        }
    }

    @Override
    public void setBarMarginTop() {
        llRecentlyPlayBar.setPadding(0, ScreenUtil.getStatusBarHeight(mContext), 0, 0);
    }

    @Override
    public void setRecentlyPlayAdapter(List<ContentBean> songSheetDetailList) {
        mAdapter = new RecentlyPlayAdapter(mContext, songSheetDetailList);
    }

    @Override
    public void setAdapterListener() {
        mAdapter.setOnItemClickListener(new RecentlyPlayAdapter.OnRecyclerViewItemClickListener() {
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
    public void refreshAdapter(List<ContentBean> contentBeanList) {
        mAdapter.refreshList(contentBeanList);
    }

    @Override
    public void refreshCount(int count) {
        tvCount.setText("(" + count + "首)");
    }

    @Override
    public void initRecyclerView(int count) {
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

        zrRecentlyPlay.addHeaderView(header);

        zrRecentlyPlay.setLayoutManager(layoutManager);

        zrRecentlyPlay.setAdapter(mAdapter);

        zrRecentlyPlay.setPullRefreshEnabled(false);

        zrRecentlyPlay.setNoMore(true);

        View footView = View.inflate(mContext, R.layout.widget_local_song_foot, null);

        //添加底部
        zrRecentlyPlay.setFootView(footView);

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
    public void showMsg(String msg) {
        toast(msg);
    }

    @Override
    public void onDestroyView() {
        mPresenter.unRegisterReceiver();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        mPresenter.registerReceiver();
        super.onResume();
    }

    @Override
    public boolean onBackPressed() {
        mPresenter.onBackPressed();
        return super.onBackPressed();
    }
}
