package org.techtown.sttampproject.SharingGridView2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;


import org.techtown.sttampproject.MenuProcess.RegisterMenu;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.RetrofitService.APIHelper;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.SharingGridView2.CountID.CountData;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ApiClient2;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ApiInterface2;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ImageClass2;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharingPictures2 extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 0;
    private static String TAG = "recyclerview_example";

    private ArrayList<Dictionary3> mArrayList;
    private List<ImageClass2> mArrayList2;
    private CustumAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ItemTouchHelper mItemTouchHelper;

    Uri uri;

    Bitmap bm;
    String user_id;
    int start;
    int s;
//    private List<CountData> mList;
    ApiInterface2 apiInterface;
String shorder;
    Switch sw;
    Button save_order;
    Button buttonInsert;
    Button button6;

    SharingApiInterface apiInterface2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_pictures2);


        apiInterface2 = SharingApiClient.getApiClient().create(SharingApiInterface.class);
       save_order = (Button) findViewById(R.id.save_button);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        // MainActivity에서 RecyclerView의 데이터에 접근시 사용됩니다.
        mArrayList = new ArrayList<>();
        mArrayList2 = new ArrayList<>();
//        mList = new ArrayList<>();

       //로그인한 아이디값
        SharedPreferences sf = getSharedPreferences("session",MODE_PRIVATE);
        user_id = sf.getString("ID","");

        SharedPreferences sf2 = getSharedPreferences("ShareActivity",MODE_PRIVATE);
        shorder = sf2.getString("shorder","");

        // RecyclerView를 위해 CustomAdapter를 사용합니다.
        mAdapter = new CustumAdapter( mArrayList);
        mRecyclerView.setAdapter(mAdapter);

               SimpleItemTouchHelperCallback mCallback = new SimpleItemTouchHelperCallback(mAdapter);
               mItemTouchHelper = new ItemTouchHelper(mCallback);


        // RecyclerView의 줄(row) 사이에 수평선을 넣기위해 사용됩니다.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
        mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



         sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    mItemTouchHelper.attachToRecyclerView(mRecyclerView);
                    sw.setVisibility(View.GONE);
                    save_order.setVisibility(View.VISIBLE);
                    buttonInsert.setVisibility(View.GONE);
                    button6.setVisibility(View.GONE);

                }else{
                    mItemTouchHelper.attachToRecyclerView(null);

                }
            }
        });












        buttonInsert = (Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();

            }
        });

        button6 = (Button) findViewById(R.id.next_button);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                SharedPreferences sharedPreferences = getSharedPreferences("count",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                editor.putString("countNumber",String.valueOf(s));
//                editor.apply();

                Intent intent = new Intent(getApplicationContext(),RegisterMenu.class);
                startActivity(intent);
                finish();




            }
        });



//사진 순서 수정하는 프로세스가 시작된다 .
        save_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemTouchHelper.attachToRecyclerView(null);
                sw.setVisibility(View.VISIBLE);
                buttonInsert.setVisibility(View.VISIBLE);
                button6.setVisibility(View.VISIBLE);
                save_order.setVisibility(View.GONE);
                sw.setChecked(false);
