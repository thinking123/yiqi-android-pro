package com.eshop.di.component;

import com.eshop.mvp.ui.fragment.CategoryAllFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.eshop.di.module.CategoryModule;
import com.eshop.mvp.ui.fragments.CategoryFragment;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午4:43
 * @Package com.eshop.di.component
 **/

@FragmentScope
@Component(modules = CategoryModule.class, dependencies = AppComponent.class)
public interface CategoryComponent {
    void inject(CategoryFragment categoryFragment);
    void inject(CategoryAllFragment categoryAllFragment);
}
