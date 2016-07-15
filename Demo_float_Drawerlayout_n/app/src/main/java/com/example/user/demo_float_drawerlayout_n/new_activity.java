package com.example.user.demo_float_drawerlayout_n;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by zhengwei on 2016/6/28.
 */
public class new_activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_news);

            final CardView card=(CardView)findViewById(R.id.channel_cardview_5);
            Toolbar toolbar = (Toolbar) findViewById(R.id.new_toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            //======DrawerLayout===================
            mDrawerLayout = (DrawerLayout) findViewById(R.id.new_drawer_layout);
            //==========ActionBar的選單觸發code=========
            final Intent intent = new Intent();
            NavigationView navigationView = (NavigationView) findViewById(R.id.new_navigation_view);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.navigation_item_home:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            new_activity.this.finish();
                            break;
                        case R.id.navigation_item_news:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.navigation_item_information:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            intent.setClass(new_activity.this, Light_infor_Activity.class);
                            startActivity(intent);
                            new_activity.this.finish();
                            break;
                        case R.id.navigation_item_location:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            intent.setClass(new_activity.this, location_Activity.class);
                            startActivity(intent);
                            new_activity.this.finish();
                            break;
                        case R.id.navigation_item_translate:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            intent.setClass(new_activity.this, testpage.class);
                            startActivity(intent);
                            new_activity.this.finish();
                            break;
                        case R.id.navigation_item_mapfunc:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            intent.setClass(new_activity.this, MapFuncListActivity.class);
                            startActivity(intent);
                            new_activity.this.finish();
                            break;
                        case R.id.navigation_item_safe:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            intent.setClass(new_activity.this, GPSget.class);
                            startActivity(intent);
                            new_activity.this.finish();
                            break;
                        case R.id.navigation_item_channellist:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            intent.setClass(new_activity.this, Channel_list_Acitvity.class);
                            startActivity(intent);
                            new_activity.this.finish();
                            break;
                        case R.id.navigation_item_browser:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            intent.setClass(new_activity.this, Web_Activity.class);
                            startActivity(intent);
                            new_activity.this.finish();
                            break;
                        case R.id.navigation_item_contact_us:
                            Toast.makeText(new_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            intent.setClass(new_activity.this, Contact_us_Activity.class);
                            startActivity(intent);
                            new_activity.this.finish();
                            break;
                        default:
                            return true;
                    }

                    return true;
                }
            });




            //==========================================================================
            card.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        transport_activity.url="http://udn.com/news/story/7326/1735007-2017%E5%8F%B0%E7%81%A3%E7%87%88%E6%9C%83%E9%AB%98%E9%90%B5%E8%BC%B8%E9%81%8B-%E9%9B%B2%E6%9E%97%E5%89%AF%E7%B8%A3%E9%95%B7%E8%88%87%E8%AD%B0%E5%93%A1%E6%9C%83%E5%8B%98";
                        Intent intent = new Intent();
                        intent.setClass(new_activity.this, webview_activity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.v("card",e.toString());
                    }
                }
            });


        }



}
