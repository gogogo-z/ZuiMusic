package somebody_z.me.zuimusic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;

/**
 * Created by Huanxing Zeng on 2017/2/13.
 * email : zenghuanxing123@163.com
 */
public class AlbumDetailManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    private static AlbumDetailManager manager;
    private static Context mContext;

    public static AlbumDetailManager getInstance() {
        if (manager == null) {
            synchronized (AlbumDetailManager.class) {
                if (manager == null) {
                    manager = new AlbumDetailManager(mContext);
                }
            }
        }
        return manager;
    }

    public AlbumDetailManager(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加唱片
     *
     * @param albumInfoBean
     */
    public void addAlbum(AlbumDetailBean.AlbumInfoBean albumInfoBean, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.insert(DBHelper.CollectAlbumTable.TABLE_NAME, null,
                getContentValuesBySongSheet(albumInfoBean));
        db.close();
    }

    /**
     * 删除歌单
     *
     * @param albumInfoBean
     */
    public void deleteAlbum(AlbumDetailBean.AlbumInfoBean albumInfoBean, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();// 可写的数据操作
        db.delete(DBHelper.CollectAlbumTable.TABLE_NAME, DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_ALBUM_ID + " = ?",
                new String[]{albumInfoBean.getAlbum_id()});
        db.close();
    }

    /**
     * 更新操作
     *
     * @param albumInfoBean
     */
    public void updateAlbum(AlbumDetailBean.AlbumInfoBean albumInfoBean, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getWritableDatabase();
        db.update(DBHelper.CollectAlbumTable.TABLE_NAME,
                getContentValuesBySongSheet(albumInfoBean), DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_ALBUM_ID + " = ?",
                new String[]{albumInfoBean.getAlbum_id()});
        db.close();
    }

    /**
     * 搜索歌单
     *
     * @param albumID
     * @param context
     */
    public AlbumDetailBean.AlbumInfoBean searchAlbum(String albumID, Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBHelper.CollectAlbumTable.TABLE_NAME + " where " +
                        DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_ALBUM_ID + " = ?",
                new String[]{albumID});
        AlbumDetailBean.AlbumInfoBean albumInfoBean = null;
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String albumTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_TITLE));

                String albumAuthor = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_AUTHOR));

                String albumPic = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_PIC));

                String albumAlbumID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_ALBUM_ID));

                albumInfoBean = new AlbumDetailBean.AlbumInfoBean(albumAlbumID, albumAuthor, albumTitle, albumPic);

            }
        } finally {
            cursor.close();
        }
        db.close();
        return albumInfoBean;
    }

    /**
     * 查询所有的历史
     *
     * @return
     */
    public List<AlbumDetailBean.AlbumInfoBean> getAlbumList(Context context) {
        DBHelper helper1 = new DBHelper(context);
        db = helper1.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "
                + DBHelper.CollectAlbumTable.TABLE_NAME, null);
        List<AlbumDetailBean.AlbumInfoBean> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                // 获取字段名所对应的值
                String albumTitle = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_TITLE));

                String albumAuthor = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_AUTHOR));

                String albumPic = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_PIC));

                String albumAlbumID = cursor.getString(cursor
                        .getColumnIndex(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_ALBUM_ID));

                AlbumDetailBean.AlbumInfoBean albumInfoBean = new AlbumDetailBean.AlbumInfoBean(albumAlbumID, albumAuthor, albumTitle, albumPic);

                list.add(albumInfoBean);
            }
        } finally {
            cursor.close();
        }

        db.close();
        return list;
    }

    private ContentValues getContentValuesBySongSheet(AlbumDetailBean.AlbumInfoBean albumInfoBean) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_TITLE, albumInfoBean.getTitle());
        values.put(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_AUTHOR, albumInfoBean.getAuthor());
        values.put(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_ALBUM_ID, albumInfoBean.getAlbum_id());
        values.put(DBHelper.CollectAlbumTable.COLUMNS_COLLECT_ALBUM_PIC, albumInfoBean.getPic_big());

        return values;
    }

}
