package somebody_z.me.zuimusic.mvp.base;

import android.support.v4.app.Fragment;

import somebody_z.me.zuimusic.mvp.base.impl.FragmentBackHandler;

/**
 * Created by Huanxing Zeng on 2017/2/3.
 * email : zenghuanxing123@163.com
 */
public class BackHandledFragment extends Fragment implements FragmentBackHandler {
    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }
}
