package somebody_z.me.zuimusic.mvp.view.fragment.music.second;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.LocalMusicContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.presenter.LocalMusicPresenter;
import somebody_z.me.zuimusic.mvp.view.adapter.CommFragmentPagerAdapter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/2/3.
 * email : zenghuanxing123@163.com
 */
public class LocalMusicFragment extends BaseFragment<LocalMusicPresenter> implements LocalMusicContract.LocalMusicView {

    @Bind(R.id.ll_local_music_back)
    LinearLayout llLocalMusicBack;
    @Bind(R.id.ll_detail_more)
    LinearLayout llDetailMore;
    @Bind(R.id.ll_detail_search)
    LinearLayout llDetailSearch;
    @Bind(R.id.tl_local_music)
    TabLayout tlLocalMusic;
    @Bind(R.id.tv_local_music_tab_indicator)
    TextView tvLocalMusicTabIndicator;
    @Bind(R.id.vp_local_music_content)
    ViewPager vpLocalMusicContent;
    @Bind(R.id.ll_local_music_bar)
    LinearLayout llLocalMusicBar;

    private TextView tvBar, tvResult, tvUri, tvBack, tvGetCoverAndLrc, tvCancel;
    private ImageView ivClose, ivScan, ivEffect;
    private RelativeLayout rlscan;

    private LinearLayout llScanLocalMusic, llSortMode, llGetCoverAndLrc, llUpQuality;

    public ValueAnimator animator;//放大镜动画

