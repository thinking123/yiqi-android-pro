package com.eshop.mvp.model;

import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.contract.RecommendContract;
import com.eshop.mvp.http.api.service.CartService;
import com.eshop.mvp.http.api.service.HomeService;
import com.eshop.mvp.http.api.service.ProductService;
import com.eshop.mvp.http.api.service.UploadService;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.AdBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeBean;
import com.eshop.mvp.http.entity.product.DelId;
import com.eshop.mvp.http.entity.product.GoodsId;
import com.eshop.mvp.http.entity.product.Product;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreId;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.model
 **/


public class ProductDetailsModel extends BaseModel implements ProductDetailsContract.Model {

    public ProductDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<MyBaseResponse<String>> addGood(String token,String userId, String goodsId, int goodNum) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .addGood(token,userId, goodsId, goodNum);
    }

    @Override
    public Observable<MyBaseResponse<ProductDetail>> getGoodDetail(String token, String goodsId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .getGoodDetail(token, goodsId);
    }

    @Override
    public Observable<MyBaseResponse<GoodsBean>> collectionGoodsFind(int pageNum, String token) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .collectionGoodsFind(token, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<StoresBean>> collectionStoreFind(int pageNum, String token) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .collectionStoreFind(token, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<StoreCatBean>> storeColumn(int pageNum, String storeId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .storeColumn(storeId, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<StoreCatBean>> storeColumnAll(int pageNum, String storeId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .storeColumnAll(storeId, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<GoodsBean>> storeGoods(int pageNum, String storeId, String storeColumn, String sortType) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .storeGoods(pageNum, storeId, storeColumn, sortType);
    }

    @Override
    public Observable<MyBaseResponse<StoreInfo>> storeId(String token, String storeId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .storeId(token, storeId);
    }

    @Override
    public Observable<MyBaseResponse<String>> collectionAddGoods(String token, GoodsId goodsId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .collectionAddGoods(token, goodsId);
    }

    @Override
    public Observable<MyBaseResponse<String>> collectionAddStore(String token, StoreId storeId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .collectionAddStore(token, storeId);
    }

    @Override
    public Observable<MyBaseResponse<String>> collectionDel(String token, DelId delId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .collectionDel(token, delId);
    }

    @Override
    public Observable<MyBaseResponse<String>> collectionDelStore(String token, DelId delId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .collectionDelStore(token, delId);
    }
}
