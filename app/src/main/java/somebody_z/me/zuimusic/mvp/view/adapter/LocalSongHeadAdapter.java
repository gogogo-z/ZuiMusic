package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.widget.indexbar.IndexableHeaderAdapter;

/**
 * Created by Huanxing Zeng on 2017/3/7.
 * email : zenghuanxing123@163.com
 */
public class LocalSongHeadAdapter extends IndexableHeaderAdapter<String> {
    private static final int TYPE = 1;

    private OnRecyclerViewItemClickListener mOnItemClickListener;

    private Context mContext;

    public LocalSongHeadAdapter(Context context, String index, String indexTitle, List<String> datas) {
        super(index, indexTitle, datas);
        this.mContext = context;
    }

    @Override
    public int getItemViewType() {
        return TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.widget_local_song_header, parent, false));
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, String entity) {
        ViewHolder vh = (ViewHolder) holder;
        vh.tvDetailHeaderCount.setText("(" + entity + "首)");

        vh.llDetailHeaderOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemSelectClick();
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_detail_header_icon)
        LinearLayout llDetailHeaderIcon;
        @Bind(R.id.ll_detail_header_option)
        LinearLayout llDetailHeaderOption;
        @Bind(R.id.tv_detail_header_count)
        TextView tvDetailHeaderCount;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // 定义接口
    public interface OnRecyclerViewItemClickListener {
        void onItemSelectClick();
    }
}
