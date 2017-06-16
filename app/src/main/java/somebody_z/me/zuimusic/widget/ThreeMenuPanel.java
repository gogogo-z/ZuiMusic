package somebody_z.me.zuimusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * 轮播图下方三个按钮
 * Created by Huanxing Zeng on 2016/12/31.
 * email : zenghuanxing123@163.com
 */
public class ThreeMenuPanel extends LinearLayout implements View.OnClickListener {

    public LinearLayout llPersonalFM, llDailyRecommend, llCloundRecommend;

    private ImageButton ibPersonalFM, ibDailyRecommend, ibCloundRecommend;

    private JumpListener jumpListener;

    public ThreeMenuPanel(Context context) {
        this(context, null);
    }

    public ThreeMenuPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initListener();
    }

    private void initView(Context context) {
        //初始化布局
        inflate(context, R.layout.widget_three_menu_panel, this);

        llPersonalFM = (LinearLayout) findViewById(R.id.ll_three_menu_personal_fm);

        llDailyRecommend = (LinearLayout) findViewById(R.id.ll_three_menu_daily_recommend);

        llCloundRecommend = (LinearLayout) findViewById(R.id.ll_three_menu_clound_recommend);

        ibPersonalFM = (ImageButton) findViewById(R.id.btn_three_menu_personal_fm);

        ibDailyRecommend = (ImageButton) findViewById(R.id.btn_three_menu_daily_recommend);

        ibCloundRecommend = (ImageButton) findViewById(R.id.btn_three_menu_clound_recommend);

        int width = ScreenUtil.getScreenWidth(context) / 7;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);

        ibPersonalFM.setLayoutParams(params);
        ibDailyRecommend.setLayoutParams(params);
        ibCloundRecommend.setLayoutParams(params);

    }

    private void initListener() {
        llPersonalFM.setOnClickListener(this);
        llDailyRecommend.setOnClickListener(this);
        llCloundRecommend.setOnClickListener(this);

        ibPersonalFM.setOnClickListener(this);
        ibDailyRecommend.setOnClickListener(this);
        ibCloundRecommend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_three_menu_personal_fm:
            case R.id.btn_three_menu_personal_fm:
                jumpListener.setPersonalFMListener();
                break;
            case R.id.ll_three_menu_daily_recommend:
            case R.id.btn_three_menu_daily_recommend:
                jumpListener.setDailyRecommendListener();
                break;
            case R.id.ll_three_menu_clound_recommend:
            case R.id.btn_three_menu_clound_recommend:
                jumpListener.setCloundRecommendListener();
                break;
        }
    }

    public void setJumpListener(JumpListener jumpListener) {
        this.jumpListener = jumpListener;
    }

    //设置监听接口，回调
    public interface JumpListener {
        void setPersonalFMListener();

        void setDailyRecommendListener();

        void setCloundRecommendListener();
    }
}
