package com.example.user.demo_float_drawerlayout_n;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
/**
 * Created by user on 2016/6/25.
 */
public class location_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        //=========圖片放大功能地要code==========
        RelativeLayout linearLayout=(RelativeLayout)findViewById(R.id.location_linear);
        PathView pathView=new PathView(this);
        linearLayout.addView(pathView);
        /*
        Button button=(Button)findViewById(R.id.testbutton);
        button.setOnClickListener(new check_button());
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.location_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //======DrawerLayout===================
        mDrawerLayout = (DrawerLayout) findViewById(R.id.location_drawer_layout);

        //==========ActionBar的選單觸發code=========
        final Intent intent = new Intent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.location_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_home:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        location_Activity.this.finish();
                        break;
                    case R.id.navigation_item_news:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(location_Activity.this, new_activity.class);
                        startActivity(intent);
                        location_Activity.this.finish();
                        break;
                    case R.id.navigation_item_information:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(location_Activity.this, Light_infor_Activity.class);
                        startActivity(intent);
                        location_Activity.this.finish();
                        break;
                    case R.id.navigation_item_location:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.navigation_item_translate:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(location_Activity.this, transport_activity.class);
                        startActivity(intent);
                        location_Activity.this.finish();
                        break;
                    case R.id.navigation_item_mapfunc:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(location_Activity.this, MapFuncListActivity.class);
                        startActivity(intent);
                        location_Activity.this.finish();
                        break;
                    case R.id.navigation_item_safe:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(location_Activity.this, GPSget.class);
                        startActivity(intent);
                        location_Activity.this.finish();
                        break;
                    case R.id.navigation_item_channellist:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(location_Activity.this, Channel_list_Acitvity.class);
                        startActivity(intent);
                        location_Activity.this.finish();
                        break;
                    case R.id.navigation_item_browser:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(location_Activity.this, Web_Activity.class);
                        startActivity(intent);
                        location_Activity.this.finish();
                        break;
                    case R.id.navigation_item_contact_us:
                        Toast.makeText(location_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(location_Activity.this, Contact_us_Activity.class);
                        startActivity(intent);
                        location_Activity.this.finish();

                        break;
                    default:
                        return true;
                }

                return true;
            }
        });


    }


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
