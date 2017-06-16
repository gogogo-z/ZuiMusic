package somebody_z.me.zuimusic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;

/**
 * Created by Huanxing Zeng on 2017/1/18.
 * email : zenghuanxing123@163.com
 */
public class RecentlyPlayManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    private static RecentlyPlayManager manager;
    private static Context mContext;

    public static RecentlyPlayManager getInstance() {
        if (manager == null) {
            synchronized (RecentlyPlayManager.class) {
                if (manager == null) {
                    manager = new RecentlyPlayManager(mContext);
                }
            }
        }
        return manager;
    }

    public RecentlyPlayManager(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加一条最近播放
     */
    public void addToRecentlyPlay(ContentBean songSheetDetail, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.insert(DBHelper.RecentlyPlayTable.TABLE_NAME, null,
                getContentValuesByRecentlyPlay(songSheetDetail));
        db.close();
    }

    /**
     * 删除一条最近播放
     *
     * @param songSheetDetail
     */
    public void deleteRecentlyPlay(ContentBean songSheetDetail, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();// 可写的数据操作
        db.delete(DBHelper.RecentlyPlayTable.TABLE_NAME, DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_TITLE + " = ?",
                new String[]{songSheetDetail.getTitle()});
        db.close();
    }

    /**
     * 更新操作
     *
     * @param songSheetDetail
     */
    public void updateRecentlyPlay(ContentBean songSheetDetail, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.update(DBHelper.RecentlyPlayTable.TABLE_NAME,
                getContentValuesByRecentlyPlay(songSheetDetail), DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_TITLE + " = ?",
                new String[]{songSheetDetail.getTitle()});
        db.close();
    }

    /**
     * 清空表数据
     *
     * @param context
     */
    public void clearRecentlyPlay(Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.execSQL("DELETE FROM " + DBHelper.RecentlyPlayTable.TABLE_NAME);
        db.close();
    }

    /**
     * 搜索最近播放
     *
     * @param songSheetDetailName
     * @param context
     */
    public ContentBean searchRecentlyPlay(String songSheetDetailName, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.RecentlyPlayTable.TABLE_NAME + " where " +
                        DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_TITLE + " = ?",
                new String[]{songSheetDetailName});
        ContentBean songSheetDetail = null;
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String songSheetDetailTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_TITLE));

                String songSheetDetailSongID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_SONG_ID));

                String songSheetDetailAuthor = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_AUTHOR));

                String songSheetDetailAlbumTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE));

                String songSheetDetailAlbumID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID));

                String songSheetDetailLocalURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_LOACLURL));

                int songSheetDetailHasMV = cursor.getInt(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_HAS_MV));

                String songSheetDetailPicURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_PIC_URL));

                String songSheetDetailLrcURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_LRC_URL));

                songSheetDetail = new ContentBean(songSheetDetailTitle, songSheetDetailSongID,
                        songSheetDetailAuthor, songSheetDetailAlbumTitle, songSheetDetailAlbumID, songSheetDetailLocalURL,
                        songSheetDetailHasMV, songSheetDetailPicURL, songSheetDetailLrcURL);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return songSheetDetail;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<ContentBean> getRecentlyPlayList(Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "
                + DBHelper.RecentlyPlayTable.TABLE_NAME, null);
        List<ContentBean> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String songSheetDetailTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_TITLE));

                String songSheetDetailSongID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_SONG_ID));

                String songSheetDetailAuthor = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_AUTHOR));

                String songSheetDetailAlbumTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE));

                String songSheetDetailAlbumID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID));

                String songSheetDetailLocalURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_LOACLURL));

                int songSheetDetailHasMV = cursor.getInt(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_HAS_MV));

                String songSheetDetailPicURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_PIC_URL));

                String songSheetDetailLrcURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_LRC_URL));

                ContentBean songSheetDetail = new ContentBean(songSheetDetailTitle, songSheetDetailSongID,
                        songSheetDetailAuthor, songSheetDetailAlbumTitle, songSheetDetailAlbumID, songSheetDetailLocalURL,
                        songSheetDetailHasMV, songSheetDetailPicURL, songSheetDetailLrcURL);

                list.add(songSheetDetail);
            }
        } finally {
            cursor.close();
        }
        db.close();

        return list;
    }

    private ContentValues getContentValuesByRecentlyPlay(ContentBean songSheetDetail) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_TITLE, songSheetDetail.getTitle());
        values.put(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_SONG_ID, songSheetDetail.getSong_id());
        values.put(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_AUTHOR, songSheetDetail.getAuthor());
        values.put(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE, songSheetDetail.getAlbum_title());
        values.put(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID, songSheetDetail.getAlbum_id());
        values.put(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_LOACLURL, songSheetDetail.getLocalUrl());
        values.put(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_HAS_MV, songSheetDetail.getHas_mv());
        values.put(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_PIC_URL, songSheetDetail.getPic_url());
        values.put(DBHelper.RecentlyPlayTable.COLUMNS_SONG_SHEET_DETAIL_LRC_URL, songSheetDetail.getLrc_url());

        return values;
    }
}
