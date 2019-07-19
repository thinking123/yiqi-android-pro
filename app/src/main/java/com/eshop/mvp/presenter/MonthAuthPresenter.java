package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.auth.MonthData;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.eshop.mvp.contract.MonthAuthContract;

import java.io.File;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/21/2019 22:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MonthAuthPresenter extends BasePresenter<MonthAuthContract.Model, MonthAuthContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    UserModel userModel;

    @Inject
    public MonthAuthPresenter(MonthAuthContract.Model model, MonthAuthContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 获取认证信息
     *
     * @param userId
     */
    public void getAuth(String token,String userId) {
        mModel.getAuth(token,userId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<Auth>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<Auth> response) {
                        if (response.isSuccess()) {
                            mRootView.getAuthSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取月结信息
     *
     * @param token
     */
    public void getMonthMsg(String token) {
        mModel.getMonthMsg(token)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<MonthMsg>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<MonthMsg> response) {
                        if (response.isSuccess()) {
                            mRootView.getMonthMsgSuccess(response.getData());
                        } else {
                            mRootView.getMonthMsgStatus(response.getStatus(),response.getMsg(),response.getData());
                        }
                    }
                });
    }

    /**
     * 添加月结信息
     *
     * @param token
     */
    public void monthAdd(String token, MonthData monthData) {
        mModel.monthAdd(token, monthData)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.monthAddSuccess(response.getMsg());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 编辑月结信息
     *
     * @param token
     */
    public void monthEdit(String token, MonthData monthData) {
        mModel.monthEdit(token, monthData)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.monthEditSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 月结支付
     *
     * @param token
     */
    public void monthPay(String token, String userId, String id) {
        mModel.monthPay(token, userId, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.monthPaySuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void updateUserImage(String upload_file) {
        MultipartBody.Part face = MultipartBody.Part.createFormData("file", "card_image.png", RequestBody.create(MediaType.parse("multipart/form-data"), new File(upload_file)));
        userModel.upLoadImage(face)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            mRootView.updateUserImageSuccess(stringBaseResponse.getData());
                        } else {
                            mRootView.showMessage(stringBaseResponse.getMsg());
                        }
                    }
                });
    }
}
