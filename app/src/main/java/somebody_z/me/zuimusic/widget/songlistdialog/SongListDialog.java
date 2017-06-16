package somebody_z.me.zuimusic.widget.songlistdialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import somebody_z.me.zuimusic.MyApplication;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.model.bean.ContentBean;
import somebody_z.me.zuimusic.service.ServiceManager;
import somebody_z.me.zuimusic.utils.ScreenUtil;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/16.
 */
public class SongListDialog {

    private ServiceManager mServiceManager = null;

    private AlertDialog dialog;

    private SongListAdapter mAdapter;

    private List<ContentBean> list;

    private TextView textView;

    public void showDialog(Context mContext) {

        mServiceManager = MyApplication.mServiceManager;

        list = mServiceManager.getMusicList();

        ContentBean contentBean = mServiceManager.getCurMusic();

        // 利用layoutInflater获得View
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_song_list, null);

        textView = (TextView) view.findViewById(R.id.tv_song_list);

        ListView listView = (ListView) view.findViewById(R.id.lv_song_list);

        textView.setText("播放列表（" + list.size() + "）");

        mAdapter = new SongListAdapter(mContext, list, contentBean);

        listView.setAdapter(mAdapter);

        int curIndex = 0;

        for (int i = 0; i < list.size(); i++) {
            if (contentBean == list.get(i)) {
                curIndex = i;
            }
        }

        if (curIndex > 3) {
            listView.setSelection(curIndex - 3);
        }

        mAdapter.setOnClickListener(new SongListAdapter.OnClickListener() {
            @Override
            public void itemClick(ContentBean contentBean) {
                //点击播放
                int index = 0;

                for (int i = 0; i < list.size(); i++) {
                    if (contentBean == list.get(i)) {
                        index = i;
                    }
                }

                mServiceManager.play(index);

                //刷新
                mAdapter.refreshList(list, contentBean);

            }

            @Override
            public void delete(ContentBean contentBean) {
                //点击删除
                int index = 0;

                for (int i = 0; i < list.size(); i++) {
                    if (contentBean == list.get(i)) {
                        index = i;
                    }
                }

                list.remove(index);

                //刷新
                mAdapter.refreshList(list, contentBean);

                mServiceManager.refreshMusicList(list);

                textView.setText("播放列表（" + list.size() + "）");
            }
        });


        //创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Dialog_Bg_White);

        builder.setView(view);

        dialog = builder.create();

        dialog.show();

        //设置宽高，需在show（）之后设置，否则没有效果。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ScreenUtil.getScreenWidth(mContext);
        layoutParams.height = ScreenUtil.getScreenHeight(mContext) * 31 / 50;
        window.setAttributes(layoutParams);
    }

    public void closeDialog() {
        dialog.cancel();
    }

}
