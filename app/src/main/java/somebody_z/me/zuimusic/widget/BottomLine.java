package somebody_z.me.zuimusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * 底部线
 * Created by Huanxing Zeng on 2016/12/31.
 * email : zenghuanxing123@163.com
 */
public class BottomLine extends LinearLayout {

    private String desc;

    private TextView tvDesc;

    public BottomLine(Context context, String desc) {
        super(context);
        this.desc = desc;
        initView(context);
    }

    public BottomLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        //初始化布局
        inflate(context, R.layout.widget_bottom_line, this);

        tvDesc = (TextView) findViewById(R.id.tv_bottom_line_desc);

        tvDesc.setText(desc);
    }

}
