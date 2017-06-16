package somebody_z.me.zuimusic.utils;


import android.content.Context;
import android.widget.Toast;

import somebody_z.me.zuimusic.common.Constants;

/**
 * Toast自定义避免显示叠加
 * 
 * @author HuanxingZeng
 * 
 * @version 创建时间：2016年12月4日 下午1:33:47
 */
public class ToastUtil {

	private Toast shortToast = null;
	private Toast longToast = null;

	public ToastUtil() {
		// TODO Auto-generated constructor stub
	}

	// 使用内部类的方式构建单例，避免线程不安全
	private static class SingletonHolder {
		private static final ToastUtil INSTANCE = new ToastUtil();
	}

	public static ToastUtil getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void showToast(Context context, String msg, int type) {

		switch (type) {
		case Constants.SHOW_SHORT:
			showShortToast(context, msg);
			break;
		case Constants.SHOW_LONG:
			showLongToast(context, msg);
			break;
		default:
			showShortToast(context, msg);
			break;
		}

	}

	// 显示时间短
	public void showShortToast(Context context, String msg) {
		// TODO Auto-generated constructor stub

		if (shortToast == null) {
			shortToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			shortToast.setText(msg);
		}
		shortToast.show();
	}

	// 显示时间长
	public void showLongToast(Context context, String msg) {
		// TODO Auto-generated constructor stub

		if (longToast == null) {
			longToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		} else {
			longToast.setText(msg);
		}
		longToast.show();
	}

}
