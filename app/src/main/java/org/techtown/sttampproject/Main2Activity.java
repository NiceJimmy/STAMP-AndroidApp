package org.techtown.sttampproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.sttampproject.MyPage.MypageActivity;
import org.techtown.sttampproject.MyShop.MyShopActivity;
import org.techtown.sttampproject.MyStamp.MyStampActivity;
import org.techtown.sttampproject.ShareActivity.MainAdapter;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.ShareActivity.ShareActivity;
import org.techtown.sttampproject.Upload_Process.UploadActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    Uri uri;
    String ID;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private List<SharingDataClass> mArrayList;
    SharingApiInterface apiInterface;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
        recyclerView = findViewById(R.id.MainRecyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("STAMP");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //헤더를 제어하는 부분이다. 로그인한 아이디값과 가져온 프로필사진을 여기다가 뿌려주면 되것다.
        View nav_header_view = navigationView.getHeaderView(0);
//        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.name);
//        nav_header_id_text.setText(" ㅎㅎㅎ");


        SharedPreferences sf = getSharedPreferences("session", MODE_PRIVATE);
        ID = sf.getString("ID", "");
        ImageView nav_header_id_Profile = (ImageView) nav_header_view.findViewById(R.id.imageView);

        Log.d("ses1", "로그인세션 : " + ID);


        Glide.with(Main2Activity.this)
                .load("http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/imageupload/uploads/" + ID + ".jpg")
                .apply(new RequestOptions().centerCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(nav_header_id_Profile);
//
//
//        uri = Uri.parse("http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/imageupload/uploads/" + ID + ".png");

        nav_header_id_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences sharedPreferences = getSharedPreferences("uri",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("uriparse", String.valueOf(uri));
//                editor.apply();

            }
        });


    }


    public void onResume(){
        super.onResume();

        getData();

    }

    public void getData(){ // 디비의 데이타를 로드하는 부분



        Call<List<SharingDataClass>> call = apiInterface.getDatas();
        call.enqueue(new Callback<List<SharingDataClass>>() {
            @Override
            public void onResponse(Call<List<SharingDataClass>> call, Response<List<SharingDataClass>> response) {

                mArrayList = response.body();
                MainAdapter mAdapter = new MainAdapter(mArrayList,Main2Activity.this);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                Log.d("datalist2","로드될 데이터목록"+mArrayList);
                Log.d("datapackage","넘어온 데이터"+response.body());
    // 디비에서 삭제를 해도 어레이리스트부분은 삭제가 되지 않는다.
                // 이부분은 반드시 다뤄줘야한다.

            }

            @Override
            public void onFailure(Call<List<SharingDataClass>> call, Throwable t) {

            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //우측 상단의 옵션 메뉴 설정 부분인듯 하다.(크리에이트 해준다)
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //메뉴아이템(정상적일 경우 항해버튼이 표시됨)
        if (item.getItemId() == R.id.option_get_place) {  //을 클릭할시에 수행할 작업을 지정해주는 부분인듯 하다.
//           onFragmentChanged(0);

        }
        return true;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        }

         if (id == R.id.nav_gallery) {

//            com.kakao.kakaonavi.Location kakao = Destination.newBuilder("카카오 판교 오피스", 127.10821222694533, 37.40205604363057).build();
//            KakaoNaviParams params = KakaoNaviParams.newBuilder(kakao)
//                    .setNaviOptions(NaviOptions.newBuilder()
//                            .setCoordType(CoordType.WGS84) // WGS84로 설정해야 경위도 좌표 사용 가능.
//                            .setRpOption(RpOption.NO_AUTO)
//                            .setStartAngle(200) //시작 앵글 크기 설정.
//                            .setVehicleType(VehicleType.TWO_WHEEL).build()).build(); //길 안내 차종 타입 설정
//            //카카오 내비앱 실행.
//            KakaoNaviService.getInstance().navigate(Main2Activity.this, params);

            Intent intent = new Intent(getApplicationContext(),MyStampActivity.class);
            startActivity(intent);


        }
//        else if (id == R.id.nav_slideshow) {
////            Intent intent = new Intent(getApplicationContext(),MypageActivity.class);
////            startActivity(intent);
////
//            Intent intent = new Intent(getApplicationContext(),MapActivity.class);
//            startActivity(intent);
//
//
//        }
        else if (id == R.id.nav_manage) {

            Intent intent = new Intent(getApplicationContext(),MyShopActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(getApplicationContext(),UploadActivity.class);
            startActivity(intent);

        }

//        else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

