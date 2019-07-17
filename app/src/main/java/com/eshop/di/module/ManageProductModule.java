package com.eshop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.mvp.contract.ManageProductContract;
import com.eshop.mvp.model.ProductModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 下午10:48
 * @Package com.eshop.di.module
 **/
@Module
public class ManageProductModule {

    private ManageProductContract.View view;

    public ManageProductModule(ManageProductContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public ManageProductContract.View provideBaseView() {
        return view;
    }

    @Provides
    @ActivityScope
    public ProductModel provideProductModel(IRepositoryManager repositoryManager) {
        return new ProductModel(repositoryManager);
    }
}
