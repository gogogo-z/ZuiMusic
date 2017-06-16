package somebody_z.me.zuimusic.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
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
import somebody_z.me.zuimusic.common.Constants;
import somebody_z.me.zuimusic.mvp.base.BaseActivity;
import somebody_z.me.zuimusic.mvp.base.BasePresenter;

/**
 * 有些机型通过webview.goBack()回退的时候,并没有触发onReceiveTitle(),
 * 导致标题依然是子网页的标题，故在onPageFinished(）中设置标题。
 * PS：onPageFinished()在API19中会多调用一次，不可处理复杂逻辑
 * <p/>
 * Created by Huanxing Zeng on 2017/2/3.
 * email : zenghuanxing123@163.com
 */
public class WebBrowserActivity extends BaseActivity {
    @Bind(R.id.ll_web_back)
    LinearLayout llWebBack;
    @Bind(R.id.tv_web_title)
    TextView tvWebTitle;
    @Bind(R.id.pb_web)
    ProgressBar progressBar;
    @Bind(R.id.wv_content)
    WebView webView;

    private String url;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra(Constants.URL);

        WebSettings settings = webView.getSettings();
        webView.setWebChromeClient(fastWebChromeClient);
        webView.setWebViewClient(fastWebViewClient);

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
        settings.setLoadWithOverviewMode(true);
        //settings.setSupportZoom(true); // 允许缩放
        //settings.setBuiltInZoomControls(true); // 原网页基础上缩放
        settings.setUseWideViewPort(true); // 任意比例缩放

        if (url != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @OnClick(R.id.ll_web_back)
    public void onClick() {
        if (webView.canGoBack() && !webView.getUrl().equals(url)) {
            webView.goBackOrForward(-1);
        } else {
            finish();
        }
    }

    public static void showWebActivity(Context context, String url) {
        Intent intent = new Intent(context, WebBrowserActivity.class);
        intent.putExtra(Constants.URL, url);
        context.startActivity(intent);
    }

    private WebChromeClient fastWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, final int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setProgress(newProgress);
                progressBar.setVisibility(View.VISIBLE);
            }
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
                WebBrowserActivity.this.startActivity(intent);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            String title = view.getTitle();
            tvWebTitle.setText(title);
        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (webView.canGoBack() && !webView.getUrl().equals(url)) {
                webView.goBackOrForward(-1);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
