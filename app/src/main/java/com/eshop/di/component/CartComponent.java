package com.eshop.di.component;

import com.eshop.mvp.ui.fragment.CartHomeFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.eshop.di.module.CartModule;
import com.eshop.mvp.ui.fragments.CartFragment;

import dagger.Component;

/**
 * @Author shijun
 * @Data 2019/3/9
 * @Package com.eshop.di.component
 **/
@FragmentScope
@Component(modules = CartModule.class, dependencies = AppComponent.class)
public interface CartComponent {
    void inject(CartFragment cartFragment);
    void inject(CartHomeFragment cartHomeFragment);
}
