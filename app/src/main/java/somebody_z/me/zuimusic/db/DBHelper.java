package somebody_z.me.zuimusic.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.io.IOException;

import somebody_z.me.zuimusic.utils.LogUtil;

/**
 * Created by Administrator on 2016/10/28.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "song_sheet_db";

    private static final int VERSION = 4;

    public static String TABLE_NAME = null;

    public String table_sql = null;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    /**
     * 创建表的操作
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MySongSheetTable.table_sql);
        db.execSQL(CollectSongSheetTable.table_sql);
        db.execSQL(CollectAlbumTable.table_sql);
        db.execSQL(LocalMusicTable.table_sql);
        db.execSQL(RecentlyPlayTable.table_sql);
        db.execSQL(UserTable.table_sql);
    }

    public void createTable(String tableName) {
        TABLE_NAME = tableName;

        SQLiteDatabase db = getWritableDatabase();

        if (!tabIsExist(tableName)) {
            new SongSheetDetailTable();
            db.execSQL(table_sql);
        }

    }

    /**
     * 判断某张表是否存在
     *
     * @param tabName 表名
     * @return
     */
    public boolean tabIsExist(String tabName) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();//此this是继承SQLiteOpenHelper类得到的
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

    /**
     * 数据库更新的操作
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 我创建的歌单 表
     */
    public static class MySongSheetTable implements BaseColumns {
        public static final String TABLE_NAME = "my_song_sheet_table";

        public static final String COLUMNS_SONG_SHEET_NAME = "song_sheet_name";

        public static final String COLUMNS_SONG_SHEET_TAG = "song_sheet_tag";

        public static final String COLUMNS_SONG_SHEET_DESC = "song_sheet_desc";

        public static final String COLUMNS_SONG_SHEET_PIC = "song_sheet_pic";

        public static String table_sql = null;

        static {
            //创建表的sql语句
            StringBuffer buffer = new StringBuffer();
            buffer.append("create table " + TABLE_NAME + " (");
            buffer.append(_ID + " integer primary key autoincrement,");
            buffer.append(COLUMNS_SONG_SHEET_NAME + " varchar(50),");
            buffer.append(COLUMNS_SONG_SHEET_TAG + " varchar(50),");
            buffer.append(COLUMNS_SONG_SHEET_DESC + " varchar(50),");
            buffer.append(COLUMNS_SONG_SHEET_PIC + " varchar(50))");

            table_sql = buffer.toString();
        }
    }

    /**
     * 我收藏的歌单 表
     */
    public static class CollectSongSheetTable implements BaseColumns {
        public static final String TABLE_NAME = "collect_song_sheet_table";

        public static final String COLUMNS_COLLECT_SONG_SHEET_LIST_ID = "song_sheet_list_id";

        public static final String COLUMNS_COLLECT_SONG_SHEET_PIC = "song_sheet_pic";

        public static final String COLUMNS_COLLECT_SONG_SHEET_TITLE = "song_sheet_title";

        public static final String COLUMNS_COLLECT_SONG_SHEET_TAG = "song_sheet_tag";

        public static String table_sql = null;

        static {
            //创建表的sql语句
            StringBuffer buffer = new StringBuffer();
            buffer.append("create table " + TABLE_NAME + " (");
            buffer.append(_ID + " integer primary key autoincrement,");
            buffer.append(COLUMNS_COLLECT_SONG_SHEET_LIST_ID + " varchar(50),");
            buffer.append(COLUMNS_COLLECT_SONG_SHEET_TITLE + " varchar(30),");
            buffer.append(COLUMNS_COLLECT_SONG_SHEET_TAG + " varchar(200),");
            buffer.append(COLUMNS_COLLECT_SONG_SHEET_PIC + " varchar(1000))");

            table_sql = buffer.toString();
        }
    }

    /**
     * 我收藏的唱片 表
     */
    public static class CollectAlbumTable implements BaseColumns {
        public static final String TABLE_NAME = "collect_album_table";

        public static final String COLUMNS_COLLECT_ALBUM_ALBUM_ID = "album_album_id";

        public static final String COLUMNS_COLLECT_ALBUM_PIC = "album_pic";

        public static final String COLUMNS_COLLECT_ALBUM_TITLE = "album_title";

        public static final String COLUMNS_COLLECT_ALBUM_AUTHOR = "album_author";

        public static String table_sql = null;

        static {
            //创建表的sql语句
            StringBuffer buffer = new StringBuffer();
            buffer.append("create table " + TABLE_NAME + " (");
            buffer.append(_ID + " integer primary key autoincrement,");
            buffer.append(COLUMNS_COLLECT_ALBUM_ALBUM_ID + " varchar(50),");
            buffer.append(COLUMNS_COLLECT_ALBUM_TITLE + " varchar(30),");
            buffer.append(COLUMNS_COLLECT_ALBUM_AUTHOR + " varchar(100),");
            buffer.append(COLUMNS_COLLECT_ALBUM_PIC + " varchar(1000))");

            table_sql = buffer.toString();
        }
    }

    /**
     * 动态生成的歌单详情表
     */
    public class SongSheetDetailTable implements BaseColumns {

        public static final String COLUMNS_SONG_SHEET_DETAIL_TITLE = "song_sheet_detail_title";

        public static final String COLUMNS_SONG_SHEET_DETAIL_SONG_ID = "song_sheet_detail_song_id";

        public static final String COLUMNS_SONG_SHEET_DETAIL_AUTHOR = "song_sheet_detail_author";

        public static final String COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE = "song_sheet_detail_album_title";

        public static final String COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID = "song_sheet_detail_album_id";

        public static final String COLUMNS_SONG_SHEET_DETAIL_LOACLURL = "song_sheet_detail_localurl";

        public static final String COLUMNS_SONG_SHEET_DETAIL_HAS_MV = "song_sheet_detail_has_mv";

        public static final String COLUMNS_SONG_SHEET_DETAIL_PIC_URL = "song_sheet_detail_pic_url";

        public static final String COLUMNS_SONG_SHEET_DETAIL_LRC_URL = "song_sheet_detail_lrc_url";

        {
            //创建表的sql语句
            StringBuffer buffer = new StringBuffer();
            LogUtil.e(TABLE_NAME);
            if (table_sql != null) {
                LogUtil.e(table_sql);
            }
            buffer.append("create table " + TABLE_NAME + " (");
            buffer.append(_ID + " integer primary key autoincrement,");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_TITLE + " varchar(100),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_SONG_ID + " varchar(30),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_AUTHOR + " varchar(100),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE + " varchar(100),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID + " varchar(30),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_LOACLURL + " varchar(1000),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_HAS_MV + " varchar(30),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_PIC_URL + " varchar(1000),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_LRC_URL + " varchar(1000))");

            table_sql = buffer.toString();
        }

    }

    /**
     * 本地音乐 表
     */
    public static class LocalMusicTable implements BaseColumns {
        public static final String TABLE_NAME = "local_music_table";

        public static final String COLUMNS_LOCAL_MUSIC_TITLE = "local_music_title";

        public static final String COLUMNS_LOCAL_MUSIC_SONG_ID = "local_music_song_id";

        public static final String COLUMNS_LOCAL_MUSIC_AUTHOR = "local_music_author";

        public static final String COLUMNS_LOCAL_MUSIC_ALBUM_TITLE = "local_music_album_title";

        public static final String COLUMNS_LOCAL_MUSIC_ALBUM_ID = "local_music_album_id";

        public static final String COLUMNS_LOCAL_MUSIC_LOCAL_URL = "local_music_local_url";

        public static final String COLUMNS_LOCAL_MUSIC_HAS_MV = "local_music_has_mv";

        public static final String COLUMNS_LOCAL_MUSIC_DURATION = "local_music_duration";

        public static final String COLUMNS_LOCAL_MUSIC_SIZE = "local_music_size";

        public static String table_sql = null;

        static {
            //创建表的sql语句
            StringBuffer buffer = new StringBuffer();
            buffer.append("create table " + TABLE_NAME + " (");
            buffer.append(_ID + " integer primary key autoincrement,");
            buffer.append(COLUMNS_LOCAL_MUSIC_TITLE + " varchar(100),");
            buffer.append(COLUMNS_LOCAL_MUSIC_SONG_ID + " varchar(30),");
            buffer.append(COLUMNS_LOCAL_MUSIC_AUTHOR + " varchar(100),");
            buffer.append(COLUMNS_LOCAL_MUSIC_ALBUM_TITLE + " varchar(100),");
            buffer.append(COLUMNS_LOCAL_MUSIC_ALBUM_ID + " varchar(50),");
            buffer.append(COLUMNS_LOCAL_MUSIC_LOCAL_URL + " varchar(1000),");
            buffer.append(COLUMNS_LOCAL_MUSIC_HAS_MV + " varchar(30),");
            buffer.append(COLUMNS_LOCAL_MUSIC_DURATION + " varchar(50),");
            buffer.append(COLUMNS_LOCAL_MUSIC_SIZE + " varchar(50))");

            table_sql = buffer.toString();
        }
    }

    /**
     * 最近播放 表
     */
    public static class RecentlyPlayTable implements BaseColumns {
        public static final String TABLE_NAME = "my_recently_play_table";

        public static final String COLUMNS_SONG_SHEET_DETAIL_TITLE = "song_sheet_detail_title";

        public static final String COLUMNS_SONG_SHEET_DETAIL_SONG_ID = "song_sheet_detail_song_id";

        public static final String COLUMNS_SONG_SHEET_DETAIL_AUTHOR = "song_sheet_detail_author";

        public static final String COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE = "song_sheet_detail_album_title";

        public static final String COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID = "song_sheet_detail_album_id";

        public static final String COLUMNS_SONG_SHEET_DETAIL_LOACLURL = "song_sheet_detail_localurl";

        public static final String COLUMNS_SONG_SHEET_DETAIL_HAS_MV = "song_sheet_detail_has_mv";

        public static final String COLUMNS_SONG_SHEET_DETAIL_PIC_URL = "song_sheet_detail_pic_url";

        public static final String COLUMNS_SONG_SHEET_DETAIL_LRC_URL = "song_sheet_detail_lrc_url";

        public static String table_sql = null;

        static {
            //创建表的sql语句
            StringBuffer buffer = new StringBuffer();
            buffer.append("create table " + TABLE_NAME + " (");
            buffer.append(_ID + " integer primary key autoincrement,");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_TITLE + " varchar(100),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_SONG_ID + " varchar(30),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_AUTHOR + " varchar(100),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_ALBUM_TITLE + " varchar(100),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_ALBUM_ID + " varchar(30),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_LOACLURL + " varchar(1000),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_HAS_MV + " varchar(30),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_PIC_URL + " varchar(1000),");
            buffer.append(COLUMNS_SONG_SHEET_DETAIL_LRC_URL + " varchar(1000))");

            table_sql = buffer.toString();
        }

    }

    /**
     * 最近播放 表
     */
    public static class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "user_table";

        public static final String COLUMNS_USER_NAME = "user_name";

        public static final String COLUMNS_USER_PWD = "user_pwd";

        public static String table_sql = null;

        static {
            //创建表的sql语句
            StringBuffer buffer = new StringBuffer();
            buffer.append("create table " + TABLE_NAME + " (");
            buffer.append(_ID + " integer primary key autoincrement,");
            buffer.append(COLUMNS_USER_NAME + " varchar(1000),");
            buffer.append(COLUMNS_USER_PWD + " varchar(1000))");

            table_sql = buffer.toString();
        }

    }
}
