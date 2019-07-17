package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.AddressModule;
import com.eshop.mvp.contract.AddressContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eshop.mvp.ui.activity.set.AddressActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/28/2019 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddressModule.class, dependencies = AppComponent.class)
public interface AddressComponent {
    void inject(AddressActivity activity);

   /** @Component.Builder
    interface Builder {
        @BindsInstance
        AddressComponent.Builder view(AddressContract.View view);

        AddressComponent.Builder appComponent(AppComponent appComponent);

        AddressComponent build();
    }*/
}