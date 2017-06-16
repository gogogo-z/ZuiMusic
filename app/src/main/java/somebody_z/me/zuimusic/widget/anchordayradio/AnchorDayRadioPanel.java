package somebody_z.me.zuimusic.widget.anchordayradio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * 推荐歌单组合控件
 * Created by Huanxing Zeng on 2017/1/1.
 * email : zenghuanxing123@163.com
 */
public class AnchorDayRadioPanel extends LinearLayout {
    private LinearLayout llMore, llroot;

    private ImageView imageView;
    private TextView textView;

    private Context context;

    private SongSheetPanelListener songSheetPanelListener;

    public AnchorDayRadioPanel(Context context, List<AnchorRadioBean.RadioList> songSheetListBean) {
        super(context);
        this.context = context;
        initView(context);
        setData(songSheetListBean);
    }

    public AnchorDayRadioPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AnchorDayRadioPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_recommend_song_sheet_panel, this);

        llMore = (LinearLayout) findViewById(R.id.ll_recommend_song_sheet_panel_more);

        llroot = (LinearLayout) findViewById(R.id.ll_recommend_song_sheet_panel_root);

        imageView = (ImageView) findViewById(R.id.iv_recommend_song_sheet_panel);
        textView = (TextView) findViewById(R.id.tv_recommend_song_sheet_panel);

        llMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                songSheetPanelListener.loadMore();
            }
        });

        llMore.setVisibility(GONE);
    }

    private void setData(List<AnchorRadioBean.RadioList> songSheetListBean) {
        textView.setText("每天精选电台 - 订阅更精彩");

        LayoutParams params = (LayoutParams) imageView.getLayoutParams();
        params.width = DisplayUtils.dp2px(context, 3);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.icon_left_songsheet);

        List<AnchorRadioBean.RadioList> list1 = songSheetListBean.subList(0, 3);
        AnchorDayRadioLayout anchorDayRadioLayout1 = new AnchorDayRadioLayout(context, list1);

        List<AnchorRadioBean.RadioList> list2 = songSheetListBean.subList(3, 6);
        AnchorDayRadioLayout anchorDayRadioLayout2 = new AnchorDayRadioLayout(context, list2);

        llroot.addView(anchorDayRadioLayout1);
        llroot.addView(anchorDayRadioLayout2);

        anchorDayRadioLayout1.setSongSheetLayoutListener(new AnchorDayRadioLayout.SongSheetLayoutListener() {
            @Override
            public void setLayout(AnchorRadioBean.RadioList songSheetListBean) {
                songSheetPanelListener.setPanel(songSheetListBean);
            }
        });

        anchorDayRadioLayout2.setSongSheetLayoutListener(new AnchorDayRadioLayout.SongSheetLayoutListener() {
            @Override
            public void setLayout(AnchorRadioBean.RadioList songSheetListBean) {
                songSheetPanelListener.setPanel(songSheetListBean);
            }
        });
    }

    public void setSongSheetPanelListener(SongSheetPanelListener songSheetPanelListener) {
        this.songSheetPanelListener = songSheetPanelListener;
    }

    //设置回调接口，歌单详情以及更多歌单
    public interface SongSheetPanelListener {
        void setPanel(AnchorRadioBean.RadioList songSheetListBean);

        void loadMore();
    }
}
