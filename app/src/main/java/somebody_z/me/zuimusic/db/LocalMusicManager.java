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
 * Created by Huanxing Zeng on 2017/2/23.
 * email : zenghuanxing123@163.com
 */
public class LocalMusicManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    private static LocalMusicManager manager;
    private static Context mContext;

    public static LocalMusicManager getInstance() {
        if (manager == null) {
            synchronized (LocalMusicManager.class) {
                if (manager == null) {
                    manager = new LocalMusicManager(mContext);
                }
            }
        }
        return manager;
    }

    public LocalMusicManager(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加本地音乐
     *
     * @param song
     */
    public void addLocalSong(ContentBean song, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.insert(DBHelper.LocalMusicTable.TABLE_NAME, null,
                getContentValuesByLocalMusic(song));
        db.close();
    }

    /**
     * 删除本地音乐
     *
     * @param song
     */
    public void deleteLocalMusic(ContentBean song, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();// 可写的数据操作
        db.delete(DBHelper.LocalMusicTable.TABLE_NAME, DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_TITLE + " = ?",
                new String[]{song.getTitle()});
        db.close();
    }

    /**
     * 更新操作
     *
     * @param song
     */
    public void updateLocalMusic(ContentBean song, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.update(DBHelper.LocalMusicTable.TABLE_NAME,
                getContentValuesByLocalMusic(song), DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_TITLE + " = ?",
                new String[]{song.getTitle()});
        db.close();
    }

    /**
     * 搜索本地音乐
     *
     * @param songTitle
     * @param context
     */
    public ContentBean searchLocalMusic(String songTitle, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.LocalMusicTable.TABLE_NAME + " where " +
                        DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_TITLE + " = ?",
                new String[]{songTitle});
        ContentBean song = null;
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String title = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_TITLE));

                String song_id = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_SONG_ID));

                String author = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_AUTHOR));

                String album_title = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_ALBUM_TITLE));

                String album_id = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_ALBUM_ID));

                String local_url = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_LOCAL_URL));

                String has_mv = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_HAS_MV));

                int duration = cursor.getInt(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_DURATION));

                long size = cursor.getLong(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_SIZE));

                song = new ContentBean(title, song_id, author, album_title, album_id, local_url,
                        Integer.valueOf(has_mv), duration, size);

            }
        } finally {
            cursor.close();
        }

        db.close();
        return song;
    }

    /**
     * 查询所有的本地歌曲
     *
     * @return
     */
    public List<ContentBean> getLocalMusicList(Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                + DBHelper.LocalMusicTable.TABLE_NAME, null);
        List<ContentBean> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String title = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_TITLE));

                String song_id = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_SONG_ID));

                String author = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_AUTHOR));

                String album_title = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_ALBUM_TITLE));

                String album_id = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_ALBUM_ID));

                String local_url = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_LOCAL_URL));

                String has_mv = cursor.getString(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_HAS_MV));

                int duration = cursor.getInt(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_DURATION));

                long size = cursor.getLong(cursor
                        .getColumnIndex(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_SIZE));

                ContentBean song = new ContentBean(title, song_id, author, album_title, album_id,
                        local_url, Integer.valueOf(has_mv), duration, size);

                list.add(song);
            }
        } finally {
            cursor.close();
        }

        db.close();
        return list;
    }

    private ContentValues getContentValuesByLocalMusic(ContentBean song) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_TITLE, song.getTitle());
        values.put(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_SONG_ID, song.getSong_id());
        values.put(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_AUTHOR, song.getAuthor());
        values.put(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_ALBUM_TITLE, song.getAlbum_title());
        values.put(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_ALBUM_ID, song.getAlbum_id());
        values.put(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_LOCAL_URL, song.getLocalUrl());
        values.put(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_HAS_MV, song.getHas_mv());
        values.put(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_DURATION, song.getDuration());
        values.put(DBHelper.LocalMusicTable.COLUMNS_LOCAL_MUSIC_SIZE, song.getSize());

        return values;
    }

}
