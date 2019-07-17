package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.mvp.http.api.service.ProductService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.CatProductContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 16:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class CatProductModel extends BaseModel implements CatProductContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CatProductModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MyBaseResponse<GoodsBean>> getCatProducts(int pageNum, String categoryParentID, String categoryId, String goodsName) {
        return mRepositoryManager
                .obtainRetrofitService(ProductService.class)
                .getCatProducts(pageNum, categoryParentID, categoryId, goodsName);
    }

}