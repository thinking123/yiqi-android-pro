package com.eshop.di.module;

import com.eshop.R;
import com.eshop.mvp.contract.CartContract;
import com.eshop.mvp.model.CartModel;
import com.eshop.mvp.ui.adapter.AppCartSectionAdapter;
import com.eshop.mvp.ui.adapter.BrandBeanSectionAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.BrandContract;
import com.eshop.mvp.model.BrandModel;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2019 17:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class BrandModule {

    private BrandContract.View view;

    public BrandModule(BrandContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public BrandBeanSectionAdapter provideBrandBeanSectionAdapter() {
        return new BrandBeanSectionAdapter(R.layout.adapter_item_brand,R.layout.adapter_item_section_head,null);
    }



    @Provides
    @ActivityScope
    public BrandContract.View provideBaseView() {
        return view;
    }

    @Provides
    @ActivityScope
    public BrandContract.Model provideBaseModel(IRepositoryManager repositoryManager) {
        return new BrandModel(repositoryManager);
    }
}