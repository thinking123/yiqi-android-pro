package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @Author shijun
 * @Data 2019/1/12
 * @Package com.eshop.mvp.http.api.service
 **/
public interface UploadService {

    @Multipart
    @POST("/uploadImg/uploadImg")
    Observable<MyBaseResponse<String>> uploadImage(
            @Part MultipartBody.Part upload_file
    );

    @Multipart
    @POST("/manage/product/upload/image")
    Observable<BaseResponse<String>> uploadaImage(
            @Part MultipartBody.Part upload_file
    );

    @Multipart
    @POST("/manage/product/upload/images")
    Observable<BaseResponse<List<String>>> uploadImages(
            @Part List<MultipartBody.Part> upload_file
    );
}
