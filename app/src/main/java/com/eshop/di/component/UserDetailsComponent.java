package com.eshop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.UserDetailsModule;
import com.eshop.mvp.ui.activity.UserDetailsActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/7/3 下午2:07
 * @Package com.eshop.di.component
 **/


@ActivityScope
@Component(modules = UserDetailsModule.class, dependencies = AppComponent.class)
public interface UserDetailsComponent {
    void inject(UserDetailsActivity userDetailsActivity);
}
