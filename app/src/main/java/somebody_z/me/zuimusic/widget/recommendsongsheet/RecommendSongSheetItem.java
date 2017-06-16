package somebody_z.me.zuimusic.widget.recommendsongsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/1.
 * email : zenghuanxing123@163.com
 */
public class RecommendSongSheetItem extends RelativeLayout {
    private TextView tvListenNum, tvTitle;

    private ImageView imageView;

    private SongSheetItemListener songSheetItemListener;

    public RecommendSongSheetItem(Context context, RecommendSongSheetBean.SongSheetListBean songSheetListBean) {
        super(context);
        initView(context);
        setData(context, songSheetListBean);
    }

    public RecommendSongSheetItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RecommendSongSheetItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_recommend_song_sheet_item, this);

        tvListenNum = (TextView) findViewById(R.id.tv_recommend_song_sheet_item_listen_num);
        tvTitle = (TextView) findViewById(R.id.tv_recommend_song_sheet_item_title);

        imageView = (ImageView) findViewById(R.id.iv_recommend_song_sheet_item);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(context, 120));
        imageView.setLayoutParams(params);
    }

    public void setData(Context context, final RecommendSongSheetBean.SongSheetListBean songSheetListBean) {
        String listenNum = songSheetListBean.getListenum();
        String title = songSheetListBean.getTitle();
        String url = songSheetListBean.getPic();

        tvListenNum.setText(listenNum);
        tvTitle.setText(title);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(imageView);

        final String listId = songSheetListBean.getListid();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                songSheetItemListener.setItem(songSheetListBean);
            }
        });
    }

    public void setSongSheetItemListener(SongSheetItemListener songSheetItemListener) {
        this.songSheetItemListener = songSheetItemListener;
    }

    //设置回调接口
    public interface SongSheetItemListener {
        void setItem(RecommendSongSheetBean.SongSheetListBean songSheetListBean);
    }

}
