package com.eshop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.CreateProductModule;
import com.eshop.mvp.ui.activity.product.CreateProductActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/13 下午1:35
 * @Package com.eshop.di.component
 **/

@ActivityScope
@Component(modules = CreateProductModule.class, dependencies = AppComponent.class)
public interface CreateProductComponent {
    void inject(CreateProductActivity createProductActivity);
}
