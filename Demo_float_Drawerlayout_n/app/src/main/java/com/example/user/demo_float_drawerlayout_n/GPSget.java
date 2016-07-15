package com.example.user.demo_float_drawerlayout_n;

import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class GPSget extends AppCompatActivity {

    private TextView btview;
    private Button bton,btopen,btsever,btreset;
    private GeoPoint currentGeoPoint;
    private LocationManager myLocationManager01;
    private String strLocationPrivider = "";
    private String bestProvider = LocationManager.GPS_PROVIDER;

    private List<String> BTpair;

    private LatLng mylk= new LatLng(23.689821,120.53891),rlk=new LatLng(0,0);
    ;
    private Marker mymark,anmark;
    private Location mLocation01=null;
    private int intZoomLevel = 12;
    private int person=0;
    static GoogleMap map;
    private Vibrator myVibrator ;
    private DrawerLayout mDrawerLayout;


    //------------bluetooth define----------------------------------------------
    private static final UUID BLUETOOTH_SPP_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private boolean isconnected;
    AcceptThread severthread;
    clientConnectThread clientthread;
    ConnectedThread connect;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    private Handler lksend_Handler = new Handler(),tryconnect_handler=new Handler(),server_handler=new Handler();
    byte[] readBuffer;
    int readBufferPosition,warningsound;
    int counter;
    volatile boolean stopWorker;
    private SoundPool sound;

//--------------------------map define---------------------------------------

    private MarkerOptions newmark;

    String TAG="bluetooth_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsget);
//===================Drwable============================================
        Toolbar toolbar = (Toolbar) findViewById(R.id.bt_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //======DrawerLayout===================
        mDrawerLayout = (DrawerLayout) findViewById(R.id.bt_drawer_layout);
        //=====GridList============================
        //==========ActionBar的選單觸發code=========
        final Intent intent = new Intent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.bt_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.navigation_item_home:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        GPSget.this.finish();
                        break;
                    case R.id.navigation_item_news:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_information:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_location:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(GPSget.this, location_Activity.class);
                        startActivity(intent);
                        GPSget.this.finish();
                        break;
                    case R.id.navigation_item_translate:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_mapfunc:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(GPSget.this, MapFuncListActivity.class);
                        startActivity(intent);
                        GPSget.this.finish();
                        break;
                    case R.id.navigation_item_safe:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_channellist:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(GPSget.this, Channel_list_Acitvity.class);
                        startActivity(intent);
                        GPSget.this.finish();
                        break;
                    case R.id.navigation_item_browser:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(GPSget.this, Web_Activity.class);
                        startActivity(intent);
                        GPSget.this.finish();
                        break;
                    case R.id.navigation_item_contact_us:
                        Toast.makeText(GPSget.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(GPSget.this, Contact_us_Activity.class);
                        startActivity(intent);
                        GPSget.this.finish();
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });
        //-----------------view---------------------------------------------------------------
        btsever=(Button)findViewById(R.id.button3);
        bton=(Button)findViewById(R.id.button);
        btopen=(Button)findViewById(R.id.button2);
        btreset=(Button)findViewById(R.id.button4);

        btview=(TextView)findViewById(R.id.textView);
       btview.setText("請選擇模式");
       Log.v("oncreat","oncreattag");
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment)).getMap();
       //----------------------------GPS-------------------------------------------------
        myLocationManager01 = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mLocation01 = getLocationPrivider(myLocationManager01);
        //------------------------------------bluetooth----------------------------------------------------------------------------
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBluetoothAdapter.isEnabled())
        {
            final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 1);
        }
        BTpair = new ArrayList<>();
        IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
       try {
           this.registerReceiver(mReceiver, filter1);
           this.registerReceiver(mReceiver, filter2);
           this.registerReceiver(mReceiver, filter3);
       }
       catch(Exception e)
       {
           Log.v("mreciver",e.toString());

       }
        myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        sound = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        warningsound=sound.load(this,R.raw.warning,1);
        isconnected=false;
        /*IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);*/
