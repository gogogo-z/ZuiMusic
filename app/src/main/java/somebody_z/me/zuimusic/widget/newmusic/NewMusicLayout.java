package somebody_z.me.zuimusic.widget.newmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/3.
 * email : zenghuanxing123@163.com
 */
public class NewMusicLayout extends LinearLayout {
    private NewMusicItem ni1, ni2, ni3;

    private NewMusicLayoutListener newMusicLayoutListener;

    public NewMusicLayout(Context context, List<NewMusicBean.SongListBean> songListBeen) {
        super(context);
        initView(context);
        setData(context, songListBeen);
    }

    public NewMusicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NewMusicLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_new_music_layout, this);

        ni1 = (NewMusicItem) findViewById(R.id.ni_new_music_layout_1);
        ni2 = (NewMusicItem) findViewById(R.id.ni_new_music_layout_2);
        ni3 = (NewMusicItem) findViewById(R.id.ni_new_music_layout_3);

        LinearLayout.LayoutParams layoutParams = (LayoutParams) ni1.getLayoutParams();
        layoutParams.height = DisplayUtils.dp2px(context, 160);
        ni1.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParams2 = (LayoutParams) ni2.getLayoutParams();
        layoutParams2.height = DisplayUtils.dp2px(context, 160);
        ni2.setLayoutParams(layoutParams2);

        LinearLayout.LayoutParams layoutParams3 = (LayoutParams) ni3.getLayoutParams();
        layoutParams3.height = DisplayUtils.dp2px(context, 160);
        ni3.setLayoutParams(layoutParams3);
    }

    private void setData(Context context, List<NewMusicBean.SongListBean> songListBeen) {
        ni1.setData(context, songListBeen.get(0));
        ni2.setData(context, songListBeen.get(1));
        ni3.setData(context, songListBeen.get(2));

        ni1.setNewMusicItemListener(new NewMusicItem.NewMusicItemListener() {
            @Override
            public void setItem(NewMusicBean.SongListBean songListBean) {
                newMusicLayoutListener.setLayout(songListBean);
            }
        });

        ni2.setNewMusicItemListener(new NewMusicItem.NewMusicItemListener() {
            @Override
            public void setItem(NewMusicBean.SongListBean songListBean) {
                newMusicLayoutListener.setLayout(songListBean);
            }
        });

        ni3.setNewMusicItemListener(new NewMusicItem.NewMusicItemListener() {
            @Override
            public void setItem(NewMusicBean.SongListBean songListBean) {
                newMusicLayoutListener.setLayout(songListBean);
            }
        });

    }

    public void setNewMusicLayoutListener(NewMusicLayoutListener newMusicLayoutListener) {
        this.newMusicLayoutListener = newMusicLayoutListener;

    }

    //设置回调接口
    public interface NewMusicLayoutListener {
        void setLayout(NewMusicBean.SongListBean songListBean);
    }

}
