package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.Category;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.category.CategoryBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author shijun
 * @Data 2019/1/16
 * @Package com.eshop.mvp.contract
 **/
public interface CategoryContract {

    interface Model extends IModel {
        Observable<MyBaseResponse<List<CatBean>>> getCategorys(int parentId);

        Observable<MyBaseResponse<List<Category>>> getAllCategorys();
    }

    interface View extends IView {
        void getCategoryBeanList(List<CatBean> data);

        void getItemCategoryBeanList(List<CatBean> data);

        void getAllCategoryList(List<Category> data);
    }
}
