package somebody_z.me.zuimusic.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.common.ActivityController;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.ToastUtil;
import somebody_z.me.zuimusic.widget.StatusBarCompat;

/**
 * Created by Huanxing Zeng on 2016/12/28.
 * email : zenghuanxing123@163.com
 */
public abstract class BaseActivity<P extends BasePresenter> extends FragmentActivity
        implements IView {

    protected CompositeSubscription sCompositeSubscription;
    public Subscription subscription;

    protected View view;

    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());

        //RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        //refWatcher.watch(this);

        ButterKnife.bind(this);
        mPresenter = loadPresenter();
        initCommonData();
        initView();
        initListener();
        initData();

        // 添加activity
       // ActivityController.getInstance().addActivity(this);

        StatusBarCompat.compat(this);

        if (sCompositeSubscription == null || sCompositeSubscription.isUnsubscribed()) {
            sCompositeSubscription = new CompositeSubscription();
        }
    }

    /**
     * 添加Subscription
     *
     * @param subscription
     */
    public void addSubscription(Subscription subscription) {
        sCompositeSubscription.add(subscription);
    }

    protected abstract P loadPresenter();

    private void initCommonData() {

        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }

    /**
     * 显示一个内容为str的toast
     *
     * @param str
     */
    public void toast(String str) {
        ToastUtil.getInstance().showShortToast(this, str);
    }

    /**
     * 显示一个内容为contentId指定的toast
     *
     * @param contentId
     */
    public void toast(int contentId) {
        ToastUtil.getInstance().showShortToast(this, contentId + "");
    }

    /**
     * 错误级别日志的处理
     *
     * @param str
     */
    public void Log(String str) {
        LogUtil.e("--------------->" + str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除activity
      //  ActivityController.getInstance().removeActivity(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (sCompositeSubscription != null) {
            sCompositeSubscription.unsubscribe();
        }

    }

    /**
     * 隐藏键盘
     */
    protected void HideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
