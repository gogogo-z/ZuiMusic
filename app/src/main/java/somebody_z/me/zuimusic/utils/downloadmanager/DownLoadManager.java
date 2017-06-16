package somebody_z.me.zuimusic.utils.downloadmanager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.util.ArrayMap;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import somebody_z.me.zuimusic.MyApplication;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/16.
 */
public class DownLoadManager {

    private DownloadManager downloadManager;

    private Context mContext;

    private DownLoadReceiver receiver;

    private List<ArrayMap> mapList = new ArrayList<>();

    private String fileName;

    private String fileDes = "1.1";

    private String url;

    private Long query_id;

    private boolean isDownload = false;

    public DownLoadManager() {
        // TODO Auto-generated constructor stub
    }

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final DownLoadManager INSTANCE = new DownLoadManager();
    }

    public static DownLoadManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void register(Context context) {
        this.mContext = context;
        receiver = new DownLoadReceiver();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(receiver, filter);
    }

    public void downLoad(Context context, String url, String fileName) {
        if (url == null) {
            return;
        }
        this.url = url;
        this.fileName = fileName;

        downloadManager = (DownloadManager) mContext.getSystemService(mContext.DOWNLOAD_SERVICE);

        downLoad();
    }

    public void downLoad() {

        for (ArrayMap arrayMap : mapList) {
            if (arrayMap.containsKey(fileName)) {
                query_id = (Long) arrayMap.get(fileName);
                isDownload = true;
            }

        }

        if (!isDownload) {
            //开始下载
            Uri resource = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(resource);

            //下载网络需求  手机数据流量、wifi
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

            //设置是否允许漫游网络建立请求 默认true
            request.setAllowedOverRoaming(false);

            //在默认的情况下，通过Download Manager下载的文件是不能被Media Scanner扫描到的 。
            //进而这些下载的文件（音乐、视频等）就不会在Gallery 和  Music Player这样的应用中看到。
            //为了让下载的音乐文件可以被其他应用扫描到，我们需要调用Request对象的
            request.allowScanningByMediaScanner();

            //设置请求的Mime
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            request.setMimeType(mimeTypeMap.getMimeTypeFromExtension(url));

            //在通知栏中显示
//            request.setShowRunningNotification(true);
//            request.setVisibleInDownloadsUi(true);

            //sdcard的目录下的download文件夹
            setDownloadFilePath(request);

            //设置通知类型
            setNotification(request);

            //开始下载
            long id = downloadManager.enqueue(request);
            //保存id
            ArrayMap map = new ArrayMap();
            map.put(fileName, id);

            isDownload = false;

            mapList.add(map);
        } else {
            //下载已经开始，检查状态
            queryDownloadStatus();
        }

    }

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(query_id);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    // ToastUtil.getInstance().showShortToast(mContext, "暂停下载");
                    break;
                case DownloadManager.STATUS_PENDING:

                case DownloadManager.STATUS_RUNNING:
                    //正在下载，不做任何事情
                    // ToastUtil.getInstance().showShortToast(mContext, "正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    //完成
                    isDownload = false;
                    // ToastUtil.getInstance().showShortToast(mContext, "已下载");
                    break;
                case DownloadManager.STATUS_FAILED:
                    //清除已下载的内容，重新下载
                    //  ToastUtil.getInstance().showShortToast(mContext, "重新下载");
                    downloadManager.remove(query_id);

                    for (ArrayMap arrayMap : mapList) {
                        if (arrayMap.containsValue(query_id)) {
                            mapList.remove(arrayMap);
                        }

                    }

                    break;
            }
        }
    }

    /**
     * 设置状态栏中显示Notification
     */
    void setNotification(DownloadManager.Request request) {
        //设置Notification的标题
        request.setTitle(fileName);

        //设置描述
        request.setDescription(fileDes);

        if (fileName.contains(".lrc")) {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        } else {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        //request.setNotificationVisibility( Request.VISIBILITY_VISIBLE ) ;

        //request.setNotificationVisibility( Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION ) ;

        //request.setNotificationVisibility( Request.VISIBILITY_HIDDEN ) ;
    }

    /**
     * 设置下载文件存储目录
     */
    void setDownloadFilePath(DownloadManager.Request request) {
        /**
         * 方法1:
         * 目录: Android -> data -> com.app -> files -> Download -> 微信.apk
         * 这个文件是你的应用所专用的,软件卸载后，下载的文件将随着卸载全部被删除
         */
        //request.setDestinationInExternalFilesDir( this , Environment.DIRECTORY_DOWNLOADS ,  "微信.apk" );

        /**
         * 方法2:
         * 下载的文件存放地址  SD卡 download文件夹，pp.jpg
         * 软件卸载后，下载的文件会保留
         */
        //在SD卡上创建一个文件夹
        request.setDestinationInExternalPublicDir("/zuimusic/", fileName);

        /**
         * 方法3:
         * 如果下载的文件希望被其他的应用共享
         * 特别是那些你下载下来希望被Media Scanner扫描到的文件（比如音乐文件）
         */
        //request.setDestinationInExternalPublicDir( Environment.DIRECTORY_MUSIC,  "笨小孩.mp3" );

        /**
         * 方法4
         * 文件将存放在外部存储的确实download文件内，如果无此文件夹，创建之，如果有，下面将返回false。
         * 系统有个下载文件夹，比如小米手机系统下载文件夹  SD卡--> Download文件夹
         */
        //创建目录
        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
        //设置文件存放路径
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "weixin.apk");
    }

    private class DownLoadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //下载完成，收到广播
            //发布一个事件
            EventBus.getDefault().post(new LrcBean(fileName));
            //这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
            if (query_id != null) {
                queryDownloadStatus();
            }
        }
    }

    public void unRegister() {
        if (receiver != null) {
            mContext.unregisterReceiver(receiver);
        }
    }
}