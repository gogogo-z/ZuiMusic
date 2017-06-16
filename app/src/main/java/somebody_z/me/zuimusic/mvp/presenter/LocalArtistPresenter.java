package somebody_z.me.zuimusic.mvp.presenter;

import somebody_z.me.zuimusic.mvp.Contract.LocalArtistContract;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;
import somebody_z.me.zuimusic.mvp.model.LocalArtistModel;
import somebody_z.me.zuimusic.mvp.view.fragment.music.second.LocalArtistFragment;

/**
 * Created by Huanxing Zeng on 2017/3/1.
 * email : zenghuanxing123@163.com
 */
public class LocalArtistPresenter extends BasePresenter<LocalArtistModel,LocalArtistFragment> implements
        LocalArtistContract.LocalArtistPresenter{
    @Override
    public LocalArtistModel loadModel() {
        return new LocalArtistModel();
    }
}
