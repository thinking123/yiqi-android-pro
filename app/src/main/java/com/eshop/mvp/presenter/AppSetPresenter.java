package com.eshop.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.eshop.mvp.contract.AppSetContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.utils.RxUtils;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/14 上午10:43
 * @Package com.eshop.mvp.presenter
 **/
@ActivityScope
public class AppSetPresenter extends BasePresenter<IModel, AppSetContract.View> {

    @Inject
    UserModel userModel;

    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    public AppSetPresenter(AppSetContract.View rootView) {
        super(rootView);
    }

    public void updateUserPassword(String password) {
        userModel.updatePassword(password)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) mRootView.updatePasswordSuccess();
                    }
                });
    }

    public void updateInfo(String username, Integer sex) {
        userModel.updateInfo(username, sex)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) mRootView.updateUsernameSuccess();
                    }
                });
    }

    public void userInfo() {
        userModel.getUserInfo()
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
}
