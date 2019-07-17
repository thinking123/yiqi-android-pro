package com.eshop.di.module;

import android.app.Application;

import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.model.LoginModel;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.ProductListContract;
import com.eshop.mvp.model.ProductListModel;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


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
@Module
public class ProductListModule {

    ProductListContract.View view;

    public ProductListModule(ProductListContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public ProductListContract.Model provideModel(IRepositoryManager repositoryManager) {
        return new ProductListModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public ProductListContract.View provideView() {
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
}