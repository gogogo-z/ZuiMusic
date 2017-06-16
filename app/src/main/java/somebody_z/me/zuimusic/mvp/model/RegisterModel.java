package somebody_z.me.zuimusic.mvp.model;

import android.content.Context;

import somebody_z.me.zuimusic.db.UserInfo;
import somebody_z.me.zuimusic.db.UserInfoManager;
import somebody_z.me.zuimusic.mvp.base.IModel;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/15.
 */
public class RegisterModel implements IModel {

    public UserInfo getUserInfo(Context context, String userName) {
        return UserInfoManager.getInstance().searchUser(userName, context);
    }

    public void addUserInfo(UserInfo userInfo, Context context) {
        UserInfoManager.getInstance().addUser(userInfo, context);
    }

}
