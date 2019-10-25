package org.techtown.sttampproject.ShareActivity.RetrofitService3;

import com.google.gson.annotations.SerializedName;

import org.techtown.sttampproject.DetailPage.ReviewModalClass;
import org.techtown.sttampproject.DetailPage.ViewPagerDataClass;
import org.techtown.sttampproject.Edit_Process.EditDataClass;
import org.techtown.sttampproject.MenuProcess.MenuDataClass;
import org.techtown.sttampproject.MyShop.MyShopDataClass;
import org.techtown.sttampproject.MyStamp.MyStampDataClass;
import org.techtown.sttampproject.RegisterProcess.RegisterDataClass;
import org.techtown.sttampproject.SharingGridView2.retrofit3.ImageClass2;
import org.techtown.sttampproject.StampProcess.StampDataClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SharingApiInterface {
    @FormUrlEncoded
    @POST("SharingData.php")
    Call<SharingDataClass> UploadShareInfo(@Field("userID") String user_id, @Field("category") String category, @Field("proName") String proName, @Field("address") String address, @Field("address2") String address2, @Field("description") String description, @Field("shorder") String shorder, @Field("path") String path, @Field("store_tel1") String store_tel1, @Field("store_tel2") String store_tel2);
 // shareactivity에서 정보를 전송해 디비에 인서트 하는 부분

    @POST("get_main_data.php")
    Call<List<SharingDataClass>> getDatas();
// 리사이클러뷰에 로드하기 위해 디비에 입력된 데이타를 가져오는 부분이다.


    @FormUrlEncoded
    @POST("UserRegister.php")
    Call<RegisterDataClass> RegisterProcess(@Field("userID") String userID, @Field("userPassword") String userPassword, @Field("userEmail") String userEmail, @Field("userProfile") String userProfile);
    // shareactivity에서 정보를 전송해 디비에 인서트 하는 부분




    @POST("get_mypage_data.php")
    Call<List<SharingDataClass>> getDatas2();
//마이페이지에 리사이클러뷰에 로드하기 위해 디비에 입력된 데이타를 가져오는 부분이다.

    @GET("get_detail_images.php")
    Call<List<ViewPagerDataClass>> getDatas3(@Query("userID") String userID, @Query("shorder") String shorder);
// 뷰페이저에 이미지를 로드하기위해 ...

    @GET("get_stamp_data.php")
    Call<List<StampDataClass>> getStampData(@Query("userID") String userID, @Query("shorder") String shorder);
// 뷰페이저에 이미지를 로드하기위해 ...

    @GET("get_menu_data.php")
    Call<List<MenuDataClass>> getMenuData(@Query("userID") String userID, @Query("shorder") String shorder);
// 뷰페이저에 이미지를 로드하기위해 ...

    @GET("get_review_data.php")
    Call<List<ReviewModalClass>> getReviewData(@Query("user_id") String userID, @Query("shorder") String shorder);
// 뷰페이저에 이미지를 로드하기위해 ...





    @FormUrlEncoded
    @POST("upload_stamp.php")
    Call<StampDataClass> uploadStamp(@Field("user_id") String userID, @Field("shorder") String shorder, @Field("count") String count, @Field("start_time") String StartTime, @Field("finish_time") String FinishTime, @Field("discount_rate") String Discount);

    @FormUrlEncoded
    @POST("upload_menu.php")
    Call<MenuDataClass> uploadMenu(@Field("user_id") String userID, @Field("shorder") String shorder, @Field("count") String count, @Field("menu_name") String MenuName, @Field("menu_price") String MenuPrice);


    @FormUrlEncoded
    @POST("upload_review.php")
    Call<ReviewModalClass> uploadReview(@Field("user_id") String userID, @Field("user_id2") String userID2, @Field("shorder") String shorder, @Field("count") String count, @Field("review") String review, @Field("rate") String rate, @Field("date") String date);


    @FormUrlEncoded
    @POST("upload_process1.php")
    Call<SharingDataClass> Upload_Process1(@Field("userID") String user_id, @Field("category") String category, @Field("proName") String proName, @Field("description") String description, @Field("shorder") String shorder, @Field("store_tel1") String store_tel1, @Field("store_tel2") String store_tel2);


    @GET("get_uploading_data.php")//업로드 작업중인 데이타를 리사이클러뷰로 로드하기 위함
    Call<List<SharingDataClass>> get_uploading_data(@Query("user_id") String userID);


    @FormUrlEncoded
    @POST("upload_process2.php")
    Call<SharingDataClass> Upload_Process2(@Field("userID") String user_id, @Field("shorder") String shorder, @Field("store_address") String address, @Field("store_address2") String address2);


    @FormUrlEncoded
    @POST("checking_pic.php")
    Call<SharingDataClass> checking_pic(@Field("userID") String user_id, @Field("shorder") String shorder);

    @FormUrlEncoded
    @POST("checking_menu.php")
    Call<SharingDataClass> checking_menu(@Field("userID") String user_id, @Field("shorder") String shorder);

    @FormUrlEncoded
    @POST("checking_stamp.php")
    Call<SharingDataClass> checking_stamp(@Field("userID") String user_id, @Field("shorder") String shorder);



    @GET("get_edit_process.php")
    Call<List<EditDataClass>> edit_process(@Query("userID") String userID, @Query("shorder") String shorder);


    @GET("get_count_info.php")
    Call<List<ImageClass2>> get_count_info(@Query("user_id") String userID, @Query("shorder") String shorder);

    @FormUrlEncoded
    @POST("update_pic_count.php")
    Call<ImageClass2> update_pic_count(@Field("userID") String user_id, @Field("shorder") String shorder, @Field("picorder") String picorder, @Field("picorder2") String picorder2);


    @GET("get_edit_process.php")
    Call<List<EditDataClass>> edit_basic_info(@Query("userID") String userID, @Query("shorder") String shorder);

    @GET("get_edit_process.php")
    Call<List<EditDataClass>> edit_address_info(@Query("userID") String userID, @Query("shorder") String shorder);


    @GET("get_pic_data.php")
    Call<List<ImageClass2>> get_pic_data(@Query("user_id") String userID, @Query("shorder") String shorder);


    @FormUrlEncoded
    @POST("update_stamp_info.php")
    Call<MyShopDataClass> update_shop_info(@Field("userID") String user_id, @Field("userID2") String user_id2, @Field("proName") String proName, @Field("about_shop") String about_shop, @Field("store_tel1") String store_tel1, @Field("store_tel2") String store_tel2, @Field("address") String address, @Field("address2") String address2, @Field("shorder") String shorder, @Field("stamp_num") String stamp_num, @Field("stamp_use") String stamp_use);


    @GET("get_MyShop_info.php")
    Call<List<MyShopDataClass>> get_MyShop_info(@Query("userID2") String userID2);


    @FormUrlEncoded
    @POST("update_usedStamp_info.php")
    Call<MyStampDataClass> update_usedStamp_info(@Field("userID") String user_id, @Field("userID2") String user_id2, @Field("proName") String proName, @Field("start_time") String start_time, @Field("finish_time") String finish_time, @Field("off_rate") String off_rate, @Field("address") String address, @Field("address2") String address2, @Field("shorder") String shorder, @Field("stamp_num") String stamp_num);

    @GET("get_MyStamp_info.php")
    Call<List<MyStampDataClass>> get_MyStamp_info(@Query("userID2") String userID2);
}
