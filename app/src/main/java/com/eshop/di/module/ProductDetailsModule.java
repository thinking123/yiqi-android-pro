package com.eshop.di.module;

import android.app.Application;

import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.product.Store;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.model.HomeModel;
import com.eshop.mvp.model.ProductDetailsModel;
import com.eshop.mvp.model.StoreManagerModel;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.ui.adapter.CollectionGoodsQuickAdapter;
import com.eshop.mvp.ui.adapter.CollectionStoreQuickAdapter;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.adapter.StoreCatQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @Author shijun
 * @Data 2019/1/26
 * @Package com.eshop.di.module
 **/
@Module
public class ProductDetailsModule {
    private ProductDetailsContract.View view;

    public ProductDetailsModule(ProductDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public ProductDetailsContract.Model provideBaseModel(IRepositoryManager repositoryManager) {
        return new ProductDetailsModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public ProductDetailsContract.View provideBaseView() {
        return view;
    }

    @ActivityScope
    @Provides
    public List<HomeGoodBean> provideRecommendProductsBean() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    public RecommendQuickAdapter prodvideRecommendQuickAdapter(
            List<HomeGoodBean> recommendProductsBeans, Application application) {
        RecommendQuickAdapter recommendQuickAdapter = new RecommendQuickAdapter(recommendProductsBeans);
        // recommendQuickAdapter.addHeaderView(LayoutInflater.from(application).inflate(R.layout.layout_recommend_for_you, null));
        return recommendQuickAdapter;

    }

    @ActivityScope
    @Provides
    public List<StoreCat> provideStoreColumns() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    public StoreCatQuickAdapter prodvideStoreCatQuickAdapter(
            List<StoreCat> storeColumns, Application application) {
        StoreCatQuickAdapter storeCatQuickAdapter = new StoreCatQuickAdapter(storeColumns);
        return storeCatQuickAdapter;

    }

    @ActivityScope
    @Provides
    public CollectionGoodsQuickAdapter prodvideCollectionGoodsQuickAdapter(
            List<HomeGoodBean> homeGoodBeans, Application application) {
        CollectionGoodsQuickAdapter collectionGoodsQuickAdapter = new CollectionGoodsQuickAdapter(homeGoodBeans);
        return collectionGoodsQuickAdapter;

    }

    @ActivityScope
    @Provides
    public List<Store> provideStore() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    public CollectionStoreQuickAdapter prodvideCollectionStoreQuickAdapter(
            List<Store> stores, Application application) {
        CollectionStoreQuickAdapter collectionStoreQuickAdapter = new CollectionStoreQuickAdapter(stores);
        return collectionStoreQuickAdapter;

    }

    @ActivityScope
    @Provides
    public HomeModel provideHomeModel(IRepositoryManager repositoryManager) {
        return new HomeModel(repositoryManager);
    }

    @ActivityScope
    @Provides
    public StoreManagerModel provideStoreManagerModel(IRepositoryManager repositoryManager) {
        return new StoreManagerModel(repositoryManager);
    }

    @ActivityScope
    @Provides
    public UserModel provideUserModel(IRepositoryManager repositoryManager) {
        return new UserModel(repositoryManager);
    }
}
