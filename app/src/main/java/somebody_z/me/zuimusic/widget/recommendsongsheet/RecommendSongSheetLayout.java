package somebody_z.me.zuimusic.widget.recommendsongsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/1.
 * email : zenghuanxing123@163.com
 */
public class RecommendSongSheetLayout extends LinearLayout {
    private RecommendSongSheetItem si1, si2, si3;

    private SongSheetLayoutListener songSheetLayoutListener;

    public RecommendSongSheetLayout(Context context, List<RecommendSongSheetBean.SongSheetListBean> songSheetListBean) {
        super(context);
        initView(context);
        setData(context, songSheetListBean);
    }

    public RecommendSongSheetLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RecommendSongSheetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_recommend_song_sheet_layout, this);

        si1 = (RecommendSongSheetItem) findViewById(R.id.si_recommend_song_sheet_layout_1);
        si2 = (RecommendSongSheetItem) findViewById(R.id.si_recommend_song_sheet_layout_2);
        si3 = (RecommendSongSheetItem) findViewById(R.id.si_recommend_song_sheet_layout_3);

        LinearLayout.LayoutParams layoutParams = (LayoutParams) si1.getLayoutParams();
        layoutParams.height = DisplayUtils.dp2px(context, 155);
        si1.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParams2 = (LayoutParams) si2.getLayoutParams();
        layoutParams2.height = DisplayUtils.dp2px(context, 155);
        si2.setLayoutParams(layoutParams2);

        LinearLayout.LayoutParams layoutParams3 = (LayoutParams) si3.getLayoutParams();
        layoutParams3.height = DisplayUtils.dp2px(context, 155);
        si3.setLayoutParams(layoutParams3);
    }

    private void setData(Context context, List<RecommendSongSheetBean.SongSheetListBean> songSheetListBean) {
        si1.setData(context, songSheetListBean.get(0));
        si2.setData(context, songSheetListBean.get(1));
        si3.setData(context, songSheetListBean.get(2));

        si1.setSongSheetItemListener(new RecommendSongSheetItem.SongSheetItemListener() {
            @Override
            public void setItem(RecommendSongSheetBean.SongSheetListBean songSheetListBean) {
                songSheetLayoutListener.setLayout(songSheetListBean);
            }
        });
        si2.setSongSheetItemListener(new RecommendSongSheetItem.SongSheetItemListener() {
            @Override
            public void setItem(RecommendSongSheetBean.SongSheetListBean songSheetListBean) {
                songSheetLayoutListener.setLayout(songSheetListBean);
            }
        });
        si3.setSongSheetItemListener(new RecommendSongSheetItem.SongSheetItemListener() {
            @Override
            public void setItem(RecommendSongSheetBean.SongSheetListBean songSheetListBean) {
                songSheetLayoutListener.setLayout(songSheetListBean);
            }
        });
    }

    public void setSongSheetLayoutListener(SongSheetLayoutListener songSheetLayoutListener) {
        this.songSheetLayoutListener = songSheetLayoutListener;

    }

    //设置回调接口
    public interface SongSheetLayoutListener {
        void setLayout(RecommendSongSheetBean.SongSheetListBean songSheetListBean);
    }

}
