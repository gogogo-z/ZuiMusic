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
public class CollectSongSheetManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    private static CollectSongSheetManager manager;
    private static Context mContext;

    public static CollectSongSheetManager getInstance() {
        if (manager == null) {
            synchronized (CollectSongSheetManager.class) {
                if (manager == null) {
                    manager = new CollectSongSheetManager(mContext);
                }
            }
        }
        return manager;
    }

    public CollectSongSheetManager(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加收藏歌单
     *
     * @param collectSongSheet
     */
    public void addCollectSongSheet(CollectSongSheet collectSongSheet, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.insert(DBHelper.CollectSongSheetTable.TABLE_NAME, null,
                getContentValuesByCollectSongSheet(collectSongSheet));
        db.close();
    }

    /**
     * 删除收藏歌单
     *
     * @param collectSongSheet
     */
    public void deleteCollectSongSheet(CollectSongSheet collectSongSheet, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();// 可写的数据操作
        db.delete(DBHelper.CollectSongSheetTable.TABLE_NAME, DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_TITLE + " = ?",
                new String[]{collectSongSheet.getTitle()});
        db.close();
    }

    /**
     * 更新操作
     *
     * @param collectSongSheet
     */
    public void updateColloctSongSheet(CollectSongSheet collectSongSheet, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.update(DBHelper.CollectSongSheetTable.TABLE_NAME,
                getContentValuesByCollectSongSheet(collectSongSheet), DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_TITLE + " = ?",
                new String[]{collectSongSheet.getTitle()});
        db.close();
    }

    /**
     * 搜索收藏歌单
     *
     * @param songSheetName
     * @param context
     */
    public CollectSongSheet searchCollectSongSheet(String songSheetName, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.CollectSongSheetTable.TABLE_NAME + " where " +
                        DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_TITLE + " = ?",
                new String[]{songSheetName});
        CollectSongSheet collectSongSheet = null;
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String songSheetListID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_LIST_ID));

                String songSheetPic = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_PIC));

                String songSheetTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_TITLE));

                String songSheetTag = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_TAG));


                collectSongSheet = new CollectSongSheet(songSheetListID, songSheetPic, songSheetTitle, songSheetTag);

            }
        } finally {
            cursor.close();
        }
        db.close();
        return collectSongSheet;
    }

    /**
     * 查询所有的历史
     *
     * @return
     */
    public List<CollectSongSheet> getCollectSongSheetList(Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                + DBHelper.CollectSongSheetTable.TABLE_NAME, null);
        List<CollectSongSheet> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String songSheetListID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_LIST_ID));

                String songSheetPic = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_PIC));

                String songSheetTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_TITLE));

                String songSheetTag = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_TAG));


                CollectSongSheet collectSongSheet = new CollectSongSheet(songSheetListID, songSheetPic, songSheetTitle, songSheetTag);

                list.add(collectSongSheet);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return list;
    }

    private ContentValues getContentValuesByCollectSongSheet(CollectSongSheet collectSongSheet) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_LIST_ID, collectSongSheet.getListid());
        values.put(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_TITLE, collectSongSheet.getTitle());
        values.put(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_TAG, collectSongSheet.getTag());
        values.put(DBHelper.CollectSongSheetTable.COLUMNS_COLLECT_SONG_SHEET_PIC, collectSongSheet.getPic());

        return values;
    }

}