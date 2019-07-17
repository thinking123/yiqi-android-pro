
package com.eshop.di.module;

import android.app.Application;
import android.view.LayoutInflater;

import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.R;
import com.eshop.mvp.contract.RecommendContract;
import com.eshop.mvp.http.entity.product.Product;
import com.eshop.mvp.model.ProductModel;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午4:52
 * @Package com.eshop.di.module
 **/

@Module
public class RecommendModule {
    private RecommendContract.View view;

    public RecommendModule(RecommendContract.View view) {
        this.view = view;
    }


    @FragmentScope
    @Provides
    public List<HomeGoodBean> provideRecommendProductsBean() {
        return new ArrayList<>();
    }


    @FragmentScope
    @Provides
    public RecommendQuickAdapter prodvideBaseQuickAdapter(
            List<HomeGoodBean> recommendProductsBeans, Application application) {
        RecommendQuickAdapter recommendQuickAdapter = new RecommendQuickAdapter(recommendProductsBeans);
       // recommendQuickAdapter.addHeaderView(LayoutInflater.from(application).inflate(R.layout.layout_recommend_for_you, null));
        return recommendQuickAdapter;

    }

    @FragmentScope
    @Provides
    public RecommendContract.View provideView() {
        return view;
    }

    @FragmentScope
    @Provides
    public RecommendContract.Model provideModel(IRepositoryManager iRepositoryManager) {
        return new ProductModel(iRepositoryManager);
    }

}
