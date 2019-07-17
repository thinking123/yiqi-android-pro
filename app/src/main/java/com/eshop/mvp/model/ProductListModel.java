package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.mvp.http.api.service.HomeService;
import com.eshop.mvp.http.api.service.ProductService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.ProductListContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/22/2019 15:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ProductListModel extends BaseModel implements ProductListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ProductListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MyBaseResponse<GoodsBean>> getMonthGoods(int pageNum, String monthId) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getMonthGoods(pageNum, monthId);
    }

    @Override
    public Observable<MyBaseResponse<GoodsBean>> getSaleGoods(int pageNum, String saleId) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getSaleGoods(pageNum, saleId);
    }

    @Override
    public Observable<MyBaseResponse<GoodsBean>> getPriceSaleGoods(int pageNum, String priceSale) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getPriceSaleGoods(pageNum, priceSale);
    }

    @Override
    public Observable<MyBaseResponse<GoodsBean>> getMiaoMiaoGouGoods(int pageNum, String miaoMiaoGou) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getMiaoMiaoGouGoods(pageNum, miaoMiaoGou);
    }

    @Override
    public Observable<MyBaseResponse<GoodsBean>> getBrandGoods(int pageNum, String brandId) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getBrandGoods(pageNum, brandId);
    }
}