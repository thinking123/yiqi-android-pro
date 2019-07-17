package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.eshop.huanxin.ui.NewChatRoomActivity;
import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.HuanXinModule;
import com.eshop.mvp.contract.HuanXinContract;
import com.jess.arms.di.scope.ActivityScope;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2019 12:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HuanXinModule.class, dependencies = AppComponent.class)
public interface HuanXinComponent {


    void inject(NewChatRoomActivity activity);
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        HuanXinComponent.Builder view(HuanXinContract.View view);
//
//        HuanXinComponent.Builder appComponent(AppComponent appComponent);
//
//        HuanXinComponent build();
//    }
}