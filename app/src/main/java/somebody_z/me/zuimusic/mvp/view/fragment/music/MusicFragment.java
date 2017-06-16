package somebody_z.me.zuimusic.mvp.view.fragment.music;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.db.CollectSongSheet;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.MusicContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.presenter.MusicPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.MusicCollectSongSheetAdapter;
import somebody_z.me.zuimusic.mvp.view.adapter.MusicMySongSheetAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.utils.WindowAnimUtil;
import somebody_z.me.zuimusic.widget.ListViewForScrollView;

/**
 * 音乐页面
 * <p/>
 * ps:popupwindow改为alertdialog
 * <p/>
 * Created by Huanxing Zeng on 2016/12/29.
 * email : zenghuanxing123@163.com
 */
public class MusicFragment extends BaseFragment<MusicPresenter> implements MusicContract.MusicView {

    public MusicFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final MusicFragment INSTANCE = new MusicFragment();
    }

    public static MusicFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Bind(R.id.tv_music_local_music_count)
    TextView tvMusicLocalMusicCount;
    @Bind(R.id.ll_music_local_music)
    LinearLayout llMusicLocalMusic;
    @Bind(R.id.tv_music_recent_play_count)
    TextView tvMusicRecentPlayCount;
    @Bind(R.id.ll_music_recent_play)
    LinearLayout llMusicRecentPlay;
    @Bind(R.id.tv_music_download_manager_count)
    TextView tvMusicDownloadManagerCount;
    @Bind(R.id.ll_music_download_manager)
    LinearLayout llMusicDownloadManager;
    @Bind(R.id.tv_music_my_singer_count)
    TextView tvMusicMySingerCount;
    @Bind(R.id.ll_music_my_singer)
    LinearLayout llMusicMySinger;
    @Bind(R.id.tv_music_my_mv_count)
    TextView tvMusicMyMvCount;
    @Bind(R.id.ll_music_my_mv)
    LinearLayout llMusicMyMv;
    @Bind(R.id.iv_music_create_song_sheet)
    ImageView ivMusicCreateSongSheet;
    @Bind(R.id.tv_music_create_song_sheet_count)
    TextView tvMusicCreateSongSheetCount;
    @Bind(R.id.ll_music_create_song_sheet_option)
    LinearLayout llMusicCreateSongSheetOption;
    @Bind(R.id.ll_music_create_song_sheet)
    LinearLayout llMusicCreateSongSheet;
    @Bind(R.id.lv_music_create_song_sheet)
    ListViewForScrollView lvMusicCreateSongSheet;
    @Bind(R.id.iv_music_collect_song_sheet)
    ImageView ivMusicCollectSongSheet;
    @Bind(R.id.tv_music_collect_song_sheet_count)
    TextView tvMusicCollectSongSheetCount;
    @Bind(R.id.ll_music_collect_song_sheet_option)
    LinearLayout llMusicCollectSongSheetOption;
    @Bind(R.id.ll_music_collect_song_sheet)
    LinearLayout llMusicCollectSongSheet;
    @Bind(R.id.lv_music_collect_song_sheet)
    public ListViewForScrollView lvMusicCollectSongSheet;
    @Bind(R.id.sr_music)
    SwipeRefreshLayout srMusic;

    private TextView tvSongSheetTitle;

    private LinearLayout llCreateNewSongSheet;

    private LinearLayout llSongSheetDelete;

    private LinearLayout llSongSheetEdit;

    private CheckBox cbPrivate;

    private Button btnCancel, btnCommit;

    private MusicMySongSheetAdapter musicMySongSheetAdapter;

    private MusicCollectSongSheetAdapter musicCollectSongSheetAdapter;

    private WindowAnimUtil windowAnimUtil;

    private float bgAlpha = 1f;
    private boolean bright = false;

    @Override
    protected MusicPresenter loadPresenter() {
        return new MusicPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        //设置刷新事件监听器
        srMusic.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });

    }

    @Override
    protected void initView() {
        //做一个判断，如果是第一次启动应用则create我喜欢的音乐，否则不调用此方法
        mPresenter.isFirstUse();
        mPresenter.init();
        mPresenter.showSongSheet();
        mPresenter.showCollectSongSheet();
        mPresenter.setCreateSongSheetVisible();
        mPresenter.setCollectSongSheetVisible();

        windowAnimUtil = new WindowAnimUtil();

        //设置swipeRefreshLayout的旋转动画颜色（最多4种）
        srMusic.setColorSchemeResources(R.color.colorRed);
    }


    @Override
    public void refresh() {
        mPresenter.showSongSheet();
        mPresenter.showCollectSongSheet();

        srMusic.setRefreshing(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    /**
     * 当fragment显示的时候做数据处理
     * 由于mPresent还没有初始化，如果调用mPresent会出现空指针异常
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            mPresenter.init();
            mPresenter.showSongSheet();
            mPresenter.showCollectSongSheet();
        }
    }

    @OnClick({R.id.ll_music_local_music, R.id.ll_music_recent_play, R.id.ll_music_download_manager, R.id.ll_music_my_singer,
            R.id.ll_music_my_mv, R.id.ll_music_create_song_sheet_option, R.id.ll_music_create_song_sheet,
            R.id.ll_music_collect_song_sheet_option, R.id.ll_music_collect_song_sheet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_music_local_music:
                mPresenter.jumpToLocalMusic();
                break;
            case R.id.ll_music_recent_play:
                mPresenter.jumpToRecentPlay();
                break;
            case R.id.ll_music_download_manager:
                mPresenter.jumpToDownloadManeger();
                break;
            case R.id.ll_music_my_singer:
                mPresenter.jumpToMyPlayer();
                break;
            case R.id.ll_music_my_mv:
                mPresenter.jumpToMyMV();
                break;
            case R.id.ll_music_create_song_sheet_option:
                mPresenter.showCreatSongSheetOption();
                break;
            case R.id.ll_music_create_song_sheet:
                mPresenter.setCreateSongSheetVisible();
                break;
            case R.id.ll_music_collect_song_sheet_option:
                mPresenter.showCollectSongSheetOption();
                break;
            case R.id.ll_music_collect_song_sheet:
                mPresenter.setCollectSongSheetVisible();
                break;
        }
    }

    @Override
    public void show(String msg) {
        toast(msg);
    }

    @Override
    public void setLocalMusicCount(String count) {
        tvMusicLocalMusicCount.setText(count);
    }

    @Override
    public void setRecentPlayCount(String count) {
        tvMusicRecentPlayCount.setText(count);
    }

    @Override
    public void setDownloadManagerCount(String count) {
        tvMusicDownloadManagerCount.setText(count);
    }

    @Override
    public void setMySingerCount(String count) {
        tvMusicMySingerCount.setText(count);
    }

    @Override
    public void setMyMVCount(String count) {
        tvMusicMyMvCount.setText(count);
    }

    @Override
    public void setCreateSongSheetCount(String count) {
        tvMusicCreateSongSheetCount.setText(count);
    }

    @Override
    public void setCollectSongSheetCount(String count) {
        tvMusicCollectSongSheetCount.setText(count);
    }

    @Override
    public void setCreateRotationCW() {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(ivMusicCreateSongSheet, "rotation", 0.0f, 90.0f);
        //设置插值器
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setDuration(200);
        rotation.start();
        lvMusicCreateSongSheet.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCreateRotationCCW() {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(ivMusicCreateSongSheet, "rotation", 90.0f, 0.0f);
        //设置插值器
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setDuration(200);
        rotation.start();
        lvMusicCreateSongSheet.setVisibility(View.GONE);
    }

    @Override
    public void setCollectRotationCW() {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(ivMusicCollectSongSheet, "rotation", 0.0f, 90.0f);
        //设置插值器
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setDuration(200);
        rotation.start();
        lvMusicCollectSongSheet.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCollectRotationCCW() {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(ivMusicCollectSongSheet, "rotation", 90.0f, 0.0f);
        //设置插值器
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setDuration(200);
        rotation.start();
        lvMusicCollectSongSheet.setVisibility(View.GONE);
    }

    @Override
    public void setMySongSheetAdapter(List<MySongSheet> mySongSheetList) {
        musicMySongSheetAdapter = new MusicMySongSheetAdapter(mContext, mySongSheetList, null);
        lvMusicCreateSongSheet.setAdapter(musicMySongSheetAdapter);

        musicMySongSheetAdapter.setMySongSheetListener(new MusicMySongSheetAdapter.MySongSheetListener() {
            @Override
            public void goToDetail(MySongSheet mySongSheet) {
                mPresenter.goToDetail(mySongSheet);
            }

            @Override
            public void goToOption(MySongSheet mySongSheet) {
                mPresenter.goToOption(mySongSheet);
            }
        });
    }

    @Override
    public void setCollectSongSheetAdapter(List<CollectSongSheet> collectSongSheetList) {
        mPresenter.setCollectSongSheetIsVisible(collectSongSheetList.size());

        musicCollectSongSheetAdapter = new MusicCollectSongSheetAdapter(mContext, collectSongSheetList, null);
        lvMusicCollectSongSheet.setAdapter(musicCollectSongSheetAdapter);

        musicCollectSongSheetAdapter.setCollectSongSheetListener(new MusicCollectSongSheetAdapter.CollectSongSheetListener() {
            @Override
            public void goToDetail(CollectSongSheet collectSongSheet) {
                mPresenter.goToCollectDetail(collectSongSheet);
            }

            @Override
            public void goToOption(CollectSongSheet collectSongSheet) {
                mPresenter.goToCollectOption(collectSongSheet);
            }
        });
    }

    /**
     * 显示歌单设置
     *
     * @param type
     */
    @Override
    public void showSongSheetPopupWindow(final int type) {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_song_sheet, null);

        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        window.setTouchable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(srMusic, Gravity.BOTTOM, 0, 0);

        //背景逐渐变暗
        toggleBright();

        tvSongSheetTitle = (TextView) view.findViewById(R.id.tv_popup_song_sheet_title);

        llCreateNewSongSheet = (LinearLayout) view.findViewById(R.id.ll_popup_song_sheet_create);

        LinearLayout llSongSheetManager = (LinearLayout) view.findViewById(R.id.ll_popup_song_sheet_manager);

        mPresenter.setPopupTitle(type);

        llCreateNewSongSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出新建歌单窗口
                mPresenter.createSongSheet();
                //隐藏
                window.dismiss();
            }
        });

        llSongSheetManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到歌单管理
                mPresenter.jumpToSongSheetManager(type);
                //隐藏
                window.dismiss();
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //逐渐恢复背景
                toggleBright();
            }
        });
    }

    /**
     * 显示歌单列表项设置
     *
     * @param songSheetName
     * @param listID
     */
    @Override
    public void showSongSheetDetailPopupWindow(final String songSheetName, final String listID) {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_song_sheet_detail, null);

        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        window.setTouchable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(srMusic, Gravity.BOTTOM, 0, 0);

        //背景逐渐变暗
        toggleBright();

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_popup_song_sheet_detail_title);

        LinearLayout llSongSheetDownLoad = (LinearLayout) view.findViewById(R.id.ll_popup_song_sheet_detail_download);

        llSongSheetDelete = (LinearLayout) view.findViewById(R.id.ll_popup_song_sheet_detail_delete);

        llSongSheetEdit = (LinearLayout) view.findViewById(R.id.ll_popup_song_sheet_detail_edit);

        tvTitle.setText("歌单 : " + songSheetName);

        mPresenter.showSongSheetDetailEdit(listID);

        llSongSheetDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //下载歌单内所有歌曲，
                //本地歌单搜索数据库，收藏歌单网络获取歌单详情内歌曲，下载
                //隐藏
                window.dismiss();
            }
        });

        llSongSheetDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出确定删除窗口
                mPresenter.showDeleteConfirm(listID, songSheetName);
                //隐藏
                window.dismiss();
            }
        });

        llSongSheetEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到歌单编辑页面
                mPresenter.jumpToSongSheetInfoManager();
                //隐藏
                window.dismiss();
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //逐渐恢复背景
                toggleBright();
            }
        });
    }

    /**
     * 创建新歌单
     */
    @Override
    public void showCreateSongSheet() {
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

                mPresenter.createSongSheet(etInput.getText().toString());

                mPresenter.showSongSheet();
            }
        });
    }

    @Override
    public void showComfirmDelete(final String listID, final String songSheetName) {
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

        final Button mbtnCancel, btnDelete;

        mbtnCancel = (Button) view.findViewById(R.id.btn_delete_song_sheet_cancel);
        btnDelete = (Button) view.findViewById(R.id.btn_delete_song_sheet_confirm);

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
                mPresenter.deleteSongSheet(listID, songSheetName);
            }
        });
    }

    @Override
    public void hideCollectSongSheet() {
        llMusicCollectSongSheet.setVisibility(View.GONE);
        lvMusicCollectSongSheet.setVisibility(View.GONE);
    }

    @Override
    public void showCollectSongSheet() {
        llMusicCollectSongSheet.setVisibility(View.VISIBLE);
    }

    public void showCollectSongSheetDetail() {
        lvMusicCollectSongSheet.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCreateSongSheetTitle() {
        tvSongSheetTitle.setText(R.string.create_song_sheet);
    }

    @Override
    public void setCollectSongSheetTitle() {
        tvSongSheetTitle.setText(R.string.collect_song_sheet);
        llCreateNewSongSheet.setVisibility(View.GONE);
    }

    @Override
    public void setSongSheetDetailEditVisible() {
        llSongSheetEdit.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llSongSheetDelete.getLayoutParams();
        params.setMargins(0, 0, 0, DisplayUtils.dp2px(mContext, 7));
        llSongSheetDelete.setLayoutParams(params);
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

    //使屏幕背景逐渐变化
    private void toggleBright() {
        //三个参数分别为： 起始值 结束值 时长  那么整个动画回调过来的值就是从0.5f--1f的
        windowAnimUtil.setValueAnimator(0.5f, 1f, 400);
        windowAnimUtil.addUpdateListener(new WindowAnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                //此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (1.5f - progress);
                backgroundAlpha(bgAlpha);//在此处改变背景，这样就不用通过Handler去刷新了。
            }
        });
        windowAnimUtil.addEndListner(new WindowAnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                //在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        windowAnimUtil.startAnimator();
    }

    /***
     * 此方法用于改变背景的透明度，从而达到变暗的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
        //FLAG_BLUR_BEHIND 设置背景模糊
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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

}