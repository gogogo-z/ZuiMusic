package somebody_z.me.zuimusic.widget.rankglobal;

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
import somebody_z.me.zuimusic.mvp.model.bean.RankBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/13.
 * email : zenghuanxing123@163.com
 */
public class RankGlobalItem extends LinearLayout {
    private ImageView imageView;

    private TextView tvName, tvUpdate;

    private RelativeLayout relativeLayout;

    private Context context;

    private RankGlobalListener rankGlobalListener;

    public RankGlobalItem(Context context, RankBean.ContentBean contentBean) {
        super(context);
        this.context = context;
        initView();
        setData(contentBean);
    }

    public RankGlobalItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RankGlobalItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(context, R.layout.widget_rank_global_item, this);
        imageView = (ImageView) findViewById(R.id.iv_rank_global_item);

        tvName = (TextView) findViewById(R.id.tv_rank_global_item_name);
        tvUpdate = (TextView) findViewById(R.id.tv_rank_global_item_update);

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_rank_global_item);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();

        layoutParams.height = DisplayUtils.dp2px(context, 60);

        relativeLayout.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DisplayUtils.dp2px(context, 60));
        imageView.setLayoutParams(params);
    }

    private void setData(final RankBean.ContentBean contentBean) {
        String url = contentBean.getPic_s192();

        String name = contentBean.getName();

        int type = contentBean.getType() % 7;

        String week = null;
        switch (type) {
            case 0:
                week = "日";
                break;
            case 1:
                week = "一";
                break;
            case 2:
                week = "二";
                break;
            case 3:
                week = "三";
                break;
            case 4:
                week = "四";
                break;
            case 5:
                week = "五";
                break;
            case 6:
                week = "六";
                break;
        }

        String update = "每周" + week + "更新";

        tvUpdate.setText(update);

        tvName.setText(name);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(imageView);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rankGlobalListener.setItem(contentBean);
            }
        });

    }

    public void setRankGlobalListener(RankGlobalListener rankGlobalListener) {
        this.rankGlobalListener = rankGlobalListener;
    }

    public interface RankGlobalListener {
        void setItem(RankBean.ContentBean contentBean);
    }

}

