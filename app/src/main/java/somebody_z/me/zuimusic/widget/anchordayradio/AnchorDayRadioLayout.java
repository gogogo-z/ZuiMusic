package somebody_z.me.zuimusic.widget.anchordayradio;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * 由于和推荐页面的推荐歌单类似，但bean不同，所以直接复制过来，
 * Created by Huanxing Zeng on 2017/1/1.
 * email : zenghuanxing123@163.com
 */
public class AnchorDayRadioLayout extends LinearLayout {
    private AnchorDayRadioItem si1, si2, si3;

    private SongSheetLayoutListener songSheetLayoutListener;

    public AnchorDayRadioLayout(Context context, List<AnchorRadioBean.RadioList> radioLists) {
        super(context);
        initView(context);
        setData(context, radioLists);
    }

    public AnchorDayRadioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AnchorDayRadioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_anchor_day_radio_layout, this);

        si1 = (AnchorDayRadioItem) findViewById(R.id.si_anchor_day_radio_layout_1);
        si2 = (AnchorDayRadioItem) findViewById(R.id.si_anchor_day_radio_layout_2);
        si3 = (AnchorDayRadioItem) findViewById(R.id.si_anchor_day_radio_layout_3);

        LayoutParams layoutParams = (LayoutParams) si1.getLayoutParams();
        layoutParams.height = DisplayUtils.dp2px(context, 155);
        si1.setLayoutParams(layoutParams);

        LayoutParams layoutParams2 = (LayoutParams) si2.getLayoutParams();
        layoutParams2.height = DisplayUtils.dp2px(context, 155);
        si2.setLayoutParams(layoutParams2);

        LayoutParams layoutParams3 = (LayoutParams) si3.getLayoutParams();
        layoutParams3.height = DisplayUtils.dp2px(context, 155);
        si3.setLayoutParams(layoutParams3);
    }

    private void setData(Context context, List<AnchorRadioBean.RadioList> songSheetListBean) {
        si1.setData(context, songSheetListBean.get(0));
        si2.setData(context, songSheetListBean.get(1));
        si3.setData(context, songSheetListBean.get(2));

        si1.setSongSheetItemListener(new AnchorDayRadioItem.SongSheetItemListener() {
            @Override
            public void setItem(AnchorRadioBean.RadioList songSheetListBean) {
                songSheetLayoutListener.setLayout(songSheetListBean);
            }
        });
        si2.setSongSheetItemListener(new AnchorDayRadioItem.SongSheetItemListener() {
            @Override
            public void setItem(AnchorRadioBean.RadioList songSheetListBean) {
                songSheetLayoutListener.setLayout(songSheetListBean);
            }
        });
        si3.setSongSheetItemListener(new AnchorDayRadioItem.SongSheetItemListener() {
            @Override
            public void setItem(AnchorRadioBean.RadioList songSheetListBean) {
                songSheetLayoutListener.setLayout(songSheetListBean);
            }
        });
    }

    public void setSongSheetLayoutListener(SongSheetLayoutListener songSheetLayoutListener) {
        this.songSheetLayoutListener = songSheetLayoutListener;
    }

    //设置回调接口
    public interface SongSheetLayoutListener {
        void setLayout(AnchorRadioBean.RadioList songSheetListBean);
    }

}
