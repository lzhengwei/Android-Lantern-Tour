package com.example.user.demo_float_drawerlayout_n;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;

import java.util.ArrayList;
import java.util.List;

//========主程式======================
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    //=============================================
    CallbackManager callbackManager;
    String temp="";
    //===============================================
    //============================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra("jsondata",temp);
                startActivity(intent);
                /*
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View vV = inflater.inflate(R.layout.fb_login_bar, null);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("請輸入你的id")
                        .setView(vV)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                        */
            }
        });
        /*
        Button button=(Button)findViewById(R.id.testbutton);
        button.setOnClickListener(new check_button());
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //======DrawerLayout===================
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //=====GridList============================
        /*
        GridView lvMember=(GridView)findViewById(R.id.gvMember);
        lvMember.setAdapter(new MemberAdapter(this));
        lvMember.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member member=(Member) parent.getItemAtPosition(position);
                String text="ID = "+member.getId()+", name = "+member.getName();
                Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();

                if(member.getId()==4){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MapFuncListActivity.class);
                    startActivity(intent);
                }
            }
        });
        */
        //==========ActionBar的選單觸發code=========
        final Intent intent = new Intent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.navigation_item_home:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_news:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, new_activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_information:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, Light_infor_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_location:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, location_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_translate:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, transport_activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_mapfunc:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, MapFuncListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_safe:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, GPSget.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_channellist:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, Channel_list_Acitvity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_browser:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, Web_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_contact_us:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(MainActivity.this, Contact_us_Activity.class);
                        startActivity(intent);
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });
        //=====================RecyclerCardViewDemo============
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        List<Member> memberList=new ArrayList<>();

        memberList.add(new Member(1, R.drawable.newss,"1"));
        memberList.add(new Member(2, R.drawable.lightinfor,"2"));
        memberList.add(new Member(3, R.drawable.compass,"3"));
        memberList.add(new Member(4, R.drawable.transporttime,"4"));
        memberList.add(new Member(5, R.drawable.map,"5"));
        memberList.add(new Member(6, R.drawable.childsecurity,"6"));
        memberList.add(new Member(7, R.drawable.showactivities,"7"));
        memberList.add(new Member(8, R.drawable.browser_icon500,"8"));


        recyclerView.setAdapter(new MemberAdapter(this,memberList));

    }
    //=====================RecycleCardView===========================
    private class MemberAdapter extends
            RecyclerView.Adapter<MemberAdapter.ViewHolder> {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Member> memberList;

        public MemberAdapter(Context context, List<Member> memberList) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.memberList = memberList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;
            TextView tvId, tvName;

            public ViewHolder(View itemView) {
                super(itemView);
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

            }
        }

        @Override
        public int getItemCount() {
            return memberList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = layoutInflater.inflate(
                    R.layout.recyclerview_cardview_item, viewGroup, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
           final  Intent intent = new Intent();
            final Member member = memberList.get(position);
            viewHolder.ivImage.setImageResource(member.getImage());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int temp = member.getId();
                    if (temp == 1) {
                        intent.setClass(MainActivity.this, new_activity.class);
                        startActivity(intent);
                    }else if(temp==2){
                        intent.setClass(MainActivity.this, Light_infor_Activity.class);
                        startActivity(intent);

                    }else if(temp==3){
                        intent.setClass(MainActivity.this, location_Activity.class);
                        startActivity(intent);

                    }else if(temp==4){
                        intent.setClass(MainActivity.this, transport_activity.class);
                        startActivity(intent);

                    }else if(temp==5){
                        intent.setClass(MainActivity.this, MapFuncListActivity.class);
                        startActivity(intent);
                    }else if(temp==6){
                        intent.setClass(MainActivity.this, GPSget.class);
                        startActivity(intent);
                    }else if(temp==7){
                        intent.setClass(MainActivity.this, Channel_list_Acitvity.class);
                        startActivity(intent);
                    }else if(temp==8){
                        intent.setClass(MainActivity.this, Web_Activity.class);
                        startActivity(intent);
                    }else{
                        intent.setClass(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
    //==================================================================

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

    //===============================grid要用到的==================================
    /*
    private class MemberAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private List<Member> memberList;

        public MemberAdapter(Context context){
            layoutInflater=LayoutInflater.from(context);
            memberList=new ArrayList<>();
            memberList.add(new Member(1,R.drawable.a1,"entry1"));
            memberList.add(new Member(2,R.drawable.a2,"entry2"));
            memberList.add(new Member(3,R.drawable.a3,"entry3"));
            memberList.add(new Member(4,R.drawable.map_func_table_icon,"map_func"));
            memberList.add(new Member(5,R.drawable.a5,"entry5"));
            memberList.add(new Member(6,R.drawable.a6,"entry6"));
            memberList.add(new Member(1,R.drawable.a1,"entry1"));
            memberList.add(new Member(2,R.drawable.a2,"entry2"));
            memberList.add(new Member(3,R.drawable.a3,"entry3"));
            memberList.add(new Member(4,R.drawable.a4,"entry4"));
            memberList.add(new Member(5,R.drawable.a5,"entry5"));
            memberList.add(new Member(6,R.drawable.a6,"entry6"));

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
                convertView=layoutInflater.inflate(R.layout.gridview_item,parent,false);
            }

            Member member=memberList.get(position);

            ImageView ivImage=(ImageView)convertView.findViewById(R.id.ivImage);
            ivImage.setImageResource(member.getImage());
            return convertView;
        }
    }
    */
}
