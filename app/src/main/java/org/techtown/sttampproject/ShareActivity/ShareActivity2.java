package org.techtown.sttampproject.ShareActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.sttampproject.DaumWebViewActivity;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.RetrofitService.APIHelper;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.SharingGridView2.SharingPictures2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareActivity2 extends AppCompatActivity {

TextView Address;
EditText Address2;

    String address;
    String address2;


    String shorder;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share2);

        Address = (TextView) findViewById(R.id.store_address);
        Address2 = (EditText) findViewById(R.id.detail_address);

        SharedPreferences sf = getSharedPreferences("session",MODE_PRIVATE);
        user_id = sf.getString("ID","");

        SharedPreferences sf2 = getSharedPreferences("ShareActivity",MODE_PRIVATE);
        shorder = sf2.getString("shorder","");





        Button button4 = (Button)findViewById(R.id.address_search); // 다음 웹 뷰 액티비티로 가는 버튼, 현재 api 작동 오류
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent23 = new Intent(getApplicationContext(),DaumWebViewActivity.class);
                startActivityForResult(intent23,3000);
            }
        });


        Button save =(Button) findViewById(R.id.insert_store);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = Address.getText().toString();
                address2 = Address2.getText().toString();




                SharedPreferences sharedPreferences = getSharedPreferences("ShareActivity2",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("shop_address",address);
                editor.putString("shop_address2",address2);
                editor.apply();

                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(ShareActivity2.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage("잠시 기다려줄려 ?....");
                progressDoalog.setTitle("데이터를 업로드 중인께");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.setCanceledOnTouchOutside(false);
                // show it
                progressDoalog.show();

                SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
                Call<SharingDataClass> call = apiInterface.Upload_Process2(user_id,shorder,address,address2);
                APIHelper.enqueueWithRetry(call, 30, new Callback<SharingDataClass>() {
                    @Override
                    public void onResponse(Call<SharingDataClass> call, Response<SharingDataClass> response) {
                        SharingDataClass data = response.body();

                        if(data.getResponse().equals("process2 uploaded Successfully...")){
                            progressDoalog.dismiss();


                            Intent intent = new Intent(getApplicationContext(), SharingPictures2.class);
                            startActivity(intent);

                        }else {

                            progressDoalog.dismiss();
                            Toast.makeText(ShareActivity2.this, "업로드 실패", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SharingDataClass> call, Throwable t) {

                    }
                });




                Intent intent = new Intent(getApplicationContext(),SharingPictures2.class);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
// MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 3000:
                    Address.setText(data.getStringExtra("result"));
                    break;
            }
        }
    }


}
