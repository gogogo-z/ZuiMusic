package somebody_z.me.zuimusic.widget.play;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.utils.MusicUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;
import somebody_z.me.zuimusic.utils.transformation.CircleTransformation;

/**
 * Created by Huanxing Zeng on 2017/3/7.
 * email : zenghuanxing123@163.com
 */
public class PlayAdapter extends PagerAdapter {

    private List<ContentBean> songList;

    private Context context;

    private ImageView ivCover;

    private View mCurrentView;

    private OnClickPageListener onClickPageListener;

    public PlayAdapter(Context context, List<ContentBean> songList) {
        this.context = context;
        this.songList = songList;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View) object;
    }

    /**
     * 获取viewpager当前显示的子view
     *
     * @return
     */
    public View getPrimaryItem() {
        return mCurrentView;
    }

    /**
     * 设置最大数值，使viewpager看不到边界
     */
    @Override
    public int getCount() {
        return songList.size() * 2000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= songList.size();
        if (position < 0) {
            position = songList.size() + position;
        }

        View view = View.inflate(context, R.layout.adapter_play, null);

        ivCover = (ImageView) view.findViewById(R.id.iv_adapter_cover);

        ImageView ivDis = (ImageView) view.findViewById(R.id.iv_adapter_dis);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivCover.getLayoutParams();
        params.width = ScreenUtil.getScreenWidth(context) / 2;
        params.height = ScreenUtil.getScreenWidth(context) / 2;
        ivCover.setLayoutParams(params);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivDis.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidth(context) * 3 / 4;
        layoutParams.height = ScreenUtil.getScreenWidth(context) * 3 / 4;
        ivDis.setLayoutParams(layoutParams);

        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }

        container.addView(view);

        if (songList.get(position).getPic_url() == null) {
            //使用Glide第三方框架加载图片
            Glide.with(context)
                    .load(MusicUtils.getCoverUri(Long.valueOf(songList.get(position).getAlbum_id())))
                    .placeholder(R.drawable.placeholder_disk_play_song)//显示默认图片
                    .bitmapTransform(new CircleTransformation(context))
                    .into(ivCover);
        } else {
            //使用Glide第三方框架加载图片
            Glide.with(context)
                    .load(songList.get(position).getPic_url())
                    .placeholder(R.drawable.placeholder_disk_play_song)//显示默认图片
                    .bitmapTransform(new CircleTransformation(context))
                    .into(ivCover);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPageListener.onClick();
            }
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    public void setOnClickPageListener(OnClickPageListener onClickPageListener) {
        this.onClickPageListener = onClickPageListener;
    }

    public interface OnClickPageListener {
        void onClick();
    }
}
