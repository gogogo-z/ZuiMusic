package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.utils.LogUtil;

/**
 * Created by Huanxing Zeng on 2017/1/4.
 * email : zenghuanxing123@163.com
 */
public class SongSelectAdapter extends RecyclerView.Adapter<SongSelectAdapter.ViewHolder> {
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    private Context mContext;

    private List<ContentBean> songSheetDetailList = new ArrayList<>();

    private ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();

    private boolean isChecked = false;

    public SongSelectAdapter(Context context, List<ContentBean> songSheetDetailList) {
        this.mContext = context;
        this.songSheetDetailList = songSheetDetailList;
    }

    // 创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_song_select_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // 将数据与界面进行绑定的操作,根据type进行数据绑定
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        String album = songSheetDetailList.get(position).getAlbum_title();
        viewHolder.tvSongSelectItemTitle.setText(songSheetDetailList.get(position).getTitle());

        if (!album.equals("")) {
            album = " - " + album;
        }

        viewHolder.tvSongSelectItemAuthorAlbum.setText(songSheetDetailList.get(position).getAuthor() + album);

        if (songSheetDetailList.get(position).getHas_mv() == 0) {
            viewHolder.ivSongSelectItemSq.setVisibility(View.GONE);
        }else {
            viewHolder.ivSongSelectItemSq.setVisibility(View.VISIBLE);
        }

        viewHolder.cbSongSelectItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mOnItemClickListener.onItemClick(songSheetDetailList.get(position), b);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    viewHolder.cbSongSelectItem.setChecked(!viewHolder.cbSongSelectItem.isChecked());
                }
            }
        });

        //recyclerview 只初始化显示在屏幕的item。所以设定选中状态，避免出现部分item没有勾选的状态
        viewHolder.cbSongSelectItem.setChecked(isChecked);
        //设置不回收
        viewHolder.setIsRecyclable(false);

        checkBoxArrayList.add(viewHolder.cbSongSelectItem);
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
        void onItemClick(ContentBean songSheetDetail, boolean isChecked);
    }

    public void setSelectAll(boolean isChecked) {
        this.isChecked = isChecked;
        for (CheckBox checkBox : checkBoxArrayList) {
            checkBox.setChecked(isChecked);
        }
    }

    // 自定义的ViewHolder，持有每个Item的的所有界面元素,itemview的高度必须自适应。
    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cb_song_select_item)
        CheckBox cbSongSelectItem;
        @Bind(R.id.ll_song_select_item)
        LinearLayout llSongSelectItem;
        @Bind(R.id.tv_song_select_item_title)
        TextView tvSongSelectItemTitle;
        @Bind(R.id.iv_song_select_item_sq)
        ImageView ivSongSelectItemSq;
        @Bind(R.id.tv_song_select_item_author_album)
        TextView tvSongSelectItemAuthorAlbum;
        @Bind(R.id.tv_song_select_item_divider)
        TextView tvSongSelectItemDivider;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
