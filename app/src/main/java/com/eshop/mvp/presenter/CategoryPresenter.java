package com.eshop.mvp.presenter;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.Category;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.mvp.contract.CategoryContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.category.CategoryBean;
import com.eshop.mvp.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author shijun
 * @Data 2019/1/16
 * @Package com.eshop.mvp.presenter
 **/
@FragmentScope
public class CategoryPresenter extends BasePresenter<CategoryContract.Model, CategoryContract.View> {
    private RxErrorHandler rxErrorHandler;

    @Inject
    public CategoryPresenter(CategoryContract.Model model, CategoryContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void getCategorys(int parentId) {
        mModel.getCategorys(parentId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<CatBean>>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<CatBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            if (parentId == 0) {
                                mRootView.getCategoryBeanList(listBaseResponse.getData());
                            } else {

                                mRootView.getItemCategoryBeanList(listBaseResponse.getData());
                            }
                        } else {
                            mRootView.showMessage(listBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void getAllCategorys() {
        mModel.getAllCategorys()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<Category>>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<Category>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {

                                mRootView.getAllCategoryList(listBaseResponse.getData());

                        } else {
                            mRootView.showMessage(listBaseResponse.getMsg());
                        }
                    }
                });
    }
}
