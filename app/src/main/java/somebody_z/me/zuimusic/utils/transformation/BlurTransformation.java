package somebody_z.me.zuimusic.utils.transformation;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * 高斯模糊
 *
 * @param
 * @author HuanxingZeng
 * @version 创建时间：2016年12月4日 下午11:55:58
 */
public class BlurTransformation extends BitmapTransformation {

    private RenderScript rs;

    public BlurTransformation(Context context) {
        super(context);

        // 创建RenderScript内核对象
        rs = RenderScript.create(context);
    }

    @SuppressLint("NewApi")
    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        // 创建一张渲染后的输出图片
        Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        // Allocate memory for Renderscript to work with
        Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SHARED);
        Allocation output = Allocation.createTyped(rs, input.getType());

        // 创建一个模糊效果的RenderScript的工具对象
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 设置blurScript对象的输入内存
        script.setInput(input);

        // 设置渲染的模糊程度, 25f是最大模糊度
        // Set the blur radius
        script.setRadius(25f);

        // 将输出数据保存到输出内存中
        // Start the ScriptIntrinisicBlur
        script.forEach(output);

        // 将数据填充到Allocation中
        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        toTransform.recycle();
        toTransform = null;

        return blurredBitmap;
    }

    @Override
    public String getId() {
        return "blur";
    }
}
