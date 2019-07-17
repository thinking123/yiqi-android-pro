package com.eshop.di.module;

import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.model.CartModel;
import com.eshop.mvp.model.OrderModel;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Author shijun
 * @Data 2019/2/1
 * @Package com.eshop.di.module
 **/
@Module
public class CreateOrderModule {

    private OrderContract.View view;

    public CreateOrderModule(OrderContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public OrderContract.View provideBaseView() {
        return view;
    }


    @Provides
    @ActivityScope
    public OrderModel provideOrderModel(IRepositoryManager iRepositoryManager) {
        return new OrderModel(iRepositoryManager);
    }

    @Provides
    @ActivityScope
    public CartModel provideCartModel(IRepositoryManager iRepositoryManager) {
        return new CartModel(iRepositoryManager);
    }


}
