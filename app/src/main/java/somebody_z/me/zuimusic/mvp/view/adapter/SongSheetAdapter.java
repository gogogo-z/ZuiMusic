package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.AllSongSheetBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/4.
 * email : zenghuanxing123@163.com
 */
public class SongSheetAdapter extends RecyclerView.Adapter<SongSheetAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    private Context mContext;

    public List<AllSongSheetBean.ContentBean> songSheetList = new ArrayList<>();

    public SongSheetAdapter(Context context) {
        this.mContext = context;
    }

    // 创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_song_sheet_item, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    // 将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DisplayUtils.dp2px(mContext, 165));
        viewHolder.ivSongSheetItem.setLayoutParams(params);

        viewHolder.rlSongSheetItem.setLayoutParams(params);

        viewHolder.tvSongSheetItemTitle.setText(songSheetList.get(position).getTitle());
        viewHolder.tvSongSheetItemListenNum.setText(songSheetList.get(position).getListenum());
        viewHolder.tvSongSheetItemDesc.setText(songSheetList.get(position).getDesc());

        //使用Glide第三方框架加载图片
        Glide.with(mContext)
                .load(songSheetList.get(position).getPic_300())
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(viewHolder.ivSongSheetItem);
        // 将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(songSheetList.get(position));
    }

    // 获取数据的数量
    @Override
    public int getItemCount() {
        return songSheetList.size();
    }

    // 自定义的ViewHolder，持有每个Item的的所有界面元素,itemview的高度必须自适应。
    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_song_sheet_item)
        ImageView ivSongSheetItem;
        @Bind(R.id.tv_song_sheet_item_desc)
        TextView tvSongSheetItemDesc;
        @Bind(R.id.rl_song_sheet_item)
        RelativeLayout rlSongSheetItem;
        @Bind(R.id.tv_song_sheet_item_listen_num)
        TextView tvSongSheetItemListenNum;
        @Bind(R.id.tv_song_sheet_item_title)
        TextView tvSongSheetItemTitle;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // 定义接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, AllSongSheetBean.ContentBean songSheet);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (AllSongSheetBean.ContentBean) v.getTag());
        }
    }

    public void addItems(List<AllSongSheetBean.ContentBean> list) {
        songSheetList.addAll(list);
        notifyDataSetChanged();
    }

    public void addCategoryItem(List<AllSongSheetBean.ContentBean> list) {
        if (songSheetList != null) {
            songSheetList.clear();
        }
        songSheetList.addAll(list);
        notifyDataSetChanged();
    }
}
