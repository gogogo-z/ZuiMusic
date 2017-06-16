package somebody_z.me.zuimusic.widget.crefresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.utils.DisplayUtils;


/**
 * 定义加载更多样式
 * Created by Huanxing Zeng on 2017/1/6.
 * email : zenghuanxing123@163.com
 */
public class LoadingMoreFooter extends LinearLayout {
    //设置状态
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    private TextView mText;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;

    private ImageView imageView;

    private AnimationDrawable animationDrawable;

    private int imageId = R.drawable.animation_loading;

    public LoadingMoreFooter(Context context) {
        this(context, null);
    }

    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //设置布局
        setGravity(Gravity.CENTER_HORIZONTAL);
        setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(getContext(), 100)));

        setPadding(0, DisplayUtils.dp2px(getContext(), 20), 0, 0);
        imageView = new ImageView(getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(DisplayUtils.dp2px(getContext(), 20), DisplayUtils.dp2px(getContext(), 20)));
        imageView.setImageResource(imageId);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        addView(imageView);

        mText = new TextView(getContext());
        mText.setText("努力加载中...");
        loadingHint = "努力加载中...";
        noMoreHint = "没有更多了...";
        loadingDoneHint = "加载完成...";
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 0, 0);

        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    //设置图片资源---这里使用的是animation-list
    public void setArrowImageView(int resid) {
        this.imageId = resid;
    }

    //根据状态设置刷新显示的内容
    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                imageView.setVisibility(View.VISIBLE);
                animationDrawable.start();
                mText.setText(loadingHint);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mText.setText(loadingDoneHint);
                this.setVisibility(View.GONE);
                animationDrawable.stop();
                break;
            case STATE_NOMORE:
                mText.setText(noMoreHint);
                imageView.setVisibility(View.GONE);
                animationDrawable.stop();
                this.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
    }

    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }
}
