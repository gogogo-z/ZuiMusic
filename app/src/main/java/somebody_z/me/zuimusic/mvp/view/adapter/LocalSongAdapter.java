package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.widget.indexbar.IndexableAdapter;

/**
 * Created by Huanxing Zeng on 2017/3/6.
 * email : zenghuanxing123@163.com
 */
public class LocalSongAdapter extends IndexableAdapter<ContentBean> {

    private LayoutInflater mInflater;

    private OnRecyclerViewItemClickListener mOnItemClickListener;

    private String mLocalUrl = "local_url";

    public LocalSongAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_city, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.adapter_local_music_item, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tvIndex.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, final ContentBean entity) {
        ContentVH viewHolder = (ContentVH) holder;
        String album = entity.getAlbum_title();

        String localurl = entity.getLocalUrl();

        if (localurl == null || mLocalUrl == null) {

        } else {
            //设置正在播放状态
            if (mLocalUrl.equals(entity.getLocalUrl())) {
                viewHolder.ivLocalSongItemPlaying.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivLocalSongItemPlaying.setVisibility(View.GONE);
            }
        }

        viewHolder.tvLocalSongItemTitle.setText(entity.getTitle());

        if (album != "") {
            album = " - " + album;
        }
        viewHolder.tvLocalSongItemAuthorAlbum.setText(entity.getAuthor() + album);

        if (entity.getHas_mv() == 1) {
            viewHolder.ivLocalSongItemSq.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivLocalSongItemSq.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, entity);
            }
        });

        viewHolder.llLocalSongItemMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemMoreClick(entity);
            }
        });

        // 将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(entity);
    }

    public void setPlaying(String localUrl) {
        mLocalUrl = localUrl;
        notifyDataSetChanged();
    }

    static class IndexVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_index)
        TextView tvIndex;

        IndexVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ContentVH extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_local_song_item_more)
        LinearLayout llLocalSongItemMore;
        @Bind(R.id.iv_local_song_item_playing)
        ImageView ivLocalSongItemPlaying;
        @Bind(R.id.tv_local_song_item_title)
        TextView tvLocalSongItemTitle;
        @Bind(R.id.iv_local_song_item_sq)
        ImageView ivLocalSongItemSq;
        @Bind(R.id.tv_local_song_item_author_album)
        TextView tvLocalSongItemAuthorAlbum;
        @Bind(R.id.tv_local_song_item_divider)
        TextView tvLocalSongItemDivider;

        ContentVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // 定义接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ContentBean songSheetDetail);

        void onItemMoreClick(ContentBean songSheetDetail);
    }

}
