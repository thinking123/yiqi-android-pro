package com.eshop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.AppSetModule;
import com.eshop.mvp.ui.activity.set.AppSetActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 上午10:35
 * @Package com.eshop.di.component
 **/
@Component(modules = AppSetModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface AppSetComponent {
    void inject(AppSetActivity appSetActivity);
}
