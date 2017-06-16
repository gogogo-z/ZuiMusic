package somebody_z.me.zuimusic.mvp.presenter;

import android.os.Build;

import somebody_z.me.zuimusic.mvp.Contract.MyPlayerContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.MyPlayerModel;
import somebody_z.me.zuimusic.mvp.view.activity.impl.topBarVisible;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.MyPlayerFragment;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class MyPlayerPresenter extends BasePresenter<MyPlayerModel, MyPlayerFragment> implements MyPlayerContract.MyPlayerPresenter {
    @Override
    public MyPlayerModel loadModel() {
        return new MyPlayerModel();
    }

    @Override
    public void isImmerse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getIView().setBarMarginTop();
        }
    }

    @Override
    public void onBackPressed() {
        if (getIView().getActivity() instanceof topBarVisible) {
            ((topBarVisible) getIView().getActivity()).showBar();
        }
        getIView().getFragmentManager().popBackStack();
    }
}
