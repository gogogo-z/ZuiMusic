package somebody_z.me.zuimusic.widget.hotalbum;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.HotAlbumBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.transformation.CutTransformation;

/**
 * 热门唱片面板
 * Created by Huanxing Zeng on 2017/1/3.
 * email : zenghuanxing123@163.com
 */
public class HotAlbumPanel extends LinearLayout implements View.OnClickListener {
    private LinearLayout llHotAlbumPanelMore;
    private ImageView ivHotAlbumPanel1;
    private TextView tvHotAlbumPanelTitle1;
    private RelativeLayout rlHotAlbumPanel1;

    private TextView tvAuthor1, tvAuthor2, tvAuthor3, tvAuthor4;

    private ImageView ivHotAlbumPanel2;
    private TextView tvHotAlbumPanelTitle2;
    private RelativeLayout rlHotAlbumPanel2;

    private ImageView ivHotAlbumPanel3;
    private TextView tvHotAlbumPanelTitle3;
    private RelativeLayout rlHotAlbumPanel3;

    private ImageView ivHotAlbumPanel4;
    private TextView tvHotAlbumPanelTitle4;
    private RelativeLayout rlHotAlbumPanel4;

    private Context context;

    private List<HotAlbumBean.ListBean> list;

    private HotAlbumListener hotAlbumListener;

    public HotAlbumPanel(Context context, List<HotAlbumBean.ListBean> list) {
        super(context);
        this.context = context;
        this.list = list;
        initView();
        setData(list);
    }

    public HotAlbumPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HotAlbumPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(context, R.layout.widget_hot_album_panel, this);

        llHotAlbumPanelMore = (LinearLayout) findViewById(R.id.ll_hot_album_panel_more);
        ivHotAlbumPanel1 = (ImageView) findViewById(R.id.iv_hot_album_panel_1);
        ivHotAlbumPanel2 = (ImageView) findViewById(R.id.iv_hot_album_panel_2);
        ivHotAlbumPanel3 = (ImageView) findViewById(R.id.iv_hot_album_panel_3);
        ivHotAlbumPanel4 = (ImageView) findViewById(R.id.iv_hot_album_panel_4);

        tvHotAlbumPanelTitle1 = (TextView) findViewById(R.id.tv_hot_album_panel_title_1);
        tvHotAlbumPanelTitle2 = (TextView) findViewById(R.id.tv_hot_album_panel_title_2);
        tvHotAlbumPanelTitle3 = (TextView) findViewById(R.id.tv_hot_album_panel_title_3);
        tvHotAlbumPanelTitle4 = (TextView) findViewById(R.id.tv_hot_album_panel_title_4);

        rlHotAlbumPanel1 = (RelativeLayout) findViewById(R.id.rl_hot_album_panel_1);
        rlHotAlbumPanel2 = (RelativeLayout) findViewById(R.id.rl_hot_album_panel_2);
        rlHotAlbumPanel3 = (RelativeLayout) findViewById(R.id.rl_hot_album_panel_3);
        rlHotAlbumPanel4 = (RelativeLayout) findViewById(R.id.rl_hot_album_panel_4);

        tvAuthor1 = (TextView) findViewById(R.id.tv_hot_album_panel_author_1);
        tvAuthor2 = (TextView) findViewById(R.id.tv_hot_album_panel_author_2);
        tvAuthor3 = (TextView) findViewById(R.id.tv_hot_album_panel_author_3);
        tvAuthor4 = (TextView) findViewById(R.id.tv_hot_album_panel_author_4);

        llHotAlbumPanelMore.setOnClickListener(this);
        rlHotAlbumPanel1.setOnClickListener(this);
        rlHotAlbumPanel2.setOnClickListener(this);
        rlHotAlbumPanel3.setOnClickListener(this);
        rlHotAlbumPanel4.setOnClickListener(this);

        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                DisplayUtils.dp2px(context, 120));
        ivHotAlbumPanel1.setLayoutParams(params3);
        ivHotAlbumPanel2.setLayoutParams(params3);
        ivHotAlbumPanel3.setLayoutParams(params3);
        ivHotAlbumPanel4.setLayoutParams(params3);


    }

    private void setData(List<HotAlbumBean.ListBean> list) {

        List<String> urls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            String url = list.get(i).getPic_radio();
            String title = list.get(i).getTitle();
            String author = list.get(i).getAuthor();
            urls.add(url);
            titles.add(title);
            authors.add(author);
        }

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(urls.get(0))
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivHotAlbumPanel1);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(urls.get(1))
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivHotAlbumPanel2);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(urls.get(2))
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivHotAlbumPanel3);

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(urls.get(3))
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(ivHotAlbumPanel4);

        tvHotAlbumPanelTitle1.setText(titles.get(0));
        tvHotAlbumPanelTitle2.setText(titles.get(1));
        tvHotAlbumPanelTitle3.setText(titles.get(2));
        tvHotAlbumPanelTitle4.setText(titles.get(3));

        tvAuthor1.setText(authors.get(0));
        tvAuthor2.setText(authors.get(1));
        tvAuthor3.setText(authors.get(2));
        tvAuthor4.setText(authors.get(3));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_hot_album_panel_more:
                hotAlbumListener.loadMore();
                break;
            case R.id.rl_hot_album_panel_1:
                hotAlbumListener.getDetail(list.get(0));
                break;
            case R.id.rl_hot_album_panel_2:
                hotAlbumListener.getDetail(list.get(1));
                break;
            case R.id.rl_hot_album_panel_3:
                hotAlbumListener.getDetail(list.get(2));
                break;
            case R.id.rl_hot_album_panel_4:
                hotAlbumListener.getDetail(list.get(3));
                break;
        }
    }

    public void setHotAlbumListener(HotAlbumListener hotAlbumListener) {
        this.hotAlbumListener = hotAlbumListener;
    }

    //设置回调接口
    public interface HotAlbumListener {
        void getDetail(HotAlbumBean.ListBean list);

        void loadMore();
    }

}
