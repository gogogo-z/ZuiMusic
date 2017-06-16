package somebody_z.me.zuimusic.mvp.Contract;

import android.animation.ValueAnimator;
import android.text.Spanned;

/**
 * Created by Huanxing Zeng on 2017/2/17.
 * email : zenghuanxing123@163.com
 */
public class LocalMusicContract {

    public interface LocalMusicView {
        void setBarMarginTop();

        void initScanDialog();

        void setScanBar();

        void clearEffectAnim();

        void setEffectVisible(int visible);

        void updateScannerText(String path);

        void updateScannerResult(String result);

        void initScanAnim();

        void setBackAndGetLrcVisible(int visible);

        void setCancelVisible(int visible);

        void setUriVisible(int visible);

        void setResult(Spanned result);

        void initMoreWindow(int height);
    }

    public interface LocalMusicPresenter {
        void isImmerse();

        void setScanBar();

        void onBackPressed();

        void stopScanAnim(ValueAnimator animator);

        void scanLocalMusic();

        void stopScan();

        void search();

        void refresh();
    }

}
