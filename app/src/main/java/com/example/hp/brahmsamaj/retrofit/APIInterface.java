package com.example.hp.brahmsamaj.retrofit;

import com.example.hp.brahmsamaj.vo.ServiceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIInterface {

    @POST("login")
    Call<ServiceResponse> login(@Body Object body);

    @POST("register")
    Call<ServiceResponse> registration(@Body Object body);

    @GET("my_profile")
    Call<ServiceResponse> getMyProfile(@Header("Authorization") String token);

    @GET("logout")
    Call<ServiceResponse> logout(@Query("token") String token);

    @POST("my_profile")
    Call<ServiceResponse> updateMyProfile(@Header("Authorization") String token, @Body Object body);
}
