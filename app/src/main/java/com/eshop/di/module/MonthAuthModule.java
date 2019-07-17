package com.eshop.di.module;

import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.model.StoreManagerModel;
import com.eshop.mvp.model.UserModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.MonthAuthContract;
import com.eshop.mvp.model.MonthAuthModel;
import com.jess.arms.integration.IRepositoryManager;


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
@Module
public class MonthAuthModule {

   // @Binds
   // abstract MonthAuthContract.Model bindMonthAuthModel(MonthAuthModel model);

    MonthAuthContract.View view;

    public MonthAuthModule(MonthAuthContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public MonthAuthContract.Model provideModel(IRepositoryManager repositoryManager) {
        return new MonthAuthModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public MonthAuthContract.View provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public UserModel provideUserModel(IRepositoryManager repositoryManager) {
        return new UserModel(repositoryManager);
    }
}