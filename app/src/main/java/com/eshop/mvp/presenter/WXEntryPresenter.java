package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.mvp.http.entity.AppData;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.WxBaseResponse;
import com.eshop.mvp.http.entity.WxUserInfoResponse;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.eshop.mvp.contract.WXEntryContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2019 10:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WXEntryPresenter extends BasePresenter<WXEntryContract.Model, WXEntryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WXEntryPresenter(WXEntryContract.Model model, WXEntryContract.View rootView) {
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

    public void wxGetToken(String code) {
        mModel.wxGetToken(AppData.WECHAT_APPID,AppData.WECHAT_APP_SECRET,code,"authorization_code")
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<WxBaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(WxBaseResponse response) {
                        if (response.isSuccess()) {
                            mRootView.getTokenResult(response);
                        } else {
                            mRootView.showMessage(response.errmsg);
                        }
                    }
                });
    }

    public void wxRefreshToken(String refresh_tokn) {
        mModel.wxRefreshToken(AppData.WECHAT_APPID,"refresh_token",refresh_tokn)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<WxBaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(WxBaseResponse response) {
                        if (response.isSuccess()) {
                            mRootView.refreshTokenResult(response);
                        } else {
                            mRootView.showMessage(response.errmsg);
                        }
                    }
                });
    }

    public void wxUserInfo(String access_token, String openid) {
        mModel.wxUserInfo(access_token,openid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<WxUserInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(WxUserInfoResponse response) {
                        if (response.isSuccess()) {
                            mRootView.userInfoResult(response);
                        } else {
                            mRootView.showMessage(response.errmsg);
                        }
                    }
                });
    }
}
