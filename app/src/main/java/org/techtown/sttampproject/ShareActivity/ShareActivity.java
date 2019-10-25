package org.techtown.sttampproject.ShareActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;

import org.techtown.sttampproject.DaumWebViewActivity;
import org.techtown.sttampproject.Main2Activity;
import org.techtown.sttampproject.MenuProcess.RegisterMenu;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.RetrofitService.APIHelper;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.SharingGridView2.CountID.CountData;
import org.techtown.sttampproject.SharingGridView2.SharingPictures2;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ApiClient2;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ApiInterface2;
import org.techtown.sttampproject.StampProcess.RegisterStamp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShareActivity extends AppCompatActivity {


        String proname;
        String cate;
        String Tell1;
        String Tell2;
        String desc;
        String user_id;

    private List<CountData> mList;


    int s;
    String s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        SharedPreferences sf = getSharedPreferences("session",MODE_PRIVATE);
        user_id = sf.getString("ID","");


       mList = new ArrayList<>();






        Spinner spinner = (Spinner) findViewById(R.id.store_category);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cate = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countID();

Button save =(Button) findViewById(R.id.insert_store);
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



        final EditText ProName = (EditText) findViewById(R.id.store_name);
        final EditText sTell1 = (EditText) findViewById(R.id.store_tel1);
        final EditText sTell2 = (EditText) findViewById(R.id.store_tel2);
        final EditText Desc = (EditText) findViewById(R.id.store_desc);


        proname = ProName.getText().toString();
        Tell1 = sTell1.getText().toString();
        Tell2 = sTell2.getText().toString();
        desc = Desc.getText().toString();


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ShareActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("잠시 기다려주세요.");
        progressDoalog.setTitle("데이터를 업로드 중입니다.");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCanceledOnTouchOutside(false);
        // show it
        progressDoalog.show();




        s2 = String.valueOf(s+1);
        Log.d("datalist554","넘어가는 기본정보 : "+s2+", "+s);
        SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
        Call<SharingDataClass> call = apiInterface.Upload_Process1(user_id,cate,proname,desc,s2,Tell1,Tell2);
        APIHelper.enqueueWithRetry(call, 30, new Callback<SharingDataClass>() {
            @Override
            public void onResponse(Call<SharingDataClass> call, Response<SharingDataClass> response) {
                SharingDataClass data = response.body();

                if(data.getResponse().equals("process1 uploaded Successfully...")){
                    progressDoalog.dismiss();


                    Intent intent = new Intent(getApplicationContext(), ShareActivity2.class);
                    startActivity(intent);
                    finish();

                }else {

                    progressDoalog.dismiss();
                    Toast.makeText(ShareActivity.this, "업로드 실패", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SharingDataClass> call, Throwable t) {

            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("ShareActivity",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("shorder",s2);
        editor.putString("shop_category",cate);
        editor.putString("shop_name",proname);
        editor.putString("shop_desc",desc);
        editor.putString("shop_tell1",Tell1);
        editor.putString("shop_tell2",Tell2);
        editor.apply();


    }
});






    }





    private void countID(){

        //  이 부분에서 레트로핏 통신을 활용하여 로그인한 ID의 게시물 업로드 개수를 조회해보자.
        ApiInterface2 apiInterface = ApiClient2.getApiClient().create(ApiInterface2.class);
        Call<List<CountData>> call = apiInterface.CountID();
        APIHelper.enqueueWithRetry(call, 5, new Callback<List<CountData>>() {
            @Override
            public void onResponse(Call<List<CountData>> call, Response<List<CountData>> response) {
                mList = response.body();
                s=0;
                for(int i=0; i<mList.size(); i++){
                    if(String.valueOf(mList.get(i).getUser_id()).equals(user_id)){
                        s +=1;
                    }
                }
                Log.d("CountUploadID","로드될 데이터목록 : "+ s);
            }
            @Override
            public void onFailure(Call<List<CountData>> call, Throwable t) {
            }
        });

    }



}
//
//
//        SharedPreferences sf = getSharedPreferences("session",MODE_PRIVATE);
//        user_id = sf.getString("ID","");
//
//
//
//
//        Button button4 = (Button)findViewById(R.id.button15); // 다음 웹 뷰 액티비티로 가는 버튼, 현재 api 작동 오류
//        button4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent23 = new Intent(getApplicationContext(),DaumWebViewActivity.class);
//                startActivity(intent23);
//            }
//        });
//
//
//        Intent intent = getIntent();
//        final String address = intent.getStringExtra("address");
//
//        //데이터를 수집하는 부분 시작 //
//
//
//
//
//
//
//
//        EditText et = (EditText) findViewById(R.id.ET);
//        EditText et2 = (EditText) findViewById(R.id.ET2);
//        EditText et3 = (EditText) findViewById(R.id.ET3);
//        EditText et4 = (EditText) findViewById(R.id.ET4);
//        EditText et5 = (EditText) findViewById(R.id.ET5);
//        EditText et6 = (EditText) findViewById(R.id.ET6);
//
//         category = et.getText().toString();
//        price = et2.getText().toString();
//         proName = et3.getText().toString();
//         description = et6.getText().toString();
//
//
//
//        et4.setText(address);
//         adds = et4.getText().toString()+" "+et5.getText().toString();
//
//
//
//
//        List<Address> list = null;
//        final Geocoder geocoder = new Geocoder(this);
//        try {
//            list = geocoder.getFromLocationName(adds, // 지역 이름
//                    10); // 읽을 개수
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
//        }
//
//        if (list != null) {
//            if (list.size() == 0) {
//
//                Toast.makeText(getApplicationContext(), "해당되는 주소 정보는 없다.", Toast.LENGTH_SHORT).show();
//            } else {
////                        tv.setText(list.get(0).toString());
//                //          list.get(0).getCountryName();  // 국가명
//                //          list.get(0).getLatitude();        // 위도
//                //          list.get(0).getLongitude();    // 경도
//                lat = list.get(0).getLatitude();
//                lon = list.get(0).getLongitude();
//                Log.d("result12","검색결과 : "+ lat);
//                Log.d("result12","검색결과 : "+ lon);
//            }
//        } // 여기까지 지오코더로 주소값 경도위도 반환하는 부분
//
//
//

//        Button button14 = (Button) findViewById(R.id.button14);
//        button14.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent12 = new Intent(getApplicationContext(),SharingPictures2.class);
//
//                startActivity(intent12);
//            }
//        });
//
//
//
//
//
//    }
//
//
//
//    public void onResume(){ //SharingPictures2.java 에서 쉐어드에 저장한 사진 값들.
//        super.onResume();
//
//        SharedPreferences pics = getSharedPreferences("shared", MODE_PRIVATE); // 저장한 데이터를 불러오는 부분이다.(로드)
//        pictures = pics.getString("pics", "");
//        Log.d("picvalue2","사진값 : "+pictures);
//
//        ArrayList aa = new ArrayList();
//
//        try {
//            jArray = new JSONArray(pictures);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
////        for(int i=0; i<jArray.length(); i++){
////            try {
////                jObject = jArray.getJSONObject(i);
////                String pic = jObject.getString("image");
////                aa.add(pic);
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////        }
//
//        try {
//            pic0 = (String) jArray.get(0);
//            pic1 = (String) jArray.get(1);
//            pic2 = (String) jArray.get(2);
//            pic3 = (String) jArray.get(3);
//            pic4 = (String) jArray.get(4);
//            pic5 = (String) jArray.get(5);
//            pic6 = (String) jArray.get(6);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("picvalue3","사진값 : "+pic0+pic1+pic2+pic3+pic4+pic5+pic6);
//    }
//
//
//
   //
//    }
//
//
//
//}
