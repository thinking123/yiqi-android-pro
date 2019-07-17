package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.login.JWTBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author shijun
 * @Data 2019/1/11
 * @Package com.eshop.mvp.http.api.service
 **/
public interface LoginService {

    @GET("/vcode/get")
    Observable<MyBaseResponse<String>> sendSms(
            @Query("phone") String mobile);

    @GET("/vcode/checkVCode")
    Observable<MyBaseResponse<String>> checkCode(
            @Query("phone") String phone, @Query("vcode") String vcode);

    @GET("/login/register")
    Observable<MyBaseResponse<UserInfoBean>> register(
            @Query("phone") String phone, @Query("passWord") String passWord,
            @Query("logo") String logo, @Query("nickNmae") String nickNmae
    );

    @GET("/login/login")
    Observable<MyBaseResponse<LoginBean>> login(
            @Query("phone") String phone, @Query("passWord") String passWord, @Query("deviceId") String deviceId
    );

    @GET("/login/forgetPwd")
    Observable<MyBaseResponse<LoginBean>> setPassword(
            @Query("phone") String phone, @Query("passWord") String passWord, @Query("deviceId") String deviceId
    );

    @GET("/login/updateUserInfo")
    Observable<MyBaseResponse<LoginBean>> updateUserInfo(
            @Query("id") String id,
            @Query("phone") String phone,
            @Query("passWord") String passWord,
            @Query("logo") String logo,
            @Query("nickNmae") String nickname,
            @Query("sex") int sex,
            @Query("deviceId") String deviceId,
            @Query("openId") String openId
    );

    @GET("/login/wxlogin")
    Observable<MyBaseResponse<LoginBean>> wxlogin(
            @Query("unionid") String unionid
    );

    @GET("/login/checkPhone")
    Observable<MyBaseResponse<String>> checkPhone(
            @Query("phone") String phone);

}
