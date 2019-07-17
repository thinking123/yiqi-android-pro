package com.eshop.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.eshop.mvp.contract.CategoryContract;
import com.eshop.mvp.model.CategoryModel;
import com.eshop.mvp.ui.adapter.CategoryLeftAdapter;
import com.eshop.mvp.ui.adapter.CategoryRightAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/13 下午4:44
 * @Package com.eshop.di.module
 **/
@Module
public class CategoryModule {
    private CategoryContract.View view;

    public CategoryModule(CategoryContract.View view) {
        this.view = view;
    }


    @FragmentScope
    @Provides
    public CategoryLeftAdapter provideCategoryLeftAdapter() {
        return new CategoryLeftAdapter();
    }


    @FragmentScope
    @Provides
    public CategoryRightAdapter provideCategoryRightAdapter() {
        return new CategoryRightAdapter();
    }

    @FragmentScope
    @Provides
    public CategoryContract.Model provideCategoryModel(IRepositoryManager repositoryManager) {
        return new CategoryModel(repositoryManager);
    }

    @FragmentScope
    @Provides
    public CategoryContract.View provideCategoryView() {
        return view;
    }

}
