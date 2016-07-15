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
 * Created by user on 2016/6/27.
 */
public class drive_self_activity extends AppCompatActivity {
    private WebView webframe;
    private Button btback,bthome;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drive_self_layout);
    }

}