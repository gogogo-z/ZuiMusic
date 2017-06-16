package somebody_z.me.zuimusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by Huanxing Zeng on 2017/1/25.
 * email : zenghuanxing123@163.com
 */
public class FullScreenVideoView extends VideoView {


    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //重新设置一下宽和高
        setMeasuredDimension(width, height);
    }
}
