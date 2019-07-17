package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.CompanyBean;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.eshop.mvp.contract.HomeContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/14/2019 08:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
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

    public void getCats() {
        mModel.getCats()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<CatBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<CatBean>> response) {
                        if (response.isSuccess())
                            mRootView.getCatBeanList(response.getData());
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }

    public void getCats(int parentid) {
        mModel.getCats(parentid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<CatBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<CatBean>> response) {
                        if (response.isSuccess())
                            mRootView.getCatBeanList(response.getData());
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }

    public void getAbout() {
        mModel.getAbout()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<CompanyBean>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<CompanyBean> response) {
                        if (response.isSuccess())
                            mRootView.getAboutResult(response.getData());
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }
}
