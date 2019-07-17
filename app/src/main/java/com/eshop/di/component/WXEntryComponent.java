package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.WXEntryModule;
import com.eshop.mvp.contract.WXEntryContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eshop.wxapi.WXEntryActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2019 10:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WXEntryModule.class, dependencies = AppComponent.class)
public interface WXEntryComponent {
    void inject(WXEntryActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WXEntryComponent.Builder view(WXEntryContract.View view);

        WXEntryComponent.Builder appComponent(AppComponent appComponent);

        WXEntryComponent build();
    }
}