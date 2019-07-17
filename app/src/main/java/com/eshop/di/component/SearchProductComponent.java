package com.eshop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.SearchProductModule;
import com.eshop.mvp.ui.activity.product.SearchProductListActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/21 下午5:21
 * @Package com.eshop.di.component
 **/

@ActivityScope
@Component(modules = SearchProductModule.class, dependencies = AppComponent.class)
public interface SearchProductComponent {
    void inject(SearchProductListActivity searchProductListActivity);
}
