package com.example.qlchdt.Service;

import com.example.qlchdt.Model.HoaDon;
import com.example.qlchdt.Model.ReturnAPISearchHD;
import com.example.qlchdt.Model.ReturnApiSearchSP;
import com.example.qlchdt.Model.SanPham;
import com.example.qlchdt.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Dataservice {

    @POST("user/login")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<User> loginUser(@Body User user);

    @POST("sanpham/search")
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ReturnApiSearchSP> searchSanpham(@Header("Authorization") String token,@Body ReturnApiSearchSP searchSP);

    @GET("sanpham/get-all")
    Call<List<SanPham>> getall(@Header("Authorization") String token);

    @GET("sanpham/get-by-id/{id}")
    Call<SanPham> getById(@Header("Authorization") String token,@Path("id")String maSP);

    @POST("sanpham/create-sanpham")
    Call<ResponseBody> ThemSP(@Header("Authorization") String token,@Body SanPham sp);

    @POST("sanpham/update-sanpham")
    Call<ResponseBody> SuaSP(@Header("Authorization") String token,@Body SanPham sp);

    @POST("sanpham/delete-item")
    Call<ResponseBody> XoaSP(@Header("Authorization") String token,@Body SanPham  sp);

    @Multipart
    @POST("sanpham/UpImage")
    Call<String> UploadPhoto(@Part MultipartBody.Part photo);

    //gọi hàm delete ở API
    @DELETE("sanpham/DeleteImage/{imgdel}")
    Call<Void> DeleteImage(@Path("imgdel")String imgdel);

    @GET("hoadon/get-all")
    Call<List<HoaDon>> getAllHoaDon(@Header("Authorization") String token);

    @GET("hoadon/get-by-id/{id}")
    Call<HoaDon> getByIdHoaDon(@Header("Authorization") String token,@Path("id")String maHD);

    @POST("hoadon/search")
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ReturnAPISearchHD> searchHoaDon(@Header("Authorization") String token, @Body ReturnAPISearchHD searchSP);

    @POST("hoadon/create-hoadon")
    Call<ResponseBody> themHD(@Header("Authorization") String token,@Body HoaDon hd);
    @POST("hoadon/update-hoadon")
    Call<ResponseBody> suaHD(@Header("Authorization") String token,@Body HoaDon hd);

    @POST("hoadon/delete-hoadon")
    Call<ResponseBody> xoaHD(@Header("Authorization") String token,@Body HoaDon  hd);
}
