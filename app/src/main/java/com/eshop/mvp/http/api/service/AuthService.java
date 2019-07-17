package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.auth.MonthData;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.store.AccountInfo;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.AuthInfo;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.CategoryId;
import com.eshop.mvp.http.entity.store.DelAccountInfo;
import com.eshop.mvp.http.entity.store.DrawBean;
import com.eshop.mvp.http.entity.store.GoodsId;
import com.eshop.mvp.http.entity.store.OpBack;
import com.eshop.mvp.http.entity.store.PhonePassword;
import com.eshop.mvp.http.entity.store.PublishGoods;
import com.eshop.mvp.http.entity.store.StoreCategory;
import com.eshop.mvp.http.entity.store.StoreCategoryEdit;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author shijun
 * @Data 2019/2/8
 * @Package com.eshop.mvp.http.api.service
 **/
public interface AuthService {


    @GET("/authentication/get")
    Observable<MyBaseResponse<Auth>> getAuth(
            @Header("token") String token,
            @Query("userId") String userId);

    @GET("/authentication/getMonthMsg")
    Observable<MyBaseResponse<MonthMsg>> getMonthMsg(
            @Header("token") String token);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/authentication/monthAdd")
    Observable<MyBaseResponse<String>> monthAdd(@Header("token") String token,
                                                      @Body MonthData monthData);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/authentication/monthAdd")
    Observable<MyBaseResponse<String>> monthEdit(@Header("token") String token,
                                                @Body MonthData monthData);

    @GET("/order/monthPay")
    Observable<MyBaseResponse<String>> monthPay(
            @Header("token") String token,
            @Query("userId") String userId,
            @Query("id") String id);

}
