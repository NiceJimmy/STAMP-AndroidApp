package org.techtown.sttampproject.RegisterProcess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

import org.techtown.sttampproject.LoginActivity;
import org.techtown.sttampproject.R;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiClient;
import org.techtown.sttampproject.ShareActivity.RetrofitService3.SharingApiInterface;
import org.techtown.sttampproject.ShareActivity.ShareActivity2;
import org.techtown.sttampproject.SharingGridView2.SharingPictures2;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 0;

    private AlertDialog dialog;
    private boolean validate = false;
    Uri uri;
    ImageView imageView;
    Bitmap bm;
    String profileimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //스피너 객체 선언 및 리소스를 가져오는 부분

      imageView = (ImageView) findViewById(R.id.imageView2);
        final EditText idText = (EditText)findViewById(R.id.ID);
        final EditText passwordText = (EditText)findViewById(R.id.PW);
        final EditText passwordText2 = (EditText)findViewById(R.id.PW2);
        final EditText emailText = (EditText)findViewById(R.id.Email);

        Resources resources = getApplicationContext().getResources();
       uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.profile)
               + '/' + getResources().getResourceTypeName(R.drawable.profile) + '/' + getResources().getResourceEntryName(R.drawable.profile) );


Button button2 = (Button) findViewById(R.id.button2);
button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        pickFromGallery();
    }
});



        //회원가입시 아이디가 사용가능한지 검증하는 부분 (CheckID 버튼을 누르면 검증을 시작한다.)
        final Button validateButton = (Button)findViewById(R.id.vali);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                if(validate){
                    return;//검증 완료
                }
                //회원가입 창에서 중복확인시에 ID 값을 입력하지 않았다면 아이디를 입력하라는 다이얼로그를 띄워준다.
                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("ID is empty")
                            .setPositiveButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }
                if(userID.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("ID는 반드시 영문으로 입력해주세요.")
                            .setPositiveButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }


                //아이디 중복확인에 대한 검증을 시작한다.
                //ValidateRequest.java 클래스를 따로 만들어준다음에 현재 클래스에서 객체를 생성하여 활용할 것이다.
                //Volly 라이브러리를 통해 이미 데이터베이스에 저장된 아이디 값들을 Json 형태로 받아와서 중복확인을 하는 방식으로 진행할 것이다.
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    // resqponseListener 객체를 생성한다.
                    @Override
                    public void onResponse(String response) { //Request 요청 후 답장이 온 데이터는 onResponse 함수의 인자값인 response에 저장된다..
                        try{
//                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();

                            //request 해서 돌아온 json형태의 response값에 대한 응답처리 과정이다?

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            // 저장된 데이터를 파싱한다..
                            // 중복여부를 판별하는 프로세스가 정확히 이해가 되지 않는다..(ASK)

                            if(success){//사용할 수 있는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                                validate = true;//검증완료
//                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
//                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }else{//사용할 수 없는 아이디라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("이미 사용중인 아이디입니다.")
                                        .setNegativeButton("OK", null)
                                        .create();
                                dialog.show();
                            }

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };//Response.Listener 완료

                //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분이다.
                //validateRequest라는 객체를 생성한다.(userID, responseListener 매개변수는 ValidateRequest.java 에서 선언되었다.)
                //Request를 보낼 queue를 생성한다.
                //Request를 보내는 과정은 ValidateRequest.java에 구현되어 있다.
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });









        //회원 가입 버튼이 눌렸을때
        Button registerButton = (Button)findViewById(R.id.next);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userPassword2 = passwordText2.getText().toString();

                String userEmail = emailText.getText().toString();

                //ID 중복체크를 했는지 확인함
                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디 중복을 확인해주세요.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }

                //한칸이라도 빠뜨렸을 경우


                if(userID.equals("")||userPassword.equals("")||userPassword2.equals("")||userEmail.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("회원정보를 빠짐없이 입력해주세요.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }


                if(!userPassword.equals(userPassword2)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호가 일치하지 않습니다.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;

                }





            profileimg = convertToString();



                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(RegisterActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage("잠시 기다려주세요..");
                progressDoalog.setTitle("데이터를 업로드 중입니다.");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.setCanceledOnTouchOutside(false);
                // show it
                progressDoalog.show();

                SharingApiInterface apiInterface2 = SharingApiClient.getApiClient().create(SharingApiInterface.class);
                Call<RegisterDataClass> call2 = apiInterface2.RegisterProcess(userID,userPassword,userEmail,profileimg);
               call2.enqueue(new Callback<RegisterDataClass>() {
                   @Override
                   public void onResponse(Call<RegisterDataClass> call, retrofit2.Response<RegisterDataClass> response) {
                       RegisterDataClass sdc = response.body();

                       if(sdc.getResponse().equals("Register Successfully....")){
                           progressDoalog.dismiss();


                           Log.d("Server Response3",""+sdc.getResponse());
                           AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                           dialog = builder.setMessage("회원가입 성공!")
                                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                       intent.putExtra("ID",userID);
//                                       Log.d("intent22","넘기는 아이디값"+userID);
                                           startActivity(intent);
                                       }
                                   })
                                   .create();
                           dialog.show();


                       }else {

                           progressDoalog.dismiss();
                           Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_LONG).show();
                       }
                   }



                   @Override
                   public void onFailure(Call<RegisterDataClass> call, Throwable t) {
                       Log.d("Server Response3",""+t.toString());
                       AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                       dialog = builder.setMessage("Register fail")
                               .setNegativeButton("OK", null)
                               .create();
                       dialog.show();

                   }
               });


//               Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//
//               startActivity(intent);
//

            }
        });







    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
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
                    Glide.with(RegisterActivity.this)
                            .load(uri)
                            .apply(new RequestOptions().centerCrop())
                            .transition(new DrawableTransitionOptions().crossFade())
                            .into(imageView);


                    break;

            }
    }


    private void UriToBitmap(){

        try {





                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);


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
        bm.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

}


