package com.eshop.di.component;

import com.eshop.mvp.ui.activity.product.ProductDetailsActivity;
import com.eshop.mvp.ui.activity.store.CollectionActivity;
import com.eshop.mvp.ui.fragment.GoodsCollectionFragment;
import com.eshop.mvp.ui.fragment.OrderFragment;
import com.eshop.mvp.ui.fragment.StoreCollectionFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.ProductDetailsModule;

import dagger.Component;

/**
 * @Author shijun
 * @Data 2018/2/16
 * @Package com.eshop.di.component
 **/
@ActivityScope
@Component(modules = ProductDetailsModule.class, dependencies = AppComponent.class)
public interface ProductDetailsComponent {
    void inject(ProductDetailsActivity productDetailsActivity);

    void inject(GoodsCollectionFragment fragment);

    void inject(StoreCollectionFragment fragment);

    void inject(CollectionActivity collectionActivity);
}
