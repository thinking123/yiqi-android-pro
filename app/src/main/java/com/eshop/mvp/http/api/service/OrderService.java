package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.OrderBean;
import com.eshop.mvp.http.entity.order.OrderSettlementsBean;
import com.eshop.mvp.http.entity.order.PayRet;

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
 * @Data 2019/2/1
 * @Package com.eshop.mvp.http.api.service
 **/
public interface OrderService {

    @GET("/order/addOrder")
    Observable<MyBaseResponse<Order>> addOrder(
            @Header("token") String token,
            @Query("userId") String userId,
            @Query("goodsId") String goodsId,
            @Query("goodsAmount") String goodsAmount,
            @Query("remarks") String remarks,
            @Query("addressId") String addressId,
            @Query("orderType") int orderType);

    @GET("/order/updateOrder")
    Observable<MyBaseResponse<String>> updateOrder(
            @Header("token") String token,
            @Query("id") String id,
            @Query("freightTotal") String freightTotal,
            @Query("receiveUserName") String receiveUserName,
            @Query("address") String address,
            @Query("receivePhone") String receivePhone);

    @GET("/order/alipayPay")
    Observable<MyBaseResponse<String>> alipayPay(
            @Header("token") String token,
            @Query("userId") String userId,
            @Query("ids") String ids);

    @GET("/order/alipayPayNotify")
    Observable<MyBaseResponse<PayRet>> alipayPayNotify(
            @Query("userId") String userId,
            @Query("id") String id);

    @GET("/order/cancelOrder")
    Observable<MyBaseResponse<AppOrder>> cancelOrder(
            @Header("token") String token,
            @Query("id") String id
           );

    @GET("/order/deleteOrder")
    Observable<MyBaseResponse<String>> deleteOrder(
            @Header("token") String token,
            @Query("id") String id
    );

    @GET("/order/deliverGoods")
    Observable<MyBaseResponse<AppOrder>> deliverGoods(
            @Header("token") String token,
            @Query("id") String id,
            @Query("expressCompany") String expressCompany,
            @Query("expressNumber") String expressNumber);

    @GET("/order/finishOrder")
    Observable<MyBaseResponse<AppOrder>> finishOrder(
            @Header("token") String token,
            @Query("id") String id);

    @GET("/order/getOrder")
    Observable<MyBaseResponse<AppOrder>> getOrder(
            @Header("token") String token,
            @Query("pageNum") String pageNum,
            @Query("userId") String userId,
            @Query("orderStatus") String orderStatus);

    @GET("/order/getOrderDetails")
    Observable<MyBaseResponse<AppOrderForm>> getOrderDetails(
            @Header("token") String token,
            @Query("id") String id);

    @GET("/order/getStoreOrder")
    Observable<MyBaseResponse<AppOrder>> getStoreOrder(
            @Header("token") String token,
            @Query("pageNum") String pageNum,
            @Query("storeId") String storeId,
            @Query("orderStatus") String orderStatus);

    @GET("/order/logistics")
    Observable<MyBaseResponse<ExpressState>> logistics(
            @Header("token") String token,
            @Query("id") String id,
            @Query("type") String type);

    @GET("/order/logisticsAll")
    Observable<MyBaseResponse<List<ExpressCode>>> logisticsAll(
            @Header("token") String token);

    @GET("/order/monthPay")
    Observable<MyBaseResponse<PayRet>> monthPay(
            @Header("token") String token,
            @Query("userId") String userId,
            @Query("id") String id);

    @GET("/order/payment")
    Observable<MyBaseResponse<String>> payment(
            @Header("token") String token,
            @Query("id") String id);

    @GET("/order/reminderShipment")
    Observable<MyBaseResponse<String>> reminderShipment(
            @Header("token") String token,
            @Query("id") String id);

    @GET("/order/wxpay")
    Observable<MyBaseResponse<PayRet>> wxpay(
            @Header("token") String token,
            @Query("userId") String userId,
            @Query("ids") String ids);

    @GET("/order/wxNotify")
    Observable<MyBaseResponse<String>> wxNotify();















}
