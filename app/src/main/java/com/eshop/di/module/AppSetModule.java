package com.eshop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.mvp.contract.AppSetContract;
import com.eshop.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 上午10:44
 * @Package com.eshop.di.module
 **/
@Module
public class AppSetModule {
    private AppSetContract.View view;

    public AppSetModule(AppSetContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public AppSetContract.View provideBaseView() {
        return view;
    }

    @Provides
    @ActivityScope
    public UserModel provideUserModel(IRepositoryManager repositoryManager) {
        return new UserModel(repositoryManager);
    }

}
