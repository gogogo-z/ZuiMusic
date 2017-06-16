package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;

/**
 * Created by Huanxing Zeng on 2017/1/4.
 * email : zenghuanxing123@163.com
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    private Context mContext;

    public List<ContentBean> songSheetDetailList = new ArrayList<>();

    public DetailAdapter(Context context, List<ContentBean> songSheetDetailList) {
        this.mContext = context;
        this.songSheetDetailList = songSheetDetailList;
    }

    // 创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_detail_item, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    // 注意这里使用getTag方法获取数据
                    mOnItemClickListener.onItemClick(view, (ContentBean) view.getTag());
                }
            }
        });
        return new ViewHolder(view);
    }

    // 将数据与界面进行绑定的操作,根据type进行数据绑定
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        String album = songSheetDetailList.get(position).getAlbum_title();
        viewHolder.tvDetailItemCount.setText(String.valueOf(position + 1));
        viewHolder.tvDetailItemTitle.setText(songSheetDetailList.get(position).getTitle());

        if (album != "") {
            album = " - " + album;
        }
        viewHolder.tvDetailItemAuthorAlbum.setText(songSheetDetailList.get(position).getAuthor() + album);

        if (songSheetDetailList.get(position).getHas_mv() == 0) {
            viewHolder.ivDetailItemSq.setVisibility(View.GONE);
        }else{
            viewHolder.ivDetailItemSq.setVisibility(View.VISIBLE);
        }

        viewHolder.llDetailItemMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemMoreClick(songSheetDetailList.get(position));
            }
        });

        // 将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(songSheetDetailList.get(position));

    }

    // 获取数据的数量
    @Override
    public int getItemCount() {
        return songSheetDetailList == null ? 0 : songSheetDetailList.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // 定义接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ContentBean songSheetDetail);

        void onItemMoreClick(ContentBean songSheetDetail);
    }

    // 自定义的ViewHolder，持有每个Item的的所有界面元素,itemview的高度必须自适应。
    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_detail_item_count)
        TextView tvDetailItemCount;
        @Bind(R.id.ll_detail_item_more)
        LinearLayout llDetailItemMore;
        @Bind(R.id.tv_detail_item_title)
        TextView tvDetailItemTitle;
        @Bind(R.id.iv_detail_item_sq)
        ImageView ivDetailItemSq;
        @Bind(R.id.tv_detail_item_author_album)
        TextView tvDetailItemAuthorAlbum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
