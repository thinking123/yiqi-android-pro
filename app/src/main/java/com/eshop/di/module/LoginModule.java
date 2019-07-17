package com.eshop.di.module;

import com.eshop.mvp.model.UserModel;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.model.LoginModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author shijun
 * @Data 2019/1/12
 * @Package com.eshop.di.module
 **/

@Module
public class LoginModule {
    LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public LoginContract.Model provideModel(IRepositoryManager repositoryManager) {
        return new LoginModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public LoginContract.View provideView() {
        return view;
    }

    @ActivityScope
    @Provides
    public UserModel provideUserModel(IRepositoryManager repositoryManager) {
        return new UserModel(repositoryManager);
    }
}
