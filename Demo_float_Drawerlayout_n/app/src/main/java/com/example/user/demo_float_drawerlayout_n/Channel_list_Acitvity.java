package com.example.user.demo_float_drawerlayout_n;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by user on 2016/6/27.
 */
public class Channel_list_Acitvity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    static LatLng lk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channal_list_layout);

        //=======ToolBar(Draw)=================
        Toolbar toolbar = (Toolbar) findViewById(R.id.channel_list_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //======DrawerLayout===================
        mDrawerLayout = (DrawerLayout) findViewById(R.id.channel_list_drawer_layout);
        //==========ActionBar的選單觸發code=========
        final Intent intent = new Intent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.channel_list_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.navigation_item_home:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        Channel_list_Acitvity.this.finish(); //跳出回home介面
                        break;
                    case R.id.navigation_item_news:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Channel_list_Acitvity.this, new_activity.class);
                        startActivity(intent);
                        Channel_list_Acitvity.this.finish();
                        break;
                    case R.id.navigation_item_information:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Channel_list_Acitvity.this, Light_infor_Activity.class);
                        startActivity(intent);
                        Channel_list_Acitvity.this.finish();
                        break;
                    case R.id.navigation_item_location:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Channel_list_Acitvity.this, location_Activity.class);
                        startActivity(intent);
                        Channel_list_Acitvity.this.finish();
                        break;
                    case R.id.navigation_item_translate:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Channel_list_Acitvity.this, transport_activity.class);
                        startActivity(intent);
                        Channel_list_Acitvity.this.finish();
                        break;
                    case R.id.navigation_item_mapfunc:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Channel_list_Acitvity.this, MapFuncListActivity.class);
                        startActivity(intent);
                        Channel_list_Acitvity.this.finish();
                        break;
                    case R.id.navigation_item_safe:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Channel_list_Acitvity.this, GPSget.class);
                        startActivity(intent);
                        Channel_list_Acitvity.this.finish();
                        break;
                    case R.id.navigation_item_channellist:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_browser:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Channel_list_Acitvity.this, Web_Activity.class);
                        startActivity(intent);
                        Channel_list_Acitvity.this.finish();
                        break;
                    case R.id.navigation_item_contact_us:
                        Toast.makeText(Channel_list_Acitvity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Channel_list_Acitvity.this, Contact_us_Activity.class);
                        startActivity(intent);
                        Channel_list_Acitvity.this.finish();
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });
        final CardView channel=(CardView)findViewById(R.id.channel_cardview_1);

        final int notifyID = 1; // 通知的識別號碼ma
        final int requestCode = notifyID; // PendingIntent的Request Code
        final Intent intentc = new Intent(getApplicationContext(), MapFuncListActivity.class); // 開啟另一個Activity的Intent
        final int flags = PendingIntent.FLAG_UPDATE_CURRENT; // ONE_SHOT：PendingIntent只使用一次；CANCEL_CURRENT：PendingIntent執行前會先結束掉之前的；NO_CREATE：沿用先前的PendingIntent，不建立新的PendingIntent；UPDATE_CURRENT：更新先前PendingIntent所帶的額外資料，並繼續沿用
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext()); // 建立TaskStackBuilder
        stackBuilder.addParentStack(MapFuncListActivity.class); // 加入目前要啟動的Activity，這個方法會將這個Activity的所有上層的Activity(Parents)都加到堆疊中
        stackBuilder.addNextIntent(intentc); // 加入啟動Activity的Intent
        final PendingIntent pendingIntent = stackBuilder.getPendingIntent(requestCode, flags); // 取得PendingIntent
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // 取得系統的通知服務
        final Notification notification = new Notification.Builder(getApplicationContext()).setSmallIcon(R.drawable.launcher_ic).setContentTitle("活動即將開始").setContentText("請盡快到達該活動位置").setContentIntent(pendingIntent).build();

        channel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                notificationManager.notify(notifyID, notification); // 發送通知
                lk=new LatLng(23.69510, 120.536060);

            }
        });


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
