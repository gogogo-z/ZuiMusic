package somebody_z.me.zuimusic.widget.play;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * 组合布局
 * Created by Huanxing Zeng on 2017/3/7.
 * email : zenghuanxing123@163.com
 */
public class PlayView extends RelativeLayout {

    private ViewPager vpWidgetPlay;
    private TextView tvWidgetCenter;
    private ImageView ivWidgetPlayView;

    private PlayAdapter pagerAdapter;

    private List<ContentBean> songList;

    private int index;

    private ObjectAnimator playAnimation, stopAnimation;

    private ObjectAnimator objectAnimator;

    private float currentValue;

    private OnClickViewPagerListener onClickViewPagerListener;

    public PlayView(Context context, List<ContentBean> songList, int index) {
        super(context);
        this.songList = songList;
        this.index = index;
        initView(context);
    }

    public PlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_play_view, this);

        vpWidgetPlay = (ViewPager) findViewById(R.id.vp_widget_play);
        tvWidgetCenter = (TextView) findViewById(R.id.tv_widget_center);

        ivWidgetPlayView = (ImageView) findViewById(R.id.iv_widget_play_view);

        //设置旋转点
        ivWidgetPlayView.setPivotX(50);
        ivWidgetPlayView.setPivotY(50);

        playAnimation = ObjectAnimator.ofFloat(ivWidgetPlayView, "rotation", -30f, 0f);
        playAnimation.setDuration(300);

        stopAnimation = ObjectAnimator.ofFloat(ivWidgetPlayView, "rotation", 0f, -30f);
        stopAnimation.setDuration(300);

        pagerAdapter = new PlayAdapter(context, songList);
        vpWidgetPlay.setAdapter(pagerAdapter);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vpWidgetPlay.getLayoutParams();
        layoutParams.height = ScreenUtil.getScreenWidth(context) * 3 / 4;
        vpWidgetPlay.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivWidgetPlayView.getLayoutParams();
        params.width = ScreenUtil.getScreenHeight(context) * 3 / 20;
        params.height = ScreenUtil.getScreenHeight(context) * 9 / 40;
        ivWidgetPlayView.setLayoutParams(params);

        //设当前项为中间项即可向左滑动
        vpWidgetPlay.setCurrentItem(songList.size() * 1000 + index);

        vpWidgetPlay.setOffscreenPageLimit(2);

        //设置viewpager的滑动监听事件
        vpWidgetPlay.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                index = position;
            }

            @Override
            public void onPageSelected(int position) {
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    //暂停
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        LogUtil.e("----------暂停");
                        break;
                    //恢复
                    case ViewPager.SCROLL_STATE_IDLE:
                        LogUtil.e("--------恢复");
                        break;
                }
            }
        });

        //禁止viewpager滑动
        vpWidgetPlay.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        pagerAdapter.setOnClickPageListener(new PlayAdapter.OnClickPageListener() {
            @Override
            public void onClick() {
                onClickViewPagerListener.onClick();
            }
        });

    }

    public void setCurIndex(int index) {
        vpWidgetPlay.setCurrentItem(index);
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        if (pagerAdapter.getPrimaryItem() != null) {

            objectAnimator = ObjectAnimator.ofFloat(pagerAdapter.getPrimaryItem(), "Rotation", currentValue - 360, currentValue);
            // 设置持续时间
            objectAnimator.setDuration(20000);

            objectAnimator.setInterpolator(new LinearInterpolator());
            // 设置循环播放
            objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            // 设置动画监听
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // TODO Auto-generated method stub
                    // 监听动画执行的位置，以便下次开始时，从当前位置开始

                    // animation.getAnimatedValue()为float类型
                    currentValue = (float) animation.getAnimatedValue();
                }
            });

            objectAnimator.start();
        }

    }

    /**
     * 停止动画 *
     */
    public void stopAnimation() {
        if (objectAnimator != null) {
            objectAnimator.end();
        }

        objectAnimator = null;
        pagerAdapter.getPrimaryItem().clearAnimation();
        currentValue = 0;// 重置起始位置
    }

    /**
     * 暂停动画 *
     */
    public void pauseAnimation() {
        objectAnimator.cancel();
        pagerAdapter.getPrimaryItem().clearAnimation();
    }

    public void rotationNeedle(int state) {
        if (state == Constants.DOWN_ANIMAL) {
            LogUtil.e("DOWN_ANIMAL---------------");
            playAnimation.start();
            startAnimation();
        } else if (state == Constants.UP_ANIMAL) {
            LogUtil.e("UP_ANIMAL---------------");
            stopAnimation.start();
            if (objectAnimator != null) {
                startAnimation();
                pauseAnimation();
            }
        }
    }

    public void setOnClickViewPagerListener(OnClickViewPagerListener onClickViewPagerListener) {
        this.onClickViewPagerListener = onClickViewPagerListener;
    }

    public interface OnClickViewPagerListener {
        void onClick();
    }


}
