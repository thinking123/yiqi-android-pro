package com.eshop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.eshop.mvp.contract.SearchProductContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.product.Product;
import com.eshop.mvp.model.ProductModel;
import com.eshop.mvp.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/21 下午5:25
 * @Package com.eshop.mvp.presenter
 **/
@ActivityScope
public class SearchProductPresenter extends BasePresenter<IModel, SearchProductContract.View> {
    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    ProductModel productModel;

    @Inject
    public SearchProductPresenter(SearchProductContract.View rootView) {
        super(rootView);
    }

    public void searchProduct(String keyword,
                              Integer categoryId,
                              String orderBy) {
        productModel.searchProductByKeyWordOrCategoryId(keyword, categoryId, 0, 0, orderBy)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Product>>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Product>> listBaseResponse) {
                        if (listBaseResponse.isSuccess())
                            mRootView.productListSuccess(listBaseResponse.getData());
                        else mRootView.showMessage(listBaseResponse.getMsg());
                    }
                });
    }
}
