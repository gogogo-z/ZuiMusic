package somebody_z.me.zuimusic.widget.rankofficial;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.RankBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/13.
 * email : zenghuanxing123@163.com
 */
public class RankOfficialPanel extends LinearLayout {
    private LinearLayout llroot;

    private Context context;

    private RankOfficialPanelListener rankOfficialPanelListener;

    public RankOfficialPanel(Context context, List<RankBean.ContentBean> contentBeenList) {
        super(context);
        this.context = context;
        initView(context);
        setData(contentBeenList);
    }

    public RankOfficialPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RankOfficialPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_rank_official_panel, this);

        llroot = (LinearLayout) findViewById(R.id.ll_rank_official_panel_root);

    }

    private void setData(List<RankBean.ContentBean> contentBeenList) {
        for (int i = 0; i < contentBeenList.size(); i++) {
            RankOfficialItem rankOfficialItem = new RankOfficialItem(context, contentBeenList.get(i));
            llroot.addView(rankOfficialItem);

            rankOfficialItem.setRankOfficialListener(new RankOfficialItem.RankOfficialListener() {
                @Override
                public void setItem(RankBean.ContentBean contentBean) {
                    rankOfficialPanelListener.setPanel(contentBean);
                }
            });

            addLine();
        }

    }

    public void setRankOfficialPanelListener(RankOfficialPanelListener rankOfficialPanelListener) {
        this.rankOfficialPanelListener = rankOfficialPanelListener;
    }

    //设置回调接口，播放主播电台及更多
    public interface RankOfficialPanelListener {
        void setPanel(RankBean.ContentBean contentBean);
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