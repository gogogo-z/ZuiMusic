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
import somebody_z.me.zuimusic.db.MySongSheet;
import somebody_z.me.zuimusic.db.SongSheetDetailManager;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.mvp.model.bean.SongSheetDetail;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Huanxing Zeng on 2017/1/17.
 * email : zenghuanxing123@163.com
 */
public class MusicMySongSheetAdapter extends BaseAdapter {

    private Context context;

    private List<MySongSheet> mySongSheetList;

    private MySongSheetListener mySongSheetListener;

    private String songSheet;

    public MusicMySongSheetAdapter(Context context, List<MySongSheet> mySongSheetList, String songSheet) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_my_song_sheet, null);
            mySongSheetView = new MySongSheetView(convertView);
            convertView.setTag(mySongSheetView);

        } else {
            mySongSheetView = (MySongSheetView) convertView.getTag();
        }

        mySongSheetView.tvAdapterMySongSheetName.setText(mySongSheetList.get(i).getSongSheetName());

        if (mySongSheetList.get(i).getSongSheetName().equals(songSheet)) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mySongSheetView.ivAdapterMySongSheetOption.getLayoutParams();
            params.width = DisplayUtils.dp2px(context, 15);
            params.setMargins(0, 0, DisplayUtils.dp2px(context, 17), 0);
            mySongSheetView.ivAdapterMySongSheetOption.setLayoutParams(params);
            mySongSheetView.llAdapterMySongSheetOption.setEnabled(false);
            mySongSheetView.ivAdapterMySongSheetOption.setBackgroundResource(R.drawable.song_playing);
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mySongSheetView.ivAdapterMySongSheetOption.getLayoutParams();
            params.width = DisplayUtils.dp2px(context, 3);
            params.setMargins(0, 0, DisplayUtils.dp2px(context, 23), 0);
            mySongSheetView.ivAdapterMySongSheetOption.setLayoutParams(params);
            mySongSheetView.llAdapterMySongSheetOption.setEnabled(true);
            mySongSheetView.ivAdapterMySongSheetOption.setBackgroundResource(R.drawable.music_create_song_sheet);
        }

        List<ContentBean> songSheetDetails = SongSheetDetailManager.getInstance().getSongSheetDetailList(context,
                mySongSheetList.get(i).getSongSheetName());

        if (songSheetDetails.size() == 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtils.dp2px(context, 40), DisplayUtils.dp2px(context, 40));
            mySongSheetView.ivAdapterMySongSheet.setLayoutParams(params);
        } else {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtils.dp2px(context, 50), DisplayUtils.dp2px(context, 50));
            mySongSheetView.ivAdapterMySongSheet.setLayoutParams(params);

            //使用Glide第三方框架加载图片
            Glide.with(context)
                    .load(songSheetDetails.get(songSheetDetails.size() - 1).getPic_url())
                    .placeholder(R.drawable.album_hidden)//显示默认图片
                    .into(mySongSheetView.ivAdapterMySongSheet);

        }

        mySongSheetView.tvAdapterMySongSheetCount.setText(songSheetDetails.size() + "首");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySongSheetListener.goToDetail(mySongSheetList.get(i));
            }
        });

        mySongSheetView.llAdapterMySongSheetOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySongSheetListener.goToOption(mySongSheetList.get(i));
            }
        });

        return convertView;
    }

    public void setMySongSheetListener(MySongSheetListener mySongSheetListener) {
        this.mySongSheetListener = mySongSheetListener;
    }

    public interface MySongSheetListener {
        void goToDetail(MySongSheet mySongSheet);

        void goToOption(MySongSheet mySongSheet);
    }

    static class MySongSheetView {
        @Bind(R.id.iv_adapter_my_song_sheet)
        ImageView ivAdapterMySongSheet;
        @Bind(R.id.tv_adapter_my_song_sheet_name)
        TextView tvAdapterMySongSheetName;
        @Bind(R.id.tv_adapter_my_song_sheet_count)
        TextView tvAdapterMySongSheetCount;
        @Bind(R.id.iv_adapter_my_song_sheet_option)
        ImageView ivAdapterMySongSheetOption;
        @Bind(R.id.ll_adapter_my_song_sheet_option)
        LinearLayout llAdapterMySongSheetOption;

        MySongSheetView(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
