package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.NewsBean;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/1/4.
 * email : zenghuanxing123@163.com
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    private Context mContext;

    public List<NewsBean.StoriesBean> newsList = new ArrayList<>();

    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    // 创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_news, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    // 将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtil.getScreenHeight(mContext) / 4);
        viewHolder.llAdapterNews.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtil.getScreenHeight(mContext) / 5);
        viewHolder.ivAdapterNews.setLayoutParams(params);

        //使用Glide第三方框架加载图片
        Glide.with(mContext)
                .load(newsList.get(position).getImages().get(0))
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(viewHolder.ivAdapterNews);

        viewHolder.tvAdapterTitle.setText(newsList.get(position).getTitle());

        // 将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(newsList.get(position));
    }

    // 获取数据的数量
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // 定义接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, NewsBean.StoriesBean news);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (NewsBean.StoriesBean) v.getTag());
        }
    }

    public void addItems(List<NewsBean.StoriesBean> list) {
        newsList.addAll(list);
        notifyDataSetChanged();
    }

    public void refreshItem(List<NewsBean.StoriesBean> list) {
        if (newsList != null) {
            newsList.clear();
        }
        newsList.addAll(list);
        notifyDataSetChanged();
    }

    // 自定义的ViewHolder，持有每个Item的的所有界面元素,itemview的高度必须自适应。
    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_adapter_news)
        ImageView ivAdapterNews;
        @Bind(R.id.tv_adapter_title)
        TextView tvAdapterTitle;
        @Bind(R.id.ll_adapter_news)
        LinearLayout llAdapterNews;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
