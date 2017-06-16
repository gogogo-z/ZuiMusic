package somebody_z.me.zuimusic.widget.songlistdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.utils.DisplayUtils;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/16.
 */
public class SongListAdapter extends BaseAdapter {

    private Context context;
    private List<ContentBean> songList;
    private ContentBean currSong;

    private OnClickListener onClickListener;

    public SongListAdapter(Context context, List<ContentBean> songList, ContentBean currSong) {
        this.context = context;
        this.songList = songList;
        this.currSong = currSong;
    }

    @Override
    public int getCount() {
        return songList == null ? 0 : songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_song_list, null);

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_song_list_title);

            viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tv_song_list_author);

            viewHolder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_song_list_delete);

            viewHolder.rlDelete = (RelativeLayout) convertView.findViewById(R.id.rl_song_list_delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(songList.get(position).getTitle());
        viewHolder.tvAuthor.setText(" - " + songList.get(position).getAuthor());

        if (songList.get(position) == currSong) {
            viewHolder.tvTitle.setTextColor(context.getResources().getColor(R.color.colorRed));
            viewHolder.tvAuthor.setTextColor(context.getResources().getColor(R.color.colorRed));
        } else {
            viewHolder.tvTitle.setTextColor(context.getResources().getColor(R.color.colorBlack));
            viewHolder.tvAuthor.setTextColor(context.getResources().getColor(R.color.Gray));
        }

        viewHolder.rlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.delete(songList.get(position));
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.itemClick(songList.get(position));
            }
        });

        return convertView;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {

        void itemClick(ContentBean contentBean);

        void delete(ContentBean contentBean);

    }

    public void refreshList(List<ContentBean> list, ContentBean currSong) {
        songList = list;
        this.currSong = currSong;
        notifyDataSetChanged();
    }

    public final class ViewHolder {

        private TextView tvTitle;
        private TextView tvAuthor;
        private ImageView ivDelete;
        private RelativeLayout rlDelete;

    }
}
