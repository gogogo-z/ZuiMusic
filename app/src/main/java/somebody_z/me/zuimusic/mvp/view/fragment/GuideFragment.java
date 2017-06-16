package somebody_z.me.zuimusic.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import somebody_z.me.zuimusic.R;

/**
 * Created by Huanxing Zeng on 2017/1/25.
 * email : zenghuanxing123@163.com
 */
public class GuideFragment extends Fragment {

    private int videoId;

    private int ivLeftId, ivRightId;

    private VideoView videoView;

    private ImageView ivLeft, ivRight;
    private int position;

    public GuideFragment() {

    }

    public GuideFragment newInstance(int videoId, int ivLeftId, int ivRightId, int position) {
        GuideFragment fragment = new GuideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("videoId", videoId);
        bundle.putInt("ivLeftId", ivLeftId);
        bundle.putInt("ivRightId", ivRightId);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("ValidFragment")
    public GuideFragment(int videoId, int ivLeftId, int ivRightId, int position) {
        this.videoId = videoId;
        this.ivLeftId = ivLeftId;
        this.ivRightId = ivRightId;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_guide, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        videoView = (VideoView) getView().findViewById(R.id.guide_vv);
        ivLeft = (ImageView) getView().findViewById(R.id.guide_left_iv);
        ivRight = (ImageView) getView().findViewById(R.id.guide_right_iv);

        //设置显示的图片内容
        ivLeft.setImageResource(ivLeftId);
        ivRight.setImageResource(ivRightId);

        //表示当前页是第一个，那么执行动画
        if (position == 0) {
            showAnim();
        }

        //url和uri
        //拼接视频的本地路径
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getActivity().getPackageName() + "/" + videoId);

        //设置循环播放
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        //设置播放路径
        videoView.setVideoURI(uri);
        //开始播放
        videoView.start();
    }

    /**
     * 当页面隐藏的时候调用该方法
     */
    public void hideImages() {
        //用GONE不用INVISIBLE的目的：防止来回滑动太快导致上一页动画没有结束
        ivLeft.setVisibility(View.GONE);
        ivRight.setVisibility(View.GONE);
    }

    /**
     * 页面进入的时候执行动画
     */
    public void showAnim() {
        ivRight.setVisibility(View.INVISIBLE);
        ivLeft.setVisibility(View.INVISIBLE);
        videoView.resume();

        //加载动画
        final Animation animLeft = AnimationUtils.loadAnimation(getContext(), R.anim.guide_iv_left);
        Animation animRight = AnimationUtils.loadAnimation(getContext(), R.anim.guide_iv_right);

        animRight.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //再执行左边的动画
                ivLeft.startAnimation(animLeft);
                //动画结束后要显示出来
                ivRight.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束后要显示出来
                ivLeft.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //执行动画
        //先执行右边的动画
        ivRight.startAnimation(animRight);
    }

    @Override
    public void onPause() {
        super.onPause();

        videoView.pause();
    }

}
