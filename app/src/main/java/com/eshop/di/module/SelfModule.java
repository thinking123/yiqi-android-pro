package com.eshop.di.module;

import com.eshop.mvp.model.StoreManagerModel;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.mvp.contract.SelfContract;
import com.eshop.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午4:32
 * @Package com.eshop.di.module
 **/
@Module
public class SelfModule {

    private SelfContract.View view;

    public SelfModule(SelfContract.View view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    public SelfContract.View provideBaseView() {
        return view;
    }

    @Provides
    @FragmentScope
    public UserModel provideBaseModel(IRepositoryManager repositoryManager){
        return new UserModel(repositoryManager);
    }

    @Provides
    @FragmentScope
    public StoreManagerModel provideStoreManagerModel(IRepositoryManager repositoryManager){
        return new StoreManagerModel(repositoryManager);
    }
}
