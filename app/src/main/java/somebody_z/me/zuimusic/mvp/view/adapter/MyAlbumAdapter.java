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
import somebody_z.me.zuimusic.mvp.model.bean.AlbumDetailBean;

/**
 * Created by Huanxing Zeng on 2017/1/17.
 * email : zenghuanxing123@163.com
 */
public class MyAlbumAdapter extends BaseAdapter {

    private Context context;

    private List<AlbumDetailBean.AlbumInfoBean> myAlbumList;

    private MyAlbumListener myAlbumListener;

    public MyAlbumAdapter(Context context, List<AlbumDetailBean.AlbumInfoBean> myAlbumList) {
        this.context = context;
        this.myAlbumList = myAlbumList;
    }

    @Override
    public int getCount() {
        return myAlbumList == null ? 0 : myAlbumList.size();
    }

    @Override
    public Object getItem(int i) {
        return myAlbumList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {

        MyAlbumView myAlbumView = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_my_song_sheet, null);
            myAlbumView = new MyAlbumView(convertView);
            convertView.setTag(myAlbumView);

        } else {
            myAlbumView = (MyAlbumView) convertView.getTag();
        }

        myAlbumView.tvAdapterMySongSheetName.setText(myAlbumList.get(i).getTitle());

        //使用Glide第三方框架加载图片
        Glide.with(context)
                .load(myAlbumList.get(i).getPic_big())
                .placeholder(R.drawable.album_hidden)//显示默认图片
                .into(myAlbumView.ivAdapterMySongSheet);

        myAlbumView.tvAdapterMySongSheetCount.setText(myAlbumList.get(i).getAuthor());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAlbumListener.goToDetail(myAlbumList.get(i));
            }
        });

        myAlbumView.llAdapterMySongSheetOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAlbumListener.goToOption(myAlbumList.get(i));
            }
        });

        return convertView;
    }

    public void setMySongSheetListener(MyAlbumListener myAlbumListener) {
        this.myAlbumListener = myAlbumListener;
    }

    public interface MyAlbumListener {
        void goToDetail(AlbumDetailBean.AlbumInfoBean albumInfoBean);

        void goToOption(AlbumDetailBean.AlbumInfoBean albumInfoBean);
    }

    static class MyAlbumView {
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

        MyAlbumView(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
