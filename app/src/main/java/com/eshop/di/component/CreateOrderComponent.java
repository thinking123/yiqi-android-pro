package com.eshop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.CreateOrderModule;
import com.eshop.mvp.ui.activity.order.CreateOrderActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/19 下午7:26
 * @Package com.eshop.di.component
 **/

@ActivityScope
@Component(modules = CreateOrderModule.class, dependencies = AppComponent.class)
public interface CreateOrderComponent {
    void inject(CreateOrderActivity createOrderActivity);

}
