package com.example.user.demo_float_drawerlayout_n;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 2016/6/25.
 */
public class MapFuncListActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    static Spinner spinner;
    private Button btsearch;
    //====map dim========================
    private GeoPoint currentGeoPoint, markGeopoint;
    static GoogleMap map;     //main map
    private LocationManager myLocationManager;
    private Location mLocation = null;
    LatLng mylk = new LatLng(23.69110, 120.536560),channel_lk;
    private Marker mymark;
    double myLatitude = mylk.latitude, myLongitude = mylk.longitude;
    private String strLocationPrivider = "",type="燈會";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list_layout);

        /*
        Button button=(Button)findViewById(R.id.testbutton);
        button.setOnClickListener(new check_button());
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.maplisttoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        btsearch = (Button) findViewById(R.id.button);
        //======DrawerLayout===================
        mDrawerLayout = (DrawerLayout) findViewById(R.id.map_list_drawer_layout);
        //=====spinner=====================================
        spinner = (Spinner) findViewById(R.id.spinner);
        String[] lunch = {"尋找地點", "附近停車場資訊", "附近加油站資訊", "附近餐廳資訊", "觀光景點"};
        spinner.setAdapter(new CustomSpinner(MapFuncListActivity.this, R.layout.map_list_listlayout_item, lunch));
        //======map========================================================
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }
        if(Channel_list_Acitvity.lk!=null)
        {   map.addMarker(new MarkerOptions().position(Channel_list_Acitvity.lk).title("活動位置").snippet("活動位置").icon(BitmapDescriptorFactory.fromResource(R.drawable.nearbylittle)));//景點說明
        }

        //======GPS========================================================
        myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocation = getLocationPrivider(myLocationManager);
        //=========Loacation========================
        if (mLocation != null) {
            processLocationUpdated(mLocation);
        } else {
            new AlertDialog.Builder(MapFuncListActivity.this)
                    .setTitle("Loacation Message")
                    .setMessage("Loacation Error")
                    .show();
        }

        myLocationManager.requestLocationUpdates(strLocationPrivider, 100000, 10, mLocationListener01);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylk, 15));

        //========button==============================
        btsearch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (spinner.getSelectedItemPosition()) {
                    case 0:

                        AlertDialog.Builder editDialog = new AlertDialog.Builder(MapFuncListActivity.this);
                        editDialog.setTitle("地圖搜尋");

                        final EditText editText = new EditText(MapFuncListActivity.this);
                        editText.setText("");
                        editText.setHint("搜尋地點");
                        editDialog.setView(editText);
                       editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           // do something when the button is clicked
                           public void onClick(DialogInterface arg0, int arg1) {
                               Log.v("type0", editText.getText().toString());
                               type=editText.getText().toString();

                               nearyfunction();
                           }
                       });
                        editDialog.show();
                        break;
                    case 1:
                        type = "parking";
                        nearyfunction();
                        break;
                    case 2:
                        type = "gas_station";
                        nearyfunction();
                        break;
                    case 3:
                        type = "restaurant";
                        nearyfunction();
                        break;
                    case 4:
                        type = "觀光";
                        nearyfunction();
                        break;
                    default:
                        type = "";
                        break;
                }


            }
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final LatLng markerLocation = marker.getPosition();
                double geoLatitude = markerLocation.latitude * 1E6;
                double geoLongitude = markerLocation.longitude * 1E6;
                markGeopoint = new GeoPoint((int) geoLatitude, (int) geoLongitude);
                Drawable[] aicon = new Drawable[5];
                aicon[0] = getResources().getDrawable(R.drawable.makerlittle);
                aicon[1] = getResources().getDrawable(R.drawable.parking2);
                aicon[2] = getResources().getDrawable(R.drawable.fix2);
                aicon[3] = getResources().getDrawable(R.drawable.restaurantlittle);
                aicon[4] = getResources().getDrawable(R.drawable.playlittle);
                new AlertDialog.Builder(MapFuncListActivity.this)
                        .setTitle("地標資訊")
                        .setMessage(marker.getTitle() + "\n" + getAddressbyGeoPoint(markGeopoint))
                        .setIcon(aicon[spinner.getSelectedItemPosition()])
                        .setPositiveButton("導航", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              //  bdir.setText("取消導航");
                              //  diring = true;
                                map.clear();
                                showselfmark();
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(markerLocation);
                                map.addMarker(markerOptions);
                                StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
                                url.append("origin=" + mylk.latitude + "," + mylk.longitude);
                                url.append("&destination=" + markerLocation.latitude + "," + markerLocation.longitude);
                                url.append("&sensor=false");
                                RouteTask routetask = new RouteTask();
                                routetask.execute(url.toString());
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylk, 16));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(mylk)      // Sets the center of the map to Mountain View
                                        .zoom(16)                   // Sets the zoom
                                        .tilt(60)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }

                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        })
                        .show();
                return false;
            }
        });
        //=====GridList============================
       /* ListView lvMember=(ListView)findViewById(R.id.map_list_lvMember);
        lvMember.setAdapter(new MemberAdapter(this));
        lvMember.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member member=(Member) parent.getItemAtPosition(position);
                ImageView imageView=new ImageView(MapFuncListActivity.this);
                imageView.setImageResource(member.getImage());
                Toast toast=new Toast(MapFuncListActivity.this);
                toast.setView(imageView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                if(member.getId()==4){

                }
            }
        });*/
        //==========ActionBar的選單觸發code=========
        final Intent intent = new Intent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.map_list_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_home:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        MapFuncListActivity.this.finish(); //跳出回home介面
                        break;
                    case R.id.navigation_item_news:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MapFuncListActivity.this, new_activity.class);
                        startActivity(intent);
                        MapFuncListActivity.this.finish();
                        break;
                    case R.id.navigation_item_information:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MapFuncListActivity.this, Light_infor_Activity.class);
                        startActivity(intent);
                        MapFuncListActivity.this.finish();

                        break;
                    case R.id.navigation_item_location:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MapFuncListActivity.this, location_Activity.class);
                        startActivity(intent);
                        MapFuncListActivity.this.finish();
                        break;
                    case R.id.navigation_item_translate:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MapFuncListActivity.this, transport_activity.class);
                        startActivity(intent);
                        MapFuncListActivity.this.finish();
                        break;
                    case R.id.navigation_item_mapfunc:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_safe:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MapFuncListActivity.this, GPSget.class);
                        startActivity(intent);
                        MapFuncListActivity.this.finish();
                        break;
                    case R.id.navigation_item_channellist:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MapFuncListActivity.this, Channel_list_Acitvity.class);
                        startActivity(intent);
                        MapFuncListActivity.this.finish();
                        break;
                    case R.id.navigation_item_browser:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MapFuncListActivity.this, Web_Activity.class);
                        startActivity(intent);
                        MapFuncListActivity.this.finish();
                        break;
                    case R.id.navigation_item_contact_us:
                        Toast.makeText(MapFuncListActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MapFuncListActivity.this, Contact_us_Activity.class);
                        startActivity(intent);
                        MapFuncListActivity.this.finish();
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
   public void nearyfunction()
   {
       StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");//
       map.clear();
       showselfmark();
       sb.append("location=" + myLatitude + "," + myLongitude);
       sb.append("&radius=5000");
       Log.v("nearbytype","-"+type.charAt(0));
       if (type.charAt(0)>140)
       {
           try{
               sb.append("&name="+ URLEncoder.encode(type, "UTF-8"));
       }
           catch (Exception e)
           {
               Log.v("typeerror",e.toString());
           }
       }

       else
           sb.append("&types=" + type);
       sb.append("&sensor=true");
       sb.append("&key=AIzaSyC3RdGMjDxFiJv5A86bk2Qeminzzxrsb18");
       if(myLocationManager.isProviderEnabled(strLocationPrivider)) {
           NearbysearchTask placesTask = new NearbysearchTask();
           Log.v("URLplace", "is " + sb + " -----");
           // Invokes the "doInBackground()" method of the class PlaceTask
           placesTask.execute(sb.toString());
           map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylk, 14));
           CameraPosition cameraPosition = new CameraPosition.Builder()
                   .target(mylk)      // Sets the center of the map to Mountain View
                   .zoom(14)                   // Sets the zoom
                   .tilt(60)                   // Sets the tilt of the camera to 30 degrees
                   .build();                   // Creates a CameraPosition from the builder
           map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
       }
       else
       {
           new AlertDialog.Builder(MapFuncListActivity.this)
                   .setTitle("Search Message")
                   .setMessage("Search Error")
                   .show();
       }
   }
    public class CustomSpinner extends ArrayAdapter<String> {
        String[] lunch = {"尋找地點", "附近停車場資訊", "附近加油站資訊", "附近餐廳資訊", "觀光景點"};
        LayoutInflater inflater;

        public CustomSpinner(Context context, int textViewResourceId,
                             String[] objects) {
            super(context, textViewResourceId, objects);
            inflater = LayoutInflater.from(context);

// TODO Auto-generated constructor stub
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
// TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
//return super.getView(position, convertView, parent);


            View row = inflater.inflate(R.layout.map_list_listlayout_item, parent, false);
            TextView label = (TextView) row.findViewById(R.id.map_list_tvname);
            label.setText(lunch[position]);

            ImageView icon = (ImageView) row.findViewById(R.id.map_list_ivImage);

            Log.v("iconswitch", " " + position);


            if (lunch[position] == "尋找地點") {
                icon.setImageResource(R.drawable.maker);
            } else if (lunch[position] == "附近停車場資訊") {
                icon.setImageResource(R.drawable.parkingblack);
            } else if (lunch[position] == "附近加油站資訊")
                icon.setImageResource(R.drawable.gas1);
            else if (lunch[position] == "附近餐廳資訊")
                icon.setImageResource(R.drawable.restaurantlittle );
            else if (lunch[position] == "觀光景點")
                icon.setImageResource(R.drawable.nearbylittle);
            return row;
        }
    }

    public Location getLocationPrivider(LocationManager lm) {
        Location retLocation = null;
        try {

            Criteria mCriteria01 = new Criteria();
            mCriteria01.setAccuracy(Criteria.ACCURACY_FINE);
            mCriteria01.setAltitudeRequired(false);
            mCriteria01.setBearingRequired(false);
            mCriteria01.setCostAllowed(true);
            mCriteria01.setPowerRequirement(Criteria.POWER_LOW);
            strLocationPrivider = lm.getBestProvider(mCriteria01, true);
            retLocation = lm.getLastKnownLocation(strLocationPrivider);
        } catch (Exception e) {
            Log.v("getLocationPrivider", e.toString());
            e.printStackTrace();
        }
        return retLocation;
    }

    private void processLocationUpdated(Location location) {
        currentGeoPoint = getGeoByLocation(location);
        Log.v("lookGeopoint", " " + currentGeoPoint);
        // refreshMapViewByGeoPoint(currentGeoPoint, myMapView01, intZoomLevel, true);

        mylk = new LatLng((int) currentGeoPoint.getLatitudeE6() / 1E6, (int) currentGeoPoint.getLongitudeE6() / 1E6);
        //mk = map.addMarker(new MarkerOptions().position(lk).title("目前位置").snippet("目前位置"));//景點說明
        showselfmark();

    }

    private GeoPoint getGeoByLocation(Location location) {
        GeoPoint gp = null;
        try {
            if (location != null) {
                double geoLatitude = location.getLatitude() * 1E6;
                double geoLongitude = location.getLongitude() * 1E6;
                gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
            }
        } catch (Exception e) {
            Log.v("GetGeobyLoacation", e.toString());
        }
        return gp;
    }

    public final void showselfmark() {
        Log.v("mark", "start");
        if (mymark != null) {
            mymark.remove();
        }

        mymark = map.addMarker(new MarkerOptions().position(mylk).title("目前位置").snippet("目前位置").icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));//景點說明

        Log.v("mark", "final");
    }

    public final LocationListener mLocationListener01 = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            processLocationUpdated(location);
            myLatitude = location.getLatitude();
            myLongitude = location.getLongitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    };

    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.v("while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }
    public String getAddressbyGeoPoint(GeoPoint gp)
    {

        String strReturn = "Adress error";String returnAddress="Adress error";
        try
        {
            if (gp != null)
            {
                Geocoder gc = new Geocoder(MapFuncListActivity.this, Locale.getDefault());

                double geoLatitude = (int)gp.getLatitudeE6()/1E6;
                double geoLongitude = (int)gp.getLongitudeE6()/1E6;

                List<Address> lstAddress = gc.getFromLocation(geoLatitude, geoLongitude, 1);

                StringBuilder sb = new StringBuilder();
                returnAddress = lstAddress.get(0).getAddressLine(0);
                if (lstAddress.size() > 0)
                {
                    Address adsLocation = lstAddress.get(0);

                    for (int i = 0; i < adsLocation.getMaxAddressLineIndex(); i++)
                    {
                        sb.append(adsLocation.getAddressLine(i)).append("\n");
                    }
                    sb.append(adsLocation.getCountryName());
                    sb.append(adsLocation.getPostalCode()).append("\n");
                    sb.append(adsLocation.getLocality()).append("\n");




                }

                strReturn = sb.toString();
            }
        }
        catch(Exception e)
        {
            Log.v("addressbegin",e.getMessage());
            strReturn=e.getMessage();
            e.printStackTrace();
        }
        return returnAddress;
    }
 /*   private class MemberAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private List<Member> memberList;

        public MemberAdapter(Context context){
            layoutInflater=LayoutInflater.from(context);
            memberList=new ArrayList<>();
            memberList.add(new Member(1,R.drawable.maker,"尋找地點"));
            memberList.add(new Member(2,R.drawable.parkingblack,"附近停車場資訊"));
            memberList.add(new Member(3,R.drawable.gas1,"附近加油站資訊"));
            memberList.add(new Member(4,R.drawable.home_icon,"附近餐廳資訊"));
            memberList.add(new Member(5,R.drawable.fix1,"觀光景點"));
        }

        @Override
        public int getCount() {
            return memberList.size();
        }

        @Override
        public Object getItem(int position) {
            return memberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return memberList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.map_list_listlayout_item,parent,false);
            }

            Member member=memberList.get(position);

            ImageView ivImage=(ImageView)convertView.findViewById(R.id.map_list_ivImage);
            ivImage.setImageResource(member.getImage());

            TextView textView=(TextView) convertView.findViewById(R.id.map_list_tvname);
            textView.setText(member.getName());

            return convertView;
        }
    }*/
//=====================NearBytask=====================================================================
    public class NearbysearchTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
                Log.v("placerun", "data" + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
            NearbysearchParserTask parserTask = new NearbysearchParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            if(result!=null)
            parserTask.execute(result);
        }
    }
    public class NearbysearchParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            NearbysearchJson placeJsonParser = new NearbysearchJson();

            try {
                jObject = new JSONObject(jsonData[0]);
                /** Getting the parsed data as a List construct */
            if(jObject!=null)
                places = placeJsonParser.parse(jObject);
                Log.v("placerun", "parser" + places);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {
            // Clears all the existing markers
            //map.clear();
            Log.v("placerun", "execute0" + list.size());

            for (int i = 0; i < list.size(); i++) {
                Log.v("placerun", "execute" + list.size());
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);
                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));
                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));
                // Getting name
                String name = hmPlace.get("place_name");
                // Getting vicinity
                String vicinity = hmPlace.get("vicinity");
                LatLng latLng = new LatLng(lat, lng);
                // Setting the position for the marker
                markerOptions.position(latLng);
                // Setting the title for the marker.
                //This will be displayed on taping the marker
                markerOptions.title(name + " : " + vicinity);
                switch (MapFuncListActivity.spinner.getSelectedItemPosition()) {
                    case 0:
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.makerlittle));
                        break;
                    case 1:
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.parking3));
                        break;
                    case 2:
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.gas2));
                        break;
                    case 3:
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurantlittle));
                        break;
                    case 4:
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.nearbylittle));
                        break;
                    default:
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bigfix4));
                }

                // Placing a marker on the touched position
                MapFuncListActivity.map.addMarker(markerOptions);
            }
        }
    }

    public class NearbysearchJson {
        public List<HashMap<String, String>> parse(JSONObject jObject) {

            JSONArray jPlaces = null;
            try {
                /** Retrieves all the elements in the 'places' array */
                jPlaces = jObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /** Invoking getPlaces with the array of json object
             * where each json object represent a place
             */
            return getPlaces(jPlaces);
        }

        private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
            int placesCount = jPlaces.length();
            List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> place = null;

            /** Taking each place, parses and adds to list object */
            for (int i = 0; i < placesCount; i++) {
                try {
                    /** Call getPlace with place JSON object to parse the place */
                    place = getPlace((JSONObject) jPlaces.get(i));
                    placesList.add(place);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return placesList;
        }

        private HashMap<String, String> getPlace(JSONObject jPlace) {

            HashMap<String, String> place = new HashMap<String, String>();
            String placeName = "-NA-";
            String vicinity = "-NA-";
            String latitude = "";
            String longitude = "";

            try {
                // Extracting Place name, if available
                if (!jPlace.isNull("name")) {
                    placeName = jPlace.getString("name");
                }

                // Extracting Place Vicinity, if available
                if (!jPlace.isNull("vicinity")) {
                    vicinity = jPlace.getString("vicinity");
                }

                latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");

                place.put("place_name", placeName);
                place.put("vicinity", vicinity);
                place.put("lat", latitude);
                place.put("lng", longitude);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return place;
        }
    }

    //=======================routetask======================================================
    public class RouteTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = MapFuncListActivity.downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            RouteParserTask parserTask = new RouteParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }
    public class RouteParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                RouteJson parser = new RouteJson();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);  //導航路徑寬度
                lineOptions.color(Color.RED); //導航路徑顏色

            }

            // Drawing polyline in the Google Map for the i-th route
            MapFuncListActivity.map.addPolyline(lineOptions);

        }
    }
    public class RouteJson {
        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;

            try {

                jRoutes = jObject.getJSONArray("routes");

                /** Traversing all routes */
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<HashMap<String, String>>();

                    /** Traversing all legs */
                    for (int j = 0; j < jLegs.length(); j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                        /** Traversing all steps */
                        for (int k = 0; k < jSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            /** Traversing all points */
                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                                hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }


            return routes;
        }

        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    }

}
