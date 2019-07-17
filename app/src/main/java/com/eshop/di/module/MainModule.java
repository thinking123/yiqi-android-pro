package com.eshop.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.mvp.contract.MainContract;
import com.eshop.mvp.model.ConversationModel;
import com.eshop.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:04
 * @Package com.eshop.di.module
 **/
@Module
public class MainModule {

    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public MainContract.View provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public MainContract.Model provideModel(IRepositoryManager repositoryManager) {
        return new ConversationModel(repositoryManager);
    }


    @ActivityScope
    @Provides
    public UserModel provideUserModel(IRepositoryManager repositoryManager) {
        return new UserModel(repositoryManager);
    }
}
