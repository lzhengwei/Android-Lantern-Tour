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
import android.widget.Toast;

/**
 * Created by user on 2016/6/26.
 */
public class Contact_us_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_layout);

        /*
        Button button=(Button)findViewById(R.id.testbutton);
        button.setOnClickListener(new check_button());
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_us_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //======DrawerLayout===================
        mDrawerLayout = (DrawerLayout) findViewById(R.id.about_us_drawer_layout);
        //==========ActionBar的選單觸發code=========
        final Intent intent = new Intent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.about_us_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_home:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        Contact_us_Activity.this.finish();
                        break;
                    case R.id.navigation_item_news:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Contact_us_Activity.this, new_activity.class);
                        startActivity(intent);
                        Contact_us_Activity.this.finish();
                    case R.id.navigation_item_information:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Contact_us_Activity.this, Light_infor_Activity.class);
                        startActivity(intent);
                        Contact_us_Activity.this.finish();
                        break;
                    case R.id.navigation_item_location:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Contact_us_Activity.this, location_Activity.class);
                        startActivity(intent);
                        Contact_us_Activity.this.finish();
                        break;
                    case R.id.navigation_item_translate:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Contact_us_Activity.this, transport_activity.class);
                        startActivity(intent);
                        Contact_us_Activity.this.finish();
                        break;
                    case R.id.navigation_item_mapfunc:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Contact_us_Activity.this, MapFuncListActivity.class);
                        startActivity(intent);
                        Contact_us_Activity.this.finish();
                        break;
                    case R.id.navigation_item_safe:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Contact_us_Activity.this, GPSget.class);
                        startActivity(intent);
                        Contact_us_Activity.this.finish();
                        break;
                    case R.id.navigation_item_channellist:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Contact_us_Activity.this, Channel_list_Acitvity.class);
                        startActivity(intent);
                        Contact_us_Activity.this.finish();
                        break;
                    case R.id.navigation_item_browser:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Contact_us_Activity.this, Web_Activity.class);
                        startActivity(intent);
                        Contact_us_Activity.this.finish();
                        break;
                    case R.id.navigation_item_contact_us:
                        Toast.makeText(Contact_us_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
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
