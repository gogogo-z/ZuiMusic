package somebody_z.me.zuimusic.widget.anchorradio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/3.
 * email : zenghuanxing123@163.com
 */
public class AnchorRadioItem extends LinearLayout {
    private ImageView imageView;

    private TextView tvTitle, tvDesc;

    private RelativeLayout relativeLayout;

    private Context context;

    private AnchorRadioListener anchorRadioListener;

    public AnchorRadioItem(Context context, AnchorRadioBean.RadioList radioList) {
        super(context);
        this.context = context;
        initView();
        setData(radioList);
    }

    public AnchorRadioItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AnchorRadioItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(context, R.layout.widget_anchor_radio_item, this);
        imageView = (ImageView) findViewById(R.id.iv_anchor_radio_item);

        tvTitle = (TextView) findViewById(R.id.tv_anchor_radio_item_title);

        tvDesc = (TextView) findViewById(R.id.tv_anchor_radio_item_desc);

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_anchor_radio);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();

        layoutParams.height = DisplayUtils.dp2px(context, 100);

        relativeLayout.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(context, 100));
        imageView.setLayoutParams(params);
    }

    private void setData(final AnchorRadioBean.RadioList radioList) {
        String url = radioList.getPic();
        String title = radioList.getTitle();
        String desc = radioList.getDesc();

        tvDesc.setText(desc);
        tvTitle.setText(title);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(imageView);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                anchorRadioListener.setItem(radioList);
            }
        });

    }

    public void setAnchorRadioListener(AnchorRadioListener anchorRadioListener) {
        this.anchorRadioListener = anchorRadioListener;
    }

    public interface AnchorRadioListener {
        void setItem(AnchorRadioBean.RadioList radioList);
    }

}
