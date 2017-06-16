package somebody_z.me.zuimusic.mvp.model;

import android.content.Context;

import java.util.List;

import somebody_z.me.zuimusic.db.AlbumDetailManager;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/13.
 */
public class MyAlbumModel implements IModel {

    public List<AlbumDetailBean.AlbumInfoBean> getMyAlbum(Context context) {
        List<AlbumDetailBean.AlbumInfoBean> myAlbumList = AlbumDetailManager.getInstance().getAlbumList(context);
        return myAlbumList;
    }

    public void deleteMyAlbum(AlbumDetailBean.AlbumInfoBean albumInfoBean, Context context) {
        AlbumDetailManager.getInstance().deleteAlbum(albumInfoBean, context);
    }

}
