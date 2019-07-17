package com.eshop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.eshop.di.module.SelfModule;
import com.eshop.mvp.ui.fragments.SelfFragment;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午4:30
 * @Package com.eshop.di.component
 **/
@FragmentScope
@Component(modules = SelfModule.class, dependencies = AppComponent.class)
public interface SelfComponent {
    void inject(SelfFragment selfFragment);
}

