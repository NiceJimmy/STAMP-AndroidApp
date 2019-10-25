package org.techtown.sttampproject.MenuProcess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.techtown.sttampproject.R;
import org.techtown.sttampproject.RetrofitService.APIHelper;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.SharingGridView2.CountID.CountData;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ApiClient2;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ApiInterface2;
import org.techtown.sttampproject.StampProcess.RegisterStamp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterMenu extends AppCompatActivity {

  private ArrayList<MenuDataClass> mArrayList;
  private org.techtown.sttampproject.MenuProcess.MenuAdapter mAdapter;
  private RecyclerView mRecyclerView;

    int start;

    private List<CountData> mList;

    String user_id;
    String menu_name;
    String menu_price;
    private FloatingActionButton fab;
    String shorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);


        SharedPreferences sf = getSharedPreferences("session",MODE_PRIVATE);
        user_id = sf.getString("ID","");

        SharedPreferences sf2 = getSharedPreferences("ShareActivity",MODE_PRIVATE);
        shorder = sf2.getString("shorder","");

        mRecyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArrayList = new ArrayList<>();

        mAdapter = new org.techtown.sttampproject.MenuProcess.MenuAdapter(this,mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        Button button = (Button) findViewById(R.id.menu_insert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMenu();
            }
        });

        Button button1 = (Button) findViewById(R.id.menu_finish);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterStamp.class);
                startActivity(intent);
                finish();
            }
        });



    }



    private void uploadMenu(){

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterMenu.this);
        View view = LayoutInflater.from(RegisterMenu.this)
                .inflate(R.layout.edit_menu2, null, false);
        builder.setView(view);
        final Button ButtonRegister22 = (Button) view.findViewById(R.id.regis_menu77);
        final EditText insertMenu = (EditText) view.findViewById(R.id.insert_menuname);
        final EditText insertPrice = (EditText) view.findViewById(R.id.insert_price);

        final AlertDialog dialog = builder.create();


//        DecimalFormat dc = new DecimalFormat("###,###,###,###");
//        double insertPrice2 = Double.parseDouble(insertPrice.getText().toString());
//        String ch = dc.format(insertPrice2);
//        insertPrice.setText(ch);

        final DecimalFormat df = new DecimalFormat("###,###");
        final String[] result = {""};

        insertPrice.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(result[0])){     // StackOverflow를 막기위해,
                    result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    insertPrice.setText(result[0]);    // 결과 텍스트 셋팅.
                    insertPrice.setSelection(result[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });

        출처: https://caliou.tistory.com/73 [미녀개발자님의 블로그]

        ButtonRegister22.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(RegisterMenu.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("잠시 기다려 주세요.");
        progressDoalog.setTitle("메뉴를 업로드 중입니다.");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCanceledOnTouchOutside(false);
        // show it
        progressDoalog.show();

        menu_name = insertMenu.getText().toString();

        menu_price = insertPrice.getText().toString();

        for(int i =0; i<=mArrayList.size(); i++){
            start = i;
        }

        String start2 = String.valueOf(start);


        SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
        Call<MenuDataClass> call = apiInterface.uploadMenu(user_id,shorder,start2,menu_name,menu_price);
        APIHelper.enqueueWithRetry(call, 30, new Callback<MenuDataClass>() {
            @Override
            public void onResponse(Call<MenuDataClass> call, Response<MenuDataClass> response) {
                MenuDataClass menu = response.body();

                if(menu.getResponse().equals("menu uploaded Succesfully....")){
                    progressDoalog.dismiss();

                    MenuDataClass dataClass = new MenuDataClass(user_id,shorder,String.valueOf(start),menu_name,menu_price,String.valueOf(response));
                    mArrayList.add(dataClass);
                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();


                    SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
                    Call<SharingDataClass> call2 = apiInterface.checking_menu(user_id,shorder);
                    APIHelper.enqueueWithRetry(call2, 30, new Callback<SharingDataClass>() {
                        @Override
                        public void onResponse(Call<SharingDataClass> call, Response<SharingDataClass> response) {

                        }

                        @Override
                        public void onFailure(Call<SharingDataClass> call, Throwable t) {

                        }
                    });


                }
                else {
                    progressDoalog.dismiss();
                    Toast.makeText(RegisterMenu.this, "업로드 실패", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MenuDataClass> call, Throwable t) {

            }
        });
    }
});



        dialog.show();
    }





}
