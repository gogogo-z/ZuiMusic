package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.view.activity.WebBrowserActivity;

/**
 * Created by Administrator on 2016/10/8.
 */
public class CommImagePageAdapter extends PagerAdapter {
    // 显示图片的ImageView
    private List<ImageView> viewList;

    private Context context;

    // 图片的地址
    private List<String> urlList;

    private List<String> webUrlList;

    public CommImagePageAdapter(Context context, List<ImageView> viewList,
                                List<String> urlList) {
        this.context = context;
        this.viewList = viewList;
        this.urlList = urlList;
    }

    public CommImagePageAdapter(Context context, List<ImageView> viewList,
                                List<String> urlList, List<String> webUrlList) {
        this.context = context;
        this.viewList = viewList;
        this.urlList = urlList;
        this.webUrlList = webUrlList;
    }

    /**
     * 设置最大数值，使viewpager看不到边界
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= viewList.size();
        if (position < 0) {
            position = viewList.size() + position;
        }
        ImageView imageView = viewList.get(position);

        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = imageView.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(imageView);
        }

        container.addView(imageView);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(urlList.get(position))
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(imageView);

        final int index = position;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webUrlList != null) {
                    WebBrowserActivity.showWebActivity(context, webUrlList.get(index));
                }
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}

