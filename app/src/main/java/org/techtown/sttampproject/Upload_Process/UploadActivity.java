package org.techtown.sttampproject.Upload_Process;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.ShareActivity.ShareActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<SharingDataClass> mArrayList;
    SharingApiInterface apiInterface;

    String user_id;
    UploadAdapter upload_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
        recyclerView = findViewById(R.id.uploaded_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        SharedPreferences sf = getSharedPreferences("session", MODE_PRIVATE);
        user_id = sf.getString("ID", "");




        Button button = (Button) findViewById(R.id.start_upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(getApplicationContext(), ShareActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData(){

        apiInterface.get_uploading_data(user_id).enqueue(new Callback<List<SharingDataClass>>() {
            @Override
            public void onResponse(Call<List<SharingDataClass>> call, Response<List<SharingDataClass>> response) {
                mArrayList = response.body();
                upload_adapter = new UploadAdapter(mArrayList,UploadActivity.this);
                recyclerView.setAdapter(upload_adapter);

            }

            @Override
            public void onFailure(Call<List<SharingDataClass>> call, Throwable t) {

            }
        });
    }

}
