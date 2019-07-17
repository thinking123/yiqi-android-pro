package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.eshop.mvp.ui.activity.store.MonthCompanyAuthActivity;
import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.MonthAuthModule;
import com.eshop.mvp.contract.MonthAuthContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eshop.mvp.ui.activity.store.MonthAuthActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/21/2019 22:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MonthAuthModule.class, dependencies = AppComponent.class)
public interface MonthAuthComponent {
    void inject(MonthAuthActivity activity);

    void inject(MonthCompanyAuthActivity activity);

}