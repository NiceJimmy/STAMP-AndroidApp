package org.techtown.sttampproject.MyStamp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.techtown.sttampproject.MyShop.MyShopActivity;
import org.techtown.sttampproject.MyShop.MyShopAdapter;
import org.techtown.sttampproject.MyShop.MyShopDataClass;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStampActivity extends AppCompatActivity {

    private List<MyStampDataClass> mArrayList;
    private RecyclerView MyShop_recyclerview;
    private RecyclerView.LayoutManager layoutManager;


    SharingApiInterface apiInterface;

    String userID2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stamp);

        apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
        MyShop_recyclerview = (RecyclerView)findViewById(R.id.mystamp_recyclerview);

        layoutManager = new LinearLayoutManager(this);
        MyShop_recyclerview.setLayoutManager(layoutManager);

        SharedPreferences sf = getSharedPreferences("session",MODE_PRIVATE);
        userID2 = sf.getString("ID","");


    }

    public void onResume(){
        super.onResume();

        getData();

    }



    public void getData(){ // 디비의 데이타를 로드하는 부분



        Call<List<MyStampDataClass>> call = apiInterface.get_MyStamp_info(userID2);
        call.enqueue(new Callback<List<MyStampDataClass>>() {
            @Override
            public void onResponse(Call<List<MyStampDataClass>> call, Response<List<MyStampDataClass>> response) {

                mArrayList = response.body();
                MyStampAdapter mAdapter = new MyStampAdapter(mArrayList,MyStampActivity.this);
                MyShop_recyclerview.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                Log.d("datalist24","로드될 데이터목록"+mArrayList);
                Log.d("datapackage","넘어온 데이터"+response.body());
                // 디비에서 삭제를 해도 어레이리스트부분은 삭제가 되지 않는다.
                // 이부분은 반드시 다뤄줘야한다.

            }

            @Override
            public void onFailure(Call<List<MyStampDataClass>> call, Throwable t) {

            }
        });



    }


}
