package somebody_z.me.zuimusic.widget.newmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.NewMusicBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/3.
 * email : zenghuanxing123@163.com
 */
public class NewMusicItem extends RelativeLayout {
    private TextView tvAuthor, tvTitle, tvReason;

    private ImageView imageView;

    private RelativeLayout rlItem;

    private NewMusicItemListener newMusicItemListener;

    public NewMusicItem(Context context, NewMusicBean.SongListBean songListBean) {
        super(context);
        initView(context);
        setData(context, songListBean);
    }

    public NewMusicItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NewMusicItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.widget_new_music_item, this);

        tvAuthor = (TextView) findViewById(R.id.tv_new_music_item_author);
        tvTitle = (TextView) findViewById(R.id.tv_new_music_item_title);
        tvReason = (TextView) findViewById(R.id.tv_new_music_item_recommend_reason);
        imageView = (ImageView) findViewById(R.id.iv_new_music_item);

        rlItem = (RelativeLayout) findViewById(R.id.rl_new_music_item);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(context, 120));
        imageView.setLayoutParams(params);

        rlItem.setLayoutParams(params);
    }

    public void setData(Context context, final NewMusicBean.SongListBean songListBean) {
        String author = songListBean.getAuthor();
        String title = songListBean.getTitle();
        String url = songListBean.getPic_premium();
        String reason = songListBean.getRecommend_reason();

        tvAuthor.setText(author);
        tvTitle.setText(title);
        tvReason.setText(reason);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(imageView);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                newMusicItemListener.setItem(songListBean);
            }
        });
    }

    public void setNewMusicItemListener(NewMusicItemListener newMusicItemListener) {
        this.newMusicItemListener = newMusicItemListener;
    }

    //设置回调接口
    public interface NewMusicItemListener {
        void setItem(NewMusicBean.SongListBean songListBean);
    }

}
