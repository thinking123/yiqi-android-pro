package com.eshop.di.component;

import android.app.Activity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.eshop.di.module.RecommendModule;
import com.eshop.mvp.ui.fragments.RecommendFragment;
import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午4:50
 * @Package com.eshop.di.component
 **/

@FragmentScope
@Component(modules = RecommendModule.class, dependencies = AppComponent.class)
public interface RecommendComponent {
    void inject(RecommendFragment fragment);
}
