package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.HotLineModule;
import com.eshop.mvp.contract.HotLineContract;

import com.jess.arms.di.scope.FragmentScope;
import com.eshop.mvp.ui.fragment.HotLineFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 12:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HotLineModule.class, dependencies = AppComponent.class)
public interface HotLineComponent {
    void inject(HotLineFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HotLineComponent.Builder view(HotLineContract.View view);

        HotLineComponent.Builder appComponent(AppComponent appComponent);

        HotLineComponent build();
    }
}