package somebody_z.me.zuimusic.widget.newmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.mvp.model.bean.RecommendSongSheetBean;
import somebody_z.me.zuimusic.widget.recommendsongsheet.RecommendSongSheetLayout;

/**
 * 最新音乐组合控件，添加回调接口
 * Created by Huanxing Zeng on 2017/1/3.
 * email : zenghuanxing123@163.com
 */
public class NewMusicPanel extends LinearLayout {
    private LinearLayout llMore, llroot;

    private Context context;

    private NewMusicPanelListener newMusicPanelListener;

    public NewMusicPanel(Context context, List<NewMusicBean.SongListBean> songListBeen) {
        super(context);
        this.context = context;
        initView(context);
        setData(songListBeen);
    }

    public NewMusicPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NewMusicPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_new_music_panel, this);

        llMore = (LinearLayout) findViewById(R.id.ll_new_music_panel_more);

        llroot = (LinearLayout) findViewById(R.id.ll_new_music_panel_root);

        llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMusicPanelListener.loadMore();
            }
        });
    }

    private void setData(List<NewMusicBean.SongListBean> songListBeen) {
        List<NewMusicBean.SongListBean> list1 = songListBeen.subList(0, 3);
        NewMusicLayout newMusicLayout1 = new NewMusicLayout(context, list1);

        List<NewMusicBean.SongListBean> list2 = songListBeen.subList(3, 6);
        NewMusicLayout newMusicLayout2 = new NewMusicLayout(context, list2);

        llroot.addView(newMusicLayout1);
        llroot.addView(newMusicLayout2);

        newMusicLayout1.setNewMusicLayoutListener(new NewMusicLayout.NewMusicLayoutListener() {
            @Override
            public void setLayout(NewMusicBean.SongListBean songListBean) {
                newMusicPanelListener.setPanel(songListBean);
            }
        });

        newMusicLayout2.setNewMusicLayoutListener(new NewMusicLayout.NewMusicLayoutListener() {
            @Override
            public void setLayout(NewMusicBean.SongListBean songListBean) {
                newMusicPanelListener.setPanel(songListBean);
            }
        });

    }

    public void setNewMusicPanelListener(NewMusicPanelListener newMusicPanelListener) {
        this.newMusicPanelListener = newMusicPanelListener;
    }

    //设置回调接口，播放最新音乐及更多
    public interface NewMusicPanelListener {
        void setPanel(NewMusicBean.SongListBean songListBean);

        void loadMore();
    }
}