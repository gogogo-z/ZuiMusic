package somebody_z.me.zuimusic.mvp.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.ToastUtil;
import somebody_z.me.zuimusic.widget.StatusBarCompat;

/**
 * Created by Huanxing Zeng on 2016/12/28.
 * email : zenghuanxing123@163.com
 */
public abstract class BaseFragment<P extends BasePresenter> extends BackHandledFragment implements IView {
    protected CompositeSubscription sCompositeSubscription;
    public Subscription subscription;

    protected View root;

    protected P mPresenter;

    protected boolean isImmersing = false;

    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(getActivity());
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(getLayoutId(), container, false);

        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = loadPresenter();
        initCommonData();
        initView();
        initListener();
        initData();

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

    private void isImmersing() {
        // 当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            isImmersing = true;
        }
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
     * 显示一个内容为str的toast
     *
     * @param str
     */
    public void toast(String str) {
        ToastUtil.getInstance().showShortToast(mContext, str);
    }

    /**
     * 显示一个内容为contentId指定的toast
     *
     * @param contentId
     */
    public void toast(int contentId) {
        ToastUtil.getInstance().showShortToast(mContext, contentId + "");
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
    public void onDestroyView() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (sCompositeSubscription != null) {
            sCompositeSubscription.unsubscribe();
        }
        //RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        //refWatcher.watch(this);
    }

    /**
     * 隐藏键盘
     */
    protected void HideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
