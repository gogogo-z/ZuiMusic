package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.db.SongSheetDetailManager;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/2/9.
 * email : zenghuanxing123@163.com
 */
public class CollectToSongSheetAdapter extends BaseAdapter {

    private Context context;

    private List<MySongSheet> mySongSheetList;

    private MySongSheetListener mySongSheetListener;

    private String songSheet;

    public CollectToSongSheetAdapter(Context context, List<MySongSheet> mySongSheetList, String songSheet) {
        this.context = context;
        this.mySongSheetList = mySongSheetList;
        this.songSheet = songSheet;
    }

    @Override
    public int getCount() {
        return mySongSheetList == null ? 0 : mySongSheetList.size();
    }

    @Override
    public Object getItem(int i) {
        return mySongSheetList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {

        MySongSheetView mySongSheetView = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_collect_to_song_sheet, null);
            mySongSheetView = new MySongSheetView(convertView);
            convertView.setTag(mySongSheetView);

        } else {
            mySongSheetView = (MySongSheetView) convertView.getTag();
        }

        List<ContentBean> songSheetDetails = SongSheetDetailManager.getInstance().getSongSheetDetailList(context,
                mySongSheetList.get(i).getSongSheetName());

        if (songSheetDetails.size() == 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mySongSheetView.ivAdapterCollectToSongSheet.getLayoutParams();
            params.width = DisplayUtils.dp2px(context, 30);
            params.height = DisplayUtils.dp2px(context, 30);
            mySongSheetView.ivAdapterCollectToSongSheet.setLayoutParams(params);
        } else {

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mySongSheetView.ivAdapterCollectToSongSheet.getLayoutParams();
            params.width = DisplayUtils.dp2px(context, 40);
            params.height = DisplayUtils.dp2px(context, 40);
            mySongSheetView.ivAdapterCollectToSongSheet.setLayoutParams(params);

            //使用Glide第三方框架加载图片
            Glide.with(context)
                    .load(songSheetDetails.get(songSheetDetails.size() - 1).getPic_url())
                    .placeholder(R.drawable.album_hidden)//显示默认图片
                    .into(mySongSheetView.ivAdapterCollectToSongSheet);
        }

        mySongSheetView.tvAdapterCollectToSongSheetTitle.setText(mySongSheetList.get(i).getSongSheetName());
        mySongSheetView.tvAdapterCollectToSongSheetCount.setText(songSheetDetails.size() + "首");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySongSheetListener.goToDetail(mySongSheetList.get(i));
            }
        });

        return convertView;
    }

    public void setMySongSheetListener(MySongSheetListener mySongSheetListener) {
        this.mySongSheetListener = mySongSheetListener;
    }

    public interface MySongSheetListener {
        void goToDetail(MySongSheet mySongSheet);
    }


    static class MySongSheetView {
        @Bind(R.id.iv_adapter_collect_to_song_sheet)
        ImageView ivAdapterCollectToSongSheet;
        @Bind(R.id.tv_adapter_collect_to_song_sheet_title)
        TextView tvAdapterCollectToSongSheetTitle;
        @Bind(R.id.tv_adapter_collect_to_song_sheet_count)
        TextView tvAdapterCollectToSongSheetCount;

        MySongSheetView(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
