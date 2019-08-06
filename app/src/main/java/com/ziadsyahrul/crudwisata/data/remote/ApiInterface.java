package com.ziadsyahrul.crudwisata.data.remote;

import com.ziadsyahrul.crudwisata.model.Login.LoginResponse;
import com.ziadsyahrul.crudwisata.model.detailwisata.DetailWisataResponse;
import com.ziadsyahrul.crudwisata.model.uploadwisata.UploadWisataResponse;
import com.ziadsyahrul.crudwisata.model.wisata.WisataResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    // Membuat endpoint register
    @FormUrlEncoded
    @POST("registeruser.php")
    Call<LoginResponse> registerUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("namauser") String namauser,
            @Field("alamat") String alamat,
            @Field("jenkel") String jenkel,
            @Field("notelp") String notelp,
            @Field("level") String level );

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUser(
            @Field("iduser") int iduser,
            @Field("namauser") String username,
            @Field("alamat") String alamat,
            @Field("jenkel") String jenkel,
            @Field("notelp") String notelp
    );

    // Membuat GET data kategori wisata
    @GET("getkategori.php")
    Call<WisataResponse> getKategoriWisata();

    // Mengambil data wisata baru
    @GET("getwisatabaru.php")
    Call<WisataResponse> getWisataBaru();

    @GET("getwisatabyuser.php")
    Call<WisataResponse>  getWisataByUser(@Query("iduser") int idUser);

    // Mengambil data wisata populer
    @GET("getwisatapopuler.php")
    Call<WisataResponse> getWisataPopuler();

    // Mengupload Wisata
    @Multipart
    @POST("uploadwisata.php")
    Call<UploadWisataResponse> uploadWisata(
            @Part("iduser") int iduser,
            @Part("idkategori") int idkategori,
            @Part("namawisata")RequestBody namawisata,
            @Part("descwisata") RequestBody descwisata,
            @Part("timeinsert") RequestBody timeinsert,
            @Part MultipartBody.Part image
            );

    // mengambil data detail wisata
    @GET("getdetailwisata.php")
    Call<DetailWisataResponse> getDetailWisata(@Query("idwisata") int idWisata);

    // Mengambil data wisata berdasarkan idkategori
    @GET("getwisatabykategori.php")
    Call<WisataResponse> getWisataByKaegori(@Query("idkategori") int idCategory);

    @FormUrlEncoded
    @POST("deletewisata.php")
    Call<WisataResponse> deleteWisata(
            @Field("idwisata") int idWisata,
            @Field("fotowisata") String namaFotoWisata
    );
    // Mengupdate makanan
    @Multipart
    @POST("updatewisata.php")
    Call<WisataResponse> updateWisata(
            @Part("idwisata") int idWisata,
            @Part("idkategori") int idKategori,
            @Part("namawisata") RequestBody namaWisata,
            @Part("descwisata") RequestBody descWisata,
            @Part("fotowisata") RequestBody namaFotoWisata,
            @Part("inserttime") RequestBody insertTime,
            @Part MultipartBody.Part image
    );

}
