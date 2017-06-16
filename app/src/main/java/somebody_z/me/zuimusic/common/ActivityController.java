package somebody_z.me.zuimusic.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * activity控制类
 *
 * @author HuanxingZeng
 * @version 创建时间：2016年12月5日 上午10:45:25
 */
public class ActivityController {
    public static final List<Activity> activities = new ArrayList<Activity>();

    // 使用内部类的方式构建单例，避免线程不安全
    private static class SingletonHolder {
        private static final ActivityController INSTANCE = new ActivityController();
    }

    public static ActivityController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        synchronized (activities) {
            for (Activity act : activities) {
                if (act != null && !act.isFinishing())
                    act.finish();
            }
        }
    }

    public static void exitApp() {

        finishAll();

        System.exit(0);
    }

}
