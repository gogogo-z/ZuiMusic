package somebody_z.me.zuimusic.mvp.view.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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
import somebody_z.me.zuimusic.mvp.Contract.SongSelectContract;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.mvp.presenter.SongSelectPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.CollectToSongSheetAdapter;
import somebody_z.me.zuimusic.mvp.view.adapter.SongSelectAdapter;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.widget.ListViewForScrollView;

/**
 * 详情页跳转歌曲选择的页面
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class SongSelectActivity extends BaseActivity<SongSelectPresenter> implements SongSelectContract.SongSelectView {
    @Bind(R.id.ll_song_select_back)
    LinearLayout llSongSelectBack;
    @Bind(R.id.tv_song_select_count)
    TextView tvSongSelectCount;
    @Bind(R.id.tv_song_select_all)
    TextView tvSongSelectAll;
    @Bind(R.id.rl_song_select)
    RecyclerView rlSongSelect;
    @Bind(R.id.ll_song_select_next_play)
    LinearLayout llSongSelectNextPlay;
    @Bind(R.id.ll_song_select_add_to_song_sheet)
    LinearLayout llSongSelectAddToSongSheet;
    @Bind(R.id.ll_song_select_download)
    LinearLayout llSongSelectDownload;
    @Bind(R.id.ll_song_select_operation)
    LinearLayout llSongSelectOperation;

    private SongSelectAdapter mAdapter;

    private Button btnCancel, btnCommit;
    private CheckBox cbPrivate;

    @Override
    protected SongSelectPresenter loadPresenter() {
        return new SongSelectPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        //设置底部播放栏的透明度
         llSongSelectOperation.setAlpha((float) 0.92);

        mPresenter.init();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rlSongSelect.setLayoutManager(layoutManager);

        rlSongSelect.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_songselect;
    }

    @Override
    public void finish() {
        super.finish();
        //去除activity跳转动画
        overridePendingTransition(0, 0);
    }

    @OnClick({R.id.ll_song_select_back, R.id.tv_song_select_all, R.id.ll_song_select_next_play, R.id.ll_song_select_add_to_song_sheet, R.id.ll_song_select_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_song_select_back:
                finish();
                break;
            case R.id.tv_song_select_all:
                mPresenter.setChecked();
                break;
            case R.id.ll_song_select_next_play:
                mPresenter.nextPlay();
                break;
            case R.id.ll_song_select_add_to_song_sheet:
                mPresenter.showCollectToSongSheetDialog();
                break;
            case R.id.ll_song_select_download:
                mPresenter.download();
                break;
        }
    }

    @Override
    public void setSongSheetAdapter(List<ContentBean> songSheetDetailList) {
        mAdapter = new SongSelectAdapter(this, songSheetDetailList);
    }

    @Override
    public void setAdapterListener() {
        mAdapter.setOnItemClickListener(new SongSelectAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ContentBean songSheetDetail, boolean isChecked) {
                mPresenter.addTiList(songSheetDetail, isChecked);
            }
        });
    }

    @Override
    public void showMsg(String msg) {
        toast(msg);
    }

    @Override
    public void setChecked(boolean isChecked) {
        mAdapter.setSelectAll(isChecked);
    }

    @Override
    public void setSelectCount(String str) {
        tvSongSelectCount.setText(str);
    }

    @Override
    public void setSelect(int str) {
        tvSongSelectAll.setText(str);
    }

    @Override
    public void initCollectToSongSheetDialog(List<MySongSheet> mySongSheetList, final List<ContentBean> songInfoList) {
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
                mPresenter.collectToMySongSheet(mySongSheet, songInfoList);
            }

        });

        //创建新歌单
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mPresenter.showCreateNewSongSheetDialog(songInfoList);
            }
        });

        dialog.show();
        //设置宽高，需在show（）之后设置，否则没有效果,设置最大高度
        // 动态设置dialog的高度，设置最大高度为ScreenUtil.getScreenHeight(mContext) * 2 / 3；
        mPresenter.setDialogMaxHeight(itemHeight * (collectToSongSheetAdapter.getCount() + 2), dialog);
    }

    /**
     * 创建新歌单
     */
    @Override
    public void showCreateSongSheet(final List<ContentBean> songInfoList) {
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

                mPresenter.createSongSheet(etInput.getText().toString(), songInfoList);
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
