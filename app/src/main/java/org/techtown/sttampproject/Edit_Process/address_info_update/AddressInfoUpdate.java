package org.techtown.sttampproject.Edit_Process.address_info_update;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.techtown.sttampproject.DaumWebViewActivity;
import org.techtown.sttampproject.Edit_Process.EditDataClass;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressInfoUpdate extends AppCompatActivity {

    List<EditDataClass> mArrayList;
    SharingApiInterface apiInterface;

    TextView Address;
    EditText Address2;
    String address;
    String address2;

    String user_id;
    String shorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_info_update);

        apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);

        SharedPreferences sf = getSharedPreferences("Login_Session", MODE_PRIVATE);
        user_id = sf.getString("user_id", "");
        shorder = sf.getString("shorder", "");


        Address = (TextView) findViewById(R.id.store_address2);
        Address2 = (EditText) findViewById(R.id.detail_address2);


        Button button4 = (Button)findViewById(R.id.address_search2); // 다음 웹 뷰 액티비티로 가는 버튼, 현재 api 작동 오류
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent23 = new Intent(getApplicationContext(),DaumWebViewActivity.class);
                startActivityForResult(intent23,3000);
            }
        });


    }


    public void onResume() {
        super.onResume();
        get_update_data();




    }


    public void get_update_data(){

        apiInterface.edit_address_info(user_id,shorder).enqueue(new Callback<List<EditDataClass>>() {
            @Override
            public void onResponse(Call<List<EditDataClass>> call, Response<List<EditDataClass>> response) {
                mArrayList = response.body();

                Address.setText(mArrayList.get(0).getAddress());
                Address2.setText(mArrayList.get(0).getAddress2());
                       }

            @Override
            public void onFailure(Call<List<EditDataClass>> call, Throwable t) {

            }
        });
    }



}
