package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetCategoryBean;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/2/15.
 * email : zenghuanxing123@163.com
 */
public class SongSheetCategoryAdapter extends RecyclerView.Adapter<SongSheetCategoryAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    private Context mContext;

    private String tag;

    private String selectedTag;

    private int index;

    private int[] imageIDs = new int[]{
            R.drawable.language,
            R.drawable.style,
            R.drawable.scene,
            R.drawable.emotion,
            R.drawable.theme
    };

    public List<SongSheetCategoryBean.TagsBean> tagsBeanList = new ArrayList<>();

    public SongSheetCategoryAdapter(Context context, List<SongSheetCategoryBean.TagsBean> tagsBeanList, String selectedTag, String tag, int index) {
        this.mContext = context;
        this.tagsBeanList = tagsBeanList;
        this.selectedTag = selectedTag;
        this.tag = tag;
        this.index = index;
    }

    // 创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_song_sheet_category, viewGroup, false);
        return new ViewHolder(view);
    }

    // 将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if (position == 0) {
            viewHolder.btnAdapterSongSheetCategory.setText(tag);
            viewHolder.btnAdapterSongSheetCategory.setTextColor(mContext.getResources().getColor(R.color.colorRed));
            viewHolder.btnAdapterSongSheetCategory.setEnabled(false);

            Drawable drawable = mContext.getResources().getDrawable(imageIDs[index]);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(ScreenUtil.getScreenWidth(mContext) / 4 - 110, 0, ScreenUtil.getScreenWidth(mContext) / 4 - 50, 60);
            viewHolder.btnAdapterSongSheetCategory.setCompoundDrawables(drawable, null, null, null);
        } else {
            viewHolder.btnAdapterSongSheetCategory.setText(tagsBeanList.get(position).getTag());
            viewHolder.btnAdapterSongSheetCategory.setOnClickListener(this);
        }

        if (selectedTag.equals(tagsBeanList.get(position).getTag())) {
            viewHolder.btnAdapterSongSheetCategory.setBackgroundResource((R.drawable.selector_category_checked));
        }

        // 将数据保存在btnAdapterSongSheetCategory的Tag中，以便点击时进行获取
        viewHolder.btnAdapterSongSheetCategory.setTag(tagsBeanList.get(position));
    }

    // 获取数据的数量
    @Override
    public int getItemCount() {
        return tagsBeanList.size();
    }

    // 自定义的ViewHolder，持有每个Item的的所有界面元素,itemview的高度必须自适应。
    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.btn_adapter_song_sheet_category)
        Button btnAdapterSongSheetCategory;

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
        void onItemClick(View view, SongSheetCategoryBean.TagsBean tagsBean);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (SongSheetCategoryBean.TagsBean) v.getTag());
        }
    }
}
