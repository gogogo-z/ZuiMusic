package somebody_z.me.zuimusic.mvp.view.fragment.music.second;

import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.MyPlayerContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.presenter.MyPlayerPresenter;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * 我的歌手 页面
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class MyPlayerFragment extends BaseFragment<MyPlayerPresenter> implements MyPlayerContract.MyPlayerView {

    @Bind(R.id.ll_my_player_back)
    LinearLayout llMyPlayerBack;
    @Bind(R.id.ll_my_player_bar)
    LinearLayout llMyPlayerBar;
    @Bind(R.id.ll_my_player_none)
    LinearLayout llMyPlayerNone;

    public MyPlayerFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final MyPlayerFragment INSTANCE = new MyPlayerFragment();
    }

    public static MyPlayerFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }


    @Override
    protected MyPlayerPresenter loadPresenter() {
        return new MyPlayerPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mPresenter.isImmerse();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_player;
    }

    @OnClick(R.id.ll_my_player_back)
    public void onClick() {
        mPresenter.onBackPressed();
    }

    @Override
    public void setBarMarginTop() {
        llMyPlayerBar.setPadding(0, ScreenUtil.getStatusBarHeight(mContext), 0, 0);
    }

    @Override
    public boolean onBackPressed() {
        mPresenter.onBackPressed();
        return super.onBackPressed();
    }
}
