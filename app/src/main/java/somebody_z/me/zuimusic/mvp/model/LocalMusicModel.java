package somebody_z.me.zuimusic.mvp.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import somebody_z.me.zuimusic.db.LocalMusicManager;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.utils.LogUtil;

import android.provider.MediaStore.Audio.Media;

/**
 * Created by Huanxing Zeng on 2017/2/17.
 * email : zenghuanxing123@163.com
 */
public class LocalMusicModel implements IModel {

    private boolean isRunning = true;

    //subject 发送最近的一个
    private BehaviorSubject<String> scanResultSubject = BehaviorSubject.create();

    public void scanLocalMusic(final Context context, final ScanLocalMusicLsitener scanLocalMusicLsitener) {

        //压力太大丢弃后面的部分 背压
        scanResultSubject.onBackpressureDrop().observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                scanLocalMusicLsitener.scanNext(s);
            }
        });

        Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                    subscriber.onNext(cursor);
                    subscriber.onCompleted();
                    cursor.close();
                }
            }
        }).map(new Func1<Cursor, List<ContentBean>>() {
            @Override
            public List<ContentBean> call(Cursor cursor) {
                List<ContentBean> songs = new ArrayList<ContentBean>();
                if (null != cursor && cursor.getCount() > 0) {
                    // 扫描歌曲
                    while (isRunning && cursor.moveToNext()) {
                        int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
                        int albumCol = cursor.getColumnIndex(Media.ALBUM);
                        int albumid = cursor.getColumnIndex(Media.ALBUM_ID);
                        int idCol = cursor.getColumnIndex(Media._ID);
                        int durationCol = cursor.getColumnIndex(Media.DURATION);
                        int sizeCol = cursor.getColumnIndex(Media.SIZE);
                        int artistCol = cursor.getColumnIndex(Media.ARTIST);
                        int urlCol = cursor.getColumnIndex(Media.DATA);

                        String title = cursor.getString(displayNameCol);
                        String album = cursor.getString(albumCol);
                        int album_id = cursor.getInt(albumid);
                        long id = cursor.getLong(idCol);
                        int duration = cursor.getInt(durationCol);
                        long size = cursor.getLong(sizeCol);
                        String artist = cursor.getString(artistCol);
                        String url = cursor.getString(urlCol);

                        if ("<unknown>".equals(artist)) {
                            artist = "未知艺术家";
                        }

                        title = title.replace(" - ", "-");

                        if (title.contains("-")) {
                            int index = title.indexOf("-");
                            artist = title.substring(0, index);
                            title = title.substring(index + 1, title.length() - 4);
                        } else {
                            title = title.substring(0, title.length() - 4);
                        }

                        double random = Math.random();

                        int has_mv;

                        if (random > 0.5) {
                            has_mv = 0;
                        } else {
                            has_mv = 1;
                        }

                        ContentBean song = new ContentBean(title, String.valueOf(id), artist, album,
                                String.valueOf(album_id), url, has_mv, duration, size);

                        //插入db信息
                        if (searchLocalSong(song, context) == null) {
                            songs.add(song);
                            addLocalSong(song, context);
                        }
                        //通知界面显示
                        scanResultSubject.onNext(song.getLocalUrl());
                    }
                }
                //注意关闭游标
                cursor.close();
                return songs;
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<ContentBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                try {
                    scanLocalMusicLsitener.scanError(0);
                } catch (Exception exception) {

                }

            }

            @Override
            public void onNext(List<ContentBean> songs) {
                scanLocalMusicLsitener.scanComplete(songs.size());

            }
        });
    }

    public void addLocalSong(ContentBean song, Context context) {
        LocalMusicManager.getInstance().addLocalSong(song, context);
    }

    public ContentBean searchLocalSong(ContentBean song, Context context) {
        return LocalMusicManager.getInstance().searchLocalMusic(song.getTitle(), context);
    }

    public int getLocalSongCount(Context context) {
        return LocalMusicManager.getInstance().getLocalMusicList(context).size();
    }

    public interface ScanLocalMusicLsitener {
        void scanComplete(int size);

        void scanError(int size);

        void scanNext(String url);

    }
}
