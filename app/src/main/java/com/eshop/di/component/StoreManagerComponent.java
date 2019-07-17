package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.eshop.mvp.ui.activity.set.OpinionActivity;
import com.eshop.mvp.ui.activity.store.AddAccountActivity;
import com.eshop.mvp.ui.activity.store.AuthActivity;
import com.eshop.mvp.ui.activity.store.BankSetActivity;
import com.eshop.mvp.ui.activity.store.CatSetActivity;
import com.eshop.mvp.ui.activity.store.CompanyAuthActivity;
import com.eshop.mvp.ui.activity.store.EditGoodsActivity;
import com.eshop.mvp.ui.activity.store.GoodsMgrActivity;
import com.eshop.mvp.ui.activity.store.PublishGoodsActivity;
import com.eshop.mvp.ui.activity.store.RealNameAuthActivity;

import com.eshop.mvp.ui.activity.store.RecordActivity;
import com.eshop.mvp.ui.activity.store.SetAccountActivity;
import com.eshop.mvp.ui.activity.store.SetPasswordNextActivity;
import com.eshop.mvp.ui.activity.store.StoreInfoActivity;
import com.eshop.mvp.ui.activity.store.StoreInnerCatActivity;
import com.eshop.mvp.ui.activity.store.StoreStateActivity;
import com.eshop.mvp.ui.activity.store.TransactionActivity;
import com.eshop.mvp.ui.activity.store.WalletActivity;
import com.eshop.mvp.ui.activity.store.WeixinZhifubaoActivity;
import com.eshop.mvp.ui.activity.store.WithDrawTypeActivity;
import com.eshop.mvp.ui.activity.store.WithdrawActivity;
import com.eshop.mvp.ui.fragment.RecordFragment;
import com.eshop.mvp.ui.fragment.TransactionFragment;
import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.StoreManagerContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eshop.mvp.ui.activity.store.StoreManagerActivity;
import com.eshop.mvp.ui.fragment.StoreManagerFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/08/2019 16:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StoreManagerModule.class, dependencies = AppComponent.class)
public interface StoreManagerComponent {
    void inject(StoreManagerActivity activity);

    void inject(StoreManagerFragment fragment);

    void inject(AuthActivity activity);

    void inject(RealNameAuthActivity activity);

    void inject(CompanyAuthActivity activity);

    void inject(StoreInfoActivity activity);

    void inject(CatSetActivity activity);

    void inject(GoodsMgrActivity activity);

    void inject(PublishGoodsActivity activity);

    void inject(EditGoodsActivity activity);

    void inject(StoreInnerCatActivity activity);

    void inject(StoreStateActivity activity);

    void inject(OpinionActivity activity);

    void inject(WalletActivity activity);

    void inject(TransactionActivity activity);

    void inject(TransactionFragment fragment);

    void inject(RecordActivity activity);

    void inject(RecordFragment fragment);

    void inject(SetPasswordNextActivity activity);

    void inject(SetAccountActivity activity);

    void inject(BankSetActivity activity);

    void inject(AddAccountActivity activity);

    void inject(WithdrawActivity activity);

    void inject(WeixinZhifubaoActivity activity);

    void inject(WithDrawTypeActivity activity);




    /**  @Component.Builder
      interface Builder {
          @BindsInstance
          StoreManagerComponent.Builder view(StoreManagerContract.View view);

          StoreManagerComponent.Builder appComponent(AppComponent appComponent);

          StoreManagerComponent build();
      }*/
}