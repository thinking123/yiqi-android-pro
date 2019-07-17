package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.mvp.contract.RecommendContract;
import com.eshop.mvp.http.api.service.CategoryService;
import com.eshop.mvp.http.api.service.HomeService;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.AdBean;
import com.eshop.mvp.http.entity.home.CompanyBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeBean;
import com.eshop.mvp.http.entity.product.RecommendBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.HomeContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/14/2019 08:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model,RecommendContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<MyBaseResponse<List<CatBean>>> getCats(int parentId) {
        return mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getCats(parentId);
    }

    @Override
    public Observable<MyBaseResponse<List<CatBean>>> getCats() {
        return mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getCats();
    }

    @Override
    public Observable<MyBaseResponse<CompanyBean>> getAbout() {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getAbout();
    }

    @Override
    public Observable<MyBaseResponse<HomeBean>> getHomeData() {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getHomeData();
    }


    @Override
    public Observable<MyBaseResponse<GoodsBean>> getGoodsData(int pageNum) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getGoodsData(pageNum);
    }

    @Override
    public Observable<MyBaseResponse<AdBean>> getAdData() {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getAdData();
    }


}