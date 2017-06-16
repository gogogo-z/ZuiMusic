package somebody_z.me.zuimusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * ScrollTo 参考的坐标系是parentView 的左上方顶点
 * ScrollBy 参考的是当前view的左上方顶点，不是parentView 的左上方顶点
 * <p/>
 * Created by Huanxing Zeng on 2017/2/8.
 * email : zenghuanxing123@163.com
 */
public class MyScrollView extends ScrollView {

    private int speed = 10;

    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private IScrollChangedListener mScrollChangedListener;

    public void setScrollChangedListener(IScrollChangedListener scrollChangedListener) {
        mScrollChangedListener = scrollChangedListener;
    }

    /**
     * 由于滚动方向是竖直的，当滑动到顶部和底部 clampedY = true
     *
     * @param scrollX
     * @param scrollY
     * @param clampedX
     * @param clampedY
     */
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (scrollY == 0) {
            isScrolledToTop = clampedY;
            isScrolledToBottom = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        }
        notifyScrollChangedListeners();
    }

    /**
     * 由于getScrollY()会超过边界值，但是它自己会恢复正确
     * 导致有可能还没到顶部或底部却判断已经到顶部或底部
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (android.os.Build.VERSION.SDK_INT < 9) {  // API 9及之后走onOverScrolled方法监听
            if (getScrollY() == 0) {    // 小心踩坑1: 这里不能是getScrollY() <= 0
                isScrolledToTop = true;
                isScrolledToBottom = false;
            } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
                // 小心踩坑2: 这里不能是 >=
                // 小心踩坑3（可能忽视的细节2）：这里最容易忽视的就是ScrollView上下的padding　
                isScrolledToBottom = true;
                isScrolledToTop = false;
            } else {
                isScrolledToTop = false;
                isScrolledToBottom = false;
            }
            notifyScrollChangedListeners();
        }
    }

    private void notifyScrollChangedListeners() {
        if (isScrolledToTop) {
            if (mScrollChangedListener != null) {
                mScrollChangedListener.scrollToBottom(speed);
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        fullScroll(FOCUS_UP);
                    }
                });
            }
        }
    }

    public interface IScrollChangedListener {
        void scrollToBottom(int speed);
    }

    float startY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                speed = (int) (ev.getY() - startY);
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(ev);
    }
}