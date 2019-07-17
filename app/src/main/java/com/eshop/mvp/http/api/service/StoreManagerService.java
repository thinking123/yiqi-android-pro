package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.product.StoreLogo;
import com.eshop.mvp.http.entity.store.AccountInfo;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.AuthInfo;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.CategoryId;
import com.eshop.mvp.http.entity.store.DelAccountInfo;
import com.eshop.mvp.http.entity.store.DrawBean;
import com.eshop.mvp.http.entity.store.GoodsId;
import com.eshop.mvp.http.entity.store.OpBack;
import com.eshop.mvp.http.entity.store.PhonePassword;
import com.eshop.mvp.http.entity.store.PublishGoods;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreCategory;
import com.eshop.mvp.http.entity.store.StoreCategoryEdit;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;

import io.reactivex.Observable;
import okhttp3.RequestBody;
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
public interface StoreManagerService {

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/accountCreat")
    Observable<MyBaseResponse<String>> accountCreat(@Header("token") String token,
                                                    @Body AccountInfo accountInfo);


    @GET("/store/banCarAll")
    Observable<MyBaseResponse<BankCards>> banCarAll(@Header("token") String token,
                                                    @Query("storeId") String storeId,
                                                    @Query("pageNum") String pageNum);
    @GET("/store/idStore")
    Observable<MyBaseResponse<StoreInfomation>> idStore(@Header("token") String token,
                                                        @Query("storeId") String storeId);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/bankIdDel")
    Observable<MyBaseResponse<String>> bankIdDel(@Header("token") String token,
                                                 @Body DelAccountInfo delAccountInfo);

    @GET("/store/cashType")
    Observable<MyBaseResponse<CashType>> cashType(@Header("token") String token,
                                                  @Query("storeId") String storeId,
                                                  @Query("pageNum") int pageNum,
                                                  @Query("type") String type);

    @GET("/store/checkPendingGoods")
    Observable<MyBaseResponse<Audit>> checkPendingGoods(@Header("token") String token,
                                                        @Query("pageNum") int pageNum);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/drawing")
    Observable<MyBaseResponse<String>> drawing(@Header("token") String token,
                                               @Body DrawBean drawBean);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/goods")
    Observable<MyBaseResponse<String>> goods(@Header("token") String token,
                                             @Body PublishGoods publishGoods);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/goodsDel")
    Observable<MyBaseResponse<String>> goodsDel(@Header("token") String token,
                                                @Body GoodsId goodsId);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/goodsPut")
    Observable<MyBaseResponse<String>> goodsPut(@Header("token") String token,
                                                @Body PublishGoods publishGoods);

    @GET("/store/inSalesGoods")
    Observable<MyBaseResponse<Audit>> inSalesGoods(@Header("token") String token,
                                                   @Query("pageNum") int pageNum);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/opinion")
    Observable<MyBaseResponse<String>>opinion(@Header("token") String token,
                                             @Body OpBack backContent);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/pwdCreat")
    Observable<MyBaseResponse<String>> pwdCreat(@Header("token") String token,
                                                @Body PhonePassword phonePassword);

    @GET("/store/record")
    Observable<MyBaseResponse<WithDrawRecord>> record(@Header("token") String token,
                                                      @Query("storeId") String storeId,
                                                      @Query("pageNum") int pageNum,
                                                      @Query("type") int type);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/store/sellingGoods")
    Observable<MyBaseResponse<String>> sellingGoods(@Header("token") String token,
                                                    @Body GoodsId goodsId);
    @GET("/store/idMyQRCode")
    Observable<MyBaseResponse<QRCode>> getIdMyQRCode(@Header("token") String token,
                                                     @Query("storeId") String  storeId,
                                                     @Query("type") String type);


    @GET("/store/state")
    Observable<MyBaseResponse<StoreState>> state(@Header("token") String token);

    @GET("/store/stayOnTheShelfGoods")
    Observable<MyBaseResponse<Audit>> stayOnTheShelfGoods(@Header("token") String token,
                                                          @Query("pageNum") int pageNum);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/store/stopSellingGoods")
    Observable<MyBaseResponse<String>> stopSellingGoods(@Header("token") String token,
                                                        @Body GoodsId goodsId);


    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/store")
    Observable<MyBaseResponse<String>> store(
            @Header("token") String token,
            @Body AuthInfo authInfo);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/storeColumn")
    Observable<MyBaseResponse<String>> storeColumn(
            @Header("token") String token, @Body StoreCategory storeCategory);


    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/storeColumnCreat")
    Observable<MyBaseResponse<String>> storeColumnCreat(@Header("token") String token,@Body StoreCategoryEdit storeCategoryEdit);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/store/storeColumnDel")
    Observable<MyBaseResponse<String>> storeColumnDel(@Header("token") String token,
                                                      @Body CategoryId categoryId);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/store/storeLogoPut")
    Observable<MyBaseResponse<String>> storeLogoPut(@Header("token") String token,@Body StoreLogo storeLogo);

    @GET("/store/transaction")
    Observable<MyBaseResponse<TransList>> transaction(@Header("token") String token,
                                                      @Query("storeId") String storeId,
                                                      @Query("pageNum") int pageNum,
                                                      @Query("type") int type);

    @GET("/store/wallet")
    Observable<MyBaseResponse<Wallet>> wallet(@Header("token") String token,
                                              @Query("storeId") String storeId);

    @GET("/authentication/get")
    Observable<MyBaseResponse<Auth>> getAuth(
            @Header("token") String token,
            @Query("userId") String userId);

}
