package somebody_z.me.zuimusic.widget.rankofficial;

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
public class RankOfficialItem extends LinearLayout {
    private ImageView imageView;

    private TextView tvFirst, tvSecond, tvThird;

    private RelativeLayout relativeLayout;

    private Context context;

    private RankOfficialListener rankOfficialListener;

    public RankOfficialItem(Context context, RankBean.ContentBean contentBean) {
        super(context);
        this.context = context;
        initView();
        setData(contentBean);
    }

    public RankOfficialItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RankOfficialItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(context, R.layout.widget_rank_official_item, this);
        imageView = (ImageView) findViewById(R.id.iv_rank_official_item);

        tvFirst = (TextView) findViewById(R.id.tv_rank_official_item_1);
        tvSecond = (TextView) findViewById(R.id.tv_rank_official_item_2);
        tvThird = (TextView) findViewById(R.id.tv_rank_official_item_3);

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_rank_official_item);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();

        layoutParams.height = DisplayUtils.dp2px(context, 90);

        relativeLayout.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(context, 90));
        imageView.setLayoutParams(params);
    }

    private void setData(final RankBean.ContentBean contentBean) {
        String url = contentBean.getPic_s192();

        String topFirstTitle = contentBean.getContent().get(0).getTitle();
        String topFirstAuthor = contentBean.getContent().get(0).getAuthor();
        String topSecondTitle = contentBean.getContent().get(1).getTitle();
        String topSecondAuthor = contentBean.getContent().get(1).getAuthor();
        String topThirdTitle = contentBean.getContent().get(2).getTitle();
        String topThirdAuthor = contentBean.getContent().get(2).getAuthor();

        String topFirst = new StringBuilder().append("1.").append(topFirstTitle).append(" - ").append(topFirstAuthor).toString();

        String topSecond=new StringBuilder().append("2.").append(topSecondTitle).append(" - ").append(topSecondAuthor).toString();

        String topThird =new StringBuilder().append("3.").append(topThirdTitle).append(" - ").append(topThirdAuthor).toString();

        tvFirst.setText(topFirst);
        tvSecond.setText(topSecond);
        tvThird.setText(topThird);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(imageView);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rankOfficialListener.setItem(contentBean);
            }
        });

    }

    public void setRankOfficialListener(RankOfficialListener rankOfficialListener) {
        this.rankOfficialListener = rankOfficialListener;
    }

    public interface RankOfficialListener {
        void setItem(RankBean.ContentBean contentBean);
    }

}