//----------------------------------GPS init------------------------------------------------------------------------------------
        if(mLocation01!=null)
        {
            processLocationUpdated(mLocation01);
        }
        else
        {
            new AlertDialog.Builder(GPSget.this)
                    .setTitle("Loacation Message")
                    .setMessage("Loacation Error")
                    .show();
        }
        myLocationManager01.requestLocationUpdates(strLocationPrivider, 1000, 0, mLocationListener01);
        //-------------------------------map init--------------------------------------------------

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylk, 15));
        Log.v("maplk", mylk.latitude + " " + mylk.longitude);
        //--------------------------------Button----------------------------------------------------------------------------
        bton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FindBT();
                    person = 2;
                    showselfmark();
                    //lksend_Handler.postDelayed(begin_send_lk,10000);
                } catch (Exception e) {
                    Log.v(TAG, e.toString());
                }
            }
        });
        btopen.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!mBluetoothAdapter.isEnabled())
                    {
                        final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableIntent, 1);
                    }
                    else
                    {
                        Toast.makeText(GPSget.this, "藍芽已開啟", Toast.LENGTH_SHORT).show();
                    }

                  //  clientthread=new clientConnectThread(mmDevice);
                  //  clientthread.start();
                } catch (Exception e) {
                    Log.v(TAG, e.toString());
                }
            }
        });

        btsever.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btview.setText("等待小孩連接");
                    person=1;
                    showselfmark();
                    server_handler.postDelayed(serveropen,1000);
                }
                catch(Exception e)
                {
                    Log.v("btserver",e.toString());
                }
            }
        });
        btreset.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btview.setText("重新選擇模式");
                  refinish();
                }
                catch(Exception e)
                {
                    Log.v("btserver",e.toString());
                }
            }
        });


    }
  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){

            Intent myIntent = new Intent();
            myIntent = new Intent(GPSget.this, mainface.class);
            startActivity(myIntent);
            return  true;
            //moveTaskToBack(true);

        }
        return super.onKeyDown(keyCode, event);
    }*/

   @Override
    protected void onStart() {
        super.onStart();
       // FindBT();

    }
    @Override
   protected void onPause(){
        super.onPause();
        refinish();


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        refinish();


        Log.v("onDestroy", "Destroy");

    }
    @Override
    protected void onStop() {
        super.onStop();
        refinish();
        Log.v("onstop", "Stop");


    }

    public void refinish()
    {
        tryconnect_handler.removeCallbacks(openBT);
        lksend_Handler.removeCallbacks(begin_send_lk);
        server_handler.removeCallbacks(serveropen);
        if(severthread!=null)
            severthread.cancel();
        if(connect!=null)
            connect.cancel();
        person=0;
        btview.setText("請選擇模式");
       // GPSget.this.finish();
        try {
            unregisterReceiver(mReceiver);
        }
        catch (Exception e)
        {
            Log.v("rigister error",e.toString());

        }
    }
   public void FindBT() throws InterruptedException {
       if (!mBluetoothAdapter.isEnabled()) {

           final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
           new AlertDialog.Builder(GPSget.this)
                   .setTitle("藍芽尚未開啟")
                   .setMessage("Open bluetooth")
                   .setPositiveButton("開啟藍芽", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           startActivityForResult( enableIntent,1);
                           pairBTdevice();

                           try {
                               tryconnect_handler.postDelayed(openBT,1000);
                           }
                           catch (Exception e)
                           {Log.v(TAG,e.toString());}

                       }
                   })
                   .show();
