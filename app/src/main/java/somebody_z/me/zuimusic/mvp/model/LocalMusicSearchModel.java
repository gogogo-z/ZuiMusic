package somebody_z.me.zuimusic.mvp.model;

import android.content.Context;

import java.util.List;

import somebody_z.me.zuimusic.db.LocalMusicManager;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;

/**
 * Created by Huanxing Zeng on 2017/2/28.
 * email : zenghuanxing123@163.com
 */
public class LocalMusicSearchModel implements IModel {

    public List<ContentBean> getLocalSong(Context context) {
        return LocalMusicManager.getInstance().getLocalMusicList(context);
    }
}
