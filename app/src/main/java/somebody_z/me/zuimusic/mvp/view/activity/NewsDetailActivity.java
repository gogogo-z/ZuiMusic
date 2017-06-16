package somebody_z.me.zuimusic.mvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import somebody_z.me.zuimusic.R;
import somebody_z.me.zuimusic.mvp.Contract.NewsDetailContract;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.presenter.NewsDetailPresenter;

/**
 * Created by Zeng Huanxing
 * <p/>
 * on 2017/4/17.
 */
public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> implements NewsDetailContract.NewsDetailView {
    @Bind(R.id.ll_new_detail_back)
    LinearLayout llNewDetailBack;
    @Bind(R.id.tv_news_detail_title)
    TextView tvNewsDetailTitle;
    @Bind(R.id.pb_news_detail)
    ProgressBar pbNewsDetail;
    @Bind(R.id.wv_news_detail_content)
    WebView wvNewsDetailContent;

    @Override
    protected NewsDetailPresenter loadPresenter() {
        return new NewsDetailPresenter();
    }

    @Override
    protected void initData() {

        mPresenter.getDetail();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mPresenter.init();

        WebSettings settings = wvNewsDetailContent.getSettings();
        wvNewsDetailContent.setWebChromeClient(fastWebChromeClient);
        wvNewsDetailContent.setWebViewClient(fastWebViewClient);

        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 设置定位
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        // 自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //settings.setLoadWithOverviewMode(true);
        //settings.setSupportZoom(true); // 允许缩放
        //settings.setBuiltInZoomControls(true); // 原网页基础上缩放
        //settings.setUseWideViewPort(true); // 任意比例缩放

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onPause() {
        //设置跳转无动画
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @OnClick(R.id.ll_new_detail_back)
    public void onClick() {
        if (wvNewsDetailContent.canGoBack()) {
            wvNewsDetailContent.goBackOrForward(-1);
        } else {
            finish();
        }
    }

    @Override
    public void loadDetail(String content) {
        // 设置WevView要显示的网页
        wvNewsDetailContent.loadDataWithBaseURL(null, content, "text/html", "utf-8",
                null);
    }

    @Override
    public void setNewProgress(int newProgress) {
        pbNewsDetail.setProgress(newProgress);
    }

    @Override
    public void setProgressBarVisible(int visible) {
        pbNewsDetail.setVisibility(visible);
    }

    @Override
    public void setTitle(String title) {
        tvNewsDetailTitle.setText(title);
    }

    private WebChromeClient fastWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mPresenter.setProgressBarVisible(newProgress);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        public void onGeolocationPermissionsHidePrompt() {

        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    };

    private WebViewClient fastWebViewClient = new WebViewClient() {
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.stopLoading();
            view.clearView();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("mailto:") || url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                NewsDetailActivity.this.startActivity(intent);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            String title = view.getTitle();
            mPresenter.setTitle(title);
        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (wvNewsDetailContent.canGoBack()) {
                wvNewsDetailContent.goBackOrForward(-1);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
