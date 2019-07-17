package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.eshop.mvp.ui.activity.product.SaleFlashActivity;
import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.ProductListModule;
import com.eshop.mvp.contract.ProductListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eshop.mvp.ui.activity.product.ProductListActivity;


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
@Component(modules = ProductListModule.class, dependencies = AppComponent.class)
public interface ProductListComponent {
    void inject(ProductListActivity activity);

    void inject(SaleFlashActivity activity);

  /**  @Component.Builder
    interface Builder {
        @BindsInstance
        ProductListComponent.Builder view(ProductListContract.View view);

        ProductListComponent.Builder appComponent(AppComponent appComponent);

        ProductListComponent build();
    }*/
}