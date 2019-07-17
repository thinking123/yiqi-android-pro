package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.eshop.mvp.ui.activity.order.DeliverRefundActivity;
import com.eshop.mvp.ui.activity.order.DoAfterSaleActivity;
import com.eshop.mvp.ui.activity.order.RefundActivity;
import com.eshop.mvp.ui.activity.order.RefundFinishActivity;
import com.eshop.mvp.ui.activity.order.RefundStateActivity;
import com.eshop.mvp.ui.activity.order.RefundSucessActivity;
import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.AfterSaleModule;
import com.eshop.mvp.contract.AfterSaleContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eshop.mvp.ui.activity.order.AfterSaleActivity;
import com.eshop.mvp.ui.fragment.AfterSaleFragment;


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
@ActivityScope
@Component(modules = AfterSaleModule.class, dependencies = AppComponent.class)
public interface AfterSaleComponent {
    void inject(AfterSaleActivity activity);

    void inject(AfterSaleFragment fragment);

    void inject(RefundActivity activity);

    void inject(RefundStateActivity activity);

    void inject(RefundSucessActivity activity);

    void inject(DoAfterSaleActivity activity);

    void inject(DeliverRefundActivity activity);

    void inject(RefundFinishActivity activity);

  /**  @Component.Builder
    interface Builder {
        @BindsInstance
        AfterSaleComponent.Builder view(AfterSaleContract.View view);

        AfterSaleComponent.Builder appComponent(AppComponent appComponent);

        AfterSaleComponent build();
    }*/
}