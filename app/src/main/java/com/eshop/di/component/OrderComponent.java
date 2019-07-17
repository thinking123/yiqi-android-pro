package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.eshop.mvp.ui.activity.order.ExpressActivity;
import com.eshop.mvp.ui.activity.order.OrderDetailsActivity;
import com.eshop.mvp.ui.activity.order.PayActivity;
import com.eshop.mvp.ui.activity.order.RefundActivity;
import com.eshop.mvp.ui.activity.order.RefundFinishActivity;
import com.eshop.mvp.ui.activity.order.StoreOrderActivity;
import com.eshop.mvp.ui.activity.order.StoreOrderDetailsActivity;
import com.eshop.mvp.ui.activity.store.DeliverGoodsActivity;
import com.eshop.mvp.ui.fragment.StoreOrderFragment;
import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.OrderModule;
import com.eshop.mvp.contract.OrderContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eshop.mvp.ui.activity.order.OrderActivity;
import com.eshop.mvp.ui.fragment.OrderFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/01/2019 23:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = OrderModule.class, dependencies = AppComponent.class)
public interface OrderComponent {
    void inject(OrderActivity activity);

    void inject(OrderFragment fragment);

    void inject(StoreOrderFragment fragment);

    void inject(PayActivity activity);

    //void inject(RefundActivity activity);

    void inject(StoreOrderActivity activity);

    void inject(DeliverGoodsActivity activity);

    void inject(OrderDetailsActivity activity);

    void inject(ExpressActivity activity);

    void inject(StoreOrderDetailsActivity activity);



    @Component.Builder
    interface Builder {
        @BindsInstance
        OrderComponent.Builder view(OrderContract.View view);

        OrderComponent.Builder appComponent(AppComponent appComponent);

        OrderComponent build();
    }
}