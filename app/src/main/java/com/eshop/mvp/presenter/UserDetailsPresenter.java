package com.eshop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.eshop.mvp.contract.UserDetailsContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.http.entity.product.Product;
import com.eshop.mvp.model.ProductModel;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/7/3 下午2:08
 * @Package com.eshop.mvp.presenter
 **/
@ActivityScope
public class UserDetailsPresenter extends BasePresenter<IModel, UserDetailsContract.View> {

    @Inject
    RxErrorHandler rxErrorHandler;
    @Inject
    UserModel userModel;

    @Inject
    ProductModel productModel;


    @Inject
    public UserDetailsPresenter(UserDetailsContract.View rootView) {
        super(rootView);
    }

    public void getUserInfo(Integer userId) {
        userModel.getUserInfo(userId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserBean> userBeanBaseResponse) {
                        if (userBeanBaseResponse.isSuccess())
                            mRootView.userInfoSuccess(userBeanBaseResponse.getData());
                        else mRootView.showMessage(userBeanBaseResponse.getMsg());
                    }
                });
    }

    public void getProductList(Integer userId) {

        productModel.list(userId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Product>>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Product>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.productList(listBaseResponse.getData());
                        } else {
                            mRootView.showMessage(listBaseResponse.getMsg());
                        }
                    }
                });
    }
}
