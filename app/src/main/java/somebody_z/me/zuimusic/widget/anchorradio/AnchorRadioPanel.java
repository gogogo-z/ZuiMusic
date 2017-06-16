package somebody_z.me.zuimusic.widget.anchorradio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.widget.recommendsongsheet.RecommendSongSheetLayout;

/**
 * Created by Huanxing Zeng on 2017/1/3.
 * email : zenghuanxing123@163.com
 */
public class AnchorRadioPanel extends LinearLayout {
    private LinearLayout llroot;

    private TextView tvMore;

    private TextView tvTitle;

    private ImageView ivIcon;

    private Context context;

    private String mTitle;

    private int imageID;

    private AnchorRadioPanelListener anchorRadioPanelListener;

    public AnchorRadioPanel(Context context, List<AnchorRadioBean.RadioList> radioLists, String title, int imageID) {
        super(context);
        this.context = context;
        this.mTitle = title;
        this.imageID = imageID;
        initView(context);
        setData(radioLists);
    }

    public AnchorRadioPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AnchorRadioPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_anchor_radio_panel, this);

        tvMore = (TextView) findViewById(R.id.tv_anchor_radio_panel_more);

        llroot = (LinearLayout) findViewById(R.id.ll_anchor_radio_panel_root);

        tvTitle = (TextView) findViewById(R.id.tv_anchor_radio_panel_title);

        ivIcon = (ImageView) findViewById(R.id.iv_anchor_radio_panel);

        if (mTitle != null) {
            tvTitle.setText(mTitle);
        }

        if (imageID == R.drawable.icon_left_songsheet) {
            tvMore.setVisibility(GONE);
            LayoutParams params = (LayoutParams) ivIcon.getLayoutParams();
            params.width = DisplayUtils.dp2px(context, 3);
            ivIcon.setLayoutParams(params);
        }

        ivIcon.setImageResource(imageID);

        tvMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                anchorRadioPanelListener.loadMore();
            }
        });
    }

    private void setData(List<AnchorRadioBean.RadioList> radioLists) {
        for (int i = 0; i < radioLists.size(); i++) {
            AnchorRadioItem anchorRadioItem = new AnchorRadioItem(context, radioLists.get(i));
            llroot.addView(anchorRadioItem);

            anchorRadioItem.setAnchorRadioListener(new AnchorRadioItem.AnchorRadioListener() {
                @Override
                public void setItem(AnchorRadioBean.RadioList radioList) {
                    anchorRadioPanelListener.setPanel(radioList);
                }
            });
            addLine();
        }

    }

    public void setAnchorRadioPanelListener(AnchorRadioPanelListener anchorRadioPanelListener) {
        this.anchorRadioPanelListener = anchorRadioPanelListener;
    }

    //设置回调接口，播放主播电台及更多
    public interface AnchorRadioPanelListener {
        void setPanel(AnchorRadioBean.RadioList radioList);

        void loadMore();
    }

    public void addLine() {
        TextView tvLine = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(context, 0.5f));
        params.setMargins(0, DisplayUtils.dp2px(context, 10), 0, DisplayUtils.dp2px(context, 10));
        tvLine.setLayoutParams(params);
        tvLine.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        llroot.addView(tvLine);
    }
}