package org.techtown.sttampproject.Edit_Process.pic_info_update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.techtown.sttampproject.MenuProcess.RegisterMenu;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.RetrofitService.APIHelper;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingDataClass;
import org.techtown.sttampproject.SharingGridView2.CustumAdapter;
import org.techtown.sttampproject.SharingGridView2.Dictionary3;
import org.techtown.sttampproject.SharingGridView2.SharingPictures2;
import org.techtown.sttampproject.SharingGridView2.SimpleItemTouchHelperCallback;
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

public class PicInfoUpdate extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 0;
    private static String TAG = "recyclerview_example";


    private List<ImageClass2> mArrayList2;
    private List<ImageClass2> mArrayList;
    private CustumAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ItemTouchHelper mItemTouchHelper;
    private PicUpdateAdapter myPicUpdateAdapter;

    Uri uri;

    Bitmap bm;
    String user_id;
    int start;
    int s;

    String shorder;
    Switch sw;
    Button save_order;
    Button buttonInsert;
    Button button6;

    SharingApiInterface apiInterface2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_info_update);

        apiInterface2 = SharingApiClient.getApiClient().create(SharingApiInterface.class);
        save_order = (Button) findViewById(R.id.save_button2);



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list2);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


//        mArrayList = new ArrayList<>(); // 프론트엔드단 리사이클러뷰 자체 어레이리스트
//        mArrayList2 = new ArrayList<>(); // 디비에서 가꼬온 사진목록정보관련 어레이리스트
//        mList = new ArrayList<>();

        //로그인한 아이디값
        SharedPreferences sf = getSharedPreferences("Login_Session", MODE_PRIVATE);
        user_id = sf.getString("user_id", "");
        shorder = sf.getString("shorder", "");



        // RecyclerView의 줄(row) 사이에 수평선을 넣기위해 사용됩니다.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
        mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        sw = (Switch) findViewById(R.id.switch2_12);



        buttonInsert = (Button)findViewById(R.id.button_main_insert2);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();

            }
        });

        button6 = (Button) findViewById(R.id.next_button2);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


       getPic();




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
        progressDoalog = new ProgressDialog(PicInfoUpdate.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("잠시 기다려주세요");
        progressDoalog.setTitle("사진을 업로드 중입니다.");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCanceledOnTouchOutside(false);
        // show it
        progressDoalog.show();


        String image = convertToString();

         //사진을 업로드하는 프로세스 (이제는 원래 사진목록에다가 추가로 다른 사진정보를 insert 하는 방식으로 해야한다.(업데이트가아니라
        //insert로 해야된다. 왜냐하면 순서를 바꾸는 방식이니깐.
        ApiInterface2 apiInterface = ApiClient2.getApiClient().create(ApiInterface2.class);
        Call<ImageClass2> call = apiInterface.uploadImage(user_id,shorder,String.valueOf(start), String.valueOf(start),user_id+"["+shorder+"]"+"["+String.valueOf(start)+"]",image);
        Log.d("imagevalue","이미지 스트링값:"+image);
        APIHelper.enqueueWithRetry(call, 30, new Callback<ImageClass2>() {
            @Override
            public void onResponse(Call<ImageClass2> call, Response<ImageClass2> response) {

                ImageClass2 img_pojo = response.body();
                Log.d("respon123",""+img_pojo.getResponse());

                if (img_pojo.getResponse().equals("Image uploaded Succesfully....")) {

                    progressDoalog.dismiss();


                    getPic();



                    //사진을 등록했다는 식별자를 업로드해줌
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
                    Toast.makeText(PicInfoUpdate.this, "업로드 실패", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ImageClass2> call, Throwable t) {


            }
        });


    }



    public void getCount2(){ // 사진이 저장되어있는 순서를 불러온다음 다시 그 순서를 바뀌어진 순서대로 저장하는 메소드


         }






 //get_pic_data.php
    public void getPic(){

        apiInterface2.get_pic_data(user_id,shorder).enqueue(new Callback<List<ImageClass2>>() {
            @Override
            public void onResponse(Call<List<ImageClass2>> call, Response<List<ImageClass2>> response) {
                mArrayList2 = response.body();

                myPicUpdateAdapter = new PicUpdateAdapter(mArrayList2);
                mRecyclerView.setAdapter(myPicUpdateAdapter);

                SimpleItemTouchHelperCallback mCallback = new SimpleItemTouchHelperCallback(myPicUpdateAdapter);
                mItemTouchHelper = new ItemTouchHelper(mCallback);

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

                        for(int i=0; i<mArrayList2.size(); i++){
                            start = Integer.parseInt(mArrayList2.get(i).getPicorder())+1;
                        }


                        apiInterface2.get_count_info(user_id,shorder).enqueue(new Callback<List<ImageClass2>>() {
                            @Override
                            public void onResponse(Call<List<ImageClass2>> call, Response<List<ImageClass2>> response) {

                mArrayList = response.body();
                Log.d("arraylistsize","어레이리스트 : " + mArrayList2.size());

                           for(int i=0; i<mArrayList2.size(); i++){
                            Log.d("change_value","입력되는 값 : " + mArrayList2.get(i).getPicorder()+", "+mArrayList.get(i).getPicorder2());
                        apiInterface2.update_pic_count(user_id,shorder,mArrayList2.get(i).getPicorder(),mArrayList.get(i).getPicorder2()).enqueue(new Callback<ImageClass2>() {

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
                });
            }

            @Override
            public void onFailure(Call<List<ImageClass2>> call, Throwable t) {

            }
        });

    }







}
