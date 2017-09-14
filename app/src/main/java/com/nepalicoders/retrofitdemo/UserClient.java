package com.nepalicoders.retrofitdemo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Sulav on 8/12/17.
 */

public interface UserClient {

    @POST("users")
    Call<User> createAccount(@Body User user);

    @Multipart
    @POST("users/upload")
    Call<ResponseBody> uploadPhoto(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part photo
    );

    @Multipart
    @POST("users/upload")
    Call<ResponseBody> uploadPhoto(
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("photographer") RequestBody photographer,
            @Part("year") RequestBody year,
            @Part MultipartBody.Part photo
    );

    @Multipart
    @POST("users/upload")
    Call<ResponseBody> uploadPhoto(
            @PartMap Map<String, RequestBody> data,
            @Part MultipartBody.Part photo
    );

    @Multipart
    @POST("users/uploadTwoPhotos")
    Call<ResponseBody> uploadTwoPhotos(
            @Part MultipartBody.Part photo1,
            @Part MultipartBody.Part photo2
    );

    @Multipart
    @POST("users/uploadAlbum")
    Call<ResponseBody> uploadAlbum(
            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> photos
    );

}
