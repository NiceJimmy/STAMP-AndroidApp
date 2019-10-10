package org.techtown.sttampproject.Edit_Process.basic_info_update;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.techtown.sttampproject.Edit_Process.EditDataClass;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicInfoUpdate extends AppCompatActivity {

    String user_id;
    String shorder;

    String proname;
    String cate;
    String Tell1;
    String Tell2;
    String desc;

    List<EditDataClass> mArrayList;
    SharingApiInterface apiInterface;

    EditText ProName;
    EditText sTell1;
    EditText sTell2;
    EditText Desc;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info_update);

        ProName = (EditText) findViewById(R.id.store_name2);
        sTell1 = (EditText) findViewById(R.id.store_tel2_1);
        sTell2 = (EditText) findViewById(R.id.store_tel2_2);
        Desc = (EditText) findViewById(R.id.store_desc2);



        apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);

        SharedPreferences sf = getSharedPreferences("Login_Session", MODE_PRIVATE);
        user_id = sf.getString("user_id", "");
        shorder = sf.getString("shorder", "");


        spinner = (Spinner) findViewById(R.id.store_category2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cate = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button update = (Button) findViewById(R.id.insert_store2);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    public void onResume() {
        super.onResume();
        get_update_data();




    }

    public void get_update_data(){

        apiInterface.edit_basic_info(user_id,shorder).enqueue(new Callback<List<EditDataClass>>() {
            @Override
            public void onResponse(Call<List<EditDataClass>> call, Response<List<EditDataClass>> response) {
                mArrayList = response.body();

                ProName.setText(mArrayList.get(0).getProName());
                sTell1.setText(mArrayList.get(0).getStore_tel1());
                sTell2.setText(mArrayList.get(0).getStore_tel2());
                Desc.setText(mArrayList.get(0).getDescription());



            }

            @Override
            public void onFailure(Call<List<EditDataClass>> call, Throwable t) {

            }
        });
    }


}
