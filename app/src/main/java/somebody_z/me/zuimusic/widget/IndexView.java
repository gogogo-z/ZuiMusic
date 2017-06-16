package somebody_z.me.zuimusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import somebody_z.me.zuimusic.R;

/**
 * 小圆点
 * @author HuanxingZeng
 * 
 * @version 创建时间：2016年12月23日 下午5:26:30
 */
public class IndexView extends View {

	// 画笔
	private Paint mPaint;

	// 圆的数量
	private int mCount = 4;

	// 被选中的颜色
	private int selectedColor = 0xffC70C0C;

	// 默认的颜色
	private int defaultColor = 0xff9F9F9F;

	// 圆的半径
	private float mRadius = 9;

	// 圆间距
	private float mMargin = 20;

	// 第一个圆最左侧的坐标
	private float fromX, fromY;

	// 画笔的宽度
	private int mStrokeWidth = 3;

	// 设置当前选中索引
	private int currIndex = 0;

	public IndexView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public IndexView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mPaint.setStrokeWidth(mStrokeWidth);

		// 计算坐标
		fromX = (getWidth() - mRadius * 2 * mCount - mMargin * (mCount - 1)) / 2;
		fromY = getHeight() / 2;

		for (int i = 0; i < mCount; i++) {
			if (i == currIndex) {
				mPaint.setStyle(Style.FILL_AND_STROKE);
				mPaint.setColor(selectedColor);
			} else {
				mPaint.setStyle(Style.FILL_AND_STROKE);
				mPaint.setColor(defaultColor);
			}
			canvas.drawCircle(fromX + mRadius + mRadius * 2 * i + mMargin * i, fromY, mRadius, mPaint);
		}

	}

	public void setCount(int count) {
		this.mCount = count;
	}

	public void setDefaultColor(int defaultColor) {
		this.defaultColor = defaultColor;
	}

	public void setSelectedColor(int selectedColor) {
		this.selectedColor = selectedColor;
	}

	public void setRadius(float mRadius) {
		this.mRadius = mRadius;
	}

	public void setMargin(float mMargin) {
		this.mMargin = mMargin;
	}

	public void setCurrIndex(int index) {
		// 赋值
		this.currIndex = index;

		// 重绘
		invalidate();
	}

}
