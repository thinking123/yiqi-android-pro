package com.eshop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.ManageProductModule;
import com.eshop.mvp.ui.activity.product.ManageProductActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 下午10:52
 * @Package com.eshop.di.component
 **/
@ActivityScope
@Component(modules = ManageProductModule.class, dependencies = AppComponent.class)
public interface ManageProductComponent {
    void inject(ManageProductActivity manageProductActivity);
}
