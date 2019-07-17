package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.eshop.mvp.contract.CityContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/30/2019 00:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class CityPresenter extends BasePresenter<CityContract.Model, CityContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CityPresenter(CityContract.Model model, CityContract.View rootView) {
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

}