    public LocalMusicFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final LocalMusicFragment INSTANCE = new LocalMusicFragment();
    }

    public static LocalMusicFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @OnClick({R.id.ll_local_music_back, R.id.ll_detail_more, R.id.ll_detail_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_local_music_back:
                mPresenter.onBackPressed();
                break;
            case R.id.ll_detail_more:
                mPresenter.showMoreWindow();
                break;
            case R.id.ll_detail_search:
                mPresenter.search();
                break;
        }
    }

    @Override
    protected LocalMusicPresenter loadPresenter() {
        return new LocalMusicPresenter();
    }

    @Override
    protected void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(LocalSongFragment.getInstance());
        //fragmentList.add(LocalArtistFragment.getInstance());
        // fragmentList.add(LocalAlbumFragment.getInstance());
        // fragmentList.add(LocalDocFragment.getInstance());

        CommFragmentPagerAdapter fragmentPagerAdapter = new CommFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        vpLocalMusicContent.setAdapter(fragmentPagerAdapter);
    }

    @Override
    protected void initListener() {
        //viewpager的滑动事件监听
        vpLocalMusicContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //tablayout横条根据viewpager的位置进行变换
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvLocalMusicTabIndicator.getLayoutParams();
                lp.leftMargin = (int) ((position % 4 + positionOffset) * ScreenUtil.getScreenWidth(mContext) / 4);
                tvLocalMusicTabIndicator.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                //与tablayout同步
                tlLocalMusic.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //tablayout的监听事件
        tlLocalMusic.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //与viewpager同步
                vpLocalMusicContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        llDetailSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toast("搜索本地歌曲");
                return true;
            }
        });

        llDetailMore.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toast("更多");
                return true;
            }
        });

    }

    @Override
    protected void initView() {
        mPresenter.isImmerse();
        //添加tab项
        tlLocalMusic.addTab(tlLocalMusic.newTab().setText("单曲"));
        //tlLocalMusic.addTab(tlLocalMusic.newTab().setText("歌手"));
        //tlLocalMusic.addTab(tlLocalMusic.newTab().setText("专辑"));
        //tlLocalMusic.addTab(tlLocalMusic.newTab().setText("文件夹"));

        vpLocalMusicContent.setOffscreenPageLimit(2);// 至少缓存两页

        //设置tablayout底部条
        tvLocalMusicTabIndicator.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(mContext) / 1,
                DisplayUtils.dp2px(mContext, 2)));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_loacl_music;
    }

    @Override
    public void setBarMarginTop() {
        llLocalMusicBar.setPadding(0, ScreenUtil.getStatusBarHeight(mContext), 0, 0);
    }

    @Override
    public void initScanDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_local_music_scaning, null);

        rlscan = (RelativeLayout) view.findViewById(R.id.rl_local_music_scan);
        ivClose = (ImageView) view.findViewById(R.id.iv_local_music_scan_close);
        ivScan = (ImageView) view.findViewById(R.id.iv_local_music_scan);
        ivEffect = (ImageView) view.findViewById(R.id.iv_local_music_scan_effect);
        tvBar = (TextView) view.findViewById(R.id.tv_dialog_local_music_scaning_bar);
        tvResult = (TextView) view.findViewById(R.id.tv_local_music_scan_result);
        tvUri = (TextView) view.findViewById(R.id.tv_local_music_scan_local_uri);
        tvBack = (TextView) view.findViewById(R.id.tv_local_music_scan_back_my_music);
        tvGetCoverAndLrc = (TextView) view.findViewById(R.id.tv_local_music_scan_get_cover_lrcs);
        tvCancel = (TextView) view.findViewById(R.id.tv_local_music_scan_cancel);

        mPresenter.setScanBar();

        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Dialog_Full_All_Screen_Anim);

        builder.setView(view);

        //创建对话框
        final AlertDialog dialog = builder.create();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mPresenter.refresh();
                mPresenter.stopScanAnim(animator);
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mPresenter.refresh();
                mPresenter.stopScanAnim(animator);
            }
        });

        //获取封面歌词
        tvGetCoverAndLrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mPresenter.refresh();
                mPresenter.stopScanAnim(animator);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.stopScanAnim(animator);
                mPresenter.stopScan();
            }
        });

        dialog.show();

        mPresenter.scanLocalMusic();

        //设置宽高，需在show（）之后设置，否则没有效果。
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtil.getScreenWidth(mContext);
        params.height = ScreenUtil.getScreenHeight(mContext);
        dialog.getWindow().setAttributes(params);

    }

    @Override
    public void setScanBar() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvBar.getLayoutParams();
        params.height = ScreenUtil.getStatusBarHeight(mContext);
        tvBar.setLayoutParams(params);
    }

    @Override
    public boolean onBackPressed() {
        mPresenter.onBackPressed();
        return super.onBackPressed();
    }

    @Override
    public void clearEffectAnim() {
        ivEffect.clearAnimation();
    }

    @Override
    public void setEffectVisible(int visible) {
        ivEffect.setVisibility(visible);
    }

    @Override
    public void updateScannerText(String path) {
        tvUri.setText(path);
    }

    @Override
    public void updateScannerResult(String result) {
        tvResult.setText(result);
    }

    @Override
    public void initScanAnim() {
        animator = ValueAnimator.ofFloat(360, 0);
        animator.setDuration(100000);
        animator.setEvaluator(new FloatEvaluator());
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        final int radius = 30;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float angle = (float) animation.getAnimatedValue();
                float translateX = (float) (radius * Math.cos(angle));
                float translateY = (float) (radius * Math.sin(angle));
                ivScan.setTranslationX(translateX);
                ivScan.setTranslationY(translateY);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ivScan.setSelected(true);
                ivScan.setTranslationX(0);
                ivScan.setTranslationY(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

        //扫描效果添加
//        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 10, 500);
        TranslateAnimation translateAnimation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.7f);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setRepeatMode(TranslateAnimation.RESTART);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ivEffect.setVisibility(View.VISIBLE);
//                scanEffectImageView.bringToFront();
//                scanEffectImageView.requestLayout();
//                scanEffectImageView.invalidate();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivEffect.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivEffect.clearAnimation();
        ivEffect.startAnimation(translateAnimation);
    }

    @Override
    public void setBackAndGetLrcVisible(int visible) {
        tvBack.setVisibility(visible);
        tvGetCoverAndLrc.setVisibility(visible);
    }

    @Override
    public void setCancelVisible(int visible) {
        tvCancel.setVisibility(visible);
    }

    @Override
    public void setUriVisible(int visible) {
        tvUri.setVisibility(visible);
    }

    @Override
    public void setResult(Spanned result) {
        tvResult.setText(result);
    }

    /**
     * 使用.9图片设置圆角和阴影
     */
    @Override
    public void initMoreWindow(int height) {
        // 利用layoutInflater获得View
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_local_music_more, null);

        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        llScanLocalMusic = (LinearLayout) view.findViewById(R.id.ll_local_music_more_scan_local_music);
        llSortMode = (LinearLayout) view.findViewById(R.id.ll_local_music_more_sort_mode);
        llGetCoverAndLrc = (LinearLayout) view.findViewById(R.id.ll_local_music_more_get_cover_and_lrc);
        llUpQuality = (LinearLayout) view.findViewById(R.id.ll_local_music_more_up_quality);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llScanLocalMusic.getLayoutParams();
        params.width = ScreenUtil.getScreenWidth(mContext) * 7 / 13;
        params.height = ScreenUtil.getScreenHeight(mContext) * 5 / 69;
        llScanLocalMusic.setLayoutParams(params);
        llSortMode.setLayoutParams(params);
        llGetCoverAndLrc.setLayoutParams(params);
        llUpQuality.setLayoutParams(params);

        llScanLocalMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
                initScanDialog();
            }
        });

        llSortMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });

        llGetCoverAndLrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });

        llUpQuality.setOnClickListener(new View.OnClickListener() {
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
        window.showAtLocation(llDetailMore, Gravity.TOP | Gravity.RIGHT, 0, height);

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

}
