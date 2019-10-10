package org.techtown.sttampproject.SharingGridView2.retrofit3;

import org.techtown.sttampproject.SharingGridView2.CountID.CountData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface2 {
    @FormUrlEncoded
    @POST("SharingData2.php")
    Call<ImageClass2> uploadImage(@Field("user_id") String userID, @Field("shorder") String shorder, @Field("picorder") String picorder, @Field("picorder2") String picorder2, @Field("image_name") String title, @Field("image") String image);

//    @FormUrlEncoded
//    @POST("get_data_count.php")
//    Call<CountData> postID(@Field("userID") String user_id);


    @POST("get_data_count.php")
    Call<List<CountData>> CountID();

}

