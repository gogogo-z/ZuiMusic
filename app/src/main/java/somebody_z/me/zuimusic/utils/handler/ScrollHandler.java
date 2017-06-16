package somebody_z.me.zuimusic.utils.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

/**
 * viewpager左右滑动以及轮播效果
 * Created by Administrator on 2016/10/8.
 */
public class ScrollHandler extends Handler{

    private ViewPager viewPager;

    public ScrollHandler(ViewPager viewPager){
        this.viewPager = viewPager;
    }

    /**
     * 请求更新显示的View。
     */
    public static final int MSG_UPDATE_IMAGE = 1;
    /**
     * 请求暂停轮播。
     */
    public static final int MSG_KEEP_SILENT = 2;
    /**
     * 请求恢复轮播。
     */
    public static final int MSG_BREAK_SILENT = 3;
    /**
     * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
     * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
     * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
     */
    public static final int MSG_PAGE_CHANGED = 4;

    //轮播间隔时间
    public static final long MSG_DELAY = 5000;

    private int currentItem = 0;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
        if (this.hasMessages(MSG_UPDATE_IMAGE)) {
            this.removeMessages(MSG_UPDATE_IMAGE);
        }

        switch (msg.what) {
            case MSG_UPDATE_IMAGE:
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                //准备下次播放
                this.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            case MSG_KEEP_SILENT:
                break;
            case MSG_BREAK_SILENT:
                this.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            case MSG_PAGE_CHANGED:
                currentItem = msg.arg1;
                this.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            default:
                break;
        }
    }

}
