package com.example.user.demo_float_drawerlayout_n;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class transport_activity extends AppCompatActivity {
    private WebView webframe;
    private ListView listView;
    private ExpandableListView expandlistview;
    private DrawerLayout mDrawerLayout;
    static public String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_activity);
  //-------------------------------------------------------------------------------------------------
        listView=(ListView)findViewById(R.id.listView);
        final String[] item=new String[] {"台鐵","高鐵","公車","接駁車","自行開車"};
        final Integer[]  item_image= {R.drawable.trainlittle,R.drawable.hsrlittle,R.drawable.buslittle,R.drawable.shuttlebuslittle,R.drawable.carlittle};
        CustomList adapter = new
                CustomList(this,item,item_image);
        listView.setAdapter(adapter);

        final Intent intent2=new Intent();
        //expandlistview.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        url="http://twtraffic.tra.gov.tw/twrail/quicksearch.aspx";
                        new AlertDialog.Builder(transport_activity.this)
                                .setTitle("台鐵交通資訊")
                                .setIcon(R.drawable.trainlittle)
                                .setMessage(item[position])
                                .setPositiveButton("線上時刻查詢", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setClass(transport_activity.this, webview_activity.class);
                                        startActivity(intent);
                                    }

                                }).show();
                        break;
                    case 1:
                        url="https://www.thsrc.com.tw/tw/TimeTable/SearchResult";
                        new AlertDialog.Builder(transport_activity.this)
                                .setTitle("高鐵交通資訊")
                                .setIcon(R.drawable.hsrlittle)
                                .setMessage(item[position])
                                .setPositiveButton("線上時刻查詢", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setClass(transport_activity.this, webview_activity.class);
                                        startActivity(intent);
                                    }

                                }).show();
                        break;
                    case 2:
                        intent2.setClass(transport_activity.this, bus_activity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        intent2.setClass(transport_activity.this, drive_self_activity.class);
                        startActivity(intent2);
                        break;
                    case 4:
                        intent2.setClass(transport_activity.this, Shuttle_Activity.class);
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }


            }
        });
        //--------------------------------------------------------------------------------------
      /*  webframe=(WebView)findViewById(R.id.webView01);
      //  String url = "http://twtraffic.tra.gov.tw/twrail/QuickSearch.aspx";http://twtraffic.tra.gov.tw/twrail/
        String url = "http://twtraffic.tra.gov.tw/twrail/QuickSearch.aspx";
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

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {                 // Handle the error
                Log.v("WebViewClient",description);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                Log.v("WebViewClient-url",url);
                view.loadUrl(url);
                return true;
            }
        });
        webframe.getSettings().setJavaScriptEnabled(true);
        webframe.loadUrl(url);*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.transport_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //======DrawerLayout===================
        mDrawerLayout = (DrawerLayout) findViewById(R.id.transport_drawer_layout);
        //=====GridList============================
        //==========ActionBar的選單觸發code=========
        final Intent intent = new Intent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.transport_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.navigation_item_home:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        transport_activity.this.finish();
                        break;
                    case R.id.navigation_item_news:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(transport_activity.this, new_activity.class);
                        startActivity(intent);
                        transport_activity.this.finish();
                        break;
                    case R.id.navigation_item_information:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(transport_activity.this, Light_infor_Activity.class);
                        startActivity(intent);
                        transport_activity.this.finish();
                        break;
                    case R.id.navigation_item_location:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(transport_activity.this, location_Activity.class);
                        startActivity(intent);
                        transport_activity.this.finish();
                        break;
                    case R.id.navigation_item_translate:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_mapfunc:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(transport_activity.this, MapFuncListActivity.class);
                        startActivity(intent);
                        transport_activity.this.finish();
                        break;
                    case R.id.navigation_item_safe:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(transport_activity.this, GPSget.class);
                        startActivity(intent);
                        transport_activity.this.finish();
                        break;
                    case R.id.navigation_item_channellist:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(transport_activity.this, Channel_list_Acitvity.class);
                        startActivity(intent);
                        transport_activity.this.finish();
                        break;
                    case R.id.navigation_item_browser:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(transport_activity.this, Web_Activity.class);
                        startActivity(intent);
                        transport_activity.this.finish();
                        break;
                    case R.id.navigation_item_contact_us:
                        Toast.makeText(transport_activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(transport_activity.this, Contact_us_Activity.class);
                        startActivity(intent);
                        transport_activity.this.finish();
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
    //======================================================
    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        private final String[] web;
        private final Integer[] imageId;
        public CustomList(Activity context,
                          String[] web, Integer[] imageId) {
            super(context, R.layout.list_single, web);
            this.context = context;
            this.web = web;
            this.imageId = imageId;

        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(web[position]);

            imageView.setImageResource(imageId[position]);
            return rowView;
        }
    }
}
