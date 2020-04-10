package com.example.dell.aplikasisewagedung.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("insert.php")
    Call<Daftar> btnDaftar(
            @Field("nama") String nama,
            @Field("tlp") String tlp,
            @Field("alamat") String alamat,
            @Field("sesi") String sesi,
            @Field("tanggal") String tanggal
    );

    @GET("read.php")
    Call<JSONResponse> getJSON();
}
