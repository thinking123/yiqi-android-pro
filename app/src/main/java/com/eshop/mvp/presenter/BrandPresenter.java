package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.BrandBean;
import com.eshop.mvp.http.entity.home.BrandBeanList;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.eshop.mvp.contract.BrandContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2019 17:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BrandPresenter extends BasePresenter<BrandContract.Model, BrandContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BrandPresenter(BrandContract.Model model, BrandContract.View rootView) {
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

    public void getBrand() {
        mModel.getBrand()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<BrandBeanList>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<BrandBeanList> response) {
                        if (response.isSuccess())
                            mRootView.getBrandSuccess(response.getData());
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }
}
