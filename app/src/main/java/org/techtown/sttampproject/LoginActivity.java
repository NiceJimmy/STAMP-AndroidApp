package org.techtown.sttampproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.techtown.sttampproject.RegisterProcess.RegisterActivity;
import org.techtown.sttampproject.RetrofitService.ResultModel2;
import org.techtown.sttampproject.RetrofitService.UserApi2;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText mID;
    EditText mPW;
    UserApi2 mUserApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ///// 해시키 구하기 ////

        try{
                         PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                         for (Signature signature : info.signatures) {
                                 MessageDigest md;
                                 md = MessageDigest.getInstance("SHA");
                                 md.update(signature.toByteArray());
                                 String key = new String(Base64.encode(md.digest(), 0));
                                 Log.d("Hash key:", "!!!!!!!"+key+"!!!!!!");
                             }
                     } catch (Exception e){
                         Log.e("name not found", e.toString());
                     }


        //////해시키 구하기 끝 ///









        mID = (EditText)findViewById(R.id.ID);
        mPW = (EditText)findViewById(R.id.PW);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(UserApi2.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mUserApi = retrofit2.create(UserApi2.class);


        Button LoginButton = (Button)findViewById(R.id.Login);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserApi.login1(mID.getText().toString(),mPW.getText().toString()).enqueue(new Callback<ResultModel2>() {
                            @Override
                            public void onResponse(Call<ResultModel2> call, Response<ResultModel2> response) {
                                // 정상 결과
                                ResultModel2 result = response.body();
                                Log.d("datapackage","넘어온 데이터"+response);

                                if (result.getResult().equals("ok")) {
                                    //로그인 성공


                                    Log.d("posit1","클릭시 위치값: ");
                                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                                    startActivity(intent);

                                    SharedPreferences sharedPreferences = getSharedPreferences("session",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    String ID = mID.getText().toString(); // 사용자가 입력한 저장할 데이터
                                    editor.putString("ID",ID);
                                    editor.apply();
                                    Log.d("ses1","로그인세션(저장한값) : "+ID);



                                }else {
                                    Toast.makeText(LoginActivity.this,"로그인 정보가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultModel2> call, Throwable t) {
                                // 네트워크 문제
                            }
                        });




            }
        });


        TextView register = (TextView) findViewById(R.id.Registration);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);






            }
        });

    }
}

