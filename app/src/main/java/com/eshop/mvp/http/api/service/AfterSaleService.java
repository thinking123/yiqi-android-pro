package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.order.AfterSaleOrder;
import com.eshop.mvp.http.entity.order.AfterSaleStore;
import com.eshop.mvp.http.entity.order.AfterSales;
import com.eshop.mvp.http.entity.order.AppLog;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.DelOrderId;
import com.eshop.mvp.http.entity.order.ExpressInfo;
import com.eshop.mvp.http.entity.order.IdBean;
import com.eshop.mvp.http.entity.order.RefundBean;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.http.entity.order.RefundUpdateBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.AuthInfo;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.PublishGoods;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;

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
 * @Data 2019/2/16
 * @Package com.eshop.mvp.http.api.service
 **/
public interface AfterSaleService {

    @GET("/order/getOrderMsg")
    Observable<MyBaseResponse<AppOrderForm>> getOrderMsg(
            @Header("token") String token,
            @Query("id") String id);

    @GET("/afterSale/applyRefund")
    Observable<MyBaseResponse<String>> applyRefund(@Header("token") String token,
                                                   @Query("orderId") String orderId);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/afterSale/applyRefund")
    Observable<MyBaseResponse<String>> applyRefund(@Header("token") String token,
                                                   @Body RefundBean refundBean);

    @GET("/afterSale/applyRefundDetails")
    Observable<MyBaseResponse<RefundDetail>> applyRefundDetails(@Header("token") String token,
                                                                @Query("orderId") String orderId);


    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/afterSale/applyRefundDel")
    Observable<MyBaseResponse<String>> applyRefundDel(@Header("token") String token,
                                                      @Body DelOrderId orderId);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/afterSale/applyRefundPut")
    Observable<MyBaseResponse<String>> applyRefundPut(@Header("token") String token,
                                                   @Body RefundUpdateBean refundUpdateBean);

    @GET("/afterSale/applyRefundById")
    Observable<MyBaseResponse<RefundInfo>> applyRefundById(@Header("token") String token,
                                                           @Query("id") String id);

    @GET("/afterSale/beingProcessedTab")
    Observable<MyBaseResponse<AfterSaleOrder>> beingProcessedTab(@Header("token") String token,
                                                                 @Query("pageNum") String pageNum,
                                                                 @Query("type") String type,
                                                                 @Query("storeId") String storeId);

    @GET("/afterSale/handlingAfterSales")
    Observable<MyBaseResponse<AfterSaleStore>> handlingAfterSales(@Header("token") String token,
                                                                  @Query("id") String id,
                                                                  @Query("storeId") String storeId);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/afterSale/handlingAfterSales")
    Observable<MyBaseResponse<String>> handlingAfterSales(@Header("token") String token,
                                                      @Body AfterSales afterSales);

    @GET("/afterSale/appLogistics")
    Observable<MyBaseResponse<List<AppLog>>> appLogistics(@Header("token") String token);



    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/afterSale/logistics")
    Observable<MyBaseResponse<String>> logistics(@Header("token") String token,
                                                          @Body ExpressInfo expressInfo);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/afterSale/confirmTheRefund")
    Observable<MyBaseResponse<String>> confirmTheRefund(@Header("token") String token,
                                                 @Body IdBean idBean);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/afterSale/confirmTheRefundUser")
    Observable<MyBaseResponse<String>> confirmTheRefundUser(@Header("token") String token,
                                                        @Body IdBean idBean);

    @GET("/afterSale/refund")
    Observable<MyBaseResponse<RefundDetail2>> refund(@Header("token") String token,
                                                     @Query("orderId") String orderId);

    @GET("/afterSale/applyRefundDetailsUser")
    Observable<MyBaseResponse<RefundDetailUser>> applyRefundDetailsUser(@Header("token") String token,
                                                                        @Query("orderId") String orderId);



}
