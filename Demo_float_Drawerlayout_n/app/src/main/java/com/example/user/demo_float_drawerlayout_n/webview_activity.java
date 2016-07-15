package com.example.user.demo_float_drawerlayout_n;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * Created by zhengwei on 2016/6/26.
 */

public class webview_activity extends AppCompatActivity {
    private WebView webframe;
    private Button  btback,bthome;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_interface);
        //-----------------view set-*-------------------------------------------------------------------------
        webframe=(WebView)findViewById(R.id.mybrowser);

        //--------------------------web-----------------------------------------------------------------
        final Activity activity = this;
        webframe.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                Log.v("Webchrome","loading");
                activity.setTitle("Loading...");
                activity.setProgress(progress * 100);
                if(progress == 100)
                    activity.setTitle(R.string.app_name);
            }

        });
        webframe.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {                 // Handle the error
                Log.v("WebViewClient", description);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.v("WebViewClient-url", url);
                view.loadUrl(url);
                return true;
            }
        });
        webframe.getSettings().setJavaScriptEnabled(true);
        webframe.loadUrl(transport_activity.url);
        Log.v("Web","loading success");
        //----------------------------------button--------------------------------------

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK && webframe.canGoBack()){
            webframe.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
