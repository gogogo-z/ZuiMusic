package somebody_z.me.zuimusic.mvp.view.fragment.music.second;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.LocalArtistContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.presenter.LocalArtistPresenter;

/**
 * Created by Huanxing Zeng on 2017/2/21.
 * email : zenghuanxing123@163.com
 */
public class LocalArtistFragment extends BaseFragment<LocalArtistPresenter> implements LocalArtistContract.LocalArtistView {

    public LocalArtistFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final LocalArtistFragment INSTANCE = new LocalArtistFragment();
    }

    public static LocalArtistFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected LocalArtistPresenter loadPresenter() {
        return new LocalArtistPresenter();
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
