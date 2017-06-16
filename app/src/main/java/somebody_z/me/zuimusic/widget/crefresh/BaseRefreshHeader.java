package somebody_z.me.zuimusic.widget.crefresh;

/**
 * 定义下拉刷新接口
 * Created by Huanxing Zeng on 2017/1/6.
 * email : zenghuanxing123@163.com
 */
public interface BaseRefreshHeader {

    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete();

}
