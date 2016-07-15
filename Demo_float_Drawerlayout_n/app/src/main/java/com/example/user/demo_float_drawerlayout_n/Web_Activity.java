package com.example.user.demo_float_drawerlayout_n;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


/**
 * Created by user on 2016/6/25.
 */


public class Web_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        webView=(WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.taiwan.net.tw/2016taiwanlantern/");

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }

        });
        //=======ToolBar(Draw)=================
        Toolbar toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //======DrawerLayout===================
        mDrawerLayout = (DrawerLayout) findViewById(R.id.web_drawer_layout);
        //==========ActionBar的選單觸發code=========
        final Intent intent = new Intent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.web_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.navigation_item_home:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        Web_Activity.this.finish(); //跳出回home介面
                        break;
                    case R.id.navigation_item_news:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Web_Activity.this, new_activity.class);
                        startActivity(intent);
                        Web_Activity.this.finish();
                        break;
                    case R.id.navigation_item_information:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Web_Activity.this, Light_infor_Activity.class);
                        startActivity(intent);
                        Web_Activity.this.finish();
                        break;
                    case R.id.navigation_item_location:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Web_Activity.this, location_Activity.class);
                        startActivity(intent);
                        Web_Activity.this.finish();
                        break;
                    case R.id.navigation_item_translate:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Web_Activity.this, transport_activity.class);
                        startActivity(intent);
                        Web_Activity.this.finish();
                        break;
                    case R.id.navigation_item_mapfunc:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Web_Activity.this, MapFuncListActivity.class);
                        startActivity(intent);
                        Web_Activity.this.finish();
                        break;
                    case R.id.navigation_item_safe:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Web_Activity.this, GPSget.class);
                        startActivity(intent);
                        Web_Activity.this.finish();
                        break;
                    case R.id.navigation_item_channellist:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Web_Activity.this, Channel_list_Acitvity.class);
                        startActivity(intent);
                        Web_Activity.this.finish();
                        break;
                    case R.id.navigation_item_browser:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_contact_us:
                        Toast.makeText(Web_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Web_Activity.this, Contact_us_Activity.class);
                        startActivity(intent);
                        Web_Activity.this.finish();
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    //=============ActionBar需要============
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
