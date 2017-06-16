package somebody_z.me.zuimusic.mvp.view.fragment.discover;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.RankContract;
import somebody_z.me.zuimusic.mvp.base.BaseFragment;
import somebody_z.me.zuimusic.mvp.model.bean.RankBean;
import somebody_z.me.zuimusic.mvp.presenter.RankPresenter;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.widget.rankglobal.RankGlobalPanel;
import somebody_z.me.zuimusic.widget.rankofficial.RankOfficialPanel;

/**
 * Created by Huanxing Zeng on 2016/12/29.
 * email : zenghuanxing123@163.com
 */
public class RankFragment extends BaseFragment<RankPresenter> implements RankContract.RankView {

    public RankFragment() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final RankFragment INSTANCE = new RankFragment();
    }

    public static RankFragment getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Bind(R.id.iv_rank_loading)
    ImageView ivRankLoading;
    @Bind(R.id.ll_rank_loading)
    LinearLayout llRankLoading;
    @Bind(R.id.ll_rank_root)
    LinearLayout llRankRoot;

    @Override
    protected RankPresenter loadPresenter() {
        return new RankPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mPresenter.showLoading();
        mPresenter.getRank();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rank;
    }

    @Override
    public void addRankOfficial(List<RankBean.ContentBean> contentBeenList) {
        RankOfficialPanel rankOfficialPanel = new RankOfficialPanel(mContext, contentBeenList);
        llRankRoot.addView(rankOfficialPanel);
        mPresenter.setRankOfficialListener(rankOfficialPanel);
    }

    @Override
    public void addRankGlobal(List<RankBean.ContentBean> contentBeenList) {
        RankGlobalPanel rankGlobalPanel = new RankGlobalPanel(mContext, contentBeenList);
        llRankRoot.addView(rankGlobalPanel);
        mPresenter.setRankGlobalListener(rankGlobalPanel);
    }


    public void showLoading() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llRankLoading.getLayoutParams();
        params.setMargins(0, DisplayUtils.dp2px(mContext, 90), 0, 0);
        llRankLoading.setLayoutParams(params);
        llRankLoading.setVisibility(View.VISIBLE);
        llRankRoot.setVisibility(View.GONE);
        ivRankLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivRankLoading.getDrawable();
        animationDrawable.start();
    }

    public void closeLoading() {
        llRankLoading.setVisibility(View.GONE);
        llRankRoot.setVisibility(View.VISIBLE);
        ivRankLoading.setImageResource(R.drawable.animation_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivRankLoading.getDrawable();
        animationDrawable.stop();
    }

    public void showMsg(String msg) {
        toast(msg);
    }
}
