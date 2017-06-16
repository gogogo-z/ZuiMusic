package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.db.CollectSongSheet;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/17.
 * email : zenghuanxing123@163.com
 */
public class MusicCollectSongSheetAdapter extends BaseAdapter {

    private Context context;

    private List<CollectSongSheet> collectSongSheetList;

    private CollectSongSheetListener collectSongSheetListener;

    private String listID;

    public MusicCollectSongSheetAdapter(Context context, List<CollectSongSheet> collectSongSheetList, String listID) {
        this.context = context;
        this.collectSongSheetList = collectSongSheetList;
        this.listID = listID;
    }

    @Override
    public int getCount() {
        return collectSongSheetList == null ? 0 : collectSongSheetList.size();
    }

    @Override
    public Object getItem(int i) {
        return collectSongSheetList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {

        CollectSongSheetView collectSongSheetView = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_collect_song_sheet, null);
            collectSongSheetView = new CollectSongSheetView(convertView);
            convertView.setTag(collectSongSheetView);
        } else {
            collectSongSheetView = (CollectSongSheetView) convertView.getTag();
        }

        collectSongSheetView.tvAdapterCollectSongSheetName.setText(collectSongSheetList.get(i).getTitle());

        if (collectSongSheetList.get(i).getListid().equals(listID)) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) collectSongSheetView.ivAdapterCollectSongSheetOption.getLayoutParams();
            params.width = DisplayUtils.dp2px(context, 15);
            params.setMargins(0, 0, DisplayUtils.dp2px(context, 17), 0);
            collectSongSheetView.ivAdapterCollectSongSheetOption.setLayoutParams(params);
            collectSongSheetView.llAdapterCollectSongSheetOption.setEnabled(false);
            collectSongSheetView.ivAdapterCollectSongSheetOption.setBackgroundResource(R.drawable.song_playing);
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) collectSongSheetView.ivAdapterCollectSongSheetOption.getLayoutParams();
            params.width = DisplayUtils.dp2px(context, 3);
            params.setMargins(0, 0, DisplayUtils.dp2px(context, 23), 0);
            collectSongSheetView.ivAdapterCollectSongSheetOption.setLayoutParams(params);
            collectSongSheetView.llAdapterCollectSongSheetOption.setEnabled(true);
            collectSongSheetView.ivAdapterCollectSongSheetOption.setBackgroundResource(R.drawable.music_create_song_sheet);
        }

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(collectSongSheetList.get(i).getPic())
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(collectSongSheetView.ivAdapterCollectSongSheet);

        collectSongSheetView.tvAdapterCollectSongSheetCount.setText(collectSongSheetList.get(i).getTag());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectSongSheetListener.goToDetail(collectSongSheetList.get(i));
            }
        });

        collectSongSheetView.llAdapterCollectSongSheetOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectSongSheetListener.goToOption(collectSongSheetList.get(i));
            }
        });

        return convertView;
    }

    public void setCollectSongSheetListener(CollectSongSheetListener collectSongSheetListener) {
        this.collectSongSheetListener = collectSongSheetListener;
    }

    public interface CollectSongSheetListener {
        void goToDetail(CollectSongSheet collectSongSheet);

        void goToOption(CollectSongSheet collectSongSheet);
    }


    static class CollectSongSheetView {
        @Bind(R.id.iv_adapter_collect_song_sheet)
        ImageView ivAdapterCollectSongSheet;
        @Bind(R.id.tv_adapter_collect_song_sheet_name)
        TextView tvAdapterCollectSongSheetName;
        @Bind(R.id.tv_adapter_collect_song_sheet_count)
        TextView tvAdapterCollectSongSheetCount;
        @Bind(R.id.iv_adapter_collect_song_sheet_option)
        ImageView ivAdapterCollectSongSheetOption;
        @Bind(R.id.ll_adapter_collect_song_sheet_option)
        LinearLayout llAdapterCollectSongSheetOption;

        CollectSongSheetView(View view) {
            ButterKnife.bind(this, view);
        }
    }
}