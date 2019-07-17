package com.eshop.mvp.model;

import com.eshop.mvp.http.api.service.HomeService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.AdBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.contract.RecommendContract;
import com.eshop.mvp.http.api.service.CartService;
import com.eshop.mvp.http.api.service.ProductService;
import com.eshop.mvp.http.api.service.UploadService;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.product.Product;
import com.eshop.mvp.http.entity.product.RecommendBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.model
 **/


public class ProductModel extends BaseModel implements RecommendContract.Model { //, ProductDetailsContract.Model {

    public ProductModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }



    @Override
    public Observable<MyBaseResponse<HomeBean>> getHomeData() {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getHomeData();

    }

    @Override
    public Observable<MyBaseResponse<GoodsBean>> getGoodsData(int pageNum) {
        return null;
    }

    @Override
    public Observable<MyBaseResponse<AdBean>> getAdData() {
        return null;
    }

    public Observable<BaseResponse<List<Product>>> list(Integer userId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .list(userId);
    }


    public Observable<BaseResponse<List<Product>>> searchProductByKeyWordOrCategoryId(String keyword,
                                                                                      Integer categoryId,
                                                                                      int pageNum,
                                                                                      int pageSize,
                                                                                      String orderBy) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .searchProductByKeyWordOrCategoryId(keyword, categoryId, pageNum, pageSize, orderBy);
    }

    public Observable<BaseResponse> updateStatus(Integer productId,
                                                 Integer status) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .updateStatus(productId, status);
    }

    public Observable<BaseResponse> addCart(Integer productId, Integer count) {
       return null;
        // return mRepositoryManager
       //         .obtainRetrofitService(CartService.class)
        //        .addProduct(productId, count);
    }

    public Observable<BaseResponse<Product>> createProduct(
            Integer id,
            Integer categoryId,
            String name,
            String subtitle,
            String mainImage,
            String subImage,
            double price,
            Integer stock,
            Integer status) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .createProduct(id, categoryId, name, subtitle, mainImage, subImage, "", price, stock, status);
    }

    public Observable<BaseResponse<String>> upLoadImage(MultipartBody.Part upload_file) {
        return mRepositoryManager.obtainRetrofitService(UploadService.class)
                .uploadaImage(upload_file);
    }

    public Observable<BaseResponse<List<String>>> upLoadImages(List<MultipartBody.Part> upload_file) {
        return mRepositoryManager.obtainRetrofitService(UploadService.class)
                .uploadImages(upload_file);
    }


}
