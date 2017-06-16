package somebody_z.me.zuimusic.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.view.adapter.CommFragmentPagerAdapter;
import somebody_z.me.zuimusic.mvp.view.fragment.GuideFragment;
import somebody_z.me.zuimusic.widget.IndexView;

/**
 * Created by Huanxing Zeng on 2017/1/25.
 * email : zenghuanxing123@163.com
 */
public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private IndexView indexView;

    /**
     * 视频id
     */
    private int[] videoIds = new int[]{
            R.raw.splash_1,
            R.raw.splash_2,
            R.raw.splash_4,
            R.raw.splash_5
    };

    /**
     * 左边的图片资源ID
     */
    private int[] ivLeftIds = new int[]
            {
                    R.drawable.guide_anim_1_2,
                    R.drawable.guide_anim_2_2,
                    R.drawable.guide_anim_3_2,
                    R.drawable.guide_anim_4_2
            };
    /**
     * 右边的图片资源ID
     */
    private int[] ivRightIds = new int[]
            {
                    R.drawable.guide_anim_1_1,
                    R.drawable.guide_anim_2_1,
                    R.drawable.guide_anim_3_1,
                    R.drawable.guide_anim_4_1
            };

    /**
     * 用来记录上一次显示的页面索引
     */
    private int lastPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide);

        //控件初始化
        viewPager = (ViewPager) findViewById(R.id.guide_vp);
        indexView = (IndexView) findViewById(R.id.guide_index_view);

        //获取数据源
        final List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            GuideFragment fragment = new GuideFragment(videoIds[i], ivLeftIds[i], ivRightIds[i], i);
            list.add(fragment);
        }

        //设置adapter
        CommFragmentPagerAdapter adapter = new CommFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //隐藏上一次显示的页面的图片
                GuideFragment lastFragment = (GuideFragment) list.get(lastPosition);
                lastFragment.hideImages();

                //显示当前页面的动画
                GuideFragment fragment = (GuideFragment) list.get(position);
                fragment.showAnim();

                //重新记录一下索引
                lastPosition = position;
                //滑动一下，切换一下索引
                indexView.setCurrIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.guide_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到主页面
                Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}

