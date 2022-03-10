package in.ignas.raselEntAdmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());

        

        

       

        // REMOTE RESOURCE
        // mWebView.loadUrl("file:///android_asset/a.html");

        if (checkInternetConnection(this)){
            mWebView.loadUrl("https://rasele.net/vaia");
        }
        else{
            try {
                // AssetManager assetManager = this.getAssets();
                // filename = 'a.html';
                Resources resources = getResources();
                InputStream stream = resources.openRawResource(R.raw.a);
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
}
