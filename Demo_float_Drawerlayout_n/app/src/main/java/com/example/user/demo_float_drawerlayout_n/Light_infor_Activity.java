package com.example.user.demo_float_drawerlayout_n;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/6/28.
 */
public class Light_infor_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_infer_layout);

        /*
        Button button=(Button)findViewById(R.id.testbutton);
        button.setOnClickListener(new check_button());
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.light_infor_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //======DrawerLayout===================
        mDrawerLayout = (DrawerLayout) findViewById(R.id.light_infor_drawer_layout);
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.light_infor_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_home:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        Light_infor_Activity.this.finish();
                        break;
                    case R.id.navigation_item_news:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Light_infor_Activity.this, new_activity.class);
                        startActivity(intent);
                        Light_infor_Activity.this.finish();
                        break;
                    case R.id.navigation_item_information:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_location:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Light_infor_Activity.this, location_Activity.class);
                        startActivity(intent);
                        Light_infor_Activity.this.finish();
                        break;
                    case R.id.navigation_item_translate:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Light_infor_Activity.this, transport_activity.class);
                        startActivity(intent);
                        Light_infor_Activity.this.finish();
                        break;
                    case R.id.navigation_item_mapfunc:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Light_infor_Activity.this, MapFuncListActivity.class);
                        startActivity(intent);
                        Light_infor_Activity.this.finish();
                        break;
                    case R.id.navigation_item_safe:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Light_infor_Activity.this, GPSget.class);
                        startActivity(intent);
                        Light_infor_Activity.this.finish();
                        break;
                    case R.id.navigation_item_channellist:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Light_infor_Activity.this, Channel_list_Acitvity.class);
                        startActivity(intent);
                        Light_infor_Activity.this.finish();
                        break;
                    case R.id.navigation_item_browser:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Light_infor_Activity.this, Web_Activity.class);
                        startActivity(intent);
                        Light_infor_Activity.this.finish();
                        break;
                    case R.id.navigation_item_contact_us:
                        Toast.makeText(Light_infor_Activity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        intent.setClass(Light_infor_Activity.this, Contact_us_Activity.class);
                        startActivity(intent);
                        Light_infor_Activity.this.finish();
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
                new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        List<Member_light> memberList=new ArrayList<>();

        memberList.add(new Member_light(1,R.drawable.main_01,"1","主燈區","齊天創鴻運","今年主燈「齊天創鴻運」以家喻戶曉兼具勇氣智謀之美猴王為主題，運用全彩LED光源創新搭配4D折射模式，打造高達26公尺，左執金箍棒、右托仙蟠桃、腳蹬觔斗雲、身穿金鎧甲的齊天大聖主燈，以現代化嶄新科技風貌，重現人們記憶深刻的猴年代表神話故事。今年除主燈高度再創新高外，首度於主燈雙眼手工鑲嵌大型水晶，以呈現主燈珍貴不凡質感，並象徵「火眼金睛，世界之星」的主燈絕世風采。"));
        memberList.add(new Member_light(2,R.drawable.other01,"2","副燈區","1.舞動桃源","舞動桃源：金龍飛舞，四海活躍。時和豐丙申，財源似水源。\n" +
                "\n" +
                "龍是抽象與具象的匯聚，為表現由神韻而生的形象，融入藝術形式中被喻為「線條之雄辯」的書法理法，將草書揮運之妙，與龍形體結合，以求龍如雲霧巧變的姿態，傳達龍於天上「重若崩雲，輕如蟬翼」的形態。藉由「龍」的形與線條的演繹，體會書寫當下心手契合，筆墨雙暢時「導之如泉注，頓之則山安」的動勢。"));
        memberList.add(new Member_light(3,R.drawable.other02,"3","副燈區","2.鳥語花香","鳥語花香：五色鳥為臺灣特有亞種，有鮮豔的五彩顏色，結合桃花、櫻花及梅花的色、香、韻有相得益彰之妙，並象徵生機盎然、欣欣向榮。另藉由五色鳥的五種顏色，隱喻桃園在地閩南、客家、原民、眷村和新住民的多元族群文化的匯聚，共同譜出繽紛的樂章，並像桃花、櫻花、梅花一樣的繁榮昌盛，寓意生活幸福美滿。"));
        memberList.add(new Member_light(4,R.drawable.other03,"4","副燈區","3.夢幻奇緣","夢幻奇緣：童話故事一直以來陪伴著許多人成長，充滿著幻想與期盼。以糕餅糖果樹屋城堡為發想概念，如童話故事般由大樹上湧出五彩繽紛的果漿，與蛋糕餅乾巧克力結合成夢幻般的糖果樹屋，並運用鮮豔的色彩燈光營造氣氛，加上由燈會吉祥物ㄚ桃與園哥帶領許多活潑可愛擬人化打扮的臺灣野生動物來表現出童話般的夢幻意境。\n" +
                "\n" +
                "現今社會人們生活忙碌，期盼此燈讓人們重拾童年純真的幻想夢境，擁有更多的歡樂與憧憬。"));
        memberList.add(new Member_light(5,R.drawable.other04,"5","副燈區","4.祥猴獻桃","祥猴獻瑞：寓意官運亨通、福祿雙全，藉由祥猴的形象，表徵拜將封侯，升官如意，展現丙申猴年豐收富足，吉慶瑞兆。 獻桃的動作，意指祈禱康壽、福祿綿長，搭配土地公的形象，意喻守護家園、五穀豐登，並祝賀桃園五宇春開，喜祥安康。\n"));

        recyclerView.setAdapter(new MemberAdapter(this,memberList));
    }
    //=====================RecycleCardView===========================
    private class MemberAdapter extends
            RecyclerView.Adapter<MemberAdapter.ViewHolder> {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Member_light> memberList;

        public MemberAdapter(Context context, List<Member_light> memberList) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.memberList = memberList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;
            TextView tvId, tvName;
            TextView tvtitle,id1,id2;

            public ViewHolder(View itemView) {
                super(itemView);
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
                tvtitle=(TextView)itemView.findViewById(R.id.tvtitle);
                id1=(TextView)itemView.findViewById(R.id.tvid1);
                id2=(TextView)itemView.findViewById(R.id.tvid2);
            }
        }

        @Override
        public int getItemCount() {
            return memberList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = layoutInflater.inflate(
                    R.layout.recyclerview_light_item, viewGroup, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final Member_light member = memberList.get(position);
            viewHolder.ivImage.setImageResource(member.getImage());
            viewHolder.tvtitle.setText(member.gettitle());
            viewHolder.id1.setText(member.getid1());
            viewHolder.id2.setText(member.getid2());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Light_infor_Activity.this, "test: " + member.getId(), Toast.LENGTH_SHORT).show();
                    int temp = member.getId();
                    if (temp == 1) {

                    }else if(temp==2){

                    }else if(temp==3){

                    }else if(temp==4){

                    }else if(temp==5){

                    }else if(temp==6){

                    }else if(temp==7){

                    }else if(temp==8){

                    }else{

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
}
