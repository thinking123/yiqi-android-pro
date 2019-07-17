package com.eshop.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.eshop.mvp.ui.activity.product.CatProductActivity;
import com.eshop.mvp.ui.activity.product.SearchActivity;
import com.jess.arms.di.component.AppComponent;

import com.eshop.di.module.CatProductModule;
import com.eshop.mvp.contract.CatProductContract;

import com.jess.arms.di.scope.FragmentScope;
import com.eshop.mvp.ui.fragment.CatProductFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 16:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CatProductModule.class, dependencies = AppComponent.class)
public interface CatProductComponent {
    void inject(CatProductFragment fragment);
    void inject(CatProductActivity activity);
    void inject(SearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CatProductComponent.Builder view(CatProductContract.View view);

        CatProductComponent.Builder appComponent(AppComponent appComponent);

        CatProductComponent build();
    }
}