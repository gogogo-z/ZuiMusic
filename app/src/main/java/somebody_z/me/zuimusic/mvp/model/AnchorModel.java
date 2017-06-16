package somebody_z.me.zuimusic.mvp.model;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.base.IModel;
import somebody_z.me.zuimusic.mvp.model.bean.AnchorRadioBean;
import somebody_z.me.zuimusic.utils.LogUtil;
import somebody_z.me.zuimusic.utils.http.ApiService;
import somebody_z.me.zuimusic.utils.http.ServiceFactory;

/**
 * Created by Huanxing Zeng on 2017/1/11.
 * email : zenghuanxing123@163.com
 */
public class AnchorModel implements IModel {
    private ApiService apiService;

    private int[] imageList = new int[]{
            R.drawable.anchor_star_be_anchor,
            R.drawable.anchor_music_story,
            R.drawable.anchor_creat_cover,
            R.drawable.anchor_emotional,
            R.drawable.anchor_talk_show,
            R.drawable.anchor_sound_novel,
            R.drawable.anchor_text_book,
            R.drawable.anchor_historical_human,
            R.drawable.anchor_foreign_language_world,
            R.drawable.anchor_3d_electron,
            R.drawable.anchor_quadratic_element,
            R.drawable.anchor_education,
            R.drawable.anchor_trip_city,
            R.drawable.anchor_baby,
            R.drawable.anchor_entertainment_television,
            R.drawable.anchor_broadcasting_play,
            R.drawable.anchor_cross_talk,
            R.drawable.anchor_i_be_anchor
    };
    private String[] nameList = new String[]{
            "明星做主播",
            "音乐故事",
            "创作|翻唱",
            "情感调频",
            "脱口秀",
            "有声小说",
            "美文读物",
            "人文历史",
            "外语世界",
            "3D电子",
            "二次元",
            "校园|教育",
            "旅途|城市",
            "亲子宝贝",
            "娱乐|影视",
            "广播剧",
            "相声曲艺",
            "我要做主播"
    };

    public int[] getImageData() {
        return imageList;
    }

    public String[] getNameData() {
        return nameList;
    }

    //获取主播电台
    public void getAnchorRadio(Subscription subscription, int num, final GetAnchorRadioListener getAnchorRadioListener) {
        apiService = ServiceFactory.getInstance().createService(ApiService.class);

        subscription = apiService.getAnchorRadio(Constants.ANCHOR_RADIO, num).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AnchorRadioBean>() {
                    @Override
                    public void onCompleted() {
                        // TODO Auto-generated method stub
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        // TODO Auto-generated method stub
                        //使用try catch 避免出现空指针异常
                        try {
                            LogUtil.e(arg0.toString());
                            getAnchorRadioListener.getFail("获取数据失败，请稍后重试。");
                        } catch (Exception e) {
                            LogUtil.e(e.toString());
                        }
                    }

                    @Override
                    public void onNext(AnchorRadioBean anchorRadioBean) {
                        // TODO Auto-generated method stu
                        getAnchorRadioListener.getSuccess(anchorRadioBean);
                    }
                });
    }

    public interface GetAnchorRadioListener {
        void getSuccess(AnchorRadioBean anchorRadioBean);

        void getFail(String msg);
    }
}
