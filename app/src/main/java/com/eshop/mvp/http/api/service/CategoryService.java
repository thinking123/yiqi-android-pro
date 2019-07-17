package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.Category;
import com.eshop.mvp.http.entity.category.CategoryBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author shijun
 * @Data 2019/1/16
 * @Package com.eshop.mvp.http.api.service
 **/
public interface CategoryService {

    @FormUrlEncoded
    @POST("/manage/product/category/item/list")
    Observable<BaseResponse<List<CategoryBean>>> getCategorys(
            @Field("parentId") int parendId
    );

    @GET("/appgoodscategory/selectOne")
    Observable<MyBaseResponse<List<CatBean>>> getCats();

    @GET("/appgoodscategory/selectOTwo")
    Observable<MyBaseResponse<List<CatBean>>> getCats(
            @Query("parentId") int parentId);

    @GET("/appgoodscategory/all")
    Observable<MyBaseResponse<List<Category>>> getAllCats();

}
