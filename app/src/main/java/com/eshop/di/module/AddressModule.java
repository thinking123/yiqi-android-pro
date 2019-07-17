package com.eshop.di.module;

import android.app.Application;

import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.model.ProductDetailsModel;
import com.eshop.mvp.ui.adapter.AddressQuickAdapter;
import com.eshop.mvp.ui.adapter.StoreCatQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.AddressContract;
import com.eshop.mvp.model.AddressModel;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/28/2019 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class AddressModule {

    //@Binds
    //abstract AddressContract.Model bindAddressModel(AddressModel model);

    private AddressContract.View view;

    public AddressModule(AddressContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public AddressContract.Model provideBaseModel(IRepositoryManager repositoryManager) {
        return new AddressModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public AddressContract.View provideBaseView() {
        return view;
    }

    @ActivityScope
    @Provides
    public List<AddressBean> provideAddressBeans() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    public AddressQuickAdapter prodvideAddressQuickAdapter(
            List<AddressBean> addressBeans, Application application) {
        AddressQuickAdapter addressQuickAdapter = new AddressQuickAdapter(addressBeans);
        return addressQuickAdapter;

    }

}