package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.EaseChatModule;
import com.eshop.mvp.contract.EaseChatContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eshop.mvp.ui.activity.EaseChatActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/25/2019 15:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EaseChatModule.class, dependencies = AppComponent.class)
public interface EaseChatComponent {
    void inject(EaseChatActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EaseChatComponent.Builder view(EaseChatContract.View view);

        EaseChatComponent.Builder appComponent(AppComponent appComponent);

        EaseChatComponent build();
    }
}