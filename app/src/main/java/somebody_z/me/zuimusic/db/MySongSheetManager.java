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
public class MySongSheetManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    private static MySongSheetManager manager;
    private static Context mContext;

    public static MySongSheetManager getInstance() {
        if (manager == null) {
            synchronized (MySongSheetManager.class) {
                if (manager == null) {
                    manager = new MySongSheetManager(mContext);
                }
            }
        }
        return manager;
    }

    public MySongSheetManager(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加歌单
     *
     * @param mySongSheet
     */
    public void addMySongSheet(MySongSheet mySongSheet, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.insert(DBHelper.MySongSheetTable.TABLE_NAME, null,
                getContentValuesBySongSheet(mySongSheet));
        db.close();
    }

    /**
     * 删除歌单
     *
     * @param mySongSheet
     */
    public void deleteMySongSheet(MySongSheet mySongSheet, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();// 可写的数据操作
        db.delete(DBHelper.MySongSheetTable.TABLE_NAME, DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_NAME + " = ?",
                new String[]{mySongSheet.getSongSheetName()});
        db.close();
    }

    /**
     * 更新操作
     *
     * @param mySongSheet
     */
    public void updateMySongSheet(MySongSheet mySongSheet, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.update(DBHelper.MySongSheetTable.TABLE_NAME,
                getContentValuesBySongSheet(mySongSheet), DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_NAME + " = ?",
                new String[]{mySongSheet.getSongSheetName()});
        db.close();
    }

    /**
     * 搜索歌单
     *
     * @param songSheetName
     * @param context
     */
    public MySongSheet searchMySongSheet(String songSheetName, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.MySongSheetTable.TABLE_NAME + " where " +
                        DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_NAME + " = ?",
                new String[]{songSheetName});
        MySongSheet mySongSheet = null;
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String songSheetname = cursor.getString(cursor
                        .getColumnIndex(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_NAME));

                String songSheetTag = cursor.getString(cursor
                        .getColumnIndex(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_TAG));

                String songSheetDesc = cursor.getString(cursor
                        .getColumnIndex(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_DESC));

                String songSheetPic = cursor.getString(cursor
                        .getColumnIndex(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_PIC));

                mySongSheet = new MySongSheet(songSheetname, songSheetTag, songSheetDesc, songSheetPic);

            }
        } finally {
            cursor.close();
        }
        db.close();
        return mySongSheet;
    }

    /**
     * 查询所有的历史
     *
     * @return
     */
    public List<MySongSheet> getMySongSheetList(Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                + DBHelper.MySongSheetTable.TABLE_NAME, null);
        List<MySongSheet> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String songSheetName = cursor.getString(cursor
                        .getColumnIndex(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_NAME));

                String songSheetTag = cursor.getString(cursor
                        .getColumnIndex(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_TAG));

                String songSheetDesc = cursor.getString(cursor
                        .getColumnIndex(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_DESC));

                String songSheetPic = cursor.getString(cursor
                        .getColumnIndex(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_PIC));

                MySongSheet mySongSheet = new MySongSheet(songSheetName, songSheetTag, songSheetDesc, songSheetPic);

                list.add(mySongSheet);
            }
        } finally {
            cursor.close();
        }

        db.close();
        return list;
    }

    private ContentValues getContentValuesBySongSheet(MySongSheet mySongSheet) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_NAME, mySongSheet.getSongSheetName());
        values.put(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_TAG, mySongSheet.getSongSheetTag());
        values.put(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_DESC, mySongSheet.getSongSheetDesc());
        values.put(DBHelper.MySongSheetTable.COLUMNS_SONG_SHEET_PIC, mySongSheet.getSongSheetPic());

        return values;
    }

}