// Otherwise, setup the chat session
       }else {
         //  btview.setText("Bluttooth 已開啟");
           pairBTdevice();

           try {
               if(severthread==null)
               tryconnect_handler.postDelayed(openBT,1000);
           }
           catch (Exception e)
           {Log.v(TAG,e.toString());}
       }



   }
    public void pairBTdevice()
    {
        Log.v(TAG, "BTdialog in");
        final Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
// If there are paired devices
        Log.v(TAG, "BTdialog in2");
        if (pairedDevices.size() > 0) {
            BTpair.clear();
            // Loop through paired devices
            Log.v(TAG,"BTdialog in for");
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                BTpair.add(device.getName());
            }
            Log.v(TAG, "d" + BTpair.size());
            new AlertDialog.Builder(GPSget.this)
                    .setTitle("以配對裝置")
                            //.setMessage("選擇一裝置進行配對")
                    .setItems(BTpair.toArray(new String[BTpair.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = BTpair.get(which);
                            for (BluetoothDevice device : pairedDevices) {
                                if (device.getName().equals(name)) {
                                    mmDevice = device;
                                    btview.setText(btview.getText() + "\n" + device.getName() + "    " + device.getAddress());
                                }
                            }
                            //  Toast.makeText(getApplicationContext(),"dd", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
            Log.v(TAG, "BTdialog out");
        } else {
            btview.setText("還沒有已配對的遠端藍牙設備！");
        }
        btview.setText( " 等待連接至 : ");
    }
    public Runnable openBT=new Runnable() {
        @Override
        public void run() {
            Log.v(TAG, "socketcreat");
            if (mmDevice != null) {
            try {

                    mmSocket = mmDevice.createRfcommSocketToServiceRecord(BLUETOOTH_SPP_UUID);
                    Log.v(TAG, "socketcontect");
                    mmSocket.connect();

                    if (mmSocket.isConnected()) {
                        btview.setText("成功連接");
                        //lksend_Handler.postDelayed(begin_send_lk, 5000);

                    }
                    else
                        btview.setText("連接失敗");

                    btview.setText("與 "+ mmDevice.getName() + " 成功連接");
                    mmOutputStream = mmSocket.getOutputStream();
                    mmInputStream = mmSocket.getInputStream();
                    connected(mmSocket, mmDevice);
                    // beginListenForData(); //開始傾聽藍芽裝置的資料

            }
            catch(Exception e)
            {Log.v(TAG,e.toString());}
            }
            else
                tryconnect_handler.postDelayed(openBT,1000);
        }

    };
    private final Runnable serveropen = new Runnable()
    {
        @Override
        public void run()
        {
           if(!mBluetoothAdapter.isEnabled())
           {
               final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
               new AlertDialog.Builder(GPSget.this)
                       .setTitle("藍芽尚未開啟")
                       .setMessage("Open bluetooth")
                       .setPositiveButton("開啟藍芽", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               startActivityForResult( enableIntent,1);
                               try {
                                   server_handler.postDelayed(serveropen,1000);
                               }
                               catch (Exception e)
                               {Log.v(TAG,e.toString());}

                           }
                       })
                       .show();
           }
            else
           {
               try {
                   severthread=new AcceptThread();
                   severthread.start();
               } catch (Exception e) {
                   Log.v(TAG, e.toString());
               }
           }

            // 若要取消可以寫一個判斷在這決定是否啟動下一次即可
        }
    };
      //  UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID


    private final Runnable begin_send_lk = new Runnable()
    {
        public void run()
        {
            String msg;
            msg=Double.toString(mylk.latitude)+" "+Double.toString(mylk.longitude);
            Log.v("lksend","send "+msg);
            try {
                if(isconnected)
                mmOutputStream.write(msg.getBytes());

            }
            catch(Exception e) {
                Log.v("lksend",e.toString());

            }
            lksend_Handler.postDelayed(this, 1000);
            // 若要取消可以寫一個判斷在這決定是否啟動下一次即可
        }
    };
    public final void showselfmark() {
        Log.v("mark", "start");
        if (mymark != null) {
            mymark.remove();
        }
        Log.v("showselfmark",person+"--");
        switch (person)
        {
            case 0:
                if (mymark != null) {
                    mymark.remove();
                }
                break;
            case 1:
                mymark = map.addMarker(new MarkerOptions().position(mylk).title("目前位置").snippet("目前位置").icon(BitmapDescriptorFactory.fromResource(R.drawable.mom)));//景點說明
                break;
            case 2:
                mymark = map.addMarker(new MarkerOptions().position(mylk).title("目前位置").snippet("目前位置").icon(BitmapDescriptorFactory.fromResource(R.drawable.child)));//景點說明
                break;
        }




        Log.v("mark", "final");
    }
    public   void beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character
        stopWorker = false;
        readBufferPosition = 0;

       byte[] buffer = new byte[1024];  // buffer store for the stream


        workerThread = new Thread(new Runnable() //建立一條新執行緒進入傾聽來自藍芽裝置資料輸入程序
        {
            @Override
            public void run() {
                // bytes returned from read()
                Log.v(TAG,"thread in");
                while( !Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try {
                        // Read from the InputStream
                        int bytes=mmInputStream.available();
                      //  byte[] buffer = new byte[bytes];
                        readBuffer = new byte[bytes];
                        if(mmInputStream.available()>0) {
                            bytes = mmInputStream.read(readBuffer);
                            final String str = new String(readBuffer);
                            Log.i(TAG, "mmInStream - " + str);
                            if (str.length()>= 10 &&str.length()<22) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //myLabel.setText(data);
                                        if (str.length() > 1 ) {
                                            String lon, lati;
                                            lati = str.substring(1, 10);
                                            lon = str.substring(10, 21);
                                            Log.v(TAG, "receive " + lati + " " + lon);
                                            LatLng rlk = new LatLng(Double.parseDouble(lati), Double.parseDouble(lon));
                                            map.addMarker(new MarkerOptions().position(rlk).title("新增位置").snippet("新增位置"));
                                        }

                                    }
                                });
                                // Send the obtained bytes to the UI Activity
                            }
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "disconnected", e);

                        break;
                    }

                  /*  try{

                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable > 0)
                        {
                            Log.v(TAG,Integer.toString(mmInputStream.available())+Integer.toString(mmInputStream.read()));
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++) {
                                byte b = packetBytes[i];

                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                   Log.v(TAG,"print "+data);
                                    readBufferPosition = 0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //myLabel.setText(data);
                                            btview_print.setText( data);

                                        }
                                    });

                                   // readBuffer[readBufferPosition++] = b;
                                    Log.v(TAG,"print else");

                            }
                        }
                    }
                    catch (Exception x)
                    {
                      stopWorker=true;
                        Log.v(TAG,"listen thread error : "+x.toString());
                    }*/
                }
            }
        });
        Log.v(TAG, "thread start");
        workerThread.start();


    }
    void sendData() throws IOException
    {
        connect.write();
     /*   String msg = btedit.getText().toString();
        msg += "\n";
        btview_print.setText(msg);
        mmOutputStream.write(msg.getBytes());*/

    }

    public Location getLocationPrivider(LocationManager lm)
    {
        Location retLocation = null;
        try
        {
            Criteria mCriteria01 = new Criteria();
            mCriteria01.setAccuracy(Criteria.ACCURACY_FINE);
            mCriteria01.setAltitudeRequired(false);
            mCriteria01.setBearingRequired(false);
            mCriteria01.setCostAllowed(true);
            mCriteria01.setPowerRequirement(Criteria.POWER_LOW);
            strLocationPrivider = lm.getBestProvider(mCriteria01, true);
            retLocation = lm.getLastKnownLocation(strLocationPrivider);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return retLocation;
    }
    private GeoPoint getGeoByLocation(Location location)
    {
        GeoPoint gp = null;
        try
        {
            if (location != null)
            {
                double geoLatitude = location.getLatitude()*1E6;
                double geoLongitude = location.getLongitude()*1E6;

                gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
                Log.v("gplk",gp.getLatitudeE6()+" "+gp.getLongitudeE6());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return gp;
    }
    private void processLocationUpdated(Location location)
    {
        currentGeoPoint = getGeoByLocation(location);
        mylk=new LatLng((int)currentGeoPoint.getLatitudeE6()/1E6,(int)currentGeoPoint.getLongitudeE6()/1E6);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylk, 21));

        // refreshMapViewByGeoPoint(currentGeoPoint, myMapView01, intZoomLevel, true);

        showselfmark();
    }



    public final LocationListener mLocationListener01 = new LocationListener()
    {
        @Override
        public void onLocationChanged(Location location)
        {
            processLocationUpdated(location);

        }

        @Override
        public void onProviderDisabled(String provider)
        {
        }

        @Override
        public void onProviderEnabled(String provider)
        {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
        }
    };




    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                 //Device found
            }

            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
            //Device is about to disconnect
                btview.setText("at BroadcastReceiver DISCONNECT_REQUESTED!!");
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)&&isconnected) {
                 //Device has disconnected
                overstep();

            }
        }
    };
    public void overstep()
    {
        Log.v("overstep","1");
        sound.play(warningsound, 1, 1, 0, 0, 1);
        Log.v("overstep", "2");
        myVibrator.vibrate(5000);
        Log.v("overstep", "3");
        new AlertDialog.Builder(GPSget.this)
                .setTitle("距離已超出安全範圍")
                .setMessage("回到安全範圍後\n重新連線")
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(myVibrator.hasVibrator())
                        myVibrator.cancel();
                        sound.stop(warningsound);
                    }
                })
                .show();
        Log.v("overstep", "4");
        map.clear();
        person=0;
        btview.setText("已失去連線，請確認距離，並重新選擇模式");
        if(connect!=null)
            connect.cancel();
        if(severthread!=null)
            severthread.cancel();
        lksend_Handler.removeCallbacks(begin_send_lk);
        isconnected=false;
        Log.v("overstep", "finish");

    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            Log.v(TAG,"accptthread  ");

            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("sever", BLUETOOTH_SPP_UUID);
            } catch (IOException e) {
                Log.v(TAG,"accptthread error : "+e.toString());
            }
            mmServerSocket = tmp;
        }
       @Override
        public void run() {
            Log.v(TAG,"accptthread  run");

            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.v(TAG,"run error : "+e.toString());

                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            btview.setText("已完成連接");
                        }
                    });
                    // Do work to manage the connection (in a separate thread)
                    Log.v(TAG, "accptthread  run connected");
                    connected(socket, socket.getRemoteDevice());

                    //  manageConnectedSocket(socket);
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) { }
                    break;
                }

            }
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (severthread != null) {severthread.cancel(); severthread = null;}
        connect = new ConnectedThread(socket);
        connect.start();
     //   connect.run();
        Log.v(TAG,"device name : "+device.getName());
    }
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private Handler connecthandler ;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();

            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            mmOutputStream=tmpOut;
            isconnected=true;
            runOnUiThread(new Runnable() {
                public void run() {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylk, 21));
                }
            });
            lksend_Handler.postDelayed(begin_send_lk, 1000);
            Log.v("lksend","ok");

        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");


            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    int bytes = mmInStream.available();
                    //  byte[] buffer = new byte[bytes];
                    readBuffer = new byte[bytes];
                    if (mmInStream.available() > 0) {
                        bytes = mmInStream.read(readBuffer);
                        final String str = new String(readBuffer);;
                        Log.i(TAG, "mmInStream - " + str);
                        if (str.length()>= 10 &&str.length()<22) {
                            if(str.length()>1) {
                                String lon, lati;
                                int index=str.indexOf(" ");
                                lati = str.substring(0, index);
                                lon = str.substring(index+1,str.length());
                                Log.v(TAG, "receive " + lati + " " + lon);
                                rlk = new LatLng(Double.parseDouble(lati), Double.parseDouble(lon));

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        if(anmark!=null)
                                            anmark.remove();
                                        if (person == 2)
                                            anmark = map.addMarker(new MarkerOptions().position(rlk).title("目前位置").snippet("目前位置").icon(BitmapDescriptorFactory.fromResource(R.drawable.mom)));//景點說明
                                        if (person == 1)
                                            anmark = map.addMarker(new MarkerOptions().position(rlk).title("目前位置").snippet("目前位置").icon(BitmapDescriptorFactory.fromResource(R.drawable.child)));//景點說明
                                    }

                                });
                            }
                            }


                            // Send the obtained bytes to the UI Activity
                      //  }
                    }
                }catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    runOnUiThread(new Runnable() {
                        public void run() {
                          //  overstep();
                            //btview.setText("at connectthread Disconnect!!");
                        }
                        });
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         // @param buffer  The bytes to write
         */
        public void write() {
            try {
                String msg = "dd";
                msg += "\n";

                mmOutStream.write(msg.getBytes());
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
    private class clientConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public clientConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;
            Log.v(TAG,"clientstart");
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(BLUETOOTH_SPP_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            connected(mmSocket,mmDevice);
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
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
