package in.ignas.raselEntAdmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import java.io.*;
import android.net.ConnectivityManager;

public class MainActivity extends Activity {

    private WebView mWebView;
    Context context = MainActivity.this;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        
        // AssetManager am = context.getAssets();
        // InputStream is = am.open("a.html");


        



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // mWebView = findViewById(R.id.activity_main_webview);
        WebView mWebView = (WebView) findViewById(R.id.activity_main_webview);
        
        mWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webSettings.setSupportMultipleWindows(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setSupportZoom(true);
        webSettings.setDatabaseEnabled(true);
        
        webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(1);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        // SetWebView(this.mWebView);

        // REMOTE RESOURCE
        // mWebView.loadUrl("file:///android_asset/a.html");

        if (checkInternetConnection(this)){
            mWebView.loadUrl("https://app.rasele.net/vaia/");
        }
        else{
            try {
                // AssetManager assetManager = this.getAssets();
                // filename = 'a.html';
                Resources resources = getResources();
                InputStream stream2 = resources.openRawResource(R.raw.sad);
                InputStream stream = resources.openRawResource(R.raw.conn);
                BufferedReader r = new BufferedReader(new InputStreamReader(stream));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append("\n");
                }
                mWebView.loadDataWithBaseURL(null, total.toString(), "text/html", "UTF-8", null);
            } 
            catch (Exception xxx) { 
                // mWebView.loadDataWithBaseURL(null, "failed", "text/html", "UTF-8", null);
            }
        }
        

        // LOCAL RESOURCE
        // mWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void SetWebView(WebView webView) {
        WebSettings settings = webView.getSettings();

        settings.setSupportMultipleWindows(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setSupportZoom(true);
        settings.setDatabaseEnabled(true);
        
        settings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(1);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
    }
}
