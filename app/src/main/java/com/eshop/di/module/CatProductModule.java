package com.eshop.di.module;

import android.app.Application;

import com.eshop.mvp.contract.CategoryContract;
import com.eshop.mvp.contract.RecommendContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.model.CategoryModel;
import com.eshop.mvp.model.ProductModel;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.adapter.SubCatLineQuickAdapter;
import com.eshop.mvp.ui.adapter.SubCatQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.CatProductContract;
import com.eshop.mvp.model.CatProductModel;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


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
@Module
public class CatProductModule {

   // private CatProductContract.View view;

   // public CatProductModule(CatProductContract.View view) {
   //     this.view = view;
   // }


    @FragmentScope
    @Provides
    public List<CatBean> provideCatBeans() {
        return new ArrayList<>();
    }


    @FragmentScope
    @Provides
    public SubCatQuickAdapter prodvideBaseQuickAdapter(
            List<CatBean> catBeans, Application application) {
        SubCatQuickAdapter subCatQuickAdapter = new SubCatQuickAdapter(catBeans);
       return subCatQuickAdapter;

    }

    @FragmentScope
    @Provides
    public SubCatLineQuickAdapter prodvideSubCatLineQuickAdapter(
            List<CatBean> catBeans, Application application) {
        SubCatLineQuickAdapter subCatQuickAdapter = new SubCatLineQuickAdapter(catBeans);
        return subCatQuickAdapter;

    }

    @FragmentScope
    @Provides
    public List<HomeGoodBean> provideRecommendProductsBean() {
        return new ArrayList<>();
    }


    @FragmentScope
    @Provides
    public RecommendQuickAdapter prodvideRecommendQuickAdapter(
            List<HomeGoodBean> recommendProductsBeans, Application application) {
        RecommendQuickAdapter recommendQuickAdapter = new RecommendQuickAdapter(recommendProductsBeans);
        // recommendQuickAdapter.addHeaderView(LayoutInflater.from(application).inflate(R.layout.layout_recommend_for_you, null));
        return recommendQuickAdapter;

    }


    @FragmentScope
    @Provides
    public CatProductContract.Model provideModel(IRepositoryManager iRepositoryManager) {
        return new CatProductModel(iRepositoryManager);
    }


}