package org.techtown.sttampproject.DetailPage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.sttampproject.DetailPage.LoadMenu.MenuAdapter2;
import org.techtown.sttampproject.DetailPage.LoadStamp.StampAdapter2;
import org.techtown.sttampproject.MapActivity;
import org.techtown.sttampproject.MenuProcess.MenuDataClass;
import org.techtown.sttampproject.MenuProcess.RegisterMenu;
import org.techtown.sttampproject.MyShop.MyShopActivity;
import org.techtown.sttampproject.MyShop.MyShopDataClass;
import org.techtown.sttampproject.MyStamp.MyStampActivity;
import org.techtown.sttampproject.MyStamp.MyStampDataClass;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.RetrofitService.APIHelper;
import org.techtown.sttampproject.ShareActivity.MainAdapter;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.ShareActivity.ShareActivity;
import org.techtown.sttampproject.ShareActivity.ShareActivity2;
import org.techtown.sttampproject.SharingGridView2.SharingPictures2;
import org.techtown.sttampproject.StampProcess.StampDataClass;
import org.techtown.sttampproject.Upload_Process.UploadActivity;
import org.techtown.sttampproject.customfonts.MyTextView_OpenSans_Regular;
import org.techtown.sttampproject.customfonts.MyTextView_Opensans_SemiBold;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {


    RecyclerView review_recycleview;
    RecyclerView stamp_recyclerview;
    RecyclerView menu_recyclerview;

    ReviewAdapter adapter_guestReview;
    StampAdapter2 stamp_adapter;
    MenuAdapter2 menu_adapter;
    private List<ViewPagerDataClass> mArrayList;
    private ArrayList<ReviewModalClass> mArrayList2;
    private ArrayList<StampDataClass> mArrayList3;
    private ArrayList<MenuDataClass> mArrayList4;


    ViewPager viewPager;
    ImageView left_nav, rightNav;
    ImageView map_button;
    MyTextView_OpenSans_Regular full_address;

    MyTextView_OpenSans_Regular about_shop;
    MyTextView_OpenSans_Regular tel_con;
    MyTextView_OpenSans_Regular tel_con2;
    MyTextView_OpenSans_Regular pro_name;


//    MyViewPagerAdapter myViewPagerAdapter;


    String userID; // 리뷰를 로드할때 쓰이는 아이디값(게시물에 대한 아이디)
    String userID2; // 리뷰를 등록할때 쓰이는 아이디값
    String shorder;
    String proName;
    String address;
    String address2;
    String stamp_num = "0";
    String stamp_use = "0";

    String About_shop;
    String Tel_con;
    String Tel_con2;


    String start_time;
    String finish_time;
    String off_rate;

    SharingApiInterface apiInterface;


    String rate;
    String date2;
    String contens;
    int start;
    String rating2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
        review_recycleview = (RecyclerView) findViewById(R.id.review_recycleview);
        stamp_recyclerview = (RecyclerView) findViewById(R.id.stamp_recycleview);
        menu_recyclerview = (RecyclerView) findViewById(R.id.menu_recyclerview2);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        left_nav = (ImageView) findViewById(R.id.left_nav);
        rightNav = (ImageView) findViewById(R.id.right_nav);
        map_button = (ImageView) findViewById(R.id.map);
        full_address = (MyTextView_OpenSans_Regular) findViewById(R.id.address);

        about_shop = (MyTextView_OpenSans_Regular) findViewById(R.id.about_shop);
        tel_con = (MyTextView_OpenSans_Regular) findViewById(R.id.tel_con);
        tel_con2 = (MyTextView_OpenSans_Regular) findViewById(R.id.tel_con2);
        pro_name = (MyTextView_OpenSans_Regular) findViewById(R.id.proName2);

        Intent intent = getIntent();
        shorder = intent.getStringExtra("shareorder");
        userID = intent.getStringExtra("userid");
        proName = intent.getStringExtra("proName");
        address = intent.getStringExtra("address");
        address2 = intent.getStringExtra("address2");
        About_shop = intent.getStringExtra("about_shop");
        Tel_con = intent.getStringExtra("tel_con");
        Tel_con2 = intent.getStringExtra("tel_con2");


        SharedPreferences sf = getSharedPreferences("session", MODE_PRIVATE);
        userID2 = sf.getString("ID", "");

        pro_name.setText(proName);
        full_address.setText(address + " " + address2);
        about_shop.setText(About_shop);
        tel_con.setText(Tel_con);
        tel_con2.setText(Tel_con2);


        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("address_1", address);
                intent.putExtra("address_2", address2);
                intent.putExtra("proName", proName);
                startActivity(intent);

            }
        });


        // Images left navigation
        left_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab);
            }
        });


        //Guest Review - Set the adaoter
        mArrayList2 = new ArrayList<>();
        adapter_guestReview = new ReviewAdapter(org.techtown.sttampproject.DetailPage.DetailActivity.this, mArrayList2);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false);
        review_recycleview.setLayoutManager(linearLayoutManager1);
        review_recycleview.setItemAnimator(new DefaultItemAnimator());
        review_recycleview.setAdapter(adapter_guestReview);


        mArrayList3 = new ArrayList<>();
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false);
        stamp_recyclerview.setLayoutManager(linearLayoutManager2);
        stamp_recyclerview.setItemAnimator(new DefaultItemAnimator());


        mArrayList4 = new ArrayList<>();
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        menu_recyclerview.setLayoutManager(linearLayoutManager3);
        menu_recyclerview.setItemAnimator(new DefaultItemAnimator());


