package somebody_z.me.zuimusic.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * 在根布局设置一个与状态栏等高的view 设置背景色为我们期望的颜色
 * 
 * @author HuanxingZeng
 * 
 * @version 创建时间：2016年12月16日 下午2:54:50
 */
public class StatusBarCompat {
	private static final int INVALID_VAL = -1;
	private static final int COLOR_DEFAULT = Color.parseColor("#00000000");

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static void compat(Activity activity, int statusColor) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			if (statusColor != INVALID_VAL) {
				activity.getWindow().setStatusBarColor(statusColor);
			}
			return;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
				&& Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			int color = COLOR_DEFAULT;
			ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
			if (statusColor != INVALID_VAL) {
				color = statusColor;
			}
			View statusBarView = new View(activity);
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					getStatusBarHeight(activity));
			statusBarView.setBackgroundColor(color);
			contentView.addView(statusBarView, lp);
		}

	}

	public static void compat(Activity activity) {
		compat(activity, INVALID_VAL);
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
}