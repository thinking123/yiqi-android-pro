package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.cart.AppcarStore;
import com.eshop.mvp.http.entity.cart.CartBean;
import com.eshop.mvp.http.entity.cart.StoreBean;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.store.DelAccountInfo;

import java.util.List;

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
 * @Data 2019/1/17
 * @Package com.eshop.mvp.http.api.service
 **/
public interface CartService {

    @GET("/appcar/get")
    Observable<MyBaseResponse<AppCartStore>> getCartList(
            @Header("token") String token,
            @Query("userId") String userId
    );

    @GET("/appcar/add")
    Observable<MyBaseResponse<String>> addGood(
            @Header("token") String token,
            @Query("userId") String userId,
            @Query("goodsId") String goodsId,
            @Query("goodNum")int goodNum
    );

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/appcar/addOrder")
    Observable<MyBaseResponse<List<Order>>> addOrder(
            @Header("token") String token,
            @Body AppcarStore appcarStore

    );

    @GET("/appcar/countPrice")
    Observable<MyBaseResponse<String>> countPrice(
            @Header("token") String token,
            @Query("ids") String ids

    );

    @GET("/appcar/del")
    Observable<MyBaseResponse<String>> delCart(
            @Header("token") String token,
            @Query("ids") String ids

    );

    @GET("/appcar/updateNum")
    Observable<MyBaseResponse<String>> updateNum(
            @Header("token") String token,
            @Query("id") String id,
            @Query("num") String num

    );

}