//        layouts1 = new int[]{
//
//                R.layout.activity_hotel_detail_image,
//                R.layout.activity_hotel_detail_image,
//                R.layout.activity_hotel_detail_image,
//                R.layout.activity_hotel_detail_image
//
//
//
//        };


//        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
//        data.add("http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/imageupload/uploads/11.jpg");


        //로그인 세션 ID


        TextView textView = (TextView) findViewById(R.id.save_shop);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_shop();

                Log.d("datapro12", "넘어가는 데타 : " + userID + "+" + userID2 + "+" + proName + "+" + address + "+" + address2 + "+" + shorder + "+" + stamp_num + "+" + stamp_use);
                //여기에 update_shop 작업을 시작한다.

                SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
                Call<MyShopDataClass> call = apiInterface.update_shop_info(userID, userID2, proName, address, address2, shorder, stamp_num, stamp_use);
                APIHelper.enqueueWithRetry(call, 30, new Callback<MyShopDataClass>() {
                    @Override
                    public void onResponse(Call<MyShopDataClass> call, Response<MyShopDataClass> response) {
                        MyShopDataClass data = response.body();

                        if (data.getResponse().equals("uploaded Successfully....")) {


//                            Toast.makeText(DetailActivity.this, "업로드 성공", Toast.LENGTH_LONG).show();

                        } else {


//                            Toast.makeText(DetailActivity.this, "업로드 실패", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyShopDataClass> call, Throwable t) {

                    }
                });


            }
        });


        TextView use_stamp = (TextView) findViewById(R.id.use_stamp);
        use_stamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref3 = getSharedPreferences("stamp_info", MODE_PRIVATE);
                start_time = pref3.getString("start_time", "");
                finish_time = pref3.getString("finish_time", "");
                off_rate = pref3.getString("off_rate", "");
                Log.d("selected_time", "선택시간2 : " + start_time + " " + finish_time + " " + off_rate);
                use_stamp();
                SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
                Call<MyStampDataClass> call = apiInterface.update_usedStamp_info(userID, userID2, proName, start_time, finish_time, off_rate, address, address2, shorder, stamp_num);
                APIHelper.enqueueWithRetry(call, 30, new Callback<MyStampDataClass>() {
                    @Override
                    public void onResponse(Call<MyStampDataClass> call, Response<MyStampDataClass> response) {
                        MyStampDataClass data = response.body();

                        if (data.getResponse().equals("uploaded Successfully....")) {


                            Toast.makeText(org.techtown.sttampproject.DetailPage.DetailActivity.this, "업로드 성공", Toast.LENGTH_LONG).show();

                        } else {


                            Toast.makeText(org.techtown.sttampproject.DetailPage.DetailActivity.this, "업로드 실패", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyStampDataClass> call, Throwable t) {

                    }
                });


            }
        });


        /////////////////////////////////////리뷰 등록하는 부분/////////////////////////////////
        TextView review_button = (TextView) findViewById(R.id.textView15);
        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadReview();

            }
        });


    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageSelected(int position) {


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    void save_shop() {
//        final List<String> ListItems = new ArrayList<>();
//        ListItems.add("찜하신 매장은 '내가 찜한 SHOP' 에서 확인하실 수 있습니다.");
//        ListItems.add("할인율이 변동할 수 있으니 빠른 시일안에 사용해주세요~!");
//
//
//        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(org.techtown.sttampproject.DetailPage.DetailActivity.this)
                .inflate(R.layout.selected_shop, null, false);
        builder.setView(view);

        builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }


    void use_stamp() {


        final List<String> ListItems = new ArrayList<>();
        ListItems.add("");

        ListItems.add("Shop Name : " + proName);
        ListItems.add("Time : " + start_time + "~" + finish_time);
        ListItems.add("Rate : " + off_rate + "%");

        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(org.techtown.sttampproject.DetailPage.DetailActivity.this)
                .inflate(R.layout.selected_stamp, null, false);
        builder.setView(view);
        builder.setTitle("STAMP를 찍었습니다!");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {

            }
        });
        builder.show();
    }


    public void onResume() {
        super.onResume();

        getData();
        getData2();
        getData3();
        getData4();

    }


    private void uploadReview() {

        AlertDialog.Builder builder = new AlertDialog.Builder(org.techtown.sttampproject.DetailPage.DetailActivity.this);
        View view = LayoutInflater.from(org.techtown.sttampproject.DetailPage.DetailActivity.this)
                .inflate(R.layout.edit_review, null, false);
        builder.setView(view);
        final Button ButtonRegister = (Button) view.findViewById(R.id.review_insert);
        final EditText insertReview = (EditText) view.findViewById(R.id.contents_insert);
        final TextView Rating = (TextView) view.findViewById(R.id.rating);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Rating.setText("평점: " + rating);
                rating2 = String.valueOf(rating);
            }
        });


        final AlertDialog dialog = builder.create();

        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(org.techtown.sttampproject.DetailPage.DetailActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage("잠시 기다려줄려 ?....");
                progressDoalog.setTitle("리뷰를 업로드 중인께");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.setCanceledOnTouchOutside(false);
                // show it
                progressDoalog.show();

                contens = insertReview.getText().toString();

                rate = Rating.getText().toString();
                Date from = new Date();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                date2 = transFormat.format(from);

                for (int i = 0; i <= mArrayList2.size(); i++) {
                    start = i;
                }
                final String start2 = String.valueOf(start);

                Log.d("inser445", "입력데이터 : " + userID);
                Log.d("inser445", "입력데이터 : " + shorder);
                Log.d("inser445", "입력데이터 : " + start2);
                Log.d("inser445", "입력데이터 : " + contens);
                Log.d("inser445", "입력데이터 : " + rating2);
                Log.d("inser445", "입력데이터 : " + date2);


                SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
                Call<ReviewModalClass> call = apiInterface.uploadReview(userID, userID2, shorder, start2, contens, rating2, date2);
                APIHelper.enqueueWithRetry(call, 30, new Callback<ReviewModalClass>() {
                    @Override
                    public void onResponse(Call<ReviewModalClass> call, Response<ReviewModalClass> response) {
                        ReviewModalClass reviewData = response.body();

                        if (reviewData.getResponse().equals("review uploaded Succesfully....")) {
                            progressDoalog.dismiss();

                            ReviewModalClass reviewClass = new ReviewModalClass(userID, userID2, shorder, start2, contens, rate, date2, String.valueOf(response));
                            mArrayList2.add(0, reviewClass);
                            adapter_guestReview.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            progressDoalog.dismiss();
                            Toast.makeText(org.techtown.sttampproject.DetailPage.DetailActivity.this, "업로드 실패", Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<ReviewModalClass> call, Throwable t) {

                    }
                });
            }
        });
        dialog.show();
    }


    public void getData() {

        Log.d("datapackage", "넘어온 데이터" + shorder);
        String aa = "1";
        apiInterface.getDatas3(userID, shorder).enqueue(new Callback<List<ViewPagerDataClass>>() {
            @Override
            public void onResponse(Call<List<ViewPagerDataClass>> call, Response<List<ViewPagerDataClass>> response) {
                mArrayList = response.body();
                Log.d("datapackage", "넘어온 데이터" + response);

                viewPager = (ViewPager) findViewById(R.id.viewpager);
                MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(org.techtown.sttampproject.DetailPage.DetailActivity.this, mArrayList);
                viewPager.setAdapter(myViewPagerAdapter);


            }

            @Override
            public void onFailure(Call<List<ViewPagerDataClass>> call, Throwable t) {

            }
        });


    }


    public void getData2() {

        apiInterface.getStampData(userID, shorder).enqueue(new Callback<List<StampDataClass>>() {
            @Override
            public void onResponse(Call<List<StampDataClass>> call, Response<List<StampDataClass>> response) {
                mArrayList3 = (ArrayList<StampDataClass>) response.body();
                stamp_adapter = new StampAdapter2(org.techtown.sttampproject.DetailPage.DetailActivity.this, mArrayList3);
                stamp_recyclerview.setAdapter(stamp_adapter);


            }

            @Override
            public void onFailure(Call<List<StampDataClass>> call, Throwable t) {

            }
        });


    }

    public void getData3() {

        apiInterface.getMenuData(userID, shorder).enqueue(new Callback<List<MenuDataClass>>() {
            @Override
            public void onResponse(Call<List<MenuDataClass>> call, Response<List<MenuDataClass>> response) {
                mArrayList4 = (ArrayList<MenuDataClass>) response.body();
                menu_adapter = new MenuAdapter2(org.techtown.sttampproject.DetailPage.DetailActivity.this, mArrayList4);
                menu_recyclerview.setAdapter(menu_adapter);
            }

            @Override
            public void onFailure(Call<List<MenuDataClass>> call, Throwable t) {

            }
        });


    }


    public void getData4() {

        apiInterface.getReviewData(userID, shorder).enqueue(new Callback<List<ReviewModalClass>>() {
            @Override
            public void onResponse(Call<List<ReviewModalClass>> call, Response<List<ReviewModalClass>> response) {
                mArrayList2 = (ArrayList<ReviewModalClass>) response.body();
                adapter_guestReview = new ReviewAdapter(org.techtown.sttampproject.DetailPage.DetailActivity.this, mArrayList2);
                review_recycleview.setAdapter(adapter_guestReview);
            }

            @Override
            public void onFailure(Call<List<ReviewModalClass>> call, Throwable t) {

            }
        });


    }

}





