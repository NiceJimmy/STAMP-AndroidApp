package org.techtown.sttampproject.MyPage;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.techtown.sttampproject.Main2Activity;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.MainAdapter;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private List<SharingDataClass> mArrayList;
    SharingApiInterface apiInterface;
    String ID;
    MypageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
        recyclerView = findViewById(R.id.MainRecyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sf = getSharedPreferences("session",MODE_PRIVATE);
        ID = sf.getString("ID","");




    }


    public void onResume() {
        super.onResume();

        getData();




    }

    public void getData() { // 디비의 데이타를 로드하는 부분


        Call<List<SharingDataClass>> call = apiInterface.getDatas2();
        call.enqueue(new Callback<List<SharingDataClass>>() {
            @Override
            public void onResponse(Call<List<SharingDataClass>> call, Response<List<SharingDataClass>> response) {

                mArrayList = response.body();

                adapter = new MypageAdapter(mArrayList, MypageActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();



                // 디비에서 삭제를 해도 어레이리스트부분은 삭제가 되지 않는다.
                // 이부분은 반드시 다뤄줘야한다.
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                searchManager.getSearchableInfo(getComponentName());
                adapter.getFilter().filter(ID);
            }

            @Override
            public void onFailure(Call<List<SharingDataClass>> call, Throwable t) {

            }
        });


    }
}