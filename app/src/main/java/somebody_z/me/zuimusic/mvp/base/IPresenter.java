package somebody_z.me.zuimusic.mvp.base;

/**
 * Created by Huanxing Zeng on 2016/12/28.
 * email : zenghuanxing123@163.com
 */
public interface IPresenter<V extends IView> {

    /**
     * 绑定
     *
     * @param view
     */
    void attachView(V view);

    /**
     * 防止内存的泄漏,清楚presenter与activity之间的绑定
     */
    void detachView();

    /**
     * @return 获取View
     */
    IView getIView();
}
