package com.eshop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.mvp.contract.SearchProductContract;
import com.eshop.mvp.model.ProductModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/21 下午5:22
 * @Package com.eshop.di.module
 **/
@Module
public class SearchProductModule {
    private SearchProductContract.View view;

    public SearchProductModule(SearchProductContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public SearchProductContract.View provideBaseView() {
        return view;
    }

    @Provides
    @ActivityScope
    public ProductModel provideProductModel(IRepositoryManager iRepositoryManager) {
        return new ProductModel(iRepositoryManager);
    }
}
