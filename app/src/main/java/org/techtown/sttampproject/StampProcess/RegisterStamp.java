package org.techtown.sttampproject.StampProcess;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.techtown.sttampproject.Main2Activity;
import org.techtown.sttampproject.MainActivity;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.RetrofitService.APIHelper;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.SharingGridView2.CountID.CountData;
import org.techtown.sttampproject.SharingGridView2.Dictionary3;
import org.techtown.sttampproject.SharingGridView2.SharingPictures2;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ApiClient2;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ApiInterface2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterStamp extends AppCompatActivity {

    private static String TAG = "recyclerview_example";

    private ArrayList<StampDataClass> mArrayList;
    private StampAdapter mAdapter;
    private RecyclerView mRecyclerView;

    TextView Start;
    TextView Finish;
    TextView Rate;

    int start;

    private List<CountData> mList;
    String user_id;
    String startTime;
    String finishTime;
    String discountRate;




    String proname;
    String desc;
    String path;
    String cate;
    String address;
    String address2;
    String Tell1;
    String Tell2;
    String shorder;

    GridLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_stamp);


        SharedPreferences sf = getSharedPreferences("session",MODE_PRIVATE);
        user_id = sf.getString("ID","");

        SharedPreferences sf2 = getSharedPreferences("ShareActivity",MODE_PRIVATE);
        shorder = sf2.getString("shorder","");


        layoutManager = new GridLayoutManager(getApplicationContext(), 3);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list2);
        mRecyclerView.setLayoutManager(layoutManager);
        mArrayList = new ArrayList<>();



        mAdapter = new StampAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        Button buttonInsert = (Button)findViewById(R.id.button_main_insert2);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadStamp();

            }
        });

        Button buttonFinish = (Button)findViewById(R.id.finish_stamp);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_finish();
                finish();

            }
        });



    }


    private void uploadStamp(){


        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterStamp.this);
        View view = LayoutInflater.from(RegisterStamp.this)
                .inflate(R.layout.edit_stamp, null, false);
        builder.setView(view);
        final Button ButtonSart = (Button) view.findViewById(R.id.insert_start);
        final Button ButtonFinish = (Button) view.findViewById(R.id.insert_finish);
        final EditText insertRate = (EditText) view.findViewById(R.id.insert_rate);
        final Button ButtonRegister = (Button) view.findViewById(R.id.button_register);
        Start = (TextView) view.findViewById(R.id.show_start);
        Finish = (TextView) view.findViewById(R.id.show_finish);



        final Calendar cal = Calendar.getInstance();

        Log.e(TAG, cal.get(Calendar.YEAR)+"");
        Log.e(TAG, cal.get(Calendar.MONTH)+1+"");
        Log.e(TAG, cal.get(Calendar.DATE)+"");
        Log.e(TAG, cal.get(Calendar.HOUR_OF_DAY)+"");
        Log.e(TAG, cal.get(Calendar.MINUTE)+"");



        ButtonSart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(RegisterStamp.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {

                        String msg = String.format("%02d:%02d", hour, min);
                        Start.setText(msg);
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                dialog.show();

            }
        });
        ButtonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(RegisterStamp.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {

                        String msg = String.format("%02d:%02d", hour, min);
                        Finish.setText(msg);
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                dialog.show();

            }
        });



        final AlertDialog dialog = builder.create();
        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(RegisterStamp.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage("잠시 기다려 주세요..");
                progressDoalog.setTitle("스탬프를 업로드 중입니다.");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.setCanceledOnTouchOutside(false);
                // show it
                progressDoalog.show();

                startTime = Start.getText().toString();
                finishTime = Finish.getText().toString();
                discountRate = insertRate.getText().toString();

                for(int i =0; i<=mArrayList.size(); i++){
                    start = i;
                }

                String start2 = String.valueOf(start);


                SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
                Call<StampDataClass> call = apiInterface.uploadStamp(user_id,shorder,start2,startTime,finishTime,discountRate);
                APIHelper.enqueueWithRetry(call, 30, new Callback<StampDataClass>() {
                    @Override
                    public void onResponse(Call<StampDataClass> call, Response<StampDataClass> response) {
                        StampDataClass stamp = response.body();

                        if (stamp.getResponse().equals("sttamp uploaded Succesfully....")) {
//
                            progressDoalog.dismiss();

                            StampDataClass dataClass = new StampDataClass(user_id,shorder,String.valueOf(start),startTime,finishTime,discountRate,String.valueOf(response));
                            mArrayList.add(dataClass); // RecyclerView의 마지막 줄에 삽입
                            mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
                            //액션 픽을 실행해서 사진을 선택했을 시에 버튼을 한번 더 눌러야지만 사진이 추가되는 현상이 있었다.
                            //어레이스트에 사진 추가시 notifydatasetchanged 를 바로 실행시켜야만 리사이클러뷰에 반영이되는데
                            //pic 메소드에 이게 있었으니 다시한번 버튼을 눌러 pic 메소드를 실행시켜야만 사진이 추가되는 문제가 생겼던 것이다.
                            dialog.dismiss();

                            SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
                            Call<SharingDataClass> call2 = apiInterface.checking_stamp(user_id,shorder);
                            APIHelper.enqueueWithRetry(call2, 30, new Callback<SharingDataClass>() {
                                @Override
                                public void onResponse(Call<SharingDataClass> call, Response<SharingDataClass> response) {

                                }

                                @Override
                                public void onFailure(Call<SharingDataClass> call, Throwable t) {

                                }
                            });

                        }else {
                            progressDoalog.dismiss();
                            Toast.makeText(RegisterStamp.this, "업로드 실패", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StampDataClass> call, Throwable t) {

                    }
                });
            }
        });



        dialog.show();

    }

















        private void save_finish(){


            SharedPreferences sf = getSharedPreferences("ShareActivity",MODE_PRIVATE);
            SharedPreferences sf2 = getSharedPreferences("ShareActivity2",MODE_PRIVATE);
            proname = sf.getString("shop_name","");
            cate = sf.getString("shop_category","");
            Tell1 = sf.getString("shop_tell1","");
            Tell2 = sf.getString("shop_tell2","");
            desc = sf.getString("shop_desc","");

            address = sf2.getString("shop_address","");
            address2 = sf2.getString("shop_address2","");


            path = "http://ec2-13-209-15-23.ap-northeast-2.compute.amazonaws.com/imageupload/sharepic/" + user_id+"["+shorder+"]" +"["+1+"]"+".jpg";

            Log.d("infoinfo","데이터 여부 : " + proname);


            SharingApiInterface apiInterface2 = SharingApiClient.getApiClient().create(SharingApiInterface.class);
            Call<SharingDataClass> call2 = apiInterface2.UploadShareInfo(user_id,cate,proname,address,address2,desc,shorder,path,Tell1,Tell2);
            call2.enqueue(new Callback<SharingDataClass>() {
                @Override
                public void onResponse(Call<SharingDataClass> call, Response<SharingDataClass> response) {
                    SharingDataClass sdc = response.body();
                    Log.d("Server Response2",""+sdc.getResponse());
                }

                @Override
                public void onFailure(Call<SharingDataClass> call, Throwable t) {
                    Log.d("Server Response2",""+t.toString());
                }
            });


            Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(intent);

            // 스택이 쌓이는 문제점을 해결하기 위해 추후에 activityfor result로 바꿔줘야 한다.

        }


}