//                Log.d("picorder","사진 등록된 인덱스 : " +mArrayList.get(0).getPosit());

                getCount2();


            }
        });

        Log.d("picorder","사진 등록된 인덱스 : " +mArrayList);
    }






    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:

                    uri = data.getData();


                    uploadImage();


                    break;

            }
    }




    //우선 uri를 bitmap으로 변환한다(그다음 비트맵을 base 64로 변환할 것이다.)

    private void UriToBitmap(){

        try {
            bm =  MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

//            ExifInterface exif = new ExifInterface(uri.getPath());
//            int exifOrientation = exif.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//            int exifDegree = exifOrientationToDegrees(exifOrientation);
//
//            )



        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }



    // 비트맵 이미지를 Base64 String으로 변환시켜주는 메소드
    private String convertToString()
    {
        UriToBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }


    // 레트로핏 통신을 이용하여 이미지를 업로드 시키는 메소드 이다.

    private void uploadImage(){

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(SharingPictures2.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("잠시 기다려줄려 ?....");
        progressDoalog.setTitle("사진을 업로드 중인께");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCanceledOnTouchOutside(false);
        // show it
        progressDoalog.show();


        String image = convertToString();

for(int i =0; i<=mArrayList.size(); i++){
     start = i;
}

        ApiInterface2 apiInterface = ApiClient2.getApiClient().create(ApiInterface2.class);
        Call<ImageClass2> call = apiInterface.uploadImage(user_id,shorder,String.valueOf(start), String.valueOf(start),user_id+"["+shorder+"]"+"["+String.valueOf(start)+"]",image);
        Log.d("imagevalue","이미지 스트링값:"+image);
        APIHelper.enqueueWithRetry(call, 30, new Callback<ImageClass2>() {
            @Override
            public void onResponse(Call<ImageClass2> call, Response<ImageClass2> response) {

                ImageClass2 img_pojo = response.body();
                Log.d("respon123",""+img_pojo.getResponse());

                if (img_pojo.getResponse().equals("Image uploaded Succesfully....")) {
//                    if(response.body()!=null){
                    progressDoalog.dismiss();


                    Dictionary3 dict = new Dictionary3(String.valueOf(uri), String.valueOf(start));

                    mArrayList.add(dict); // RecyclerView의 마지막 줄에 삽입
                    mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
                    //액션 픽을 실행해서 사진을 선택했을 시에 버튼을 한번 더 눌러야지만 사진이 추가되는 현상이 있었다.
                    //어레이스트에 사진 추가시 notifydatasetchanged 를 바로 실행시켜야만 리사이클러뷰에 반영이되는데
                    //pic 메소드에 이게 있었으니 다시한번 버튼을 눌러 pic 메소드를 실행시켜야만 사진이 추가되는 문제가 생겼던 것이다.

                    Log.d("picorder","사진 등록된 인덱스 : " +mArrayList.get(0).getPosit());
                    SharingApiInterface apiInterface = SharingApiClient.getApiClient().create(SharingApiInterface.class);
                    Call<SharingDataClass> call2 = apiInterface.checking_pic(user_id,shorder);
                    APIHelper.enqueueWithRetry(call2, 30, new Callback<SharingDataClass>() {
                        @Override
                        public void onResponse(Call<SharingDataClass> call, Response<SharingDataClass> response) {

                        }

                        @Override
                        public void onFailure(Call<SharingDataClass> call, Throwable t) {

                        }
                    });






                }else {
                    progressDoalog.dismiss();
                    Toast.makeText(SharingPictures2.this, "업로드 실패", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ImageClass2> call, Throwable t) {

                Log.d("respon123",""+t.toString());

            }
        });


    }



    public void getCount2(){ // 사진이 저장되어있는 순서를 불러온다음 다시 그 순서를 바뀌어진 순서대로 저장하는 메소드

        apiInterface2.get_count_info(user_id,shorder).enqueue(new Callback<List<ImageClass2>>() {
            @Override
            public void onResponse(Call<List<ImageClass2>> call, Response<List<ImageClass2>> response) {


                mArrayList2 = response.body();

                Log.d("test33","테스트"+user_id+shorder);
             for(int i=0; i<mArrayList.size(); i++){

                 apiInterface2.update_pic_count(user_id,shorder,mArrayList.get(i).getPosit(),mArrayList2.get(i).getPicorder2()).enqueue(new Callback<ImageClass2>() {
                     @Override
                     public void onResponse(Call<ImageClass2> call, Response<ImageClass2> response) {

                     }

                     @Override
                     public void onFailure(Call<ImageClass2> call, Throwable t) {

                     }
                 });

                    }




            }

            @Override
            public void onFailure(Call<List<ImageClass2>> call, Throwable t) {

            }
        });



    }



}
