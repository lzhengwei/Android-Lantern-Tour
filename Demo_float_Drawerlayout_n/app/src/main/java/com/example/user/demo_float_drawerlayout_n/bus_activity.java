package com.example.user.demo_float_drawerlayout_n;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by zhengwei on 2016/6/28.
 */
public class bus_activity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_layout);
        final Button btsearch=(Button)findViewById(R.id.button2);
        btsearch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                transport_activity.url="http://www.taisibus.com/content/view/184/80/";
                Intent intent = new Intent();
                intent.setClass(bus_activity.this, webview_activity.class);
                startActivity(intent);
            }
        });
    }
}
