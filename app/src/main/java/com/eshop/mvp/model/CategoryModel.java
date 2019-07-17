package com.eshop.mvp.model;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.Category;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.eshop.mvp.contract.CategoryContract;
import com.eshop.mvp.http.api.service.CategoryService;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.category.CategoryBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author shijun
 * @Data 2019/1/16
 * @Package com.eshop.mvp.model
 **/
public class CategoryModel extends BaseModel implements CategoryContract.Model {

    public CategoryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<MyBaseResponse<List<CatBean>>> getCategorys(int parentId) {
        return mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getCats(parentId);
    }

    @Override
    public Observable<MyBaseResponse<List<Category>>> getAllCategorys() {
        return mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getAllCats();
    }

}
