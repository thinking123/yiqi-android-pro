package com.eshop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.MainModule;
import com.eshop.mvp.ui.activity.MainActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:08
 * @Package com.eshop.di.component
 **/
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
