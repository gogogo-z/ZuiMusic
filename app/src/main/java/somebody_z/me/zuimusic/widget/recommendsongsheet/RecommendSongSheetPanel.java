package somebody_z.me.zuimusic.widget.recommendsongsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;

/**
 * 推荐歌单组合控件
 * Created by Huanxing Zeng on 2017/1/1.
 * email : zenghuanxing123@163.com
 */
public class RecommendSongSheetPanel extends LinearLayout {
    private LinearLayout llMore, llroot;

    private Context context;

    private SongSheetPanelListener songSheetPanelListener;

    public RecommendSongSheetPanel(Context context, List<RecommendSongSheetBean.SongSheetListBean> songSheetListBean) {
        super(context);
        this.context = context;
        initView(context);
        setData(songSheetListBean);
    }

    public RecommendSongSheetPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RecommendSongSheetPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_recommend_song_sheet_panel, this);

        llMore = (LinearLayout) findViewById(R.id.ll_recommend_song_sheet_panel_more);

        llroot = (LinearLayout) findViewById(R.id.ll_recommend_song_sheet_panel_root);

        llMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                songSheetPanelListener.loadMore();
            }
        });
    }

    private void setData(List<RecommendSongSheetBean.SongSheetListBean> songSheetListBean) {
        List<RecommendSongSheetBean.SongSheetListBean> list1 = songSheetListBean.subList(0, 3);
        RecommendSongSheetLayout recommendSongSheetLayout1 = new RecommendSongSheetLayout(context, list1);

        List<RecommendSongSheetBean.SongSheetListBean> list2 = songSheetListBean.subList(3, 6);
        RecommendSongSheetLayout recommendSongSheetLayout2 = new RecommendSongSheetLayout(context, list2);

        llroot.addView(recommendSongSheetLayout1);
        llroot.addView(recommendSongSheetLayout2);

        recommendSongSheetLayout1.setSongSheetLayoutListener(new RecommendSongSheetLayout.SongSheetLayoutListener() {
            @Override
            public void setLayout(RecommendSongSheetBean.SongSheetListBean songSheetListBean) {
                songSheetPanelListener.setPanel(songSheetListBean);
            }
        });

        recommendSongSheetLayout2.setSongSheetLayoutListener(new RecommendSongSheetLayout.SongSheetLayoutListener() {
            @Override
            public void setLayout(RecommendSongSheetBean.SongSheetListBean songSheetListBean) {
                songSheetPanelListener.setPanel(songSheetListBean);
            }
        });
    }

    public void setSongSheetPanelListener(SongSheetPanelListener songSheetPanelListener) {
        this.songSheetPanelListener = songSheetPanelListener;
    }

    //设置回调接口，歌单详情以及更多歌单
    public interface SongSheetPanelListener {
        void setPanel(RecommendSongSheetBean.SongSheetListBean songSheetListBean);

        void loadMore();
    }
}
