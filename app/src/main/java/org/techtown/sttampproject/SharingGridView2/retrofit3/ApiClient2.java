package org.techtown.sttampproject.SharingGridView2.retrofit3;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient2 {
    private static final String BaseUrl = "http://13.209.15.23/";
    private static Retrofit retrofit;




    public static Retrofit getApiClient() {

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder().baseUrl(BaseUrl).client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;


    }
}
