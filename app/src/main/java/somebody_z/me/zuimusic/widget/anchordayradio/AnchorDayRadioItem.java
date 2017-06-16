package somebody_z.me.zuimusic.widget.anchordayradio;

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
 * Created by Huanxing Zeng on 2017/1/1.
 * email : zenghuanxing123@163.com
 */
public class AnchorDayRadioItem extends RelativeLayout {
    private TextView tvTitle;

    private ImageView imageView;

    private LinearLayout linearLayout;

    private SongSheetItemListener songSheetItemListener;

    public AnchorDayRadioItem(Context context, AnchorRadioBean.RadioList radioList) {
        super(context);
        initView(context);
        setData(context, radioList);
    }

    public AnchorDayRadioItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AnchorDayRadioItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_recommend_song_sheet_item, this);

        tvTitle = (TextView) findViewById(R.id.tv_recommend_song_sheet_item_title);

        linearLayout = (LinearLayout) findViewById(R.id.ll_recommend_song_sheet_item_top);

        linearLayout.setVisibility(GONE);

        imageView = (ImageView) findViewById(R.id.iv_recommend_song_sheet_item);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(context, 120));
        imageView.setLayoutParams(params);
    }

    public void setData(Context context, final AnchorRadioBean.RadioList radioList) {
        String title = radioList.getTitle();
        String url = radioList.getPic();

        tvTitle.setText(title);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(imageView);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                songSheetItemListener.setItem(radioList);
            }
        });
    }

    public void setSongSheetItemListener(SongSheetItemListener songSheetItemListener) {
        this.songSheetItemListener = songSheetItemListener;
    }

    //设置回调接口
    public interface SongSheetItemListener {
        void setItem(AnchorRadioBean.RadioList radioList);
    }

}
