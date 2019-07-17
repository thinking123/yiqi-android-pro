package com.eshop.di.component;

import com.eshop.di.module.ProductDetailsModule;
import com.eshop.mvp.ui.activity.product.StoreActivity;
import com.eshop.mvp.ui.activity.product.StoreIntroduceActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

/**
 * @Author shijun
 * @Data 2019/1/25
 * @Package com.eshop.di.component
 **/
@ActivityScope
@Component(modules = ProductDetailsModule.class, dependencies = AppComponent.class)
public interface StoreIntroduceComponent {
    void inject(StoreIntroduceActivity storeIntroduceActivity);
}
