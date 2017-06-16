package somebody_z.me.zuimusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import somebody_z.me.zuimusic.utils.HttpNetUtil;

/**
 * @author HuanxingZeng
 * 
 * @version 创建时间：2016年12月5日 上午11:20:38
 */
public class NetWorkReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		HttpNetUtil.INSTANCE.setConnected(context);
	}
}
