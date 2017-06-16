package somebody_z.me.zuimusic.mvp.view.fragment.music.second;

import android.animation.Animator;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.MyAlbumContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;
import somebody_z.me.zuimusic.mvp.presenter.MyAlbumPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.MyAlbumAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.utils.WindowAnimUtil;
import somebody_z.me.zuimusic.widget.ListViewForScrollView;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class MyAlbumFragment extends BaseFragment<MyAlbumPresenter> implements MyAlbumContract.MyAlbumView {

    @Bind(R.id.ll_my_album_back)
    LinearLayout llMyAlbumBack;
    @Bind(R.id.ll_my_album_bar)
    LinearLayout llMyAlbumBar;
    @Bind(R.id.lv_my_album)
    ListViewForScrollView lvMyAlbum;
    @Bind(R.id.ll_my_album_root)
    LinearLayout llMyAlbumRoot;

    private MyAlbumAdapter mAdapter;

    private WindowAnimUtil windowAnimUtil;

    private float bgAlpha = 1f;
    private boolean bright = false;

    private LinearLayout llSongSheetDelete;

    private LinearLayout llSongSheetEdit;

    @Override
    protected MyAlbumPresenter loadPresenter() {
        return new MyAlbumPresenter();
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

        windowAnimUtil = new WindowAnimUtil();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_album;
    }

    @OnClick(R.id.ll_my_album_back)
    public void onClick() {
        mPresenter.onBackPressed();
    }

    @Override
    public void setBarMarginTop() {
        llMyAlbumBar.setPadding(0, ScreenUtil.getStatusBarHeight(mContext), 0, 0);
    }

    @Override
    public boolean onBackPressed() {
        mPresenter.onBackPressed();
        return super.onBackPressed();
    }

    @Override
    public void setMyAlbumAdapter(List<AlbumDetailBean.AlbumInfoBean> mySongSheetList) {
        mAdapter = new MyAlbumAdapter(mContext, mySongSheetList);
        lvMyAlbum.setAdapter(mAdapter);

        mAdapter.setMySongSheetListener(new MyAlbumAdapter.MyAlbumListener() {
            @Override
            public void goToDetail(AlbumDetailBean.AlbumInfoBean albumInfoBean) {
                mPresenter.goToDetail(albumInfoBean);
            }

            @Override
            public void goToOption(AlbumDetailBean.AlbumInfoBean albumInfoBean) {
                mPresenter.goToOption(albumInfoBean);
            }
        });

    }

    /**
     * 显示歌单列表项设置
     */
    @Override
    public void showSongSheetDetailPopupWindow(final AlbumDetailBean.AlbumInfoBean albumInfoBean) {
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
        window.showAtLocation(llMyAlbumRoot, Gravity.BOTTOM, 0, 0);

        //背景逐渐变暗
        toggleBright();

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_popup_song_sheet_detail_title);

        LinearLayout llSongSheetDownLoad = (LinearLayout) view.findViewById(R.id.ll_popup_song_sheet_detail_download);

        llSongSheetDelete = (LinearLayout) view.findViewById(R.id.ll_popup_song_sheet_detail_delete);

        llSongSheetEdit = (LinearLayout) view.findViewById(R.id.ll_popup_song_sheet_detail_edit);

        tvTitle.setText("唱片 : " + albumInfoBean.getTitle());

        mPresenter.showSongSheetDetailEdit(albumInfoBean.getAlbum_id());

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
                mPresenter.showDeleteConfirm(albumInfoBean);
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

    @Override
    public void setSongSheetDetailEditVisible() {
        llSongSheetEdit.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llSongSheetDelete.getLayoutParams();
        params.setMargins(0, 0, 0, DisplayUtils.dp2px(mContext, 7));
        llSongSheetDelete.setLayoutParams(params);
    }

    @Override
    public void showComfirmDelete(final AlbumDetailBean.AlbumInfoBean albumInfoBean) {
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
                mPresenter.deleteSongSheet(albumInfoBean);
            }
        });
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

}
