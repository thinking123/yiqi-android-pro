package com.eshop.di.module;

import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.model.LoginModel;
import com.eshop.mvp.model.OrderModel;
import com.eshop.mvp.model.UserModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.AfterSaleContract;
import com.eshop.mvp.model.AfterSaleModel;
import com.jess.arms.integration.IRepositoryManager;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/16/2019 22:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class AfterSaleModule {

   // @Binds
   // abstract AfterSaleContract.Model bindAfterSaleModel(AfterSaleModel model);

    private AfterSaleContract.View view;

    public AfterSaleModule(AfterSaleContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public AfterSaleContract.Model provideBaseModel(IRepositoryManager repositoryManager) {
        return new AfterSaleModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public AfterSaleContract.View provideBaseView() {
        return view;
    }

    @ActivityScope
    @Provides
    public UserModel provideUserModel(IRepositoryManager repositoryManager) {
        return new UserModel(repositoryManager);
    }

    @ActivityScope
    @Provides
    public OrderModel provideOrderModel(IRepositoryManager repositoryManager) {
        return new OrderModel(repositoryManager);
    }
}