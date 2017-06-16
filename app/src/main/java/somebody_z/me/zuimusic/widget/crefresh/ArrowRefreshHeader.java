package somebody_z.me.zuimusic.widget.crefresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import somebody_z.me.zuimusic.R;

/**
 * 定义下拉刷新使用的样式
 * Created by Huanxing Zeng on 2017/1/6.
 * email : zenghuanxing123@163.com
 */
public class ArrowRefreshHeader extends LinearLayout implements BaseRefreshHeader {
    private ImageView mArrowImageView;

    private LinearLayout mContainer;

    private TextView mStatusTextView;

    public int mMeasuredHeight;

    private int mState = STATE_NORMAL;

    private AnimationDrawable animationDrawable;

    private int imageId = R.drawable.animation_loading;

    public ArrowRefreshHeader(Context context) {
        this(context, null);
    }

    public ArrowRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrowRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //多加一层，直接inflate会报 layoutparams cast出错，StaggeredGridLayoutManager can't cast...
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.recyclerview_header, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        this.setLayoutParams(params);
        this.setPadding(0, 0, 0, 0);

        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);

        mArrowImageView = (ImageView) findViewById(R.id.iv_refresh_loading);
        mStatusTextView = (TextView) findViewById(R.id.tv_refresh_loading);

        mArrowImageView.setImageResource(imageId);
        animationDrawable = (AnimationDrawable) mArrowImageView.getDrawable();

        //测量高度
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //获取高度
        mMeasuredHeight = getMeasuredHeight();
    }

    public void setArrowImageView(int resid) {
        this.imageId = resid;
    }

    //设置状态，根据状态显示不同内容
    public void setState(int state) {
        if (state == mState) {
            return;
        }

        switch (state) {
            case STATE_NORMAL:
                mStatusTextView.setText("下拉刷新...");
                break;
            case STATE_RELEASE_TO_REFRESH:
                animationDrawable.start();
                mStatusTextView.setText("释放刷新...");
                break;
            case STATE_REFRESHING:
                smoothScrollTo(mMeasuredHeight);
                mStatusTextView.setText("正在刷新...");
                break;
            case STATE_DONE:
                mStatusTextView.setText("刷新完成...");
                animationDrawable.stop();
                break;
        }

        mState = state;
    }

    public int getState() {
        return mState;
    }

    //设置布局下拉了多少
    @Override
    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <= mMeasuredHeight) {
            //return;
        }
        if (mState != STATE_REFRESHING) {
            smoothScrollTo(0);
        }

        if (mState == STATE_REFRESHING) {
            int destHeight = mMeasuredHeight;
            smoothScrollTo(destHeight);
        }

        return isOnRefresh;
    }

    @Override
    public void refreshComplete() {
        setState(STATE_DONE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 200);
    }

    public void reset() {
        smoothScrollTo(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 500);
    }

    //滑动到指定位置
    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    //设置显示出来的高度
    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    //获取要显示出来的高度
    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

}
