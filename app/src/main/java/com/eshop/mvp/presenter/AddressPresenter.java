package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.app.base.BaseApp;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.eshop.mvp.contract.AddressContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/28/2019 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddressPresenter extends BasePresenter<AddressContract.Model, AddressContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private String token;

    @Inject
    public AddressPresenter(AddressContract.Model model, AddressContract.View rootView) {
        super(model, rootView);
        token = BaseApp.loginBean.getToken();
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
     * 获取省列表
     */
    public void selectPro() {
        mModel.selectPro()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<CityBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<CityBean>> response) {
                        if (response.isSuccess()) {
                            mRootView.selectProSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取城市列表
     * @param cityCode
     */
    public void selectCity(String cityCode) {
        mModel.selectCity(cityCode)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<CityBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<CityBean>> response) {
                        if (response.isSuccess()) {
                            mRootView.selectCitySuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取收货地址列表
     */
    public void get(String token,String userId) {
        mModel.get(token,userId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<AddressBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<AddressBean>> response) {
                        if (response.isSuccess()) {
                            mRootView.getSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void getById(String token,String userId) {
        mModel.getById(token,userId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<AddressBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<AddressBean>> response) {
                        if (response.isSuccess()) {
                            mRootView.getSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 添加收货地址
     * @param userId
     * @param address
     * @param receiveUserName
     * @param receivePhone
     */
    public void add(
            String userId,
            String address,
            String receiveUserName,
            String receivePhone
    ) {
        mModel.add(token,userId, address, receiveUserName, receivePhone)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.addSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void edit(
            String token, String id, String address, String receiveUserName, String receivePhone, String isDefault
    ) {
        mModel.edit(token, id, address, receiveUserName, receivePhone, isDefault)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.addSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 删除收货地址
     * @param id
     */
    public void del(
            String id
    ) {
        mModel.del(token,id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.delSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 设置缺省收货地址
     * @param userId
     * @param id
     */
    public void setDefault(
            String userId,
            String id
    ) {
        mModel.setDefault(token,userId, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.setDefaultSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }
}
