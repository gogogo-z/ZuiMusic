package somebody_z.me.zuimusic.mvp.base;

import java.lang.ref.WeakReference;

/**
 * Created by Huanxing Zeng on 2016/12/28.
 * email : zenghuanxing123@163.com
 */
public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter {

    private WeakReference actReference;
    protected V iView;
    protected M iModel;

    public M getiModel() {
        iModel = loadModel(); // 使用前先进行初始化
        return iModel;
    }

    @Override
    public void attachView(IView iView) {
        actReference = new WeakReference(iView);
    }

    @Override
    public void detachView() {
        if (actReference != null) {
            actReference.clear();
            actReference = null;
        }
    }

    @Override
    public V getIView() {
        return (V) actReference.get();
    }

    public abstract M loadModel();
}