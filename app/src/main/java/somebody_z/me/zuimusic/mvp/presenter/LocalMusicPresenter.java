package somebody_z.me.zuimusic.mvp.presenter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.ArrayList;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.LocalMusicContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.LocalMusicModel;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.discover.second.SongSheetDetailSearchFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.LocalMusicFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.LocalMusicSearchFragment;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.LocalSongFragment;
import somebody_z.me.zuimusic.utils.KeywordUtil;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/2/17.
 * email : zenghuanxing123@163.com
 */
public class LocalMusicPresenter extends BasePresenter<LocalMusicModel, LocalMusicFragment> implements
        LocalMusicContract.LocalMusicPresenter {

    private int index = 0;

    @Override
    public LocalMusicModel loadModel() {
        return new LocalMusicModel();
    }

    @Override
    public void isImmerse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setBarMarginTop();
        }
    }

    @Override
    public void setScanBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setScanBar();
        }
    }

    @Override
    public void onBackPressed() {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).showBar();
        }
        getIView().getFragmentManager().popBackStack();

    }

    @Override
    public void stopScanAnim(ValueAnimator animator) {
        getIView().clearEffectAnim();
        getIView().setEffectVisible(View.GONE);
        if (null != animator && animator.isRunning()) {
            animator.end();
        }
    }

    @Override
    public void scanLocalMusic() {
        getIView().initScanAnim();
        getIView().setBackAndGetLrcVisible(View.GONE);
        getIView().setUriVisible(View.VISIBLE);
        getIView().setCancelVisible(View.VISIBLE);
        getiModel().scanLocalMusic(getIView().mContext, new LocalMusicModel.ScanLocalMusicLsitener() {
            @Override
            public void scanComplete(int size) {
                stopScanAnim(getIView().animator);
                getIView().setBackAndGetLrcVisible(View.VISIBLE);
                getIView().setUriVisible(View.GONE);
                getIView().setCancelVisible(View.GONE);
                //设置文本部分变色
                // getIView().setResult(Html.fromHtml("共扫描到<font color='#C70C0C'>" + size + "首</font>歌曲，新增" + size + "首"));
                getIView().setResult(KeywordUtil.matcherSearchTitle(getIView().getResources().getColor(R.color.colorRed),
                        "共扫描到" + getiModel().getLocalSongCount(getIView().mContext) + "首歌曲，新增" + size + "首",
                        new String[]{getiModel().getLocalSongCount(getIView().mContext) + "首", size + "首"}));

                index = 0;
            }

            @Override
            public void scanError(int size) {
                stopScanAnim(getIView().animator);
                getIView().setBackAndGetLrcVisible(View.VISIBLE);
                getIView().setUriVisible(View.GONE);
                getIView().setCancelVisible(View.GONE);

                getIView().setResult(KeywordUtil.matcherSearchTitle(getIView().getResources().getColor(R.color.colorRed),
                        "共扫描到" + getiModel().getLocalSongCount(getIView().mContext) + "首歌曲，新增" + size + "首",
                        new String[]{getiModel().getLocalSongCount(getIView().mContext) + "首", size + "首"}));
            }

            @Override
            public void scanNext(String url) {
                getIView().updateScannerText(url);

                index++;

                getIView().setResult(KeywordUtil.matcherSearchTitle(getIView().getResources().getColor(R.color.colorRed),
                        "共扫描到" + index + "首歌曲", new String[]{index + "首"}));

            }
        });
    }

    @Override
    public void stopScan() {
        getIView().setBackAndGetLrcVisible(View.VISIBLE);
        getIView().setUriVisible(View.GONE);
        getIView().setCancelVisible(View.GONE);
    }

    @Override
    public void search() {
        FragmentTransaction ft = getIView().getFragmentManager().beginTransaction();
        LocalMusicSearchFragment searchFragment = LocalMusicSearchFragment.getInstance();
        ft.add(R.id.fl_home_content, searchFragment);
        ft.hide(getIView());
        //将当前的事务添加到了回退栈
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void refresh() {
        LocalSongFragment.getInstance().init();
    }

    public void showMoreWindow() {
        getIView().initMoreWindow(ScreenUtil.getStatusBarHeight(getIView().mContext));
    }
}
