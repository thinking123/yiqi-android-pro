package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.product.CollectNum;
import com.eshop.mvp.http.entity.product.DelId;
import com.eshop.mvp.http.entity.product.GoodsId;
import com.eshop.mvp.http.entity.product.Product;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.RecommendBean;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreId;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoresBean;

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
 * @Data 2019/1/14
 * @Package com.eshop.mvp.http.api.service
 **/
public interface ProductService {

    @GET("/goods/collectionNum")
    Observable<MyBaseResponse<CollectNum>> getCollectionNum(
            @Header("token") String token

    );

    @GET("/goods/categoryId")
    Observable<MyBaseResponse<GoodsBean>> getCatProducts(
            @Query("pageNum") int pageNum,
            @Query("categoryParentID") String categoryParentID,
            @Query("categoryId") String categoryId,
            @Query("goodsName") String goodsName
    );

    @GET("/goods/goodsId")
    Observable<MyBaseResponse<ProductDetail>> getGoodDetail(
            @Header("token") String token,
            @Query("goodsId") String goodsId
    );

    @GET("/goods/collectionGoodsFind")
    Observable<MyBaseResponse<GoodsBean>> collectionGoodsFind(
            @Header("token") String token,
            @Query("pageNum") int pageNum
    );

    @GET("/goods/collectionStoreFind")
    Observable<MyBaseResponse<StoresBean>> collectionStoreFind(
            @Header("token") String token,
            @Query("pageNum") int pageNum
    );

    @GET("/goods/storeColumn")
    Observable<MyBaseResponse<StoreCatBean>> storeColumn(
            @Query("storeId") String storeId,
            @Query("pageNum") int pageNum
    );

    @GET("/goods/storeColumnAll")
    Observable<MyBaseResponse<StoreCatBean>> storeColumnAll(
            @Query("storeId") String storeId,
            @Query("pageNum") int pageNum
    );

    @GET("/goods/storeGoods")
    Observable<MyBaseResponse<GoodsBean>> storeGoods(
            @Query("pageNum") int pageNum,
            @Query("storeId") String storeId,
            @Query("storeColumnId") String storeColumnId,
            @Query("sortType") String sortType

    );

    @Headers({"Accept: application/json"})
    @GET("/goods/storeId")
    Observable<MyBaseResponse<StoreInfo>> storeId(
            @Header("token") String token,
            @Query("storeId") String storeId

    );

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/goods/collectionAddGoods")
    Observable<MyBaseResponse<String>> collectionAddGoods(
            @Header("token") String token,

            @Body GoodsId goodsId
    );

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/goods/collectionAddStore")
    Observable<MyBaseResponse<String>> collectionAddStore(
            @Header("token") String token,
            @Body StoreId storeId
    );

    @POST("/goods/collectionDel")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Observable<MyBaseResponse<String>> collectionDel(
            @Header("token") String token,
            @Body DelId delId

    );

    @POST("/goods/collectionDelStore")
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    Observable<MyBaseResponse<String>> collectionDelStore(
            @Header("token") String token,
            @Body DelId delId
    );



    @POST("/user/product/list")
    @FormUrlEncoded
    Observable<BaseResponse<List<Product>>> searchProductByKeyWordOrCategoryId(
            @Field("keyword") String keyword,
            @Field("categoryId") Integer categoryId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("orderBy") String orderBy
    );

    @POST("/manage/product/update")
    @FormUrlEncoded
    Observable<BaseResponse<Product>> createProduct(
            @Field("id") Integer id,
            @Field("categoryId") Integer categoryId,
            @Field("name") String name,
            @Field("subtitle") String subtitle,
            @Field("mainImage") String mainImage,
            @Field("subImages") String subImages,
            @Field("detail") String detail,
            @Field("price") double price,
            @Field("stock") Integer stock,
            @Field("status") Integer status
    );

    @FormUrlEncoded
    @POST("/manage/product/list")
    Observable<BaseResponse<List<Product>>> list(
            @Field("userId") Integer userId);

    @FormUrlEncoded
    @POST("/manage/product/status")
    Observable<BaseResponse> updateStatus(
            @Field("productId") Integer productId,
            @Field("status") Integer status
    );


}
