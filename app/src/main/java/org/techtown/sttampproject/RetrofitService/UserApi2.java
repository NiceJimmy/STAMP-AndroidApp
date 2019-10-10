package org.techtown.sttampproject.RetrofitService;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi2 {

    String BASE_URL = "http://13.209.15.23/Login.php/";


    @GET("Login.php")
    Call<ResultModel2> login1(@Query("email") String email,@Query("password") String password);

}