package somebody_z.me.zuimusic.mvp.view.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author HuanxingZeng
 * 
 * @version 创建时间：2016年12月26日 上午10:01:20
 */
public class CommPagerAdapter extends PagerAdapter {

	private List<ImageView> imageViews;
	private List<Integer> imageList;

	public CommPagerAdapter(List<ImageView> imageViews, List<Integer> imageList) {
		// TODO Auto-generated constructor stub
		this.imageViews = imageViews;
		this.imageList = imageList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageViews == null ? 0 : imageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		ImageView imageView = imageViews.get(position);

		imageView.setImageResource(imageList.get(position));

		container.addView(imageView);

		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ImageView imageView = imageViews.get(position);
		container.removeView(imageView);
	}

}