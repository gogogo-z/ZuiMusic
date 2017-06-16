package somebody_z.me.zuimusic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;

/**
 * Created by Huanxing Zeng on 2017/1/18.
 * email : zenghuanxing123@163.com
 */
public class SongSheetDetailManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    private static SongSheetDetailManager manager;
    private static Context mContext;

    public static SongSheetDetailManager getInstance() {
        if (manager == null) {
            synchronized (SongSheetDetailManager.class) {
                if (manager == null) {
                    manager = new SongSheetDetailManager(mContext);
                }
            }
        }
        return manager;
    }

    public SongSheetDetailManager(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加一条歌单项
     */
    public void addSongSheetDetail(ContentBean songSheetDetail, Context context, String songSheetName) {
        DBHelper helper1 = new DBHelper(context);
        helper1.createTable(songSheetName);
        db = helper1.getWritableDatabase();
        db.insert(songSheetName, null,
                getContentValuesBySongSheetDetail(songSheetDetail));
        db.close();
    }

    /**
     * 删除一条歌单项
     *
     * @param songSheetDetail
     */
    public void deleteSongSheetDetail(ContentBean songSheetDetail, Context context, String songSheetName) {
        DBHelper helper1 = new DBHelper(context);
        helper1.createTable(songSheetName);
        db = helper1.getWritableDatabase();// 可写的数据操作
        db.delete(songSheetName, DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_TITLE + " = ?",
                new String[]{songSheetDetail.getTitle()});
        db.close();
    }

    /**
     * 更新操作
     *
     * @param songSheetDetail
     */
    public void updateSongSheetDetail(ContentBean songSheetDetail, Context context, String songSheetName) {
        DBHelper helper1 = new DBHelper(context);
        helper1.createTable(songSheetName);
        db = helper1.getWritableDatabase();
        db.update(songSheetName,
                getContentValuesBySongSheetDetail(songSheetDetail), DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_TITLE + " = ?",
                new String[]{songSheetDetail.getTitle()});
        db.close();
    }

    /**
     * 搜索歌单项
     *
     * @param songSheetDetailName
     * @param context
     */
    public ContentBean searchSongSheetDetail(String songSheetName, String songSheetDetailName, Context context) {
        DBHelper helper1 = new DBHelper(context);
        helper1.createTable(songSheetName);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + songSheetName + " where " +
                        DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_TITLE + " = ?",
                new String[]{songSheetDetailName});
        ContentBean songSheetDetail = null;
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String songSheetDetailTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_TITLE));

                String songSheetDetailSongID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_SONG_ID));

                String songSheetDetailAuthor = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_AUTHOR));

                String songSheetDetailAlbumTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE));

                String songSheetDetailAlbumID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID));

                String songSheetDetailLocalURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_LOACLURL));

                int songSheetDetailHasMV = cursor.getInt(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_HAS_MV));

                String songSheetDetailPicURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_PIC_URL));

                String songSheetDetailLrcURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_LRC_URL));

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
     * 查询所有的历史
     *
     * @return
     */
    public List<ContentBean> getSongSheetDetailList(Context context, String songSheetName) {
        DBHelper helper1 = new DBHelper(context);
        helper1.createTable(songSheetName);
        db = helper1.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "
                + songSheetName, null);
        List<ContentBean> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String songSheetDetailTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_TITLE));

                String songSheetDetailSongID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_SONG_ID));

                String songSheetDetailAuthor = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_AUTHOR));

                String songSheetDetailAlbumTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE));

                String songSheetDetailAlbumID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID));

                String songSheetDetailLocalURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_LOACLURL));

                int songSheetDetailHasMV = cursor.getInt(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_HAS_MV));

                String songSheetDetailPicURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_PIC_URL));

                String songSheetDetailLrcURL = cursor.getString(cursor
                        .getColumnIndex(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_LRC_URL));

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

    private ContentValues getContentValuesBySongSheetDetail(ContentBean songSheetDetail) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_TITLE, songSheetDetail.getTitle());
        values.put(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_SONG_ID, songSheetDetail.getSong_id());
        values.put(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_AUTHOR, songSheetDetail.getAuthor());
        values.put(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE, songSheetDetail.getAlbum_title());
        values.put(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID, songSheetDetail.getAlbum_id());
        values.put(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_LOACLURL, songSheetDetail.getLocalUrl());
        values.put(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_HAS_MV, songSheetDetail.getHas_mv());
        values.put(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_PIC_URL, songSheetDetail.getPic_url());
        values.put(DBHelper.SongSheetDetailTable.COLUMNS_SONG_SHEET_DETAIL_LRC_URL, songSheetDetail.getLrc_url());

        return values;
    }
}
