package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.eshop.mvp.ui.activity.set.HelpDetailActivity;
import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.HelpModule;
import com.eshop.mvp.contract.HelpContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eshop.mvp.ui.activity.set.HelpActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/27/2019 22:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HelpModule.class, dependencies = AppComponent.class)
public interface HelpComponent {
    void inject(HelpActivity activity);
    void inject(HelpDetailActivity activity);

  /**  @Component.Builder
    interface Builder {
        @BindsInstance
        HelpComponent.Builder view(HelpContract.View view);

        HelpComponent.Builder appComponent(AppComponent appComponent);

        HelpComponent build();
    }*/
}