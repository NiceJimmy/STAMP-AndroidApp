package org.techtown.sttampproject.Edit_Process;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.techtown.sttampproject.Edit_Process.address_info_update.AddressInfoUpdate;
import org.techtown.sttampproject.Edit_Process.basic_info_update.BasicInfoUpdate;
import org.techtown.sttampproject.Edit_Process.pic_info_update.PicInfoUpdate;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    String user_id;
    String shorder;

    List<EditDataClass> mArrayList;
    SharingApiInterface apiInterface;

    TextView address_button;
    TextView pic_button;
    TextView menu_button;
    TextView stamp_button;

    Button basic_button2;
    Button address_button2;
    Button pic_button2;
    Button menu_button2;
    Button stamp_button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        address_button = (TextView) findViewById(R.id.address_button);
        pic_button = (TextView) findViewById(R.id.pic_button);
        menu_button = (TextView) findViewById(R.id.menu_button);
        stamp_button = (TextView) findViewById(R.id.stamp_button);

        address_button2 = (Button) findViewById(R.id.address_button2);
        pic_button2 = (Button) findViewById(R.id.pic_button2);
        menu_button2 = (Button) findViewById(R.id.menu_button2);
        stamp_button2 = (Button) findViewById(R.id.stamp_button2);
        basic_button2 = (Button) findViewById(R.id.basic_button2);


        Intent intent = getIntent();
        shorder = intent.getStringExtra("shorder");
        user_id = intent.getStringExtra("user_id");

        apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);

        Log.d("dataintent","넘어오는 데타 : " +shorder +" " + user_id);

        SharedPreferences sharedPreferences = getSharedPreferences("Login_Session",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id",user_id);
        editor.putString("shorder",shorder);
        editor.apply();

        basic_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BasicInfoUpdate.class);
                startActivity(intent);
            }
        });

        address_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddressInfoUpdate.class);
                startActivity(intent);
            }
        });

        pic_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PicInfoUpdate.class);
                startActivity(intent);
            }
        });



    }


    public void onResume() {
        super.onResume();
        getData();

    }

    public void getData(){

        apiInterface.edit_process(user_id,shorder).enqueue(new Callback<List<EditDataClass>>() {
            @Override
            public void onResponse(Call<List<EditDataClass>> call, Response<List<EditDataClass>> response) {
                mArrayList = response.body();
                Log.d("dataintent2","넘어오는 데타 : " +mArrayList);

                if(!mArrayList.get(0).getAddress().equals("0")){
                    address_button.setVisibility(View.VISIBLE);
                    address_button2.setVisibility(View.VISIBLE);
                    pic_button2.setVisibility(View.VISIBLE);
                    pic_button2.setText("진행하기");

                }
                if(mArrayList.get(0).getPic_done().equals("1")){
                    pic_button.setVisibility(View.VISIBLE);
                    menu_button2.setVisibility(View.VISIBLE);
                    pic_button2.setText("수정하기");
                    menu_button2.setText("진행하기");
                }
                if(mArrayList.get(0).getMenu_done().equals("1")){
                    menu_button.setVisibility(View.VISIBLE);
                    menu_button2.setText("수정하기");
                    stamp_button2.setVisibility(View.VISIBLE);
                    stamp_button2.setText("진행하기");

                }
                if(mArrayList.get(0).getStamp_done().equals("1")){
                    stamp_button.setVisibility(View.VISIBLE);
                    stamp_button2.setText("수정하기");
                }
            }

            @Override
            public void onFailure(Call<List<EditDataClass>> call, Throwable t) {

            }
        });


    }

}
