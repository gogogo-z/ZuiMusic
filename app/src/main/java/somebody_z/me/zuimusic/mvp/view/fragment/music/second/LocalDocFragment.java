package somebody_z.me.zuimusic.mvp.view.fragment.music.second;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;

/**
 * Created by Huanxing Zeng on 2017/2/21.
 * email : zenghuanxing123@163.com
 */
public class LocalDocFragment extends BaseFragment {

    public LocalDocFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final LocalDocFragment INSTANCE = new LocalDocFragment();
    }

    public static LocalDocFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
