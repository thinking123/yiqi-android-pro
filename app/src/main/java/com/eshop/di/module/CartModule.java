package com.eshop.di.module;

import com.eshop.R;
import com.eshop.mvp.ui.adapter.AppCartSectionAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.mvp.contract.CartContract;
import com.eshop.mvp.model.CartModel;

import dagger.Module;
import dagger.Provides;

/**
 * @Author shijun
 * @Data 2019/1/17
 * @Package com.eshop.di.module
 **/
@Module
public class CartModule {

    private CartContract.View view;

    public CartModule(CartContract.View view) {
        this.view = view;
    }


  /**  @Provides
    @FragmentScope
    public CartListAdapter provideCartListAdapter() {
        return new CartListAdapter();
    }

    @Provides
    @FragmentScope
    public AppCartListAdapter provideAppCartListAdapter() {
        return new AppCartListAdapter();
    }*/

    @Provides
    @FragmentScope
    public AppCartSectionAdapter provideAppCartSectionAdapter() {
        return new AppCartSectionAdapter(R.layout.adapter_item_appcart,R.layout.adapter_item_appcart_head,null);
    }



    @Provides
    @FragmentScope
    public CartContract.View provideBaseView() {
        return view;
    }

    @Provides
    @FragmentScope
    public CartContract.Model provideBaseModel(IRepositoryManager repositoryManager) {
        return new CartModel(repositoryManager);
    }
}
