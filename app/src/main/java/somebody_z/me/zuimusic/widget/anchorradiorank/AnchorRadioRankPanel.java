package somebody_z.me.zuimusic.widget.anchorradiorank;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;

/**
 * 偏懒，直接固定长度
 * Created by Huanxing Zeng on 2017/1/12.
 * email : zenghuanxing123@163.com
 */
public class AnchorRadioRankPanel extends LinearLayout {
    private ImageView ivInner1, ivInner2, ivOutSide;

    private Context context;

    public AnchorRadioRankPanel(Context context, List<AnchorRadioBean.RadioList> radioLists) {
        super(context);
        this.context = context;
        initView(context);
        setData(radioLists);
    }

    public AnchorRadioRankPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AnchorRadioRankPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_anchor_radio_rank_panel, this);
        ivInner1 = (ImageView) findViewById(R.id.iv_anchor_radio_rank_inner_1);
        ivInner2 = (ImageView) findViewById(R.id.iv_anchor_radio_rank_inner_2);
        ivOutSide = (ImageView) findViewById(R.id.iv_anchor_radio_rank_outside);
    }

    private void setData(List<AnchorRadioBean.RadioList> radioLists) {

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(radioLists.get(0).getPic())
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivOutSide);

        Glide.with(context)
                .load(radioLists.get(1).getPic())
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivInner1);

        Glide.with(context)
                .load(radioLists.get(2).getPic())
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivInner2);
    }
}
