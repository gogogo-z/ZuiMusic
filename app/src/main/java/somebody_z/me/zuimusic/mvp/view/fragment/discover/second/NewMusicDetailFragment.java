package somebody_z.me.zuimusic.mvp.view.fragment.discover.second;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.mvp.Contract.NewMusicDetailContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.mvp.presenter.NewMusicDetailPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.CollectToSongSheetAdapter;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.utils.transformation.BlurTransformation;
import somebody_z.me.zuimusic.widget.ListViewForScrollView;

/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class NewMusicDetailFragment extends BaseFragment<NewMusicDetailPresenter> implements NewMusicDetailContract.NewMusicDetailView {

    @Bind(R.id.iv_new_music_detail_bg)
    ImageView ivNewMusicDetailBg;
    @Bind(R.id.ll_new_music_detail_back)
    LinearLayout llNewMusicDetailBack;
    @Bind(R.id.ll_new_music_detail_collect)
    LinearLayout llNewMusicDetailCollect;
    @Bind(R.id.rl_new_music_detail_bar)
    RelativeLayout rlNewMusicDetailBar;
    @Bind(R.id.iv_new_music_detail_cover)
    ImageView ivNewMusicDetailCover;
    @Bind(R.id.tv_new_music_detail_cover_title)
    TextView tvNewMusicDetailCoverTitle;
    @Bind(R.id.tv_new_music_detail_cover_author_and_album)
    TextView tvNewMusicDetailCoverAuthorAndAlbum;
    @Bind(R.id.tv_new_music_detail_cover_recommend_reason)
    TextView tvNewMusicDetailCoverRecommendReason;
    @Bind(R.id.btn_new_music_detail_play)
    Button btnNewMusicDetailCollect;
    @Bind(R.id.iv_new_song_sheet_collect)
    ImageView ivNewSongSheetCollect;
    @Bind(R.id.tv_new_music_detail_cover_is_collect)
    TextView tvNewMusicDetailCoverIsCollect;

    private Button btnCancel, btnCommit;
    private CheckBox cbPrivate;

    public NewMusicDetailFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final NewMusicDetailFragment INSTANCE = new NewMusicDetailFragment();
    }

    public static NewMusicDetailFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected NewMusicDetailPresenter loadPresenter() {
        return new NewMusicDetailPresenter();
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

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivNewMusicDetailCover.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidth(mContext) * 17 / 28;
        layoutParams.height = ScreenUtil.getScreenHeight(mContext) * 11 / 30;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_music_detail;
    }

    @OnClick({R.id.ll_new_music_detail_back, R.id.ll_new_music_detail_collect, R.id.btn_new_music_detail_play})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_new_music_detail_back:
                mPresenter.onBackPressed();
                break;
            case R.id.ll_new_music_detail_collect:
                mPresenter.showCollectToSongSheetDialog();
                break;
            case R.id.btn_new_music_detail_play:
                //播放歌曲
                mPresenter.playSong();
                break;
        }
    }

    @Override
    public void setMarginTop() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlNewMusicDetailBar.getLayoutParams();
        params.setMargins(0, ScreenUtil.getStatusBarHeight(mContext), 0, 0);
    }

    @Override
    public void setTitle(String title) {
        tvNewMusicDetailCoverTitle.setText(title);
    }

    @Override
    public void setAuthorAndAlbum(String str) {
        tvNewMusicDetailCoverAuthorAndAlbum.setText(str);
    }

    @Override
    public void setRecommendReason(String recommendReason) {
        tvNewMusicDetailCoverRecommendReason.setText(recommendReason);
    }

    @Override
    public void setIsCollect(String msg) {
        tvNewMusicDetailCoverIsCollect.setText(msg);
    }

    @Override
    public void setCollectVisible(int resource) {
        ivNewSongSheetCollect.setImageResource(resource);
    }

    @Override
    public void setCoverAndBg(String url, String url_bg) {
        //加载歌单封面和背景
        //设置滤镜
        ivNewMusicDetailCover.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        ivNewMusicDetailBg.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        //使用Glide第三方框架加载图片
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivNewMusicDetailCover);

        Glide.with(mContext)
                .load(url_bg)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .bitmapTransform(new BlurTransformation(mContext))
                .into(ivNewMusicDetailBg);
    }

    @Override
    public boolean onBackPressed() {
        mPresenter.onBackPressed();
        return super.onBackPressed();
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
                //取消收藏
                mPresenter.delectCollectSong();
            }
        });
    }

    @Override
    public void showMsg(String msg) {
        toast(msg);
    }
}
