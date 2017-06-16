package somebody_z.me.zuimusic;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.sharesdk.framework.ShareSDK;
import somebody_z.me.zuimusic.common.CrashHandler;
import somebody_z.me.zuimusic.service.ServiceManager;

/**
 * @author HuanxingZeng
 * @version 创建时间：2016年12月5日 上午11:07:29
 */
public class MyApplication extends Application {
    private static Context context;

    private static final String TAG = "全部歌单";

    public static ServiceManager mServiceManager = null;

    private String tag;

    private RefWatcher refWatcher;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        //初始化shareSDK
        ShareSDK.initSDK(this);

        //初始化leakcanary
        //refWatcher = LeakCanary.install(this);

        mServiceManager = new ServiceManager(this);

        setTag(TAG); // 初始化全局变量
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

}
