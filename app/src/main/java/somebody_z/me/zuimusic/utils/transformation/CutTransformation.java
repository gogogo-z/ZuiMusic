package somebody_z.me.zuimusic.utils.transformation;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 图片的裁剪
 * Created by Huanxing Zeng on 2017/1/3.
 * email : zenghuanxing123@163.com
 */
public class CutTransformation extends BitmapTransformation {
    private float widthPercent, heightPercent;

    private Context context;

    public CutTransformation(Context context, float widthPercent, float heightPercent) {
        super(context);
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
        this.context = context;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return cutCrop(toTransform);
    }

    /**
     * 图片的裁剪
     *
     * @param source
     * @return
     */
    public Bitmap cutCrop(Bitmap source) {

        Bitmap bitmap = Bitmap.createBitmap(source, 0, (int) (source.getHeight() * widthPercent), source.getWidth(), (int) (source.getHeight() * heightPercent), null, false);

        //记得及时回收，避免内存泄漏
        source.recycle();
        source = null;

        return bitmap;

    }

    @Override
    public String getId() {
        return "cut";
    }
}
