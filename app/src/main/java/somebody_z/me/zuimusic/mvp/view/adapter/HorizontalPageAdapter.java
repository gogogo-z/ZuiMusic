package somebody_z.me.zuimusic.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.utils.DisplayUtils;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Huanxing Zeng on 2017/1/12.
 * email : zenghuanxing123@163.com
 */
public class HorizontalPageAdapter extends RecyclerView.Adapter<HorizontalPageAdapter.MyViewHolder> {

    private int[] imageList = new int[]{};
    private String[] nameList = new String[]{};

    private ItemClickListener itemClickListener;

    private Context mContext;

    public HorizontalPageAdapter(Context context, int[] imageList, String[] nameList) {
        this.imageList = imageList;
        this.nameList = nameList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_anchor_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //4等分
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(mContext) / 4,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        holder.itemView.setLayoutParams(params);

        holder.textView.setText(nameList[position]);
        holder.imageView.setImageResource(imageList[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.click(nameList[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_adapter_anchor);
            imageView = (ImageView) itemView.findViewById(R.id.iv_adapter_anchor);
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void click(String name);
    }
}
