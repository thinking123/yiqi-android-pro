package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.HotLineBean;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.eshop.mvp.http.entity.ship.ShippingBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author shijun
 * @Data 2019/1/28
 * @Package com.eshop.mvp.http.api.service
 **/
public interface AddressService {

    /**
     * 添加收货地址
     * @return
     */
    @GET("/appuseraddress/add")
    Observable<MyBaseResponse<String>> add(
            @Header("token") String token,
            @Query("userId") String userId,
            @Query("address") String address,
            @Query("receiveUserName") String receiveUserName,
            @Query("receivePhone") String receivePhone
    );

    /**
     * 获取收货地址列表
     * @return
     */
    @GET("/appuseraddress/get")
    Observable<MyBaseResponse<List<AddressBean>>> get(
            @Header("token") String token,
            @Query("userId") String userId
    );

    @GET("/appuseraddress/get")
    Observable<MyBaseResponse<List<AddressBean>>> getById(
            @Header("token") String token,
            @Query("id") String id
    );

    /**
     * 删除收货地址
     * @return
     */
    @GET("/appuseraddress/del")
    Observable<MyBaseResponse<String>> del(
            @Header("token") String token,
            @Query("id") String id
    );

    @GET("/appuseraddress/edit")
    Observable<MyBaseResponse<String>> edit(
            @Header("token") String token,
            @Query("id") String id,
            @Query("address") String address,
            @Query("receiveUserName") String receiveUserName,
            @Query("receivePhone") String receivePhone,
            @Query("isDefault") String isDefault

    );

    /**
     * 设置缺省收货地址
     * @return
     */
    @GET("/appuseraddress/setDefault")
    Observable<MyBaseResponse<String>> setDefault(
            @Header("token") String token,
            @Query("userId") String userId,
            @Query("id") String id
    );

    /**
     * 获取省列表
     * @return
     */
    @GET("/cityCode/selectPro")
    Observable<MyBaseResponse<List<CityBean>>> selectPro();


    /**
     * 获取城市列表
     * @return
     */
    @GET("/cityCode/selectCity")
    Observable<MyBaseResponse<List<CityBean>>> selectCity(
            @Query("cityCode") String cityCode
    );





}
