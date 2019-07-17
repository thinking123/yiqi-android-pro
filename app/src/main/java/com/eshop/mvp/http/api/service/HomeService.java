package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.CategoryBean;
import com.eshop.mvp.http.entity.home.AdBean;
import com.eshop.mvp.http.entity.home.BrandBean;
import com.eshop.mvp.http.entity.home.BrandBeanList;
import com.eshop.mvp.http.entity.home.CompanyBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeBean;
import com.eshop.mvp.http.entity.home.HotLineBean;
import com.eshop.mvp.http.entity.store.HelpBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.http.api.service
 **/
public interface HomeService {


    @GET("/homePage")
    Observable<MyBaseResponse<HomeBean>> getHomeData();

    @GET("/apphotlin")
    Observable<MyBaseResponse<List<HotLineBean>>> getHotLine();

    @GET("/homePage/goods")
    Observable<MyBaseResponse<GoodsBean>> getGoodsData(
            @Query("pageNum") int pageNum);

    @GET("/brand/get")
    Observable<MyBaseResponse<BrandBeanList>> getBrand();

    @GET("/homePage/brandId")
    Observable<MyBaseResponse<GoodsBean>> getBrandGoods(
            @Query("pageNum") int pageNum,@Query("brandId") String brandId);

    @GET("/homePage/month")
    Observable<MyBaseResponse<GoodsBean>> getMonthGoods(
            @Query("pageNum") int pageNum,@Query("monthId") String monthId);

    @GET("/homePage/sale")
    Observable<MyBaseResponse<GoodsBean>> getSaleGoods(
            @Query("pageNum") int pageNum,@Query("saleId") String saleId);

    @GET("/homePage/priceSale")
    Observable<MyBaseResponse<GoodsBean>> getPriceSaleGoods(
            @Query("pageNum") int pageNum,@Query("priceSale") String priceSale);

    @GET("/homePage/miaoMiaoGou")
    Observable<MyBaseResponse<GoodsBean>> getMiaoMiaoGouGoods(
            @Query("pageNum") int pageNum,@Query("miaoMiaoGou") String miaoMiaoGou);

    @GET("/homePage/advertising")
    Observable<MyBaseResponse<AdBean>> getAdData();

    @GET("/homePage/company")
    Observable<MyBaseResponse<CompanyBean>> getAbout();


    @GET("/myHelp")
    Observable<MyBaseResponse<List<HelpBean>>> myHelp();











}
