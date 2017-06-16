package somebody_z.me.zuimusic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huanxing Zeng on 2017/1/18.
 * email : zenghuanxing123@163.com
 */
public class UserInfoManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    private static UserInfoManager manager;
    private static Context mContext;

    public static UserInfoManager getInstance() {
        if (manager == null) {
            synchronized (UserInfoManager.class) {
                if (manager == null) {
                    manager = new UserInfoManager(mContext);
                }
            }
        }
        return manager;
    }

    public UserInfoManager(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加用户
     */
    public void addUser(UserInfo userInfo, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.insert(DBHelper.UserTable.TABLE_NAME, null,
                getContentValuesByUser(userInfo));
        db.close();
    }

    /**
     * 删除用户
     *
     * @param userInfo
     */
    public void deleteUser(UserInfo userInfo, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();// 可写的数据操作
        db.delete(DBHelper.UserTable.TABLE_NAME, DBHelper.UserTable.COLUMNS_USER_NAME + " = ?",
                new String[]{userInfo.getUserName()});
        db.close();
    }

    /**
     * 更新操作
     *
     * @param userInfo
     */
    public void updateUser(UserInfo userInfo, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.update(DBHelper.UserTable.TABLE_NAME,
                getContentValuesByUser(userInfo), DBHelper.UserTable.COLUMNS_USER_NAME + " = ?",
                new String[]{userInfo.getUserName()});
        db.close();
    }

    /**
     * 清空表数据
     *
     * @param context
     */
    public void clearUser(Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.execSQL("DELETE FROM " + DBHelper.UserTable.TABLE_NAME);
        db.close();
    }

    /**
     * 搜索用户
     */
    public UserInfo searchUser(String userName, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.UserTable.TABLE_NAME + " where " +
                        DBHelper.UserTable.COLUMNS_USER_NAME + " = ?",
                new String[]{userName});
        UserInfo userInfo = null;
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String user_name = cursor.getString(cursor
                        .getColumnIndex(DBHelper.UserTable.COLUMNS_USER_NAME));

                String user_pwd = cursor.getString(cursor
                        .getColumnIndex(DBHelper.UserTable.COLUMNS_USER_PWD));

                userInfo = new UserInfo(user_name, user_pwd);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return userInfo;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<UserInfo> getUserList(Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "
                + DBHelper.UserTable.TABLE_NAME, null);
        List<UserInfo> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String user_name = cursor.getString(cursor
                        .getColumnIndex(DBHelper.UserTable.COLUMNS_USER_NAME));

                String user_pwd = cursor.getString(cursor
                        .getColumnIndex(DBHelper.UserTable.COLUMNS_USER_PWD));

                UserInfo userInfo = new UserInfo(user_name, user_pwd);

                list.add(userInfo);
            }
        } finally {
            cursor.close();
        }
        db.close();

        return list;
    }

    private ContentValues getContentValuesByUser(UserInfo userInfo) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.UserTable.COLUMNS_USER_NAME, userInfo.getUserName());
        values.put(DBHelper.UserTable.COLUMNS_USER_PWD, userInfo.getUserPwd());

        return values;
    }
}
