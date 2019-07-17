package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.CityModule;
import com.eshop.mvp.contract.CityContract;

import com.jess.arms.di.scope.FragmentScope;
import com.eshop.mvp.ui.fragment.CityFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/30/2019 00:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CityModule.class, dependencies = AppComponent.class)
public interface CityComponent {
    void inject(CityFragment fragment);

  /**  @Component.Builder
    interface Builder {
        @BindsInstance
        CityComponent.Builder view(CityContract.View view);

        CityComponent.Builder appComponent(AppComponent appComponent);

        CityComponent build();
    }*/
}